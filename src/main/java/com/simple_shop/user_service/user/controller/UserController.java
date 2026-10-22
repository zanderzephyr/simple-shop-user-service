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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_shop.user_service.shared.dto.RequestConstraintsDto;
import com.simple_shop.user_service.shared.model.RequestConstraints;
import com.simple_shop.user_service.shared.model.SortingDirection;
import com.simple_shop.user_service.user.dto.UserDto;
import com.simple_shop.user_service.user.exceptions.InvalidMessageException;
import com.simple_shop.user_service.user.model.User;
import com.simple_shop.user_service.user.model.UserType;
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

	@PostMapping("/create-user")
	public ResponseEntity<UserDto> createUser(//
			@RequestHeader(name = "X-User-Id", required = false) final Long executingUserId, //
			@RequestBody final UserDto userDto) {

		final User userToCreate = DtoMapper.toDomain(userDto, this.passwordEncoder);
		final User createdUser = this.userService.createUser(userToCreate, executingUserId);
		final UserDto createdUserDto = DtoMapper.toDto(createdUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
	}

	@PutMapping("/modify-user/{userId}")
	public ResponseEntity<UserDto> modifyUser(//
			@RequestHeader(name = "X-User-Id") final long executingUserId, //
			@RequestHeader(name = "X-User-Type") final UserType executingUserType, //
			@PathVariable("userId") final long userId, //
			@RequestBody final UserDto userDto) {

		if (!UserService.isDataOwned(executingUserId, executingUserType, userId)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		final User userToModify = DtoMapper.toDomain(userDto, this.passwordEncoder);
		final User modifiedUser = this.userService.modifyUser(userId, userToModify, executingUserId);
		final UserDto modifiedUserDto = DtoMapper.toDto(modifiedUser);
		return ResponseEntity.status(HttpStatus.OK).body(modifiedUserDto);
	}

	/*
	 * No version checking here for optimistic locking. A delete operation is valid
	 * even on stale data.
	 */
	@DeleteMapping("/delete-user/{userId}")
	public ResponseEntity<Void> deleteUser(//
			@RequestHeader(name = "X-User-Id") final long executingUserId, //
			@RequestHeader(name = "X-User-Type") final UserType executingUserType, //
			@PathVariable("userId") final long userId) {

		if (!UserService.isDataOwned(executingUserId, executingUserType, userId)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		this.userService.deleteUser(userId, executingUserId);

		/*
		 * NO_CONTENT (204) means no response body. If we return OK (200), there would
		 * have to be a body.
		 */
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	// ModelAttribute: Different URL parameters are collected into this dto object.
	@GetMapping("/get-users")
	public ResponseEntity<PagedModel<UserDto>> getUsers(
			@ModelAttribute final RequestConstraintsDto requestConstraintsDto) {

		final RequestConstraints requestConstraints = DtoMapper.toDomain(requestConstraintsDto);

		final Pageable pageable = this.createPageable(requestConstraints);
		final Page<UserDto> page = this.userService.getUsers(pageable).map(DtoMapper::toDto);
		final PagedModel<UserDto> pagedModel = new PagedModel<>(page);
		return ResponseEntity.status(HttpStatus.OK).body(pagedModel);
	}

	// TODO Move to common lib
	private Pageable createPageable(final RequestConstraints constraints) {
		final Direction direction = UserController.convert(constraints.getSortingDirection());
		final String sortingColumnEntity = constraints.getSortingColumn();
		final Sort sort = Sort.by(Arrays.asList(new Order(direction, sortingColumnEntity)));
		return PageRequest.of(constraints.getPageIndex(), constraints.getPageSize(), sort);
	}

	// TODO Move to common lib
	private static Direction convert(final SortingDirection sortingDirection) {
		final Direction direction;

		if (SortingDirection.ASCENDING.equals(sortingDirection)) {
			direction = Direction.ASC;
		} else if (SortingDirection.DESCENDING.equals(sortingDirection)) {
			direction = Direction.DESC;
		} else {
			throw new InvalidMessageException("Unexpected sorting direction: " + sortingDirection);
		}

		return direction;
	}

	@GetMapping("/get-user/{userId}")
	public ResponseEntity<UserDto> getUser(//
			@RequestHeader(name = "X-User-Id") final long executingUserId, //
			@RequestHeader(name = "X-User-Type") final UserType executingUserType, //
			@PathVariable("userId") final long userId) {

		if (!UserService.isDataOwned(executingUserId, executingUserType, userId)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		final User user = this.userService.getUserById(userId);
		final UserDto userDto = DtoMapper.toDto(user);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}

	@GetMapping("/get-user-type/{userId}")
	public ResponseEntity<String> getUserType(//
			@RequestHeader(name = "X-User-Id", required = false) final Long executingUserId, //
			@RequestHeader(name = "X-User-Type", required = false) final UserType executingUserType, //
			@PathVariable("userId") final long userId) {

		if (!UserService.isDataOwned(executingUserId, executingUserType, userId)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		final UserType userType = this.userService.determineUserType(userId);
		return ResponseEntity.status(HttpStatus.OK).body(userType.name());
	}
}