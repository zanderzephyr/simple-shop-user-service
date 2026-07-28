package com.simple_shop.user_service.user.persistence;

import com.simple_shop.user_service.user.model.User;
import com.simple_shop.user_service.user.model.User.UserBuilder;
import com.simple_shop.user_service.user.model.UserHistoryState;
import com.simple_shop.user_service.user.model.UserHistoryState.UserHistoryStateBuilder;
import com.simple_shop.user_service.utils.UserServiceUtils;

public final class UserEntityMapper {
	private UserEntityMapper() {
		throw new UnsupportedOperationException();
	}

	public static User toDomain(final UserEntity userEntity) {
		return new UserBuilder()//
				.setId(userEntity.getId()) //
				.setUsername(userEntity.getUsername()) //
				.setFirstName(userEntity.getFirstName()) //
				.setMiddleNames(UserServiceUtils.convertMiddleNames(userEntity.getMiddleNames())) //
				.setLastName(userEntity.getLastName()) //
				.setEmail(userEntity.getEmail()) //
				.setUserType(userEntity.getUserType()) //
				.setPasswordHash(userEntity.getPasswordHash()) //
				.setLastChange(userEntity.getLastChange()) //
				.setLastChangedBy(userEntity.getLastChangedBy()) //
				.setVersion(userEntity.getVersion())//
				.build();
	}

	public static UserEntity toEntity(final User user) {
		final UserEntity entity = new UserEntity();

		entity.setId(user.getId());
		entity.setUsername(user.getUsername());
		entity.setFirstName(user.getFirstName());
		entity.setMiddleNames(UserServiceUtils.convertMiddleNames(user.getMiddleNames()));
		entity.setLastName(user.getLastName());
		entity.setEmail(user.getEmail());
		entity.setUserType(user.getUserType());
		entity.setPasswordHash(user.getPasswordHash());
		entity.setLastChange(user.getLastChange());
		entity.setLastChangedBy(user.getLastChangedBy());
		entity.setVersion(user.getVersion());

		return entity;
	}

	public static UserHistoryState toDomain(final UserHistoryStateEntity userHistoryStateEntity) {
		return new UserHistoryStateBuilder()//
				.setId(userHistoryStateEntity.getId()) //
				.setUserId(userHistoryStateEntity.getUserId()) //
				.setUsername(userHistoryStateEntity.getUsername()) //
				.setFirstName(userHistoryStateEntity.getFirstName()) //
				.setMiddleNames(UserServiceUtils.convertMiddleNames(userHistoryStateEntity.getMiddleNames()))//
				.setLastName(userHistoryStateEntity.getLastName()) //
				.setEmail(userHistoryStateEntity.getEmail()) //
				.setUserType(userHistoryStateEntity.getUserType())//
				.setAction(userHistoryStateEntity.getAction())//
				.setLastChange(userHistoryStateEntity.getLastChange()) //
				.setLastChangedBy(userHistoryStateEntity.getLastChangedBy()) //
				.setVersion(userHistoryStateEntity.getVersion())//
				.build();
	}

	public static UserHistoryStateEntity toEntity(final UserHistoryState userHistoryState) {
		final UserHistoryStateEntity entity = new UserHistoryStateEntity();

		entity.setId(userHistoryState.getId());
		entity.setUserId(userHistoryState.getUserId());
		entity.setUsername(userHistoryState.getUsername());
		entity.setFirstName(userHistoryState.getFirstName());
		entity.setMiddleNames(UserServiceUtils.convertMiddleNames(userHistoryState.getMiddleNames()));
		entity.setLastName(userHistoryState.getLastName());
		entity.setEmail(userHistoryState.getEmail());
		entity.setUserType(userHistoryState.getUserType());
		entity.setAction(userHistoryState.getAction());
		entity.setLastChange(userHistoryState.getLastChange());
		entity.setLastChangedBy(userHistoryState.getLastChangedBy());
		entity.setVersion(userHistoryState.getVersion());

		return entity;
	}
}
