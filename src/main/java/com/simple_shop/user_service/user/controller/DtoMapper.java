package com.simple_shop.user_service.user.controller;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.simple_shop.user_service.user.dto.UserDto;
import com.simple_shop.user_service.user.exceptions.UserValidationException;
import com.simple_shop.user_service.user.model.User;
import com.simple_shop.user_service.user.model.User.UserBuilder;
import com.simple_shop.user_service.user.model.UserType;
import com.simple_shop.user_service.utils.DataTypeUtils;

public final class DtoMapper {
	private DtoMapper() {
		throw new UnsupportedOperationException();
	}

	public static User toDomain(final UserDto userDto, final PasswordEncoder passwordEncoder) {
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
			throw new UserValidationException("Unknown user type: " + userType);
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
}
