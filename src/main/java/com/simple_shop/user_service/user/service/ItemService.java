package com.simple_shop.item_service.item.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.simple_shop.item_service.item.exceptions.ItemNotFoundException;
import com.simple_shop.item_service.item.exceptions.ItemValidationException;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.ItemAction;
import com.simple_shop.item_service.item.persistence.ItemTransactionHandler;

@Service
public final class ItemService {
	private final ItemTransactionHandler itemTransactionHandler;

	public ItemService(final ItemTransactionHandler itemTransactionHandler) {
		super();

		this.itemTransactionHandler = itemTransactionHandler;
	}

	public Page<Item> getAllItems(final Pageable pageable) {
		return this.itemTransactionHandler.findAll(pageable);
	}

	public Item getItemById(final long id) {
		final Item item = this.findItemById(id);
		return item;
	}

	public Item createItem(final Item itemToCreate, final long userId) {
		ItemService.validateItemToCreate(itemToCreate);

		final Item finalizedItem = ItemTransactionHandler.finalizeItem(itemToCreate, userId);
		final Item createdItem = this.itemTransactionHandler.saveItem(finalizedItem, ItemAction.CREATE);
		return createdItem;
	}

	private static void validateItemToCreate(final Item itemToCreate) {
		if (itemToCreate.getId() != null) {
			throw new ItemValidationException("An item to create must not have an ID!");
		}

		if (itemToCreate.getVersion() != null) {
			throw new ItemValidationException("An item to create must not have a version!");
		}
	}

	public Item modifyItem(final Item itemToModify, final long userId) {
		// Ensure the item exists
		this.findItemById(itemToModify.getId());

		final Item finalizedItem = ItemTransactionHandler.finalizeItem(itemToModify, userId);

		final Item modifiedItem = this.itemTransactionHandler.saveItem(finalizedItem, ItemAction.MODIFY);

		return modifiedItem;
	}

	public void deleteItem(final long itemId, final long userId) {
		final Item item = this.itemTransactionHandler.deleteItem(itemId, userId);
		if (item == null) {
			throw new ItemNotFoundException(ItemService.createItemNotFoundMessage(itemId));
		}
	}

	private Item findItemById(final long itemId) {
		final Item item = this.itemTransactionHandler.findById(itemId);
		if (item == null) {
			throw new ItemNotFoundException(ItemService.createItemNotFoundMessage(itemId));
		}
		return item;
	}

	private static String createItemNotFoundMessage(final long itemId) {
		return "Didn't find item with ID " + itemId + "!";
	}
}
