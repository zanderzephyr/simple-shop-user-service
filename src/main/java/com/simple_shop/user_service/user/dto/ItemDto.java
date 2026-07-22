package com.simple_shop.item_service.item.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.simple_shop.item_service.item.dto.formatter.JsonLocalDateTimeFormatter;
import com.simple_shop.item_service.item.dto.formatter.JsonLongFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDto {
	@JsonLongFormatter
	private Long id;

	private String title;

	private String mediaType;

	private Integer stock;

	private Integer price;

	private String imageUrl;

	/**
	 * In UTC.
	 */
	@JsonLocalDateTimeFormatter
	private LocalDateTime lastChange;

	@JsonLongFormatter
	private Long lastChangedBy;

	@JsonLongFormatter
	private Long version;

	public ItemDto() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public ItemDto setId(final Long id) {
		this.id = id;

		return this;
	}

	public String getTitle() {
		return this.title;
	}

	public ItemDto setTitle(final String title) {
		this.title = title;

		return this;
	}

	public String getMediaType() {
		return this.mediaType;
	}

	public ItemDto setMediaType(final String mediaType) {
		this.mediaType = mediaType;

		return this;
	}

	public Integer getStock() {
		return this.stock;
	}

	public ItemDto setStock(final Integer stock) {
		this.stock = stock;

		return this;
	}

	public Integer getPrice() {
		return this.price;
	}

	public ItemDto setPrice(final Integer price) {
		this.price = price;

		return this;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public ItemDto setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;

		return this;
	}

	public LocalDateTime getLastChange() {
		return this.lastChange;
	}

	public ItemDto setLastChange(final LocalDateTime lastChange) {
		this.lastChange = lastChange;

		return this;
	}

	public Long getLastChangedBy() {
		return this.lastChangedBy;
	}

	public ItemDto setLastChangedBy(final Long lastChangedBy) {
		this.lastChangedBy = lastChangedBy;

		return this;
	}

	public Long getVersion() {
		return this.version;
	}

	public ItemDto setVersion(final Long version) {
		this.version = version;

		return this;
	}

	public static enum ItemDtoMember {
		ID("id"),

		TITLE("title"),

		MEDIA_TYPE("mediaType"),

		STOCK("stock"),

		PRICE("price"),

		IMAGE_URL("imageUrl"),

		LAST_CHANGE("lastChange"),

		LAST_CHANGED_BY("lastChangedBy"),

		VERSION("version"),

		;

		private final String domainName;

		private ItemDtoMember(final String domainName) {
			this.domainName = domainName;
		}

		public String getDomainName() {
			return this.domainName;
		}
	}
}