package com.simple_shop.item_service.item.controller;

import com.simple_shop.item_service.item.dto.ItemDto;
import com.simple_shop.item_service.item.exceptions.ItemValidationException;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.Item.ItemBuilder;
import com.simple_shop.item_service.item.model.MediaType;
import com.simple_shop.item_service.utils.DataTypeUtils;

public final class ItemDtoMapper {
	private ItemDtoMapper() {
		throw new UnsupportedOperationException();
	}

	public static Item toDomain(final ItemDto itemDto) {
		return new ItemBuilder()//
				.setId(itemDto.getId())//
				.setImageUrl(itemDto.getImageUrl())//
				.setLastChange(itemDto.getLastChange())//
				.setLastChangedBy(itemDto.getLastChangedBy())//
				.setMediaType(ItemDtoMapper.parseMediaType(itemDto.getMediaType()))//
				.setPrice(itemDto.getPrice())//
				.setStock(itemDto.getStock())//
				.setTitle(itemDto.getTitle())//
				.setVersion(itemDto.getVersion())//
				.build();
	}

	private static MediaType parseMediaType(final String mediaType) {
		try {
			return DataTypeUtils.parseEnum(mediaType, MediaType.class);
		} catch (final IllegalArgumentException e) {
			throw new ItemValidationException("Unknown media type: " + mediaType);
		}
	}

	/*
	 * This is the location to set the precision of decimals, should we ever have
	 * them and need precision. Do not solve this using the formatters.
	 */
	public static ItemDto toDto(final Item item) {
		final ItemDto itemDto = new ItemDto();

		itemDto.setId(item.getId());
		itemDto.setImageUrl(item.getImageUrl());
		itemDto.setLastChange(item.getLastChange());
		itemDto.setLastChangedBy(item.getLastChangedBy());
		itemDto.setMediaType(item.getMediaType().name());
		itemDto.setPrice(item.getPrice());
		itemDto.setStock(item.getStock());
		itemDto.setTitle(item.getTitle());
		itemDto.setVersion(item.getVersion());

		return itemDto;
	}
}
