package com.simple_shop.user_service.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.simple_shop.user_service.user.exceptions.InvalidMessageException;
import com.simple_shop.user_service.user.exceptions.UserNotFoundException;
import com.simple_shop.user_service.user.model.LoginRequest;
import com.simple_shop.user_service.user.model.LoginResponse;
import com.simple_shop.user_service.user.model.LoginResponse.LoginResponseBuilder;
import com.simple_shop.user_service.user.model.User;
import com.simple_shop.user_service.user.model.UserAction;
import com.simple_shop.user_service.user.persistence.UserTransactionHandler;

@Service
public final class UserService {
	private final UserTransactionHandler userTransactionHandler;

	public UserService(final UserTransactionHandler userTransactionHandler) {
		super();

		this.userTransactionHandler = userTransactionHandler;
	}

	public Page<User> getUsers(final Pageable pageable) {
		return this.userTransactionHandler.findAll(pageable);
	}

	public User getUserById(final long id) {
		final User user = this.findUserById(id);
		return user;
	}

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

	public User modifyUser(final User userToModify, final long executingUserId) {
		// Ensure the user exists
		final User existingUser = this.findUserById(userToModify.getId());

		final User finalizedUser = UserTransactionHandler.finalizeUserForDbProcessing(//
				userToModify, existingUser, executingUserId);

		final User modifiedUser = this.userTransactionHandler.saveUser(finalizedUser, UserAction.MODIFY);

		return modifiedUser;
	}

	public void deleteUser(final long userId, final long executingUserId) {
		final User user = this.userTransactionHandler.deleteUser(userId, executingUserId);
		if (user == null) {
			throw new UserNotFoundException(UserService.createUserNotFoundMessage(userId));
		}
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
			if (user.getPasswordHash().equals(loginRequest.getPasswordHash())) {
				loginResponseBuilder.setCredentialsOk(true);
			}
		} else {
			loginResponseBuilder.setCredentialsOk(false);
		}

		return loginResponseBuilder.build();
	}
}
