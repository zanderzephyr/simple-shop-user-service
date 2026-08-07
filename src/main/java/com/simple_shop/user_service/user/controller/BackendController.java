package com.simple_shop.user_service.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_shop.user_service.user.dto.LoginRequestDto;
import com.simple_shop.user_service.user.dto.LoginResponseDto;
import com.simple_shop.user_service.user.exceptions.InvalidRequestException;
import com.simple_shop.user_service.user.model.LoginRequest;
import com.simple_shop.user_service.user.model.LoginResponse;
import com.simple_shop.user_service.user.service.UserService;

@RestController
@RequestMapping("/api/users/v1/backend")
public final class BackendController {
	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	public BackendController(final UserService userService, final PasswordEncoder passwordEncoder) {
		super();

		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/verifyLoginCredentials")
	public ResponseEntity<LoginResponseDto> verifyLoginCredentials(@RequestBody final LoginRequestDto loginRequestDto) {
		if (loginRequestDto == null) {
			throw new InvalidRequestException("No login request!");
		}

		final LoginRequest loginRequest = DtoMapper.toDomain(loginRequestDto, this.passwordEncoder);
		final LoginResponse loginResponse = this.userService.verifyLoginCredentials(loginRequest);
		final LoginResponseDto loginResponseDto = DtoMapper.toDto(loginResponse);

		return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);
	}
}