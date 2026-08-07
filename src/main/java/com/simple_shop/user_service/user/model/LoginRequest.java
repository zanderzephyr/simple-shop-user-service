package com.simple_shop.user_service.user.model;

import java.util.Objects;

public final class LoginRequest {
	private final String username;

	private final String passwordHash;

	private LoginRequest(final LoginRequestBuilder builder) throws IllegalStateException {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.username = builder.getUsername();
		this.passwordHash = builder.getPasswordHash();
	}

	public String getUsername() {
		return this.username;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public static final class LoginRequestBuilder {
		private String username;

		private String passwordHash;

		public LoginRequestBuilder() {
			super();
		}

		public String getUsername() {
			return this.username;
		}

		public LoginRequestBuilder setUsername(final String username) {
			this.username = username;

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
			if (this.username == null || this.username.isBlank()) {
				throw new IllegalStateException("Invalid username!");
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