package com.simple_shop.item_service.item.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_shop.item_service.item.dto.ItemDto;
import com.simple_shop.item_service.item.dto.ItemDto.ItemDtoMember;
import com.simple_shop.item_service.item.dto.ItemRequestConstraintsDto;
import com.simple_shop.item_service.item.dto.ItemRequestConstraintsDto.SortingDirection;
import com.simple_shop.item_service.item.exceptions.InvalidItemRequestConstraintsException;
import com.simple_shop.item_service.item.exceptions.ItemValidationException;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.persistence.ItemEntity.ItemEntityMember;
import com.simple_shop.item_service.item.service.ItemService;

/*
 * Proper version compatibility between frontend and backend is quite complex
 * and difficult to manage. For now, we simply leave the version segment as a
 * last resort if something needs a hard break in handling properly. For that
 * case, another controller with an incremented version could be created.
 */
@RestController
@RequestMapping("/api/v1/items")
public final class ItemController {
	private static final long MAX_PAGE_SIZE = 100;

	private static final Map<String, String> SORTABLE_DTO_TO_ENTITY_COLUMNS = ItemController.collectSortableColumns();

	private final ItemService itemService;

	private static Map<String, String> collectSortableColumns() {
		final Map<String, String> columns = new HashMap<>();

		columns.put(ItemDtoMember.LAST_CHANGE.getDomainName(), ItemEntityMember.LAST_CHANGE.getDomainName());
		columns.put(ItemDtoMember.LAST_CHANGED_BY.getDomainName(), ItemEntityMember.LAST_CHANGED_BY.getDomainName());
		columns.put(ItemDtoMember.MEDIA_TYPE.getDomainName(), ItemEntityMember.MEDIA_TYPE.getDomainName());
		columns.put(ItemDtoMember.PRICE.getDomainName(), ItemEntityMember.PRICE.getDomainName());
		columns.put(ItemDtoMember.STOCK.getDomainName(), ItemEntityMember.STOCK.getDomainName());
		columns.put(ItemDtoMember.TITLE.getDomainName(), ItemEntityMember.TITLE.getDomainName());

		return columns;
	}

	public ItemController(final ItemService itemService) {
		super();

		this.itemService = itemService;
	}

	@PostMapping
	public ResponseEntity<ItemDto> createItem(@RequestBody final ItemDto itemDto) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		final Item itemToCreate = ItemDtoMapper.toDomain(itemDto);

		// TODO Replace userId by actual session user ID
		final Item createdItem = this.itemService.createItem(itemToCreate, 42l);
		final ItemDto createdItemDto = ItemDtoMapper.toDto(createdItem);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdItemDto);
	}

	@PutMapping("/{itemId}")
	public ResponseEntity<ItemDto> modifyItem(@PathVariable final long itemId, @RequestBody final ItemDto itemDto) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		final Item itemToModify = ItemDtoMapper.toDomain(itemDto);
		if (!Long.valueOf(itemId).equals(itemToModify.getId())) {
			throw new ItemValidationException("Path ID and payload ID mismatch!");
		}
		if (itemDto.getVersion() == null) {
			throw new ItemValidationException("Version must not be null!");
		}

		// TODO Replace userId by actual session user ID
		final Item modifiedItem = this.itemService.modifyItem(itemToModify, 42l);
		final ItemDto modifiedItemDto = ItemDtoMapper.toDto(modifiedItem);
		return ResponseEntity.status(HttpStatus.OK).body(modifiedItemDto);
	}

	/*
	 * No version checking here for optimistic locking. A delete operation is valid
	 * even on stale data.
	 */
	@DeleteMapping("/{itemId}")
	public ResponseEntity<Void> deleteItem(@PathVariable final long itemId) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		// TODO Replace userId by actual session user ID
		this.itemService.deleteItem(itemId, 42l);

		/*
		 * NO_CONTENT (204) means no response body. If we return OK (200), there would
		 * have to be a body.
		 */
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	// Using ModelAttribute to prevent GET requests without body causing issues
	@GetMapping
	public ResponseEntity<PagedModel<ItemDto>> getItems(@ModelAttribute final ItemRequestConstraintsDto constraints) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		this.validate(constraints);

		final Pageable pageable = this.createPageable(constraints);
		final Page<ItemDto> page = this.itemService.getAllItems(pageable).map(ItemDtoMapper::toDto);
		final PagedModel<ItemDto> pagedModel = new PagedModel<>(page);
		return ResponseEntity.status(HttpStatus.OK).body(pagedModel);
	}

	private Pageable createPageable(final ItemRequestConstraintsDto constraints) {
		final SortingDirection sortingDirection = constraints.getSortingDirection();
		final Direction direction;
		if (SortingDirection.ASCENDING.equals(sortingDirection)) {
			direction = Direction.ASC;
		} else if (SortingDirection.DESCENDING.equals(sortingDirection)) {
			direction = Direction.DESC;
		} else {
			throw new IllegalStateException("Unexpected sorting direction: " + sortingDirection);
		}
		final String sortingColumnEntity = ItemController.SORTABLE_DTO_TO_ENTITY_COLUMNS
				.get(constraints.getSortingColumn());
		final Sort sort = Sort.by(Arrays.asList(new Order(direction, sortingColumnEntity)));

		return PageRequest.of(constraints.getPageIndex(), constraints.getPageSize(), sort);
	}

	private void validate(final ItemRequestConstraintsDto constraints) throws InvalidItemRequestConstraintsException {
		if (constraints == null) {
			throw new InvalidItemRequestConstraintsException("No item search constraints!");
		}

		final int pageIndex = constraints.getPageIndex();
		if (pageIndex < 0) {
			throw new InvalidItemRequestConstraintsException("Page index must not be negative, but was " + pageIndex);
		}

		final int pageSize = constraints.getPageSize();
		if (pageSize <= 0 || pageSize > ItemController.MAX_PAGE_SIZE) {
			throw new InvalidItemRequestConstraintsException(
					"Page size must be in [1," + ItemController.MAX_PAGE_SIZE + "]!");
		}

		final String sortingColumn = constraints.getSortingColumn();
		if (!ItemController.SORTABLE_DTO_TO_ENTITY_COLUMNS.keySet().contains(sortingColumn)) {
			throw new InvalidItemRequestConstraintsException(
					"Columns " + sortingColumn + " is not allowed for sorting!");
		}
	}

	@GetMapping("/{itemId}")
	public ResponseEntity<ItemDto> getItem(@PathVariable final long itemId) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		final Item item = this.itemService.getItemById(itemId);
		final ItemDto itemDto = ItemDtoMapper.toDto(item);
		return ResponseEntity.status(HttpStatus.OK).body(itemDto);
	}
}