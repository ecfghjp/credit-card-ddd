package com.ecfghjp.credit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecfghjp.credit.controller.domain.CreditCardRequestDTO;
import com.ecfghjp.credit.controller.domain.CreditCardResponseDTO;
import com.ecfghjp.credit.controller.domain.PaymentRequestDTO;
import com.ecfghjp.credit.controller.domain.PaymentResponseDTO;
import com.ecfghjp.credit.controller.helper.CreditControllerHelper;
import com.ecfghjp.credit.exception.CreditCardLimitAlreadyAssigned;
import com.ecfghjp.credit.exception.CreditCardNotSetup;
import com.ecfghjp.credit.exception.CreditLimitAlreadyRegistered;
import com.ecfghjp.credit.exception.NotEnoughCreditException;
import com.ecfghjp.credit.exception.TransactionInLastHour;
import com.ecfghjp.credit.service.CreditCardOperationsService;

@RestController
@RequestMapping("/credit")
public class CreditCardOperationsController {
	
	private CreditCardOperationsService creditService;
	
	@Autowired
	public CreditCardOperationsController(CreditCardOperationsService creditService) {
		this.creditService = creditService;
	}

	@PostMapping("/payment/")
	public PaymentResponseDTO payment(@RequestBody PaymentRequestDTO withdrawalRequest){
		PaymentResponseDTO paymentResponseDTO =  CreditControllerHelper.convertWithddrawalVOtoTO(creditService.payment(withdrawalRequest));
		return paymentResponseDTO;
	}
	
	@PutMapping("/assign/")
	public CreditCardResponseDTO createCreditCard(@RequestBody CreditCardRequestDTO creditCard){
		String messsage =  creditService.assignLimit(creditCard);
		
		return new CreditCardResponseDTO(messsage);
	}
	
	
	//should be via an event
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void carNotFoundHandler(CreditCardNotSetup ex) {
		
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private void notenoughCredit(NotEnoughCreditException ex) {
		
	}
	
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.CONFLICT)
	private void creditLimitAlreadyAssigned(CreditLimitAlreadyRegistered ex) {
		
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private void creditCardTransactionInLastHour(TransactionInLastHour ex) {
		
	}
	
}
