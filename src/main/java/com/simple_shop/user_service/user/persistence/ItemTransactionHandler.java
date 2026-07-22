package com.simple_shop.item_service.item.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.Item.ItemBuilder;
import com.simple_shop.item_service.item.model.ItemAction;
import com.simple_shop.item_service.item.model.ItemHistoryState;
import com.simple_shop.item_service.item.model.ItemHistoryState.ItemHistoryStateBuilder;
import com.simple_shop.item_service.item.repository.ItemEntityRepository;
import com.simple_shop.item_service.item.repository.ItemHistoryStateEntityRepository;
import com.simple_shop.item_service.utils.ItemServiceUtils;

@Component
public class ItemTransactionHandler {
	private final ItemEntityRepository itemRepository;

	private final ItemHistoryStateEntityRepository itemHistoryStateRepository;

	public ItemTransactionHandler(//
			final ItemEntityRepository itemRepository, final ItemHistoryStateEntityRepository historyRepository) {

		super();

		this.itemRepository = itemRepository;
		this.itemHistoryStateRepository = historyRepository;
	}

	@Transactional(readOnly = true)
	public Page<Item> findAll(final Pageable pageable) {
		final Page<ItemEntity> entityPage = this.itemRepository.findAll(pageable);

		return entityPage.map(ItemEntityMapper::toDomain);
	}

	@Transactional(readOnly = true)
	public Item findById(final long id) {
		final ItemEntity entity = this.itemRepository.findById(id).orElse(null); //
		return entity == null ? null : ItemEntityMapper.toDomain(entity);
	}

	@Transactional
	public Item saveItem(final Item item, final ItemAction itemAction) {
		final ItemEntity itemEntity = ItemEntityMapper.toEntity(item);

		/*
		 * Flushing makes sure, the Id and Version fields are properly created / updated
		 * before going on.
		 */
		final ItemEntity savedItemEntity = this.itemRepository.saveAndFlush(itemEntity);

		final Item savedItem = ItemEntityMapper.toDomain(savedItemEntity);

		final ItemHistoryState itemHistoryState = ItemTransactionHandler.buildItemHistoryState(savedItem, itemAction);
		final ItemHistoryStateEntity itemHistoryStateEntity = ItemEntityMapper.toEntity(itemHistoryState);
		this.itemHistoryStateRepository.save(itemHistoryStateEntity);

		return savedItem;
	}

	@Transactional
	public Item deleteItem(final long itemId, final long userId) {
		final ItemEntity existingItemEntity = this.itemRepository.findById(itemId).orElse(null);
		if (existingItemEntity == null) {
			return null;
		}

		final Item existingItem = ItemEntityMapper.toDomain(existingItemEntity);

		/*
		 * The main itemEntity remains unmutated so it can be cleanly deleted via its
		 * current database version.
		 */
		final Item itemDeletionState = ItemTransactionHandler.finalizeItem(existingItem, userId);
		final ItemHistoryState itemHistoryState = ItemTransactionHandler.buildItemHistoryState(itemDeletionState,
				ItemAction.DELETE);
		final ItemHistoryStateEntity itemHistoryStateEntity = ItemEntityMapper.toEntity(itemHistoryState);

		this.itemRepository.delete(existingItemEntity);
		this.itemHistoryStateRepository.save(itemHistoryStateEntity);

		return existingItem;
	}

	private static ItemHistoryState buildItemHistoryState(final Item item, final ItemAction action) {
		return new ItemHistoryStateBuilder() //
				.setId(null) //
				.setItemId(item.getId()) //
				.setTitle(item.getTitle()) //
				.setMediaType(item.getMediaType()) //
				.setStock(item.getStock()) //
				.setPrice(item.getPrice()) //
				.setImageUrl(item.getImageUrl()) //
				.setLastChange(item.getLastChange()) //
				.setLastChangedBy(item.getLastChangedBy()) //
				.setAction(action) //
				.setVersion(item.getVersion())//
				.build();
	}

	public static Item finalizeItem(final Item item, final long userId) {
		return ItemBuilder.initFrom(item) //
				.setLastChange(ItemServiceUtils.createNow()) //
				.setLastChangedBy(userId) //
				.build();
	}
}
