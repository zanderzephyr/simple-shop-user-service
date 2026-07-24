package com.simple_shop.user_service.user.controller;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.simple_shop.user_service.user.dto.UserServiceErrorResponseDto;
import com.simple_shop.user_service.user.dto.UserServiceErrorResponseDto.UserServiceErrorResponseDtoBuilder;
import com.simple_shop.user_service.user.exceptions.InvalidUserRequestConstraintsException;
import com.simple_shop.user_service.user.exceptions.UserNotFoundException;
import com.simple_shop.user_service.user.exceptions.UserValidationException;

@RestControllerAdvice(assignableTypes = UserController.class)
public final class UserControllerAdvice {

	public UserControllerAdvice() {
		super();
	}

	@ExceptionHandler(UserValidationException.class)
	public ResponseEntity<UserServiceErrorResponseDto> handleUserValidationException(
			final UserValidationException exception) {

		final UserServiceErrorResponseDto responseDto = new UserServiceErrorResponseDtoBuilder()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.BAD_REQUEST.value())//
				.setMessage("User validation failed")//
				.setTimestamp(Instant.now())//
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<UserServiceErrorResponseDto> handleUserNotFoundException(
			final UserNotFoundException exception) {

		final UserServiceErrorResponseDto responseDto = new UserServiceErrorResponseDtoBuilder()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.NOT_FOUND.value())//
				.setMessage("User not found")//
				.setTimestamp(Instant.now())//
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
	}

	@ExceptionHandler(InvalidUserRequestConstraintsException.class)
	public ResponseEntity<UserServiceErrorResponseDto> handleInvalidUserRequestConstraintsException(
			final InvalidUserRequestConstraintsException exception) {

		final UserServiceErrorResponseDto responseDto = new UserServiceErrorResponseDtoBuilder()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.BAD_REQUEST.value())//
				.setMessage("Page setup is invalid")//
				.setTimestamp(Instant.now())//
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<UserServiceErrorResponseDto> handleIllegalStateException(
			final IllegalStateException exception) {

		final UserServiceErrorResponseDto responseDto = new UserServiceErrorResponseDtoBuilder()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.INTERNAL_SERVER_ERROR.value())//
				.setMessage("Internal server error")//
				.setTimestamp(Instant.now())//
				.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
	}

	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ResponseEntity<UserServiceErrorResponseDto> handleObjectOptimisticLockingFailureException(
			final ObjectOptimisticLockingFailureException exception) {

		final UserServiceErrorResponseDto responseDto = new UserServiceErrorResponseDtoBuilder()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.CONFLICT.value())//
				.setMessage("Trying to work with a stale version")//
				.setTimestamp(Instant.now())//
				.build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<UserServiceErrorResponseDto> handleHttpMessageNotReadableException(
			final HttpMessageNotReadableException exception) {

		final UserServiceErrorResponseDto responseDto = new UserServiceErrorResponseDtoBuilder()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.BAD_REQUEST.value())//
				.setMessage("Request contains bad content!")//
				.setTimestamp(Instant.now())//
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	}
}
