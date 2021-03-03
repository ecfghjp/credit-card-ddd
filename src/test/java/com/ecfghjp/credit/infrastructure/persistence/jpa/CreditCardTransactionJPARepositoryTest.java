package com.ecfghjp.credit.infrastructure.persistence.jpa;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecfghjp.credit.infrastructure.persistence.jpa.entities.CreditCardTransactionJPA;
import com.ecfghjp.credit.service.domain.CreditCardTransaction;
import com.ecfghjp.credit.service.domain.TransactionPurpose;
import com.ecfghjp.credit.service.repository.CreditCardTransactionsRepository;

@ExtendWith(SpringExtension.class)
@ComponentScan("com.ecfghjp.credit")
@DataJpaTest
class CreditCardTransactionJPARepositoryTest {

	@Autowired
	private CreditCardTransactionsRepository creditCardTransactionsRepository;

	@Autowired
	private TestEntityManager testEntitymanager;

	@Test
	void when_transaction_then_save_transaction() {
		String creditCardNumber = "10012001001";

		CreditCardTransactionJPA creditCardTransactionJPA = testEntitymanager.persistAndFlush(
				new CreditCardTransactionJPA(creditCardNumber, new BigDecimal(200), LocalDateTime.now(),
						TransactionPurpose.PAYMENT.toString(), new BigDecimal(1000), new BigDecimal(800)));
		
		CreditCardTransactionJPA creditCardTransactionJPA1 = testEntitymanager.persistAndFlush(
				new CreditCardTransactionJPA(creditCardNumber, new BigDecimal(200), LocalDateTime.now().minusDays(2),
						TransactionPurpose.REPAYMENT.toString(), new BigDecimal(200), new BigDecimal(400)));
		
		CreditCardTransactionJPA creditCardTransactionJPA2 = testEntitymanager.persistAndFlush(
				new CreditCardTransactionJPA(creditCardNumber, new BigDecimal(200), LocalDateTime.now().minusDays(3),
						TransactionPurpose.REPAYMENT.toString(), new BigDecimal(200), new BigDecimal(400)));
		
		CreditCardTransaction creditCardTransaction = creditCardTransactionsRepository
				.findLastCreditCardTransaction(creditCardNumber);
		assertThat(creditCardTransaction.getTransactionPurpose()).isEqualTo(TransactionPurpose.PAYMENT);
		assertThat(creditCardTransaction.creditLeftAfterTransaction()).isEqualTo(new BigDecimal(800));


		
	}

	@Test
	void when_transaction_then_failed_transaction() {
		String creditCardNumber = "10012001001";

		assertThat(creditCardTransactionsRepository
				.findLastCreditCardTransaction(creditCardNumber)).isNull();
	}

}
