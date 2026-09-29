package com.simple_shop.user_service.shared.controller;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.simple_shop.user_service.shared.dto.ServiceErrorResponseDto;
import com.simple_shop.user_service.shared.dto.ServiceErrorResponseDto.ServiceErrorResponseDtoBuilder;
import com.simple_shop.user_service.user.exceptions.InvalidMessageException;

@RestControllerAdvice // Covers all RestControllers in the application context
public final class GlobalControllerAdvice {

	public GlobalControllerAdvice() {
		super();
	}

	@ExceptionHandler(InvalidMessageException.class)
	public ResponseEntity<ServiceErrorResponseDto> handleInvalidMessageException(
			final InvalidMessageException exception) {

		final ServiceErrorResponseDto responseDto = new ServiceErrorResponseDtoBuilder()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.BAD_REQUEST.value())//
				.setMessage("Invalid message!")//
				.setTimestamp(Instant.now())//
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	}

	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ResponseEntity<ServiceErrorResponseDto> handleObjectOptimisticLockingFailureException(
			final ObjectOptimisticLockingFailureException exception) {

		final ServiceErrorResponseDto responseDto = new ServiceErrorResponseDtoBuilder()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.CONFLICT.value())//
				.setMessage("Trying to work with a stale version")//
				.setTimestamp(Instant.now())//
				.build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ServiceErrorResponseDto> handleHttpMessageNotReadableException(
			final HttpMessageNotReadableException exception) {

		final ServiceErrorResponseDto responseDto = new ServiceErrorResponseDtoBuilder()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.BAD_REQUEST.value())//
				.setMessage("Request contains bad content!")//
				.setTimestamp(Instant.now())//
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	}
}
