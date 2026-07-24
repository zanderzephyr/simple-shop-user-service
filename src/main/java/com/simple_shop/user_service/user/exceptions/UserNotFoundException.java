package com.simple_shop.user_service.user.exceptions;

public final class UserNotFoundException extends IllegalArgumentException {
	private static final long serialVersionUID = 3126015455269952391L;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(final String message) {
		super(message);
	}

	public UserNotFoundException(final Throwable throwable) {
		super(throwable);
	}

	public UserNotFoundException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
