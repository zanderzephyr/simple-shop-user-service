package com.simple_shop.user_service.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.simple_shop.user_service.user.dto.formatter.JsonLongFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDto {
	@JsonLongFormatter
	private Long userId;

	private Boolean success;

	public LoginResponseDto() {
		super();
	}

	public Long getUserId() {
		return this.userId;
	}

	public LoginResponseDto setUserId(final Long userId) {
		this.userId = userId;

		return this;
	}

	public Boolean getSuccess() {
		return this.success;
	}

	public LoginResponseDto setSuccess(final Boolean success) {
		this.success = success;

		return this;
	}
}