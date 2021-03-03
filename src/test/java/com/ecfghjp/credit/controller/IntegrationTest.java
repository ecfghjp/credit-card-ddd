//package com.ecfghjp.credit.controller;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.ecfghjp.credit.controller.domain.PaymentRequestDTO;
//import com.ecfghjp.credit.controller.domain.PaymentResponseDTO;
//
///**
// * @author abhishek.sharma
// *The Test cases use : 
// * Assertionj
// * SpringIntegration testing framework
// * Application runs on random port
// * Uses TestRestTemplate
// */
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class IntegrationTest {
//	
//	@Autowired
//	private TestRestTemplate restTemplate;
//
//	@LocalServerPort
//	private int port;
//	
//	
//	//credit card number should be in the header and shouldnt come as a plain text
//	
//	@Test
//	public void withdrawalRequest_ShouldReturnMoneyWithdrew() {
//		//arange/ setup
//		
//		//call service
//		ResponseEntity<PaymentResponseDTO> response = restTemplate.postForEntity("/credit/payment/",PaymentRequestDTO.class,PaymentResponseDTO.class);
//
//		//check asserts
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//	}
//	
//	
//	//shouldnt be responsibility to assign limit
//	//that should be a seperate component and generate an event 
//	@Test
//	public void assignLimitRequest_ShouldAssignLimit() {
//		//arange/ setup
//		
//		//call service
//		//check asserts
//	}
//	
//	@Test
//	public void repay_CreditCard_Debts() {
//		//arange/ setup
//		
//		//call service
//
//		//check asserts
//	}
//	
//	@Test
//	public void when_credit_payment_then_success() {
//		//arange/ setup
//		
//		//call service
//
//		//check asserts
//	}
//	
//	
//	@Test
//	public void when_credit_repayment_then_success() {
//		//arange/ setup
//		
//		//call service
//
//		//check asserts
//	}
//	
//	@Test
//	public void when_credit_payment_then_failure_missing_required_argument() {
//		//arange/ setup
//		
//		//call service
//
//		//check asserts
//	}
//	
//}
