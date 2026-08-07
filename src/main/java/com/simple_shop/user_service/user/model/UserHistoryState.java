package com.simple_shop.user_service.user.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.simple_shop.user_service.utils.UserServiceUtils;

public final class UserHistoryState {
	private final Long id;

	private final Long userId;

	private final String username;

	private final String firstName;

	private final List<String> middleNames;

	private final String lastName;

	private final String email;

	private final UserType userType;

	private final LocalDateTime lastChange;

	private final Long lastChangedBy;

	private final UserAction action;

	private final Long version;

	private UserHistoryState(final UserHistoryStateBuilder builder) throws IllegalStateException {
		super();

		builder.validate();

		this.id = builder.getId();
		this.userId = builder.getUserId();
		this.username = builder.getUsername();
		this.firstName = builder.getFirstName();

		final List<String> middleNames = builder.getMiddleNames();
		this.middleNames = middleNames != null ? new ArrayList<>(middleNames) : new ArrayList<>();

		this.lastName = builder.getLastName();
		this.email = builder.getEmail();
		this.userType = builder.getUserType();
		this.action = builder.getAction();
		this.lastChange = builder.getLastChange();
		this.lastChangedBy = builder.getLastChangedBy();
		this.version = builder.getVersion();
	}

	public Long getId() {
		return this.id;
	}

	public Long getUserId() {
		return this.userId;
	}

	public String getUsername() {
		return this.username;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public List<String> getMiddleNames() {
		return this.middleNames;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public UserType getUserType() {
		return this.userType;
	}

	public UserAction getAction() {
		return this.action;
	}

	public LocalDateTime getLastChange() {
		return this.lastChange;
	}

	public Long getLastChangedBy() {
		return this.lastChangedBy;
	}

	public Long getVersion() {
		return this.version;
	}

	public static final class UserHistoryStateBuilder {
		private Long id;

		private Long userId;

		private String username;

		private String firstName;

		private List<String> middleNames;

		private String lastName;

		private String email;

		private UserType userType;

		private UserAction action;

		private LocalDateTime lastChange;

		private Long lastChangedBy;

		private Long version;

		public UserHistoryStateBuilder() {
			super();
		}

		public Long getId() {
			return this.id;
		}

		public UserHistoryStateBuilder setId(final Long id) {
			this.id = id;

			return this;
		}

		public Long getUserId() {
			return this.userId;
		}

		public UserHistoryStateBuilder setUserId(final Long userId) {
			this.userId = userId;

			return this;
		}

		public String getUsername() {
			return this.username;
		}

		public UserHistoryStateBuilder setUsername(final String username) {
			this.username = username;

			return this;
		}

		public String getFirstName() {
			return this.firstName;
		}

		public UserHistoryStateBuilder setFirstName(final String firstName) {
			this.firstName = firstName;

			return this;
		}

		public List<String> getMiddleNames() {
			return this.middleNames;
		}

		public UserHistoryStateBuilder setMiddleNames(final List<String> middleNames) {
			this.middleNames = middleNames;

			return this;
		}

		public String getLastName() {
			return this.lastName;
		}

		public UserHistoryStateBuilder setLastName(final String lastName) {
			this.lastName = lastName;

			return this;
		}

		public String getEmail() {
			return this.email;
		}

		public UserHistoryStateBuilder setEmail(final String email) {
			this.email = email;

			return this;
		}

		public UserType getUserType() {
			return this.userType;
		}

		public UserHistoryStateBuilder setUserType(final UserType userType) {
			this.userType = userType;

			return this;
		}

		public UserAction getAction() {
			return this.action;
		}

		public UserHistoryStateBuilder setAction(final UserAction action) {
			this.action = action;

			return this;
		}

		public LocalDateTime getLastChange() {
			return this.lastChange;
		}

		public UserHistoryStateBuilder setLastChange(final LocalDateTime lastChange) {
			this.lastChange = lastChange;

			return this;
		}

		public Long getLastChangedBy() {
			return this.lastChangedBy;
		}

		public UserHistoryStateBuilder setLastChangedBy(final Long lastChangedBy) {
			this.lastChangedBy = lastChangedBy;

			return this;
		}

		public Long getVersion() {
			return this.version;
		}

		public UserHistoryStateBuilder setVersion(final Long version) {
			this.version = version;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.id != null && this.id < 0) {
				throw new IllegalStateException("Invalid ID!");
			}

			if (this.userId == null || this.userId < 0) {
				throw new IllegalStateException("Invalid user ID!");
			}

			if (this.username == null || this.username.isBlank() || this.username.length() > 30) {
				throw new IllegalStateException("Invalid username!");
			}

			if (this.firstName == null || this.firstName.isBlank() || this.firstName.length() > 30) {
				throw new IllegalStateException("Invalid first name!");
			}

			final String middleNameString = UserServiceUtils.convertMiddleNames(this.middleNames);
			if (middleNameString != null && middleNameString.length() > 100) {
				throw new IllegalStateException("Invalid middle names!");
			}

			if (this.lastName == null || this.lastName.isBlank() || this.lastName.length() > 30) {
				throw new IllegalStateException("Invalid last name!");
			}

			if (this.email == null || this.email.isBlank() || this.email.length() > 254) {
				throw new IllegalStateException("Invalid email!");
			}

			if (this.userType == null) {
				throw new IllegalStateException("Invalid user type!");
			}

			if (this.action == null) {
				throw new IllegalStateException("Invalid action!");
			}

			if (this.lastChange == null) {
				throw new IllegalStateException("Invalid last change!");
			}

			if (this.lastChangedBy != null && this.lastChangedBy < 0) {
				throw new IllegalStateException("Invalid last changed by!");
			}

			if (this.version != null && this.version < 0) {
				throw new IllegalStateException("Invalid version!");
			}
		}

		public UserHistoryState build() throws IllegalStateException {
			return new UserHistoryState(this);
		}

		public static UserHistoryStateBuilder initFrom(final UserHistoryState userHistory) {
			return new UserHistoryStateBuilder()//
					.setId(userHistory.getId())//
					.setUserId(userHistory.getUserId())//
					.setUsername(userHistory.getUsername())//
					.setFirstName(userHistory.getFirstName())//
					.setMiddleNames(userHistory.getMiddleNames())//
					.setLastName(userHistory.getLastName())//
					.setEmail(userHistory.getEmail())//
					.setUserType(userHistory.getUserType())//
					.setAction(userHistory.getAction())//
					.setLastChange(userHistory.getLastChange())//
					.setLastChangedBy(userHistory.getLastChangedBy())//
					.setVersion(userHistory.getVersion());
		}
	}
}