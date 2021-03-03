package com.ecfghjp.credit.service.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.ecfghjp.credit.exception.InvalidOperationException;
import com.ecfghjp.credit.exception.NotEnoughCreditException;

public class CreditCardOperationAggregateTest {

	// throw an exception
	@Test
	public void cannot_withdraw_when_limit_notassigned() {
		CreditLimit creditLimit = new CreditLimit();
		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
		CreditCardOperationAggregate creditCardOperationAggregate = new CreditCardOperationAggregate.Builder(creditCard).build();

		assertThrows(InvalidOperationException.class, () -> {
			creditCardOperationAggregate.transaction(new BigDecimal(100),TransactionPurpose.PAYMENT);
		});

	}
	
	
	@Test
	public void cannot_withdraw_if_withdrawal_in_last_hour() {
		//setup
		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditLimit creditLimit = new CreditLimit(new BigDecimal(1000));
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
		
		CreditCardTransaction creditCardTransaction = new CreditCardTransaction(new BigDecimal(100),TransactionPurpose.PAYMENT, LocalDateTime.now(), new BigDecimal(1000), new BigDecimal(500));
		
		//operation
		CreditCardOperationAggregate creditCardOperationAggregate = new CreditCardOperationAggregate.Builder(creditCard)
									.withPreviousTransaction(creditCardTransaction)
									.build();
		assertThrows(InvalidOperationException.class, () -> {
			creditCardOperationAggregate.transaction(new BigDecimal(100),TransactionPurpose.PAYMENT);
		});
	}

	@Test
	public void cannot_withdraw_if_not_enough_credit() {
		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditLimit creditLimit = new CreditLimit(new BigDecimal(1000));
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
		
		CreditCardOperationAggregate creditCardOperationAggregate = new CreditCardOperationAggregate.Builder(creditCard).build();
		assertThrows(NotEnoughCreditException.class, () -> {
			creditCardOperationAggregate.transaction(new BigDecimal(1500),TransactionPurpose.PAYMENT);
		});
	}
	
	@Test
	public void cannot_withdraw_if_transaction_amount_0() {
		
		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditLimit creditLimit = new CreditLimit(new BigDecimal(1000));
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
		
		
		CreditCardOperationAggregate creditCardOperationAggregate = new CreditCardOperationAggregate.Builder(creditCard).build();
		assertThrows(InvalidOperationException.class, () -> {
			creditCardOperationAggregate.transaction(new BigDecimal(0),TransactionPurpose.PAYMENT);
		});
	}
	
	//withdrawal should return transaction id
	@Test
	public void when_valid_transaction() {

		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditLimit creditLimit = new CreditLimit(new BigDecimal(1000));
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
		
		CreditCardOperationAggregate creditCardOperationAggregate = new CreditCardOperationAggregate.Builder(creditCard).build();

		creditCardOperationAggregate.transaction(new BigDecimal(500),TransactionPurpose.PAYMENT);

		assertThat(creditCardOperationAggregate.getCurrentCreditCardTransaction().creditLeftAfterTransaction()).isEqualTo(new BigDecimal(500));
	}

	@Test
	public void when_withdraw_then_sucess_values_before_after_valid() {
		
		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditLimit creditLimit = new CreditLimit(new BigDecimal(1000));
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);

		CreditCardOperationAggregate creditCardOperationAggregate = new CreditCardOperationAggregate.Builder(creditCard).build();

		creditCardOperationAggregate.transaction(new BigDecimal(500),TransactionPurpose.PAYMENT);

