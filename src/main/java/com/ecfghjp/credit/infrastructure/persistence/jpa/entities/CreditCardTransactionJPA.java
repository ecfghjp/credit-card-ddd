package com.ecfghjp.credit.infrastructure.persistence.jpa.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class CreditCardTransactionJPA {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String transactionId;

	private String creditCardNumber;
	private BigDecimal transactionAmount;
	private LocalDateTime transactionDate;
	private String transactionPurpose;;
	private BigDecimal creditBeforeTransaction;
	private BigDecimal creditAfterTransaction;
	private String creditCardTransactionStatus;

	public CreditCardTransactionJPA(String creditCardNumber, BigDecimal transactionAmount,
			LocalDateTime transactionDate, String transactionPurpose, BigDecimal creditBeforeTransaction,
			BigDecimal creditAfterTransaction) {
		this.creditCardNumber = creditCardNumber;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
		this.transactionPurpose = transactionPurpose;
		this.creditBeforeTransaction = creditBeforeTransaction;
		this.creditAfterTransaction = creditAfterTransaction;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public CreditCardTransactionJPA() {
	}

	public String getTransactionId() {
		return transactionId;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public String getTransactionPurpose() {
		return transactionPurpose;
	}

	public BigDecimal getCreditBeforeTransaction() {
		return creditBeforeTransaction;
	}

	public BigDecimal getCreditAfterTransaction() {
		return creditAfterTransaction;
	}

	public String getCreditCardTransactionStatus() {
		return creditCardTransactionStatus;
	}

	public void setCreditCardTransactionStatus(String creditCardTransactionStatus) {
		this.creditCardTransactionStatus = creditCardTransactionStatus;
	}

}
