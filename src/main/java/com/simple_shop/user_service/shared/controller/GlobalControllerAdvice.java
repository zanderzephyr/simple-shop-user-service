package com.simple_shop.user_service.shared.controller;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.simple_shop.user_service.shared.dto.ServiceErrorResponseDto;
import com.simple_shop.user_service.user.exceptions.InvalidMessageException;

import jakarta.validation.ConstraintViolationException;

// TODO Move to lib
@RestControllerAdvice // Covers all RestControllers in the application context
public final class GlobalControllerAdvice {

	public GlobalControllerAdvice() {
		super();
	}

	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ResponseEntity<ServiceErrorResponseDto> handleObjectOptimisticLockingFailureException(
			final ObjectOptimisticLockingFailureException exception) {

		final ServiceErrorResponseDto responseDto = new ServiceErrorResponseDto()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.CONFLICT.value())//
				.setMessage("Trying to work with a stale version")//
				.setTimestamp(Instant.now());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ServiceErrorResponseDto> handleHttpMessageNotReadableException(
			final HttpMessageNotReadableException exception) {

		return GlobalControllerAdvice.createCommonBadRequestResponse(exception);
	}

	@ExceptionHandler(InvalidMessageException.class)
	public ResponseEntity<ServiceErrorResponseDto> handleInvalidMessageException(
			final InvalidMessageException exception) {

		return GlobalControllerAdvice.createCommonBadRequestResponse(exception);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ServiceErrorResponseDto> handleConstraintViolationException(
			final ConstraintViolationException exception) {

		return GlobalControllerAdvice.createCommonBadRequestResponse(exception);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ServiceErrorResponseDto> handleMethodArgumentNotValidException(
			final MethodArgumentNotValidException exception) {

		return GlobalControllerAdvice.createCommonBadRequestResponse(exception);
	}

	private static ResponseEntity<ServiceErrorResponseDto> createCommonBadRequestResponse(final Throwable throwable) {
		final ServiceErrorResponseDto responseDto = new ServiceErrorResponseDto()//
				.setDetail(throwable.getMessage())//
				.setHttpState(HttpStatus.BAD_REQUEST.value())//
				.setMessage("Invalid message!")//
				.setTimestamp(Instant.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	}
}
