package com.petshop.banhoetosa.controller.exceptions;

import com.petshop.banhoetosa.service.exceptions.DataIntegratyViolationException;
import com.petshop.banhoetosa.service.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandler {
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
		StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(DataIntegratyViolationException.class)
	public ResponseEntity<StandardError> dataIntegratyViolation(DataIntegratyViolationException ex, HttpServletRequest request) {
		StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
