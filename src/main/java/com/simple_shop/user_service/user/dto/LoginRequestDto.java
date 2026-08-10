package com.simple_shop.user_service.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.simple_shop.user_service.user.dto.formatter.JsonLongFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequestDto {
	@JsonLongFormatter
	private Long userId;

	private String passwordHash;

	public LoginRequestDto() {
		super();
	}

	public Long getUserId() {
		return this.userId;
	}

	public LoginRequestDto setUserId(final Long userId) {
		this.userId = userId;

		return this;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public LoginRequestDto setPasswordHash(final String passwordHash) {
		this.passwordHash = passwordHash;

		return this;
	}
}