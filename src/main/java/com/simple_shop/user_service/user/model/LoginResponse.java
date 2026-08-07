package com.simple_shop.user_service.user.model;

import java.util.Objects;

public final class LoginResponse {
	private final Long userId;

	private final boolean success;

	private LoginResponse(final LoginResponseBuilder builder) throws IllegalStateException {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.userId = builder.getUserId();
		this.success = builder.getSuccess();
	}

	public Long getUserId() {
		return this.userId;
	}

	public boolean getSuccess() {
		return this.success;
	}

	public static final class LoginResponseBuilder {
		private Long userId;

		private Boolean success;

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

		public Boolean getSuccess() {
			return this.success;
		}

		public LoginResponseBuilder setSuccess(final Boolean success) {
			this.success = success;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.userId != null && this.userId < 0) {
				throw new IllegalStateException("Invalid user ID!");
			}

			if (this.success == null) {
				throw new IllegalStateException("Invalid success!");
			}
		}

		public LoginResponse build() throws IllegalStateException {
			return new LoginResponse(this);
		}
	}
}