package com.simple_shop.item_service.item.model;

import java.time.LocalDateTime;

public final class ItemHistoryState {
	private final Long id;

	private final Long itemId;

	private final String title;

	private final MediaType mediaType;

	private final int stock;

	private final int price;

	private final String imageUrl;

	private final LocalDateTime lastChange;

	private final Long lastChangedBy;

	private final ItemAction action;

	private final Long version;

	private ItemHistoryState(final ItemHistoryStateBuilder builder) {
		super();

		builder.validate();

		this.id = builder.getId();
		this.itemId = builder.getItemId();
		this.title = builder.getTitle();
		this.mediaType = builder.getMediaType();
		this.stock = builder.getStock();
		this.price = builder.getPrice();
		this.imageUrl = builder.getImageUrl();
		this.lastChange = builder.getLastChange();
		this.lastChangedBy = builder.getLastChangedBy();
		this.action = builder.getAction();
		this.version = builder.getVersion();
	}

	public Long getId() {
		return this.id;
	}

	public Long getItemId() {
		return this.itemId;
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

	public ItemAction getAction() {
		return this.action;
	}

	public Long getVersion() {
		return this.version;
	}

	public static final class ItemHistoryStateBuilder {
		private Long id;

		private Long itemId;

		private String title;

		private MediaType mediaType;

		private Integer stock;

		private Integer price;

		private String imageUrl;

		private LocalDateTime lastChange;

		private Long lastChangedBy;

		private ItemAction action;

		private Long version;

		public ItemHistoryStateBuilder() {
			super();
		}

		public Long getId() {
			return this.id;
		}

		public ItemHistoryStateBuilder setId(final Long id) {
			this.id = id;

			return this;
		}

		public Long getItemId() {
			return this.itemId;
		}

		public ItemHistoryStateBuilder setItemId(final Long itemId) {
			this.itemId = itemId;

			return this;
		}

		public String getTitle() {
			return this.title;
		}

		public ItemHistoryStateBuilder setTitle(final String title) {
			this.title = title;

			return this;
		}

		public MediaType getMediaType() {
			return this.mediaType;
		}

		public ItemHistoryStateBuilder setMediaType(final MediaType mediaType) {
			this.mediaType = mediaType;

			return this;
		}

		public Integer getStock() {
			return this.stock;
		}

		public ItemHistoryStateBuilder setStock(final Integer stock) {
			this.stock = stock;

			return this;
		}

		public Integer getPrice() {
			return this.price;
		}

		public ItemHistoryStateBuilder setPrice(final Integer price) {
			this.price = price;

			return this;
		}

		public String getImageUrl() {
			return this.imageUrl;
		}

		public ItemHistoryStateBuilder setImageUrl(final String imageUrl) {
			this.imageUrl = imageUrl;

			return this;
		}

		public LocalDateTime getLastChange() {
			return this.lastChange;
		}

		public ItemHistoryStateBuilder setLastChange(final LocalDateTime lastChange) {
			this.lastChange = lastChange;

			return this;
		}

		public Long getLastChangedBy() {
			return this.lastChangedBy;
		}

		public ItemHistoryStateBuilder setLastChangedBy(final Long lastChangedBy) {
			this.lastChangedBy = lastChangedBy;

			return this;
		}

		public ItemAction getAction() {
			return this.action;
		}

		public ItemHistoryStateBuilder setAction(final ItemAction action) {
			this.action = action;

			return this;
		}

		public Long getVersion() {
			return this.version;
		}

		public ItemHistoryStateBuilder setVersion(final Long version) {
			this.version = version;

			return this;
		}

		public void validate() {
			if (this.title == null || this.title.isBlank()) {
				throw new IllegalArgumentException("Invalid title!");
			}

			if (this.mediaType == null) {
				throw new IllegalArgumentException("Invalid media type!");
			}

			if (this.stock == null || this.stock < 0) {
				throw new IllegalArgumentException("Invalid stock!");
			}

			if (this.price == null || this.price < 0) {
				throw new IllegalArgumentException("Invalid price!");
			}

			if (this.imageUrl == null || this.imageUrl.isBlank()) {
				throw new IllegalArgumentException("Invalid Image URL!");
			}

			if (this.action == null) {
				throw new IllegalArgumentException("Invalid action!");
			}
		}

		public ItemHistoryState build() {
			return new ItemHistoryState(this);
		}

		public static ItemHistoryStateBuilder initFrom(final ItemHistoryState itemHistory) {
			return new ItemHistoryStateBuilder()//
					.setId(itemHistory.getId())//
					.setItemId(itemHistory.getItemId())//
					.setImageUrl(itemHistory.getImageUrl())//
					.setLastChange(itemHistory.getLastChange())//
					.setLastChangedBy(itemHistory.getLastChangedBy())//
					.setMediaType(itemHistory.getMediaType())//
					.setPrice(itemHistory.getPrice())//
					.setStock(itemHistory.getStock())//
					.setTitle(itemHistory.getTitle())//
					.setAction(itemHistory.getAction())//
					.setVersion(itemHistory.getVersion());
		}
	}
}