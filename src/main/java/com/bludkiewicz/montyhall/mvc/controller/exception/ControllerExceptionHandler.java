package com.bludkiewicz.montyhall.mvc.controller.exception;

import com.bludkiewicz.montyhall.mvc.controller.json.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * Exceptions are automatically handled by Spring Boot.
 * I am overriding that handling as the response does not usually indicate what the problem was.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

	/**
	 * Bean Validation API Constraints.
	 * For some reason this is not automatically returned as a 400.
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Message> handleBadInput(ConstraintViolationException ex) {

		return new ResponseEntity<>(new Message(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Unsupported request methods.
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Message> handleInvalidMethodArgument(HttpRequestMethodNotSupportedException ex) {

		return new ResponseEntity<>(new Message(ex.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * Invalid argument types (character values in an integer, etc).
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Message> handleInvalidMethodArgument(IllegalArgumentException ex) {

		return new ResponseEntity<>(new Message(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Invalid JSON arguments.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Message> handleInvalidMethodArgument(MethodArgumentNotValidException ex) {

		// ex.getMessage() is not very user friendly
		// so we will build our own messages
		String message =
				ex.getBindingResult().getFieldErrors()
						.stream()
						.map(error -> error.getField() +" " +error.getDefaultMessage())
						.collect(Collectors.joining(", "));

		return new ResponseEntity<>(new Message(message), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Invalid JSON format.
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Message> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

		return new ResponseEntity<>(new Message(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
