package com.ecfghjp.credit.service.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.ecfghjp.credit.exception.InvalidCreditCardNumberException;

class CreditCardNumberTest {

	@Test
	void credte_credit_card_value() {
		CreditCardNumber creditCardNumber = new CreditCardNumber("100100100");
		assertThat(creditCardNumber.getCreditCardNumber()).isEqualTo("100100100");
	}
	
	
	@Test
	void create_credit_card_number_null() {
		assertThrows(InvalidCreditCardNumberException.class, () -> {
			new CreditCardNumber(null);
		});	}
	
	@Test
	void create_credit_card_number_empty() {
		assertThrows(InvalidCreditCardNumberException.class, () -> {
			new CreditCardNumber("");
		});	}

}
