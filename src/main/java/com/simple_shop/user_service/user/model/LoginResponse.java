package com.simple_shop.user_service.user.model;

import java.util.Objects;

public final class LoginResponse {
	private final Long userId;

	private final boolean credentialsOk;

	private LoginResponse(final LoginResponseBuilder builder) throws IllegalStateException {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.userId = builder.getUserId();
		this.credentialsOk = builder.getCredentialsOk();
	}

	public Long getUserId() {
		return this.userId;
	}

	public boolean getCredentialsOk() {
		return this.credentialsOk;
	}

	public static final class LoginResponseBuilder {
		private Long userId;

		private Boolean credentialsOk;

		public LoginResponseBuilder() {
			super();
		}

		public Long getUserId() {
			return this.userId;
		}

		public LoginResponseBuilder setUserId(final Long userId) {
			this.userId = userId;

			return this;
		}

		public Boolean getCredentialsOk() {
			return this.credentialsOk;
		}

		public LoginResponseBuilder setCredentialsOk(final Boolean credentialsOk) {
			this.credentialsOk = credentialsOk;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.userId != null && this.userId < 0) {
				throw new IllegalStateException("Invalid user ID!");
			}

			if (this.credentialsOk == null) {
				throw new IllegalStateException("Invalid credentialsOk!");
			}
		}

		public LoginResponse build() throws IllegalStateException {
			return new LoginResponse(this);
		}
	}
}