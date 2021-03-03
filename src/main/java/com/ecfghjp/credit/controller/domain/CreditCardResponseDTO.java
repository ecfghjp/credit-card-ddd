package com.ecfghjp.credit.controller.domain;

public class CreditCardResponseDTO {

	private String message;

	public CreditCardResponseDTO(String message) {
		this.message = message;
	}

	public CreditCardResponseDTO() {
	}

	public String getMessage() {
		return message;
	}

}
