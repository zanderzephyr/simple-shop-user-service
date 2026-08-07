package com.simple_shop.user_service.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequestDto {
	private String username;

	private String passwordHash;

	public LoginRequestDto() {
		super();
	}

	public String getUsername() {
		return this.username;
	}

	public LoginRequestDto setUsername(final String username) {
		this.username = username;

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