package com.simple_shop.user_service.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.simple_shop.user_service.user.dto.formatter.JsonLongFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserIdResponseDto {
	@JsonLongFormatter
	private Long userId;

	public UserIdResponseDto() {
		super();
	}

	public Long getUserId() {
		return this.userId;
	}

	public UserIdResponseDto setUserId(final Long userId) {
		this.userId = userId;

		return this;
	}
}