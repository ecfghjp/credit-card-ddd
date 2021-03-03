package com.ecfghjp.credit.service.domain;

import com.ecfghjp.credit.exception.InvalidCreditCardNumberException;

//should be getting this from the database
//and form the value object here
public class CreditCardNumber {
	
	private String creditCardNumber;

	public CreditCardNumber(String creditCardNumber) {
		//use validator 
		if(null==creditCardNumber || creditCardNumber.isEmpty()) {
			throw new InvalidCreditCardNumberException();
		}
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}
}
