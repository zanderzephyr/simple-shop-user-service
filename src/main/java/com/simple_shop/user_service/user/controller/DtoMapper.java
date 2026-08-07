package com.simple_shop.user_service.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.simple_shop.user_service.user.dto.LoginRequestDto;
import com.simple_shop.user_service.user.dto.LoginResponseDto;
import com.simple_shop.user_service.user.dto.UserDto;
import com.simple_shop.user_service.user.dto.UserDto.UserDtoMember;
import com.simple_shop.user_service.user.dto.RequestConstraintsDto;
import com.simple_shop.user_service.user.exceptions.InvalidRequestException;
import com.simple_shop.user_service.user.model.LoginRequest;
import com.simple_shop.user_service.user.model.LoginRequest.LoginRequestBuilder;
import com.simple_shop.user_service.user.model.LoginResponse;
import com.simple_shop.user_service.user.model.SortingDirection;
import com.simple_shop.user_service.user.model.User;
import com.simple_shop.user_service.user.model.User.UserBuilder;
import com.simple_shop.user_service.user.model.RequestConstraints;
import com.simple_shop.user_service.user.model.RequestConstraints.RequestConstraintsBuilder;
import com.simple_shop.user_service.user.model.UserType;
import com.simple_shop.user_service.user.persistence.UserEntity.UserEntityMember;
import com.simple_shop.user_service.utils.DataTypeUtils;

public final class DtoMapper {
	private static final Map<String, String> SORTABLE_DTO_TO_ENTITY_COLUMNS = DtoMapper.collectSortableColumns();

	private DtoMapper() {
		throw new UnsupportedOperationException();
	}

	private static Map<String, String> collectSortableColumns() {
		final Map<String, String> columns = new HashMap<>();

		columns.put(UserDtoMember.USERNAME.getDomainName(), UserEntityMember.USERNAME.getDomainName());
		columns.put(UserDtoMember.FIRST_NAME.getDomainName(), UserEntityMember.FIRST_NAME.getDomainName());
		columns.put(UserDtoMember.LAST_NAME.getDomainName(), UserEntityMember.LAST_NAME.getDomainName());
		columns.put(UserDtoMember.EMAIL.getDomainName(), UserEntityMember.EMAIL.getDomainName());
		columns.put(UserDtoMember.USER_TYPE.getDomainName(), UserEntityMember.USER_TYPE.getDomainName());
		columns.put(UserDtoMember.LAST_CHANGE.getDomainName(), UserEntityMember.LAST_CHANGE.getDomainName());
		columns.put(UserDtoMember.LAST_CHANGED_BY.getDomainName(), UserEntityMember.LAST_CHANGED_BY.getDomainName());

		return columns;
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
					.setUserType(DataTypeUtils.parseEnum(userDto.getUserType(), UserType.class))//
					.setPasswordHash(DtoMapper.hashClientPassword(userDto.getPasswordHash(), passwordEncoder))//
					.setLastChange(userDto.getLastChange())//
					.setLastChangedBy(userDto.getLastChangedBy())//
					.setVersion(userDto.getVersion())//
					.build();
		} catch (final Exception e) {
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
		} catch (final Exception e) {
			throw new InvalidRequestException(e);
		}
	}

	public static LoginResponseDto toDto(final LoginResponse loginResponse) {
		final LoginResponseDto loginResponseDto = new LoginResponseDto();

		loginResponseDto.setSuccess(loginResponse.getSuccess());
		loginResponseDto.setUserId(loginResponse.getUserId());

		return loginResponseDto;
	}

	public static RequestConstraints toDomain(final RequestConstraintsDto userRequestConstraintsDto) {
		try {
			return new RequestConstraintsBuilder()//
					.setPageIndex(userRequestConstraintsDto.getPageIndex() != null ? //
							userRequestConstraintsDto.getPageIndex() : 0)//
					.setPageSize(userRequestConstraintsDto.getPageSize() != null ? //
							userRequestConstraintsDto.getPageSize() : 20)//
					.setSortingColumn(DtoMapper.getSortableColumnValue(userRequestConstraintsDto.getSortingColumn()))//
					.setSortingDirection(userRequestConstraintsDto.getSortingDirection() != null ? //
							DataTypeUtils.parseEnum(userRequestConstraintsDto.getSortingDirection(),
									SortingDirection.class)
							: SortingDirection.ASCENDING)//
					.build();
		} catch (final Exception e) {
			throw new InvalidRequestException(e);
		}
	}

	private static String getSortableColumnValue(final String sortableColumnRequest) {
		String sortableColumnValue;

		if (sortableColumnRequest == null) {
			sortableColumnValue = UserDtoMember.USERNAME.getDomainName();
		} else {
			sortableColumnValue = DtoMapper.SORTABLE_DTO_TO_ENTITY_COLUMNS.get(sortableColumnRequest);
			if (sortableColumnValue == null) {
				sortableColumnValue = UserEntityMember.USERNAME.getDomainName();
			}
		}

		return sortableColumnRequest;
	}
}
