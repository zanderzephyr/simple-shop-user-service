package com.simple_shop.user_service.user.exceptions;

public final class InvalidUserRequestConstraintsException extends IllegalArgumentException {
	private static final long serialVersionUID = 3126015455269952391L;

	public InvalidUserRequestConstraintsException() {
		super();
	}

	public InvalidUserRequestConstraintsException(final String message) {
		super(message);
	}

	public InvalidUserRequestConstraintsException(final Throwable throwable) {
		super(throwable);
	}

	public InvalidUserRequestConstraintsException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
