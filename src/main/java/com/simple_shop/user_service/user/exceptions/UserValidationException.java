package com.simple_shop.user_service.user.exceptions;

public final class UserValidationException extends IllegalArgumentException {
	private static final long serialVersionUID = 3126015455269952391L;

	public UserValidationException() {
		super();
	}

	public UserValidationException(final String message) {
		super(message);
	}

	public UserValidationException(final Throwable throwable) {
		super(throwable);
	}

	public UserValidationException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
