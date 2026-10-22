package com.simple_shop.user_service.user.service;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.simple_shop.user_service.user.exceptions.InvalidMessageException;
import com.simple_shop.user_service.user.exceptions.UserNotFoundException;
import com.simple_shop.user_service.user.model.LoginRequest;
import com.simple_shop.user_service.user.model.LoginResponse;
import com.simple_shop.user_service.user.model.LoginResponse.LoginResponseBuilder;
import com.simple_shop.user_service.user.model.User;
import com.simple_shop.user_service.user.model.UserAction;
import com.simple_shop.user_service.user.model.UserType;
import com.simple_shop.user_service.user.persistence.UserTransactionHandler;
import com.simple_shop.user_service.user.service.redis.RedisUserData;
import com.simple_shop.user_service.user.service.redis.RedisUserData.RedisUserDataBuilder;
import com.simple_shop.user_service.user.service.redis.RedisUserService;

@Service
public final class UserService {
	private final UserTransactionHandler userTransactionHandler;

	private final RedisUserService redisUserService;

	private final PasswordEncoder passwordEncoder;

	public UserService(final UserTransactionHandler userTransactionHandler, final RedisUserService redisUserService,
			final PasswordEncoder passwordEncoder) {
		super();

		this.userTransactionHandler = userTransactionHandler;
		this.redisUserService = redisUserService;
		this.passwordEncoder = passwordEncoder;
	}

	public Page<User> getUsers(final Pageable pageable) {
		return this.userTransactionHandler.findAll(pageable);
	}

	public User getUserById(final long id) {
		final User user = this.findUserById(id);
		return user;
	}

	// TODO Deal with user already exists case
	public User createUser(final User userToCreate, final Long executingUserId) {
		UserService.validateUserToCreate(userToCreate);

		final User finalizedUser = UserTransactionHandler.finalizeUserForDbProcessing(//
				userToCreate, null, executingUserId);
		final User createdUser = this.userTransactionHandler.saveUser(finalizedUser, UserAction.CREATE);
		return createdUser;
	}

	private static void validateUserToCreate(final User userToCreate) {
		if (userToCreate.getId() != null) {
			throw new InvalidMessageException("A user to create must not have an ID!");
		}

		if (userToCreate.getPasswordHash() == null) {
			throw new InvalidMessageException("A user to create must have a password!");
		}

		if (userToCreate.getVersion() != null) {
			throw new InvalidMessageException("A user to create must not have a version!");
		}
	}

	public User modifyUser(final long userId, final User userToModify, final long executingUserId) {
		this.validateUserToModify(userId, userToModify);

		// Ensure the user exists
		final User existingUser = this.findUserById(userToModify.getId());

		final User finalizedUser = UserTransactionHandler.finalizeUserForDbProcessing(//
				userToModify, existingUser, executingUserId);

		final User modifiedUser = this.userTransactionHandler.saveUser(finalizedUser, UserAction.MODIFY);

		this.updateCache(modifiedUser, Boolean.FALSE);

		return modifiedUser;
	}

	private void validateUserToModify(final long userId, final User userToModify) {
		if (!Objects.equals(userId, userToModify.getId())) {
			throw new InvalidMessageException("Path ID and payload ID mismatch!");
		}

		if (userToModify.getVersion() == null) {
			throw new InvalidMessageException("Version must not be null!");
		}
	}

	public void deleteUser(final long userId, final long executingUserId) {
		final User user = this.userTransactionHandler.deleteUser(userId, executingUserId);
		if (user == null) {
			throw new UserNotFoundException(UserService.createUserNotFoundMessage(userId));
		}

		this.emptyCache(userId);
	}

	private User findUserById(final long userId) {
		final User user = this.userTransactionHandler.findById(userId);
		if (user == null) {
			throw new UserNotFoundException(UserService.createUserNotFoundMessage(userId));
		}
		return user;
	}

	private static String createUserNotFoundMessage(final long userId) {
		return "Didn't find user with ID " + userId + "!";
	}

	public LoginResponse verifyCredentials(final LoginRequest loginRequest) {
		final LoginResponseBuilder loginResponseBuilder = new LoginResponseBuilder();

		final User user = this.userTransactionHandler.findByUsername(loginRequest.getUsername());
		if (user != null) {
			loginResponseBuilder.setUserId(user.getId());
			if (this.passwordEncoder.matches(loginRequest.getPasswordHash(), user.getPasswordHash())) {
				loginResponseBuilder.setCredentialsOk(true);

				this.updateCache(user, Boolean.TRUE);
			} else {
				loginResponseBuilder.setCredentialsOk(false);
			}
		} else {
			loginResponseBuilder.setCredentialsOk(false);
		}

		return loginResponseBuilder.build();
	}

	public UserType determineUserType(final long userId) {
		final UserType userType;

		final RedisUserData cachedData = this.redisUserService.getUserEntry(userId);
		if (cachedData != null) {
			userType = cachedData.getUserType();
		} else {
			final User user = this.getUserById(userId);

			this.updateCache(user, Boolean.TRUE);

			userType = user.getUserType();
		}

		return userType;
	}

	public void emptyCache(final long userId) {
		this.redisUserService.deleteUserEntry(userId);
	}

	private void updateCache(final User user, final boolean addIfDoesntExist) {
		final RedisUserData existingEntry = this.redisUserService.getUserEntry(user.getId());
		if (existingEntry != null || addIfDoesntExist) {
			final RedisUserData newEntry = new RedisUserDataBuilder().setUserType(user.getUserType()).build();
			this.redisUserService.createUserEntry(user.getId(), newEntry);
		}
	}

	public static boolean isDataOwned(//
			final Long executingUserId, //
			final UserType executingUserType, //
			final long targetedUserId) {

		if (executingUserId != null && executingUserType != null) {
			return UserType.ADMIN.equals(executingUserType) || executingUserId.equals(targetedUserId);
		}
		return Boolean.TRUE;
	}
}
