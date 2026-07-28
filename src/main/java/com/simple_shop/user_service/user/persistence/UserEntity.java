package com.simple_shop.user_service.user.persistence;

import java.time.LocalDateTime;

import com.simple_shop.user_service.user.model.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "[User]")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name = "Username", length = 30, nullable = false)
	private String username;

	@Column(name = "FirstName", length = 30, nullable = false)
	private String firstName;

	@Column(name = "MiddleNames", length = 100, nullable = true)
	private String middleNames;

	@Column(name = "LastName", length = 30, nullable = false)
	private String lastName;

	@Column(name = "Email", length = 254, nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(name = "UserType", length = 20, nullable = false)
	private UserType userType;

	@Column(name = "PasswordHash", length = 128, nullable = false)
	private String passwordHash;

	/**
	 * In UTC.
	 */
	@Column(name = "LastChange", nullable = false)
	private LocalDateTime lastChange;

	@Column(name = "LastChangedBy", nullable = true)
	private Long lastChangedBy;

	@Version
	@Column(name = "Version", nullable = false)
	private Long version;

	public UserEntity() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleNames() {
		return this.middleNames;
	}

	public void setMiddleNames(final String middleNames) {
		this.middleNames = middleNames;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public UserType getUserType() {
		return this.userType;
	}

	public void setUserType(final UserType userType) {
		this.userType = userType;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(final String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public LocalDateTime getLastChange() {
		return this.lastChange;
	}

	public void setLastChange(final LocalDateTime lastChange) {
		this.lastChange = lastChange;
	}

	public Long getLastChangedBy() {
		return this.lastChangedBy;
	}

	public void setLastChangedBy(final Long lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}

	public Long getVersion() {
		return this.version;
	}

	/**
	 * Never call this without very good reason. It could disturb Hibernate's
	 * optimistic locking.
	 */
	public void setVersion(final Long version) {
		this.version = version;
	}

	public static enum UserEntityMember {
		ID("id"),

		USERNAME("username"),

		FIRST_NAME("firstName"),

		MIDDLE_NAMES("middleNames"),

		LAST_NAME("lastName"),

		EMAIL("email"),

		USER_TYPE("userType"),

		PASSWORD_HASH("passwordHash"),

		LAST_CHANGE("lastChange"),

		LAST_CHANGED_BY("lastChangedBy"),

		VERSION("version"),

		;

		private final String domainName;

		private UserEntityMember(final String domainName) {
			this.domainName = domainName;
		}

		public String getDomainName() {
			return this.domainName;
		}
	}
}