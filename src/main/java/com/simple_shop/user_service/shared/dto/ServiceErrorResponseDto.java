package com.simple_shop.user_service.shared.dto;

import java.time.Instant;

public final class ServiceErrorResponseDto {
	private Instant timestamp;

	private String message;

	private String detail;

	private Integer httpState;

	public ServiceErrorResponseDto() {
		super();
	}

	public Instant getTimestamp() {
		return this.timestamp;
	}

	public ServiceErrorResponseDto setTimestamp(final Instant timestamp) {
		this.timestamp = timestamp;

		return this;
	}

	public String getMessage() {
		return this.message;
	}

	public ServiceErrorResponseDto setMessage(final String message) {
		this.message = message;

		return this;
	}

	public String getDetail() {
		return this.detail;
	}

	public ServiceErrorResponseDto setDetail(final String detail) {
		this.detail = detail;

		return this;
	}

	public Integer getHttpState() {
		return this.httpState;
	}

	public ServiceErrorResponseDto setHttpState(final Integer httpState) {
		this.httpState = httpState;

		return this;
	}
}