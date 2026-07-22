package com.simple_shop.item_service.item.exceptions;

public final class ItemNotFoundException extends IllegalArgumentException {
	private static final long serialVersionUID = 3126015455269952391L;

	public ItemNotFoundException() {
		super();
	}

	public ItemNotFoundException(final String message) {
		super(message);
	}

	public ItemNotFoundException(final Throwable throwable) {
		super(throwable);
	}

	public ItemNotFoundException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
