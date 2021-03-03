package com.ecfghjp.credit.infrastructure.persistence.jpa.entities;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class CreditCardJPA {
	
	@Id
	@NotNull
	String creditCardNumber;
	@NotNull
	BigDecimal maxLimit;

	public CreditCardJPA() {
	}

	public CreditCardJPA(String creditCardNumber, BigDecimal limit) {
		this.creditCardNumber = creditCardNumber;
		this.maxLimit = limit;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public BigDecimal getMaxLimit() {
		return maxLimit;
	}

}
