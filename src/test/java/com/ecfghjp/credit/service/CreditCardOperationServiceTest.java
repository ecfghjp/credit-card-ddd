package com.ecfghjp.credit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecfghjp.credit.controller.domain.PaymentRequestDTO;
import com.ecfghjp.credit.exception.InvalidOperationException;
import com.ecfghjp.credit.exception.NotEnoughCreditException;
import com.ecfghjp.credit.service.domain.CreditCard;
import com.ecfghjp.credit.service.domain.CreditCardNumber;
import com.ecfghjp.credit.service.domain.CreditCardTransaction;
import com.ecfghjp.credit.service.domain.CreditLimit;
import com.ecfghjp.credit.service.domain.TransactionId;
import com.ecfghjp.credit.service.domain.TransactionPurpose;
import com.ecfghjp.credit.service.repository.CreditCardRepository;
import com.ecfghjp.credit.service.repository.CreditCardTransactionsRepository;

@ExtendWith(MockitoExtension.class)
class CreditCardOperationServiceTest {
	
	private CreditCardOperationsService creditService;
	
	@Mock
	private CreditCardTransactionsRepository creditCardTransactionsRepository;
	
	
	@Mock
	private CreditCardRepository creditCardRepository;
	
	
	
	@BeforeEach
	public void setUp() throws Exception{
		creditService = new CreditCardOperationsService(creditCardTransactionsRepository,creditCardRepository);
		
	}

	@Test
	void when_payment_payment_success() {
		//should return a transaction id
		
		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditLimit creditLimit = new CreditLimit(new BigDecimal(1000));
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
		
		CreditCardTransaction lastTransaction = new CreditCardTransaction(new BigDecimal(1000),TransactionPurpose.PAYMENT, LocalDateTime.now().minusHours(2), new BigDecimal(1000), new BigDecimal(500));
		
		
		when(creditCardTransactionsRepository.findLastCreditCardTransaction(any())).thenReturn(lastTransaction);
		when(creditCardTransactionsRepository.persistTransaction(any(),any())).thenReturn(new TransactionId());
		when(creditCardRepository.findCreditCardDetails(any())).thenReturn(creditCard);


		PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO("100100100100",new BigDecimal(500),TransactionPurpose.PAYMENT);
		
		CreditCardTransaction creditCardTransaction = creditService.payment(paymentRequestDTO);
		assertThat(creditCardTransaction.getTransactionAmount()).isEqualTo(new BigDecimal(500));
		assertThat(creditCardTransaction.getTransactionPurpose()).isEqualTo(TransactionPurpose.PAYMENT);
		//Assertions.assertThat(creditCardTransaction.getTransactionStatus()).isEqualTo(CreditCardTransactionStatus.SUCCESS);


	}
	
	
	@Test
	void when_payment_payment_first_time_success() {
		//should return a transaction id
		
		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditLimit creditLimit = new CreditLimit(new BigDecimal(1000));
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
		
		
		
		when(creditCardTransactionsRepository.findLastCreditCardTransaction(any())).thenReturn(null);
		when(creditCardTransactionsRepository.persistTransaction(any(),any())).thenReturn(new TransactionId());
		when(creditCardRepository.findCreditCardDetails(any())).thenReturn(creditCard);


		PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO("100100100100",new BigDecimal(500),TransactionPurpose.PAYMENT);
		
		CreditCardTransaction creditCardTransaction = creditService.payment(paymentRequestDTO);
		assertThat(creditCardTransaction.getTransactionAmount()).isEqualTo(new BigDecimal(500));
		assertThat(creditCardTransaction.getTransactionPurpose()).isEqualTo(TransactionPurpose.PAYMENT);
		//Assertions.assertThat(creditCardTransaction.getTransactionStatus()).isEqualTo(CreditCardTransactionStatus.SUCCESS);


	}
	
