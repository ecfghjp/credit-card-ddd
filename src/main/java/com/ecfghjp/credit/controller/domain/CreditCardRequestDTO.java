package com.ecfghjp.credit.controller.domain;

import java.math.BigDecimal;

public class CreditCardRequestDTO {

	private BigDecimal limitAssigned;
	private String creditCardNumber;

	public CreditCardRequestDTO() {
	}

	public CreditCardRequestDTO(String creditCardNumber,BigDecimal limitAssigned) {
		this.limitAssigned = limitAssigned;
		this.creditCardNumber = creditCardNumber;
	}

	public BigDecimal getLimitAssigned() {
		return limitAssigned;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

}
