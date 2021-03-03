package com.ecfghjp.credit.infrastructure.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ecfghjp.credit.infrastructure.persistence.jpa.entities.CreditCardJPA;
import com.ecfghjp.credit.service.domain.CreditCard;
import com.ecfghjp.credit.service.repository.CreditCardRepository;

@ExtendWith(SpringExtension.class)
@ComponentScan("com.ecfghjp.credit")
@DataJpaTest
class CreditCardJPARepositoryTest {
	
	@Autowired
	private CreditCardRepository creditCardJPARepository;
	
	@Autowired
	private TestEntityManager testEntitymanager;
	
	@Test
	void when_find_credit_card_then_success() {
		CreditCardJPA creditCard = testEntitymanager.persistAndFlush(new CreditCardJPA("100100100100",new BigDecimal(1000)));
		CreditCard creditCardDetailsFetched= creditCardJPARepository.findCreditCardDetails("100100100100");
		
		assertThat(creditCardDetailsFetched.getCreditCardNumber().getCreditCardNumber()).isEqualTo("100100100100");
		assertThat(creditCardDetailsFetched.getCreditLimit().getLimit()).isEqualTo(new BigDecimal(1000));
	}

	@Test
	void when_find_credit_card_then_failure() {
		CreditCardJPA creditCard = testEntitymanager.persistAndFlush(new CreditCardJPA("100100100100",new BigDecimal(1000)));
		assertThat(creditCardJPARepository.findCreditCardDetails("100")).isNull();

		
	}
	
}
