package com.simple_shop.user_service.user.model;

import java.util.Objects;

public final class LoginResponse {
	private final boolean success;

	private LoginResponse(final LoginResponseBuilder builder) throws IllegalStateException {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.success = builder.getSuccess();
	}

	public boolean getSuccess() {
		return this.success;
	}

	public static final class LoginResponseBuilder {
		private Boolean success;

		public LoginResponseBuilder() {
			super();
		}

		public Boolean getSuccess() {
			return this.success;
		}

		public LoginResponseBuilder setSuccess(final Boolean success) {
			this.success = success;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.success == null) {
				throw new IllegalStateException("Invalid success!");
			}
		}

		public LoginResponse build() throws IllegalStateException {
			return new LoginResponse(this);
		}
	}
}