		assertThat(creditCardOperationAggregate.getCurrentCreditCardTransaction().creditLeftAfterTransaction()).isEqualTo(new BigDecimal(500));
		assertThat(creditCardOperationAggregate.getCreditCard().getCreditLimit().getLimit()).isEqualTo(new BigDecimal(1000));
		//since just adding one transaction to the List but its a list of previous transactions with date time etc
		assertThat(creditCardOperationAggregate.getCurrentCreditCardTransaction().getTransactionAmount()).isEqualTo(new BigDecimal(500));
		assertThat(creditCardOperationAggregate.getCurrentCreditCardTransaction().creditForUseBeforeTransaction()).isEqualTo(new BigDecimal(1000));
		assertThat(creditCardOperationAggregate.getCurrentCreditCardTransaction().creditLeftAfterTransaction()).isEqualTo(new BigDecimal(500));
	}
	
	@Test
	public void when_withdraw_then_sucess_within_one_hour() {

		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditLimit creditLimit = new CreditLimit(new BigDecimal(1000));
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
		
		//creditTransaction one
		CreditCardTransaction previousCreditCardTransaction = new CreditCardTransaction(new BigDecimal(1000),TransactionPurpose.PAYMENT, LocalDateTime.now().minusHours(2), new BigDecimal(1000), new BigDecimal(500));
		
		CreditCardOperationAggregate creditCardOperationAggregate = new CreditCardOperationAggregate.Builder(creditCard)
												.withPreviousTransaction(previousCreditCardTransaction)
												.build();

		creditCardOperationAggregate.transaction(new BigDecimal(100),TransactionPurpose.PAYMENT);

		assertThat(creditCardOperationAggregate.getCurrentCreditCardTransaction().creditLeftAfterTransaction()).isEqualTo(new BigDecimal(400));
		assertThat(creditCardOperationAggregate.getCreditCard().getCreditLimit().getLimit()).isEqualTo(new BigDecimal(1000));
		//since just adding one transaction to the List but its a list of previous transactions with date time etc
		assertThat(creditCardOperationAggregate.getCurrentCreditCardTransaction().getTransactionAmount()).isEqualTo(new BigDecimal(100));
		assertThat(creditCardOperationAggregate.getCurrentCreditCardTransaction().creditForUseBeforeTransaction()).isEqualTo(new BigDecimal(500));
		assertThat(creditCardOperationAggregate.getCurrentCreditCardTransaction().creditLeftAfterTransaction()).isEqualTo(new BigDecimal(400));
	}

	//by repaying the money we are updating the transaction as well
	//should it be a seperate class
	@Test
	public void repay_money() {
		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditLimit creditLimit = new CreditLimit(new BigDecimal(1000));
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
		
		CreditCardTransaction creditCardTransaction = new CreditCardTransaction(new BigDecimal(1000),TransactionPurpose.REPAYMENT, LocalDateTime.now().minusHours(2), new BigDecimal(1000), new BigDecimal(500));
		CreditCardOperationAggregate creditCardOperationAggregate = new CreditCardOperationAggregate.Builder(creditCard)
									.withPreviousTransaction(creditCardTransaction)
									.build();
		//when repay then debt should become 0
		creditCardOperationAggregate.transaction(new BigDecimal(500),TransactionPurpose.REPAYMENT);
		assertThat(creditCardOperationAggregate.getCreditCard().getCreditLimit().getLimit()).isEqualTo(new BigDecimal(1000));
		assertThat(creditCardOperationAggregate.getCurrentCreditCardTransaction().creditLeftAfterTransaction()).isEqualTo(new BigDecimal(1000));


		// shoulkd we have setters after extracting fro database or
		// should be have entities and do it via builder??? Imporrtant about VOs

	}
	
	@Test
	public void repay_money_no_check_one_hour() {
		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditLimit creditLimit = new CreditLimit(new BigDecimal(1000));
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
		
		CreditCardTransaction previousTransaction = new CreditCardTransaction(new BigDecimal(1000),TransactionPurpose.PAYMENT, LocalDateTime.now(), new BigDecimal(1000), new BigDecimal(500));
		CreditCardOperationAggregate creditCardOperationAggregate = new CreditCardOperationAggregate.Builder(creditCard)
									.withPreviousTransaction(previousTransaction)
									.build();
		//when repay then debt should become 0
		creditCardOperationAggregate.transaction(new BigDecimal(500),TransactionPurpose.REPAYMENT);
		assertThat(creditCardOperationAggregate.getCreditCard().getCreditLimit().getLimit()).isEqualTo(new BigDecimal(1000));
		assertThat(creditCardOperationAggregate.getCurrentCreditCardTransaction().creditLeftAfterTransaction()).isEqualTo(new BigDecimal(1000));


		// shoulkd we have setters after extracting fro database or
		// should be have entities and do it via builder??? Imporrtant about VOs

	}
	
//	@Test
//	public void repay_money_no_repayment_if_no_transaction() {
//		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
//		CreditLimit creditLimit = new CreditLimit(new BigDecimal(1000));
//		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
//		
//		CreditCardTransaction previousTransaction = new CreditCardTransaction(new BigDecimal(1000),TransactionPurpose.PAYMENT, LocalDateTime.now(), new BigDecimal(1000), new BigDecimal(500));
//		CreditCardOperationAggregate creditCardOperationAggregate = new CreditCardOperationAggregate.Builder(creditCard)
//									.withPreviousTransaction(null)
//									.build();
//		//when repay then debt should become 0
//		creditCardOperationAggregate.transaction(new BigDecimal(500),TransactionPurpose.REPAYMENT);
//		assertThrows(InvalidOperationException.class, () -> {
//			creditCardOperationAggregate.transaction(new BigDecimal(1500),TransactionPurpose.PAYMENT);
//		});
//
//		// shoulkd we have setters after extracting fro database or
//		// should be have entities and do it via builder??? Imporrtant about VOs
//
//	}

}
