package com.simple_shop.item_service.item.persistence;

import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.Item.ItemBuilder;
import com.simple_shop.item_service.item.model.ItemHistoryState;
import com.simple_shop.item_service.item.model.ItemHistoryState.ItemHistoryStateBuilder;

public final class ItemEntityMapper {
	private ItemEntityMapper() {
		throw new UnsupportedOperationException();
	}

	public static Item toDomain(final ItemEntity itemEntity) {
		return new ItemBuilder()//
				.setId(itemEntity.getId()) //
				.setImageUrl(itemEntity.getImageUrl()) //
				.setLastChange(itemEntity.getLastChange()) //
				.setLastChangedBy(itemEntity.getLastChangedBy()) //
				.setMediaType(itemEntity.getMediaType())//
				.setPrice(itemEntity.getPrice())//
				.setStock(itemEntity.getStock())//
				.setTitle(itemEntity.getTitle())//
				.setVersion(itemEntity.getVersion())//
				.build();
	}

	public static ItemEntity toEntity(final Item item) {
		final ItemEntity entity = new ItemEntity();

		entity.setId(item.getId());
		entity.setImageUrl(item.getImageUrl());
		entity.setLastChange(item.getLastChange());
		entity.setLastChangedBy(item.getLastChangedBy());
		entity.setMediaType(item.getMediaType());
		entity.setPrice(item.getPrice());
		entity.setStock(item.getStock());
		entity.setTitle(item.getTitle());
		entity.setVersion(item.getVersion());

		return entity;
	}

	public static ItemHistoryState toDomain(final ItemHistoryStateEntity itemHistoryStateEntity) {
		return new ItemHistoryStateBuilder()//
				.setAction(itemHistoryStateEntity.getAction())//
				.setId(itemHistoryStateEntity.getId()) //
				.setItemId(itemHistoryStateEntity.getItemId()) //
				.setImageUrl(itemHistoryStateEntity.getImageUrl()) //
				.setLastChange(itemHistoryStateEntity.getLastChange()) //
				.setLastChangedBy(itemHistoryStateEntity.getLastChangedBy()) //
				.setMediaType(itemHistoryStateEntity.getMediaType())//
				.setPrice(itemHistoryStateEntity.getPrice())//
				.setStock(itemHistoryStateEntity.getStock())//
				.setTitle(itemHistoryStateEntity.getTitle())//
				.setVersion(itemHistoryStateEntity.getVersion())//
				.build();
	}

	public static ItemHistoryStateEntity toEntity(final ItemHistoryState itemHistoryState) {
		final ItemHistoryStateEntity entity = new ItemHistoryStateEntity();

		entity.setAction(itemHistoryState.getAction());
		entity.setId(itemHistoryState.getId());
		entity.setItemId(itemHistoryState.getItemId());
		entity.setImageUrl(itemHistoryState.getImageUrl());
		entity.setLastChange(itemHistoryState.getLastChange());
		entity.setLastChangedBy(itemHistoryState.getLastChangedBy());
		entity.setMediaType(itemHistoryState.getMediaType());
		entity.setPrice(itemHistoryState.getPrice());
		entity.setStock(itemHistoryState.getStock());
		entity.setTitle(itemHistoryState.getTitle());
		entity.setVersion(itemHistoryState.getVersion());

		return entity;
	}
}
