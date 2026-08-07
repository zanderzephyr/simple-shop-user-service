package com.simple_shop.user_service.user.controller;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.simple_shop.user_service.user.dto.LoginRequestDto;
import com.simple_shop.user_service.user.dto.LoginResponseDto;
import com.simple_shop.user_service.user.dto.UserDto;
import com.simple_shop.user_service.user.exceptions.InvalidRequestException;
import com.simple_shop.user_service.user.model.LoginRequest;
import com.simple_shop.user_service.user.model.LoginRequest.LoginRequestBuilder;
import com.simple_shop.user_service.user.model.LoginResponse;
import com.simple_shop.user_service.user.model.User;
import com.simple_shop.user_service.user.model.User.UserBuilder;
import com.simple_shop.user_service.user.model.UserType;
import com.simple_shop.user_service.utils.DataTypeUtils;

public final class DtoMapper {
	private DtoMapper() {
		throw new UnsupportedOperationException();
	}

	public static User toDomain(final UserDto userDto, final PasswordEncoder passwordEncoder) {
		try {
			return new UserBuilder()//
					.setId(userDto.getId())//
					.setUsername(userDto.getUsername())//
					.setFirstName(userDto.getFirstName())//
					.setMiddleNames(userDto.getMiddleNames())//
					.setLastName(userDto.getLastName())//
					.setEmail(userDto.getEmail())//
					.setUserType(DtoMapper.parseUserType(userDto.getUserType()))//
					.setPasswordHash(DtoMapper.hashClientPassword(userDto.getPasswordHash(), passwordEncoder))//
					.setLastChange(userDto.getLastChange())//
					.setLastChangedBy(userDto.getLastChangedBy())//
					.setVersion(userDto.getVersion())//
					.build();
		} catch (final IllegalStateException e) {
			throw new InvalidRequestException(e);
		}

	}

	private static String hashClientPassword(final String clientPassword, final PasswordEncoder passwordEncoder) {
		final String hash;

		if (clientPassword == null || clientPassword.isBlank()) {
			hash = null;
		} else {
			hash = passwordEncoder.encode(clientPassword);
		}

		return hash;
	}

	private static UserType parseUserType(final String userType) {
		try {
			return DataTypeUtils.parseEnum(userType, UserType.class);
		} catch (final IllegalArgumentException e) {
			throw new InvalidRequestException("Unknown user type: " + userType);
		}
	}

	/*
	 * This is the location to set the precision of decimals, should we ever have
	 * them and need precision. Do not solve this using the formatters.
	 */
	public static UserDto toDto(final User user) {
		final UserDto userDto = new UserDto();

		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setFirstName(user.getFirstName());
		userDto.setMiddleNames(user.getMiddleNames());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setUserType(user.getUserType().name());
		userDto.setLastChange(user.getLastChange());
		userDto.setLastChangedBy(user.getLastChangedBy());
		userDto.setVersion(user.getVersion());

		return userDto;
	}

	public static LoginRequest toDomain(final LoginRequestDto loginRequestDto, final PasswordEncoder passwordEncoder) {
		try {
			return new LoginRequestBuilder()//
					.setPasswordHash(DtoMapper.hashClientPassword(loginRequestDto.getPasswordHash(), passwordEncoder))//
					.setUsername(loginRequestDto.getUsername())//
					.build();
		} catch (final IllegalStateException e) {
			throw new InvalidRequestException(e);
		}
	}

	public static LoginResponseDto toDto(final LoginResponse loginResponse) {
		final LoginResponseDto loginResponseDto = new LoginResponseDto();

		loginResponseDto.setSuccess(loginResponse.getSuccess());
		loginResponseDto.setUserId(loginResponse.getUserId());

		return loginResponseDto;
	}
}
