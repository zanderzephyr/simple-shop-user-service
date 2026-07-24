package com.simple_shop.user_service.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class UserServiceUtils {
	private static final String MIDDLE_NAME_SEPARATOR = " ";

	private UserServiceUtils() {
		throw new UnsupportedOperationException();
	}

	public static LocalDateTime createNow() {
		return ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
	}

	public static String convertMiddleNames(final List<String> middleNames) {
		if (middleNames == null || middleNames.isEmpty()) {
			return null;
		}

		final StringBuilder sb = new StringBuilder();

		final Iterator<String> iterator = middleNames.iterator();
		while (iterator.hasNext()) {
			sb.append(iterator.next().trim());
			if (iterator.hasNext()) {
				sb.append(UserServiceUtils.MIDDLE_NAME_SEPARATOR);
			}
		}

		return sb.toString();
	}

	public static List<String> convertMiddleNames(final String middleNames) {
		if (middleNames == null || middleNames.isBlank()) {
			return new ArrayList<>();
		}
		return Arrays.asList(middleNames.trim().split(" "));
	}
}