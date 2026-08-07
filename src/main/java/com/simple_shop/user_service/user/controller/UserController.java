package com.simple_shop.user_service.user.controller;

import java.util.Arrays;

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

import com.simple_shop.user_service.user.dto.RequestConstraintsDto;
import com.simple_shop.user_service.user.dto.UserDto;
import com.simple_shop.user_service.user.exceptions.InvalidRequestException;
import com.simple_shop.user_service.user.model.RequestConstraints;
import com.simple_shop.user_service.user.model.SortingDirection;
import com.simple_shop.user_service.user.model.User;
import com.simple_shop.user_service.user.service.UserService;

@RestController
@RequestMapping("/api/users/v1/regular")
public final class UserController {
	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

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

		if (userDto == null) {
			throw new InvalidRequestException("No user!");
		}

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

		if (userDto == null) {
			throw new InvalidRequestException("No user!");
		}

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
	public ResponseEntity<PagedModel<UserDto>> getUsers(
			@ModelAttribute final RequestConstraintsDto userRequestConstraintsDto) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		if (userRequestConstraintsDto == null) {
			throw new InvalidRequestException("No user request constraints!");
		}

		final RequestConstraints userRequestConstraints = DtoMapper.toDomain(userRequestConstraintsDto);

		final Pageable pageable = this.createPageable(userRequestConstraints);
		final Page<UserDto> page = this.userService.getAllUsers(pageable).map(DtoMapper::toDto);
		final PagedModel<UserDto> pagedModel = new PagedModel<>(page);
		return ResponseEntity.status(HttpStatus.OK).body(pagedModel);
	}

	private Pageable createPageable(final RequestConstraints constraints) {
		final Direction direction = UserController.convert(constraints.getSortingDirection());
		final String sortingColumnEntity = constraints.getSortingColumn();
		final Sort sort = Sort.by(Arrays.asList(new Order(direction, sortingColumnEntity)));
		return PageRequest.of(constraints.getPageIndex(), constraints.getPageSize(), sort);
	}

	private static Direction convert(final SortingDirection sortingDirection) {
		final Direction direction;

		if (SortingDirection.ASCENDING.equals(sortingDirection)) {
			direction = Direction.ASC;
		} else if (SortingDirection.DESCENDING.equals(sortingDirection)) {
			direction = Direction.DESC;
		} else {
			throw new InvalidRequestException("Unexpected sorting direction: " + sortingDirection);
		}

		return direction;
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