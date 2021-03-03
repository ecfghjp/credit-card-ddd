package com.ecfghjp.credit.infrastructure.persistence.jpa;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecfghjp.credit.infrastructure.persistence.jpa.entities.CreditCardJPA;

@Repository
public interface CreditCardJPARepositoryInterface extends CrudRepository<CreditCardJPA, String>{

	public Optional<CreditCardJPA> findByCreditCardNumber(String creditCardNumber);

}
