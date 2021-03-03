package com.ecfghjp.credit.infrastructure.persistence.jpa;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecfghjp.credit.infrastructure.persistence.jpa.entities.CreditCardTransactionJPA;

@Repository
public interface CreditCardTransactionJPARepositotyInterface extends CrudRepository<CreditCardTransactionJPA, String>{

	Optional<CreditCardTransactionJPA> findTopByCreditCardNumberOrderByTransactionDateDesc(String creditCardNumber);

}
