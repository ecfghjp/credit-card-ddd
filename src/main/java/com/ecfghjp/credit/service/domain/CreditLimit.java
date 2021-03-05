package com.ecfghjp.credit.service.domain;

import java.math.BigDecimal;

public class CreditLimit {
	private BigDecimal limit;
	
	public CreditLimit() {
	}

	public CreditLimit(BigDecimal limit) {
		// TODO Auto-generated constructor stub
		this.limit = limit;
	}

	public BigDecimal getLimit() {
		// TODO Auto-generated method stub
		return limit;
	}

}
