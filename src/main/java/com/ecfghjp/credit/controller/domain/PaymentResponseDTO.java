package com.ecfghjp.credit.controller.domain;

import java.math.BigDecimal;

public class PaymentResponseDTO {
	private String transactionId;
	private BigDecimal transactionAmount;
	private BigDecimal remainingCreditAmount;

	public PaymentResponseDTO() {
	}

	public PaymentResponseDTO(String transactionId, BigDecimal transactionAmount, BigDecimal remainingCreditAmount) {
		this.transactionId = transactionId;
		this.transactionAmount = transactionAmount;
		this.remainingCreditAmount = remainingCreditAmount;
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

}
