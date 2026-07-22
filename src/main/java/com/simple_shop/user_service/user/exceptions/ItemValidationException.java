package com.simple_shop.item_service.item.exceptions;

public final class ItemValidationException extends IllegalArgumentException {
	private static final long serialVersionUID = 3126015455269952391L;

	public ItemValidationException() {
		super();
	}

	public ItemValidationException(final String message) {
		super(message);
	}

	public ItemValidationException(final Throwable throwable) {
		super(throwable);
	}

	public ItemValidationException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
