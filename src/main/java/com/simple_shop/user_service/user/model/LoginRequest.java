package com.simple_shop.user_service.user.model;

import java.util.Objects;

public final class LoginRequest {
	private final long userId;

	private final String passwordHash;

	private LoginRequest(final LoginRequestBuilder builder) throws IllegalStateException {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.userId = builder.getUserId();
		this.passwordHash = builder.getPasswordHash();
	}

	public long getUserId() {
		return this.userId;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public static final class LoginRequestBuilder {
		private Long userId;

		private String passwordHash;

		public LoginRequestBuilder() {
			super();
		}

		public Long getUserId() {
			return this.userId;
		}

		public LoginRequestBuilder setUserId(final Long userId) {
			this.userId = userId;

			return this;
		}

		public String getPasswordHash() {
			return this.passwordHash;
		}

		public LoginRequestBuilder setPasswordHash(final String passwordHash) {
			this.passwordHash = passwordHash;

			return this;
		}

		public void validate() throws IllegalStateException {
			// No need to validate the db length requirements here
			if (this.userId == null || this.userId < 0) {
				throw new IllegalStateException("Invalid user ID!");
			}

			// No need to validate the db length requirements here
			if (this.passwordHash == null || this.passwordHash.isBlank()) {
				throw new IllegalStateException("Invalid password hash!");
			}
		}

		public LoginRequest build() throws IllegalStateException {
			return new LoginRequest(this);
		}
	}
}