package com.simple_shop.item_service.item.model;

import java.time.LocalDateTime;

import com.simple_shop.item_service.item.exceptions.ItemValidationException;

public final class Item {
	private final Long id;

	private final String title;

	private final MediaType mediaType;

	private final int stock;

	private final int price;

	private final String imageUrl;

	/**
	 * In UTC.
	 */
	private final LocalDateTime lastChange;

	private final Long lastChangedBy;

	private final Long version;

	/**
	 * @param  builder
	 *
	 * @throws ItemValidationException When the builder doesn't validate properly.
	 */
	private Item(final ItemBuilder builder) throws ItemValidationException {
		super();

		builder.validate();

		this.id = builder.getId();
		this.title = builder.getTitle();
		this.mediaType = builder.getMediaType();
		this.stock = builder.getStock();
		this.price = builder.getPrice();
		this.imageUrl = builder.getImageUrl();
		this.lastChange = builder.getLastChange();
		this.lastChangedBy = builder.getLastChangedBy();
		this.version = builder.getVersion();
	}

	public Long getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public MediaType getMediaType() {
		return this.mediaType;
	}

	public int getStock() {
		return this.stock;
	}

	public int getPrice() {
		return this.price;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public LocalDateTime getLastChange() {
		return this.lastChange;
	}

	public Long getLastChangedBy() {
		return this.lastChangedBy;
	}

	public Long getVersion() {
		return this.version;
	}

	public static final class ItemBuilder {
		private Long id;

		private String title;

		private MediaType mediaType;

		private Integer stock;

		private Integer price;

		private String imageUrl;

		/**
		 * In UTC.
		 */
		private LocalDateTime lastChange;

		private Long lastChangedBy;

		private Long version;

		public ItemBuilder() {
			super();
		}

		public Long getId() {
			return this.id;
		}

		public ItemBuilder setId(final Long id) {
			this.id = id;

			return this;
		}

		public String getTitle() {
			return this.title;
		}

		public ItemBuilder setTitle(final String title) {
			this.title = title;

			return this;
		}

		public MediaType getMediaType() {
			return this.mediaType;
		}

		public ItemBuilder setMediaType(final MediaType mediaType) {
			this.mediaType = mediaType;

			return this;
		}

		public Integer getStock() {
			return this.stock;
		}

		public ItemBuilder setStock(final Integer stock) {
			this.stock = stock;

			return this;
		}

		public Integer getPrice() {
			return this.price;
		}

		public ItemBuilder setPrice(final Integer price) {
			this.price = price;

			return this;
		}

		public String getImageUrl() {
			return this.imageUrl;
		}

		public ItemBuilder setImageUrl(final String imageUrl) {
			this.imageUrl = imageUrl;

			return this;
		}

		public LocalDateTime getLastChange() {
			return this.lastChange;
		}

		public ItemBuilder setLastChange(final LocalDateTime lastChange) {
			this.lastChange = lastChange;

			return this;
		}

		public Long getLastChangedBy() {
			return this.lastChangedBy;
		}

		public ItemBuilder setLastChangedBy(final Long lastChangedBy) {
			this.lastChangedBy = lastChangedBy;

			return this;
		}

		public Long getVersion() {
			return this.version;
		}

		public ItemBuilder setVersion(final Long version) {
			this.version = version;

			return this;
		}

		/**
		 * @throws ItemValidationException When a field is not properly set.
		 */
		public void validate() throws ItemValidationException {
			if (this.id != null && this.id < 0) {
				throw new ItemValidationException("Invalid ID!");
			}

			if (this.lastChangedBy != null && this.lastChangedBy < 0) {
				throw new ItemValidationException("Invalid last changed by!");
			}

			if (this.version != null && this.version < 0) {
				throw new ItemValidationException("Invalid version!");
			}

			if (this.title == null || this.title.isBlank() || this.title.length() > 400) {
				throw new ItemValidationException("Invalid title!");
			}

			if (this.mediaType == null) {
				throw new ItemValidationException("Invalid media type!");
			}

			if (this.stock == null || this.stock < 0) {
				throw new ItemValidationException("Invalid stock!");
			}

			if (this.price == null || this.price < 0) {
				throw new ItemValidationException("Invalid price!");
			}

			if (this.imageUrl == null || this.imageUrl.isBlank() || this.imageUrl.length() > 2048) {
				throw new ItemValidationException("Invalid Image URL!");
			}
		}

		/**
		 * @return
		 *
		 * @throws ItemValidationException When the builder doesn't validate properly.
		 */
		public Item build() throws ItemValidationException {
			return new Item(this);
		}

		public static ItemBuilder initFrom(final Item item) {
			return new ItemBuilder()//
					.setId(item.getId())//
					.setImageUrl(item.getImageUrl())//
					.setLastChange(item.getLastChange())//
					.setLastChangedBy(item.getLastChangedBy())//
					.setMediaType(item.getMediaType())//
					.setPrice(item.getPrice())//
					.setStock(item.getStock())//
					.setTitle(item.getTitle())//
					.setVersion(item.getVersion());
		}
	}
}