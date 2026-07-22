package com.simple_shop.item_service.item.dto;

import java.time.Instant;

public final class ItemServiceErrorResponseDto {
	private final Instant timestamp;

	private final String message;

	private final String detail;

	private final int httpState;

	private ItemServiceErrorResponseDto(final ItemServiceErrorResponseDtoBuilder builder) throws IllegalStateException {
		super();

		builder.validate();

		this.timestamp = builder.getTimestamp();
		this.message = builder.getMessage();
		this.detail = builder.getDetail();
		this.httpState = builder.getHttpState();
	}

	public Instant getTimestamp() {
		return this.timestamp;
	}

	public String getMessage() {
		return this.message;
	}

	public String getDetail() {
		return this.detail;
	}

	public int getHttpState() {
		return this.httpState;
	}

	public static final class ItemServiceErrorResponseDtoBuilder {
		private Instant timestamp;

		private String message;

		private String detail;

		private Integer httpState;

		public ItemServiceErrorResponseDtoBuilder() {
			super();
		}

		public Instant getTimestamp() {
			return this.timestamp;
		}

		public ItemServiceErrorResponseDtoBuilder setTimestamp(final Instant timestamp) {
			this.timestamp = timestamp;

			return this;
		}

		public String getMessage() {
			return this.message;
		}

		public ItemServiceErrorResponseDtoBuilder setMessage(final String message) {
			this.message = message;

			return this;
		}

		public String getDetail() {
			return this.detail;
		}

		public ItemServiceErrorResponseDtoBuilder setDetail(final String detail) {
			this.detail = detail;

			return this;
		}

		public Integer getHttpState() {
			return this.httpState;
		}

		public ItemServiceErrorResponseDtoBuilder setHttpState(final Integer httpState) {
			this.httpState = httpState;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.timestamp == null) {
				throw new IllegalStateException("No timestamp!");
			}

			if (this.message == null) {
				throw new IllegalStateException("No message!");
			}

			if (this.httpState == null) {
				throw new IllegalStateException("No HTTP state!");
			}
		}

		public ItemServiceErrorResponseDto build() {
			return new ItemServiceErrorResponseDto(this);
		}
	}
}