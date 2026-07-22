package com.simple_shop.item_service.item.persistence;

import java.time.LocalDateTime;

import com.simple_shop.item_service.item.model.ItemAction;
import com.simple_shop.item_service.item.model.MediaType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ItemHistoryState", schema = "item_shop")
public class ItemHistoryStateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name = "ItemId", nullable = false)
	private Long itemId;

	@Column(name = "Title", length = 400, nullable = false)
	private String title;

	@Enumerated(EnumType.STRING)
	@Column(name = "MediaType", length = 20, nullable = false)
	private MediaType mediaType;

	@Column(name = "Stock", nullable = false)
	private Integer stock;

	@Column(name = "Price", nullable = false)
	private Integer price;

	@Column(name = "ImageUrl", length = 2048, nullable = false)
	private String imageUrl;

	/**
	 * In UTC.
	 */
	@Column(name = "LastChange", nullable = false)
	private LocalDateTime lastChange;

	@Column(name = "LastChangedBy", nullable = false)
	private Long lastChangedBy;

	@Enumerated(EnumType.STRING)
	@Column(name = "Action", length = 10, nullable = false)
	private ItemAction action;

	@Column(name = "Version", nullable = false)
	private Long version;

	public ItemHistoryStateEntity() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(final Long itemId) {
		this.itemId = itemId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public MediaType getMediaType() {
		return this.mediaType;
	}

	public void setMediaType(final MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public Integer getStock() {
		return this.stock;
	}

	public void setStock(final Integer stock) {
		this.stock = stock;
	}

	public Integer getPrice() {
		return this.price;
	}

	public void setPrice(final Integer price) {
		this.price = price;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public LocalDateTime getLastChange() {
		return this.lastChange;
	}

	public void setLastChange(final LocalDateTime lastChange) {
		this.lastChange = lastChange;
	}

	public Long getLastChangedBy() {
		return this.lastChangedBy;
	}

	public void setLastChangedBy(final Long lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}

	public ItemAction getAction() {
		return this.action;
	}

	public void setAction(final ItemAction action) {
		this.action = action;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(final Long version) {
		this.version = version;
	}
}