	@Test
	void when_repayment_payment_success() {
		//should return a transaction id
		
		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditLimit creditLimit = new CreditLimit(new BigDecimal(1000));
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
		
		CreditCardTransaction lastTransaction = new CreditCardTransaction(new BigDecimal(1000),TransactionPurpose.PAYMENT, LocalDateTime.now().minusHours(2), new BigDecimal(1000), new BigDecimal(500));
		
		
		when(creditCardTransactionsRepository.findLastCreditCardTransaction(any())).thenReturn(lastTransaction);
		when(creditCardTransactionsRepository.persistTransaction(any(),any())).thenReturn(new TransactionId());
		when(creditCardRepository.findCreditCardDetails(any())).thenReturn(creditCard);


		PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO("100100100100",new BigDecimal(500),TransactionPurpose.REPAYMENT);
		
		CreditCardTransaction creditCardTransaction = creditService.payment(paymentRequestDTO);
		assertThat(creditCardTransaction.getTransactionAmount()).isEqualTo(new BigDecimal(500));
		assertThat(creditCardTransaction.getTransactionPurpose()).isEqualTo(TransactionPurpose.REPAYMENT);
		assertThat(creditCardTransaction.creditForUseBeforeTransaction()).isEqualTo(new BigDecimal(500));
		assertThat(creditCardTransaction.creditLeftAfterTransaction()).isEqualTo(new BigDecimal(1000));


		//Assertions.assertThat(creditCardTransaction.getTransactionStatus()).isEqualTo(CreditCardTransactionStatus.SUCCESS);


	}
	
	@Test
	void when_payment_credit_not_assigned_exception() {
		//should throw an exception
		//since we have already tested domain objects it should be ok
		//check if the code is throwing exception
		
		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditLimit creditLimit = new CreditLimit();
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
		
		CreditCardTransaction lastTransaction = new CreditCardTransaction(new BigDecimal(1000),TransactionPurpose.PAYMENT, LocalDateTime.now().minusHours(2), new BigDecimal(1000), new BigDecimal(500));
		
		
		when(creditCardTransactionsRepository.findLastCreditCardTransaction(any())).thenReturn(lastTransaction);
		when(creditCardRepository.findCreditCardDetails(any())).thenReturn(creditCard);


		PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO("100100100100",new BigDecimal(500),TransactionPurpose.PAYMENT);
		
		Assertions.assertThrows(InvalidOperationException.class, () -> {
			creditService.payment(paymentRequestDTO);
		});
		
		
	}
	
	
	@Test
	void when_payment_more_than_remaining_credit_failure() {
		//should throw an exception
		//since we have already tested domain objects it should be ok
		//check if the code is throwing exception
		
		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditLimit creditLimit = new CreditLimit(new BigDecimal(1000));
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
		
		CreditCardTransaction lastTransaction = new CreditCardTransaction(new BigDecimal(1000),TransactionPurpose.PAYMENT, LocalDateTime.now().minusHours(2), new BigDecimal(1000), new BigDecimal(500));
		
		
		when(creditCardTransactionsRepository.findLastCreditCardTransaction(any())).thenReturn(lastTransaction);
		when(creditCardRepository.findCreditCardDetails(any())).thenReturn(creditCard);


		PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO("100100100100",new BigDecimal(1200),TransactionPurpose.PAYMENT);
		
		Assertions.assertThrows(NotEnoughCreditException.class, () -> {
			creditService.payment(paymentRequestDTO);
		});
		
		
	}
	
	@Test
	void when_payment_payment_last_hour() {
		//should throw an exception
		//since we have already tested domain objects it should be ok
		//check if the code is throwing exception
		
		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100100");
		CreditLimit creditLimit = new CreditLimit(new BigDecimal(1000));
		CreditCard creditCard = new CreditCard(creditCardNumber, creditLimit);
		
		CreditCardTransaction lastTransaction = new CreditCardTransaction(new BigDecimal(1000),TransactionPurpose.PAYMENT, LocalDateTime.now(), new BigDecimal(1000), new BigDecimal(500));
		
		
		when(creditCardTransactionsRepository.findLastCreditCardTransaction(any())).thenReturn(lastTransaction);
		when(creditCardRepository.findCreditCardDetails(any())).thenReturn(creditCard);


		PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO("100100100100",new BigDecimal(500),TransactionPurpose.PAYMENT);
		
		Assertions.assertThrows(InvalidOperationException.class, () -> {
			creditService.payment(paymentRequestDTO);
		});
	}
}
