package com.simple_shop.user_service.user.controller;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_shop.user_service.user.dto.UserDto;
import com.simple_shop.user_service.user.dto.UserDto.UserDtoMember;
import com.simple_shop.user_service.user.dto.UserRequestConstraintsDto;
import com.simple_shop.user_service.user.dto.UserRequestConstraintsDto.SortingDirection;
import com.simple_shop.user_service.user.exceptions.InvalidRequestException;
import com.simple_shop.user_service.user.model.User;
import com.simple_shop.user_service.user.persistence.UserEntity.UserEntityMember;
import com.simple_shop.user_service.user.service.UserService;

/*
 * Proper version compatibility between frontend and backend is quite complex
 * and difficult to manage. For now, we simply leave the version segment as a
 * last resort if something needs a hard break in handling properly. For that
 * case, another controller with an incremented version could be created.
 */
@RestController
@RequestMapping("/api/users/v1")
public final class UserController {
	private static final long MAX_PAGE_SIZE = 100;

	private static final Map<String, String> SORTABLE_DTO_TO_ENTITY_COLUMNS = UserController.collectSortableColumns();

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	private static Map<String, String> collectSortableColumns() {
		final Map<String, String> columns = new HashMap<>();

		columns.put(UserDtoMember.USERNAME.getDomainName(), UserEntityMember.USERNAME.getDomainName());
		columns.put(UserDtoMember.FIRST_NAME.getDomainName(), UserEntityMember.FIRST_NAME.getDomainName());
		columns.put(UserDtoMember.LAST_NAME.getDomainName(), UserEntityMember.LAST_NAME.getDomainName());
		columns.put(UserDtoMember.EMAIL.getDomainName(), UserEntityMember.EMAIL.getDomainName());
		columns.put(UserDtoMember.USER_TYPE.getDomainName(), UserEntityMember.USER_TYPE.getDomainName());
		columns.put(UserDtoMember.LAST_CHANGE.getDomainName(), UserEntityMember.LAST_CHANGE.getDomainName());
		columns.put(UserDtoMember.LAST_CHANGED_BY.getDomainName(), UserEntityMember.LAST_CHANGED_BY.getDomainName());

		return columns;
	}

	public UserController(final UserService userService, final PasswordEncoder passwordEncoder) {
		super();

		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody final UserDto userDto) {
		/*
		 * TODO If there's a valid session: Make sure, it's an admin. If it isn't or any
		 * other problem arises, answer appropriately with negative response.
		 *
		 * If there is no session, we are in the registration phase for some new user.
		 */

		final User userToCreate = DtoMapper.toDomain(userDto, this.passwordEncoder);

		// TODO If there's a session, use the user ID of the session instead
		final User createdUser = this.userService.createUser(userToCreate, null);
		final UserDto createdUserDto = DtoMapper.toDto(createdUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> modifyUser(@PathVariable final long userId, @RequestBody final UserDto userDto) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		final User userToModify = DtoMapper.toDomain(userDto, this.passwordEncoder);
		if (!Long.valueOf(userId).equals(userToModify.getId())) {
			throw new InvalidRequestException("Path ID and payload ID mismatch!");
		}
		if (userDto.getVersion() == null) {
			throw new InvalidRequestException("Version must not be null!");
		}

		// TODO Replace userId with actual session user ID
		final User modifiedUser = this.userService.modifyUser(userToModify, 42l);
		final UserDto modifiedUserDto = DtoMapper.toDto(modifiedUser);
		return ResponseEntity.status(HttpStatus.OK).body(modifiedUserDto);
	}

	/*
	 * No version checking here for optimistic locking. A delete operation is valid
	 * even on stale data.
	 */
	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable final long userId) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		// TODO Replace userId with actual session user ID
		this.userService.deleteUser(userId, 42l);

		/*
		 * NO_CONTENT (204) means no response body. If we return OK (200), there would
		 * have to be a body.
		 */
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	// Using ModelAttribute to prevent GET requests without body causing issues
	@GetMapping
	public ResponseEntity<PagedModel<UserDto>> getUsers(@ModelAttribute final UserRequestConstraintsDto constraints) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		this.validate(constraints);

		final Pageable pageable = this.createPageable(constraints);
		final Page<UserDto> page = this.userService.getAllUsers(pageable).map(DtoMapper::toDto);
		final PagedModel<UserDto> pagedModel = new PagedModel<>(page);
		return ResponseEntity.status(HttpStatus.OK).body(pagedModel);
	}

	private Pageable createPageable(final UserRequestConstraintsDto constraints) {
		final SortingDirection sortingDirection = constraints.getSortingDirection();
		final Direction direction;
		if (SortingDirection.ASCENDING.equals(sortingDirection)) {
			direction = Direction.ASC;
		} else if (SortingDirection.DESCENDING.equals(sortingDirection)) {
			direction = Direction.DESC;
		} else {
			throw new InvalidRequestException("Unexpected sorting direction: " + sortingDirection);
		}
		final String sortingColumnEntity = UserController.SORTABLE_DTO_TO_ENTITY_COLUMNS
				.get(constraints.getSortingColumn());
		final Sort sort = Sort.by(Arrays.asList(new Order(direction, sortingColumnEntity)));

		return PageRequest.of(constraints.getPageIndex(), constraints.getPageSize(), sort);
	}

	private void validate(final UserRequestConstraintsDto constraints) throws InvalidRequestException {
		if (constraints == null) {
			throw new InvalidRequestException("No user search constraints!");
		}

		final int pageIndex = constraints.getPageIndex();
		if (pageIndex < 0) {
			throw new InvalidRequestException("Page index must not be negative, but was " + pageIndex);
		}

		final int pageSize = constraints.getPageSize();
		if (pageSize <= 0 || pageSize > UserController.MAX_PAGE_SIZE) {
			throw new InvalidRequestException("Page size must be in [1," + UserController.MAX_PAGE_SIZE + "]!");
		}

		final String sortingColumn = constraints.getSortingColumn();
		if (!UserController.SORTABLE_DTO_TO_ENTITY_COLUMNS.keySet().contains(sortingColumn)) {
			throw new InvalidRequestException("Columns " + sortingColumn + " is not allowed for sorting!");
		}
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable final long userId) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		final User user = this.userService.getUserById(userId);
		final UserDto userDto = DtoMapper.toDto(user);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}
}