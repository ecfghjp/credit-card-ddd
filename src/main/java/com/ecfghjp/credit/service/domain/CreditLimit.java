package com.ecfghjp.credit.service.domain;

import java.math.BigDecimal;

public class CreditLimit {
	private BigDecimal creditLimit;
	
	public CreditLimit() {
	}

	public CreditLimit(BigDecimal creditLimit) {
		// TODO Auto-generated constructor stub
		this.creditLimit = creditLimit;
	}

	public BigDecimal getLimit() {
		// TODO Auto-generated method stub
		return creditLimit;
	}

}
