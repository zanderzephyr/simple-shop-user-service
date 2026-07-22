package com.simple_shop.item_service.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public final class ItemServiceUtils {
	private ItemServiceUtils() {
		throw new UnsupportedOperationException();
	}

	public static LocalDateTime createNow() {
		return ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
	}
}