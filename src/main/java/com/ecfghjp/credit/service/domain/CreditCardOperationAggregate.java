package com.ecfghjp.credit.service.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.ecfghjp.credit.exception.InvalidOperationException;
import com.ecfghjp.credit.exception.NotEnoughCreditException;

//Root Aggregate for creditCardTransactions
public class CreditCardOperationAggregate {
	// should be persisted in withdrawal subdomain
	private CreditCard creditCard;
	// just get the credit details for the operation like the credit card number and
	// get
	// transaction ifds for that credit card number
	// credit left after transction, should be persisted or
	// can be persisted along with transactions as well
	// these should be persisted
	private CreditCardTransaction previousTransaction;
	// this transaction should be saved in database
	private  CreditCardTransaction currentCreditCardTransaction;
	//to generate an id repository should be called, shoud that be called from domain object

	private CreditCardOperationAggregate() {
	}

	public static class Builder {
		private CreditCard creditCard;
		private CreditCardTransaction previousTransaction;

		public Builder(CreditCard creditCard) {
			this.creditCard = creditCard;
			// should raise an event to the credit card for assigning number to credit card
		}

		public Builder withPreviousTransaction(CreditCardTransaction previousTransaction) {
			this.previousTransaction = previousTransaction;
			return this;
		}

		public CreditCardOperationAggregate build() {
			CreditCardOperationAggregate creditCardCardOperationAggregate = new CreditCardOperationAggregate();
			creditCardCardOperationAggregate.creditCard = this.creditCard;
			creditCardCardOperationAggregate.previousTransaction = this.previousTransaction;
			return creditCardCardOperationAggregate;
		}

	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public CreditCardTransaction getLastTransaction() {
		return previousTransaction;
	}

	public CreditCardTransaction getCurrentCreditCardTransaction() {
		return currentCreditCardTransaction;
	}
	
	public CreditCardTransaction transaction(BigDecimal transactionAmount, TransactionPurpose transactionPurpose) {
		LocalDateTime transactionDateForAValidTransaction = checkTransactionValidandReturnTransactionDate(transactionAmount,transactionPurpose);
		
		BigDecimal balanceCredit = remainingCreditAfterLastTransaction()
				.subtract(transactionAmount);
		// add to transaction
		// save to transaction database
		// create transaction and then mark it as successfull
		// should be created in a factory because that will take care of scenarios
		//CreditCardTransaction creditCardTransaction;
		if(TransactionPurpose.PAYMENT.equals(transactionPurpose)) {
		 currentCreditCardTransaction = new CreditCardTransaction(transactionAmount,
				 transactionPurpose, transactionDateForAValidTransaction,
				remainingCreditAfterLastTransaction(), balanceCredit);
		}else {
			currentCreditCardTransaction = new CreditCardTransaction(transactionAmount, TransactionPurpose.REPAYMENT,
					LocalDateTime.now(), previousTransaction.creditLeftAfterTransaction(),
					adjustCreditAfterRepayment(previousTransaction, transactionAmount));
		}
		return currentCreditCardTransaction;
	}
	
	private BigDecimal adjustCreditAfterRepayment(CreditCardTransaction clearDebt, BigDecimal transactionAmount) {
		return clearDebt.creditLeftAfterTransaction().add(transactionAmount);
	}
	
	//should this validation be in a separate class
	public LocalDateTime checkTransactionValidandReturnTransactionDate(BigDecimal transactionAmount,TransactionPurpose transactionPurpose) {
		if (null == getCreditCard().getCreditLimit().getLimit()) {
			throw new InvalidOperationException("Credit Limit Not Assigned");
		}

		if (transactionAmount.compareTo(getCreditCard().getCreditLimit().getLimit()) > 0) {
			throw new NotEnoughCreditException();

		}

		if (transactionAmount.equals(new BigDecimal(0))) {
			throw new InvalidOperationException("Withdrawal amount should be greater than 0");
		}
		// if withdrawan in last hour
		LocalDateTime now = LocalDateTime.now();
		if (null != previousTransaction && transactionPurpose.equals(TransactionPurpose.PAYMENT)) {
			long timeDifferenceFromLastTransaction = getLastTransactionDate().until(now, ChronoUnit.HOURS);

			if ((timeDifferenceFromLastTransaction < 1)) {
				throw new InvalidOperationException("Withdrawan within an hour");
			}
		}
		return now;
	}

	private LocalDateTime getLastTransactionDate() {
		return previousTransaction.getTransactionDate();
	}
	

	public BigDecimal remainingCreditAfterLastTransaction() {
		return null != previousTransaction ? previousTransaction.creditLeftAfterTransaction()
				: getCreditCard().getCreditLimit().getLimit();
	}
}
