package com.ecfghjp.credit.service.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//should be persisted , and should have an id
//should only be persisted once the credit has been debited
//repo should be called from here by DDD principles (DODI)
//aggregate for transaction
//will also need to be stored along with the transaction customer account details
//create a transaction repository and call save //retrieve from the service
public class CreditCardTransaction {
	// change to Transaction amount to follow Ubiqutous language
	// should we be calling Transaction repository from here?
	// id should noly be generated if saveed
	private TransactionId transactionId;
	private BigDecimal transactionAmount;
	private LocalDateTime transactionDate;
	private TransactionPurpose transactionPurpose;;
	private BigDecimal creditBeforeTransaction;
	private BigDecimal creditAfterTransaction;
	private CreditCardTransactionStatus creditCardTransactionStatus;

	public CreditCardTransaction() {
	}

	public CreditCardTransaction(BigDecimal transactionAmount, TransactionPurpose transactionPurpose,
			LocalDateTime transactionDate, BigDecimal remainingCreditBeforeTransaction,
			BigDecimal remainingCreditAfterTransaction) {
		// TODO Auto-generated constructor stub
		this.transactionAmount = transactionAmount;
		this.transactionPurpose = transactionPurpose;
		this.transactionDate = transactionDate;
		this.creditBeforeTransaction = remainingCreditBeforeTransaction;
		this.creditAfterTransaction = remainingCreditAfterTransaction;
	}
	
	public void addTransactionId(TransactionId transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public BigDecimal creditForUseBeforeTransaction() {
		// TODO Auto-generated method stub
		return creditBeforeTransaction;
	}

	public BigDecimal creditLeftAfterTransaction() {
		// TODO Auto-generated method stub
		return creditAfterTransaction;
	}

	public TransactionPurpose getTransactionPurpose() {
		return transactionPurpose;
	}

	public TransactionId getTransactionId() {
		return transactionId;
	}

	public CreditCardTransactionStatus getCreditCardTransactionStatus() {
		return creditCardTransactionStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((transactionAmount == null) ? 0 : transactionAmount.hashCode());
		result = prime * result + ((transactionDate == null) ? 0 : transactionDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCardTransaction other = (CreditCardTransaction) obj;
		if (transactionAmount == null) {
			if (other.transactionAmount != null)
				return false;
		} else if (!transactionAmount.equals(other.transactionAmount))
			return false;
		if (transactionDate == null) {
			if (other.transactionDate != null)
				return false;
		} else if (!transactionDate.equals(other.transactionDate))
			return false;
		return true;
	}

	public CreditCardTransactionStatus getTransactionStatus() {
		// TODO Auto-generated method stub
		return creditCardTransactionStatus;
	}

}
