package com.ecfghjp.credit.controller.domain;

import java.math.BigDecimal;

import com.ecfghjp.credit.service.domain.TransactionPurpose;

public class TransactionRequestDTO {

	private String creditCardNumber;
	private BigDecimal transactionAmount;
	private TransactionPurpose transactionPurpose;

	public TransactionRequestDTO(String creditCardNumber, BigDecimal transactionAmount,
			TransactionPurpose transactionPurpose) {
		this.creditCardNumber = creditCardNumber;
		this.transactionAmount = transactionAmount;
		this.transactionPurpose = transactionPurpose;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public TransactionPurpose getTransactionPurpose() {
		return transactionPurpose;
	}
}
