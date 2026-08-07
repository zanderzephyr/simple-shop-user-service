package com.simple_shop.user_service.user.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.simple_shop.user_service.utils.UserServiceUtils;

public final class User {
	private final Long id;

	private final String username;

	private final String firstName;

	private final List<String> middleNames;

	private final String lastName;

	private final String email;

	private final UserType userType;

	private final String passwordHash;

	/**
	 * In UTC.
	 */
	private final LocalDateTime lastChange;

	private final Long lastChangedBy;

	private final Long version;

	private User(final UserBuilder builder) throws IllegalStateException {
		super();

		builder.validate();

		this.id = builder.getId();
		this.username = builder.getUsername();
		this.firstName = builder.getFirstName();

		final List<String> middleNames = builder.getMiddleNames();
		this.middleNames = middleNames != null ? new ArrayList<>(middleNames) : new ArrayList<>();

		this.lastName = builder.getLastName();
		this.email = builder.getEmail();
		this.userType = builder.getUserType();
		this.passwordHash = builder.getPasswordHash();
		this.lastChange = builder.getLastChange();
		this.lastChangedBy = builder.getLastChangedBy();
		this.version = builder.getVersion();
	}

	public Long getId() {
		return this.id;
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

	public String getPasswordHash() {
		return this.passwordHash;
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

	public static final class UserBuilder {
		private Long id;

		private String username;

		private String firstName;

		private List<String> middleNames;

		private String lastName;

		private String email;

		private UserType userType;

		private String passwordHash;

		/**
		 * In UTC.
		 */
		private LocalDateTime lastChange;

		private Long lastChangedBy;

		private Long version;

		public UserBuilder() {
			super();
		}

		public Long getId() {
			return this.id;
		}

		public UserBuilder setId(final Long id) {
			this.id = id;

			return this;
		}

		public String getUsername() {
			return this.username;
		}

		public UserBuilder setUsername(final String username) {
			this.username = username;

			return this;
		}

		public String getFirstName() {
			return this.firstName;
		}

		public UserBuilder setFirstName(final String firstName) {
			this.firstName = firstName;

			return this;
		}

		public List<String> getMiddleNames() {
			return this.middleNames;
		}

		public UserBuilder setMiddleNames(final List<String> middleNames) {
			this.middleNames = middleNames;

			return this;
		}

		public String getLastName() {
			return this.lastName;
		}

		public UserBuilder setLastName(final String lastName) {
			this.lastName = lastName;

			return this;
		}

		public String getEmail() {
			return this.email;
		}

		public UserBuilder setEmail(final String email) {
			this.email = email;

			return this;
		}

		public UserType getUserType() {
			return this.userType;
		}

		public UserBuilder setUserType(final UserType userType) {
			this.userType = userType;

			return this;
		}

		public String getPasswordHash() {
			return this.passwordHash;
		}

		public UserBuilder setPasswordHash(final String passwordHash) {
			this.passwordHash = passwordHash;

			return this;
		}

		public LocalDateTime getLastChange() {
			return this.lastChange;
		}

		public UserBuilder setLastChange(final LocalDateTime lastChange) {
			this.lastChange = lastChange;

			return this;
		}

		public Long getLastChangedBy() {
			return this.lastChangedBy;
		}

		public UserBuilder setLastChangedBy(final Long lastChangedBy) {
			this.lastChangedBy = lastChangedBy;

			return this;
		}

		public Long getVersion() {
			return this.version;
		}

		public UserBuilder setVersion(final Long version) {
			this.version = version;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.id != null && this.id < 0) {
				throw new IllegalStateException("Invalid ID!");
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

			if (this.passwordHash != null && (this.passwordHash.isBlank() || this.passwordHash.length() > 128)) {
				throw new IllegalStateException("Invalid password hash!");
			}

			if (this.lastChangedBy != null && this.lastChangedBy < 0) {
				throw new IllegalStateException("Invalid last changed by!");
			}

			if (this.version != null && this.version < 0) {
				throw new IllegalStateException("Invalid version!");
			}
		}

		public User build() throws IllegalStateException {
			return new User(this);
		}

		public static UserBuilder initFrom(final User user) {
			return new UserBuilder()//
					.setId(user.getId())//
					.setUsername(user.getUsername())//
					.setFirstName(user.getFirstName())//
					.setMiddleNames(user.getMiddleNames())//
					.setLastName(user.getLastName())//
					.setEmail(user.getEmail())//
					.setUserType(user.getUserType())//
					.setPasswordHash(user.getPasswordHash())//
					.setLastChange(user.getLastChange())//
					.setLastChangedBy(user.getLastChangedBy())//
					.setVersion(user.getVersion());
		}
	}
}