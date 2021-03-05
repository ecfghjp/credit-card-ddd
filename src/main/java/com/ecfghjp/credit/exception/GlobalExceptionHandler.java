package com.ecfghjp.credit.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author abhishek.ad.sharma<br>
 * Global Exception Handler for Employees Exceptions class handling exceptions and relaying back with a customized message
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	// define your methods here


	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Malformed JSON request", details);
		return buildResponse(err);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(Exception ex, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Constraint Violations", details);
		return buildResponse(err);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Type Mismatch", details);
		return buildResponse(err);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, "Method Not Found", details);

		return buildResponse(err);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ApiError apiError = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
		return buildResponse(apiError);
	}

	private ResponseEntity<Object> buildResponse(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
	@ExceptionHandler
	private ResponseEntity<?> cardNotFoundHandler(CreditCardNotSetup ex) {
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, "Card not yet setup", details);
		return buildResponse(err);
		
	}
	
	@ExceptionHandler
	private ResponseEntity<Object> notenoughCredit(NotEnoughCreditException ex) {
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, "Not enough Credit in card", details);
		return buildResponse(err);
		
	}
	
	
	@ExceptionHandler
	private ResponseEntity<?> creditLimitAlreadyAssigned(CreditLimitAlreadyRegistered ex) {
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.CONFLICT, "Credit Limit Already Assigned", details);
		return buildResponse(err);
		
	}
	
	@ExceptionHandler
	private ResponseEntity<?> creditCardTransactionInLastHour(TransactionInLastHourException ex) {
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, "Credit Transaction in Last 1 hour please wait", details);
		return buildResponse(err);
		
		
	}
	
	@ExceptionHandler
	private ResponseEntity<?> invalidOperationException(InvalidOperationException ex) {
		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());
		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, "Credit Transaction in Last 1 hour please wait", details);
		return buildResponse(err);
		
		
	}
}
