package com.simple_shop.user_service.user.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.simple_shop.user_service.user.dto.formatter.JsonLocalDateTimeFormatter;
import com.simple_shop.user_service.user.dto.formatter.JsonLongFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
	@JsonLongFormatter
	private Long id;

	private String username;

	private String firstName;

	private List<String> middleNames;

	private String lastName;

	private String email;

	private String userType;

	private String srpSalt;

	private String srpVerifier;

	/**
	 * In UTC.
	 */
	@JsonLocalDateTimeFormatter
	private LocalDateTime lastChange;

	@JsonLongFormatter
	private Long lastChangedBy;

	@JsonLongFormatter
	private Long version;

	public UserDto() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public UserDto setId(final Long id) {
		this.id = id;

		return this;
	}

	public String getUsername() {
		return this.username;
	}

	public UserDto setUsername(final String username) {
		this.username = username;

		return this;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public UserDto setFirstName(final String firstName) {
		this.firstName = firstName;

		return this;
	}

	public List<String> getMiddleNames() {
		return this.middleNames;
	}

	public UserDto setMiddleNames(final List<String> middleNames) {
		this.middleNames = middleNames;

		return this;
	}

	public String getLastName() {
		return this.lastName;
	}

	public UserDto setLastName(final String lastName) {
		this.lastName = lastName;

		return this;
	}

	public String getEmail() {
		return this.email;
	}

	public UserDto setEmail(final String email) {
		this.email = email;

		return this;
	}

	public String getUserType() {
		return this.userType;
	}

	public UserDto setUserType(final String userType) {
		this.userType = userType;

		return this;
	}

	public String getSrpSalt() {
		return this.srpSalt;
	}

	public UserDto setSrpSalt(final String srpSalt) {
		this.srpSalt = srpSalt;

		return this;
	}

	public String getSrpVerifier() {
		return this.srpVerifier;
	}

	public UserDto setSrpVerifier(final String srpVerifier) {
		this.srpVerifier = srpVerifier;

		return this;
	}

	public LocalDateTime getLastChange() {
		return this.lastChange;
	}

	public UserDto setLastChange(final LocalDateTime lastChange) {
		this.lastChange = lastChange;

		return this;
	}

	public Long getLastChangedBy() {
		return this.lastChangedBy;
	}

	public UserDto setLastChangedBy(final Long lastChangedBy) {
		this.lastChangedBy = lastChangedBy;

		return this;
	}

	public Long getVersion() {
		return this.version;
	}

	public UserDto setVersion(final Long version) {
		this.version = version;

		return this;
	}

	public static enum UserDtoMember {
		ID("id"),

		USERNAME("username"),

		FIRST_NAME("firstName"),

		MIDDLE_NAMES("middleNames"),

		LAST_NAME("lastName"),

		EMAIL("email"),

		USER_TYPE("userType"),

		SRP_SALT("srpSalt"),

		SRP_VERIFIER("srpVerifier"),

		LAST_CHANGE("lastChange"),

		LAST_CHANGED_BY("lastChangedBy"),

		VERSION("version"),

		;

		private final String domainName;

		private UserDtoMember(final String domainName) {
			this.domainName = domainName;
		}

		public String getDomainName() {
			return this.domainName;
		}
	}
}