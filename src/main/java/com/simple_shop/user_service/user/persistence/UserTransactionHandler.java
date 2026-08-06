package com.simple_shop.user_service.user.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.simple_shop.user_service.user.model.User;
import com.simple_shop.user_service.user.model.User.UserBuilder;
import com.simple_shop.user_service.user.model.UserAction;
import com.simple_shop.user_service.user.model.UserHistoryState;
import com.simple_shop.user_service.user.model.UserHistoryState.UserHistoryStateBuilder;
import com.simple_shop.user_service.user.repository.UserRepository;
import com.simple_shop.user_service.user.repository.UserHistoryStateRepository;
import com.simple_shop.user_service.utils.UserServiceUtils;

@Component
public class UserTransactionHandler {
	private final UserRepository userRepository;

	private final UserHistoryStateRepository userHistoryStateRepository;

	public UserTransactionHandler(//
			final UserRepository userRepository, final UserHistoryStateRepository historyRepository) {

		super();

		this.userRepository = userRepository;
		this.userHistoryStateRepository = historyRepository;
	}

	@Transactional(readOnly = true)
	public Page<User> findAll(final Pageable pageable) {
		final Page<UserEntity> entityPage = this.userRepository.findAll(pageable);

		return entityPage.map(PersistenceMapper::toDomain);
	}

	@Transactional(readOnly = true)
	public User findById(final long id) {
		final UserEntity entity = this.userRepository.findById(id).orElse(null); //
		return entity == null ? null : PersistenceMapper.toDomain(entity);
	}

	@Transactional
	public User saveUser(final User user, final UserAction userAction) {
		final UserEntity userEntity = PersistenceMapper.toEntity(user);

		/*
		 * Flushing makes sure, the Id and Version fields are properly created / updated
		 * before going on.
		 */
		final UserEntity savedUserEntity = this.userRepository.saveAndFlush(userEntity);

		final User savedUser = PersistenceMapper.toDomain(savedUserEntity);

		final UserHistoryState userHistoryState = UserTransactionHandler.buildUserHistoryState(savedUser, userAction);
		final UserHistoryStateEntity userHistoryStateEntity = PersistenceMapper.toEntity(userHistoryState);
		this.userHistoryStateRepository.save(userHistoryStateEntity);

		return savedUser;
	}

	@Transactional
	public User deleteUser(final long userId, final long executingUserId) {
		final UserEntity existingUserEntity = this.userRepository.findById(userId).orElse(null);
		if (existingUserEntity == null) {
			return null;
		}

		final User existingUser = PersistenceMapper.toDomain(existingUserEntity);

		/*
		 * The main userEntity remains unmutated so it can be cleanly deleted via its
		 * current database version.
		 */
		final User userDeletionState = UserTransactionHandler.finalizeUserForDbProcessing(//
				existingUser, existingUser, executingUserId);
		final UserHistoryState userHistoryState = UserTransactionHandler.buildUserHistoryState(userDeletionState,
				UserAction.DELETE);
		final UserHistoryStateEntity userHistoryStateEntity = PersistenceMapper.toEntity(userHistoryState);

		this.userRepository.delete(existingUserEntity);
		this.userHistoryStateRepository.save(userHistoryStateEntity);

		return existingUser;
	}

	private static UserHistoryState buildUserHistoryState(final User user, final UserAction action) {
		return new UserHistoryStateBuilder() //
				.setId(null) //
				.setUserId(user.getId()) //
				.setUsername(user.getUsername()) //
				.setFirstName(user.getFirstName()) //
				.setMiddleNames(user.getMiddleNames()) //
				.setLastName(user.getLastName()) //
				.setEmail(user.getEmail()) //
				.setUserType(user.getUserType()) //
				.setAction(action) //
				.setLastChange(user.getLastChange()) //
				.setLastChangedBy(user.getLastChangedBy()) //
				.setVersion(user.getVersion())//
				.build();
	}

	public static User finalizeUserForDbProcessing(//
			final User newState, final User previousState, final Long executingUserId) {

		final String passwordHash = newState.getPasswordHash();
		final String newPasswordHash = passwordHash != null ? passwordHash : previousState.getPasswordHash();

		return UserBuilder.initFrom(newState) //
				.setPasswordHash(newPasswordHash) //
				.setLastChange(UserServiceUtils.createNow()) //
				.setLastChangedBy(executingUserId) //
				.build();
	}
}
