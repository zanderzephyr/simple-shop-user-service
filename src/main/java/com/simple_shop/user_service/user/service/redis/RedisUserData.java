package com.simple_shop.user_service.user.service.redis;

import java.util.Objects;

import com.simple_shop.user_service.user.model.UserType;

public final class RedisUserData {
	private final UserType userType;

	private RedisUserData(final RedisUserDataBuilder builder) {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.userType = builder.getUserType();
	}

	public UserType getUserType() {
		return this.userType;
	}

	public static final class RedisUserDataBuilder {
		private UserType userType;

		public RedisUserDataBuilder() {
			super();
		}

		public UserType getUserType() {
			return this.userType;
		}

		public RedisUserDataBuilder setUserType(final UserType userType) {
			this.userType = userType;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.userType == null) {
				throw new IllegalStateException("Invalid user type!");
			}
		}

		public RedisUserData build() {
			return new RedisUserData(this);
		}
	}
}
