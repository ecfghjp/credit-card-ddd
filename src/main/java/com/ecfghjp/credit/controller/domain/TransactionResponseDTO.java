package com.ecfghjp.credit.controller.domain;

import java.math.BigDecimal;

public class TransactionResponseDTO {
	private String transactionId;
	private BigDecimal transactionAmount;
	private BigDecimal remainingCreditAmount;
	private String transactionDate;

	public TransactionResponseDTO() {
	}

	public TransactionResponseDTO(String transactionId, BigDecimal transactionAmount, BigDecimal remainingCreditAmount,
			String transactionDate) {
		this.transactionId = transactionId;
		this.transactionAmount = transactionAmount;
		this.remainingCreditAmount = remainingCreditAmount;
		this.transactionDate = transactionDate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public BigDecimal getRemainingCreditAmount() {
		return remainingCreditAmount;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

}
