package com.ecfghjp.credit.service.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class CreditCardTransactionTest {

	@Test
	void when_transaction_done_transaction_amount_not_0() {
		BigDecimal value = new BigDecimal(100);
		TransactionPurpose purpose = TransactionPurpose.PAYMENT;
		CreditCardTransaction creditCardTransaction = new CreditCardTransaction(value, purpose, LocalDateTime.now(),
				value, value);
		
		
	}
	
	@Test
	void when_transaction_done_transaction_generate_identifier() {
		BigDecimal value = new BigDecimal(100);
		TransactionPurpose purpose = TransactionPurpose.PAYMENT;
		CreditCardTransaction creditCardTransaction = new CreditCardTransaction(value, purpose, LocalDateTime.now(),
				value, value);
		
	}
	
}
