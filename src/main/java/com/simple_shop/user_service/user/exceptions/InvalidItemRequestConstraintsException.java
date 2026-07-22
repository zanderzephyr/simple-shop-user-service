package com.simple_shop.item_service.item.exceptions;

public final class InvalidItemRequestConstraintsException extends IllegalArgumentException {
	private static final long serialVersionUID = 3126015455269952391L;

	public InvalidItemRequestConstraintsException() {
		super();
	}

	public InvalidItemRequestConstraintsException(final String message) {
		super(message);
	}

	public InvalidItemRequestConstraintsException(final Throwable throwable) {
		super(throwable);
	}

	public InvalidItemRequestConstraintsException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
