package com.simple_shop.user_service.user.controller;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.simple_shop.user_service.shared.dto.ServiceErrorResponseDto;
import com.simple_shop.user_service.user.exceptions.UserNotFoundException;

@RestControllerAdvice(assignableTypes = UserController.class)
public final class UserControllerAdvice {

	public UserControllerAdvice() {
		super();
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ServiceErrorResponseDto> handleUserNotFoundException(final UserNotFoundException exception) {

		final ServiceErrorResponseDto responseDto = new ServiceErrorResponseDto()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.NOT_FOUND.value())//
				.setMessage("User not found")//
				.setTimestamp(Instant.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
	}
}
