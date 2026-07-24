package com.simple_shop.user_service.user.dto;

import com.simple_shop.user_service.user.dto.UserDto.UserDtoMember;

/*
 * Must never contain sensitive information, as the properties of this object
 * are passed via URL arguments.
 */
public final class UserRequestConstraintsDto {
	private int pageIndex = 0;

	private int pageSize = 20;

	/**
	 * This is the name of a member of the UserDto class.
	 */
	private String sortingColumn = UserDtoMember.USERNAME.getDomainName();

	private SortingDirection sortingDirection = SortingDirection.ASCENDING;

	public UserRequestConstraintsDto() {
		super();
	}

	public int getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(final int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortingColumn() {
		return this.sortingColumn;
	}

	public void setSortingColumn(final String sortingColumn) {
		this.sortingColumn = sortingColumn;
	}

	public SortingDirection getSortingDirection() {
		return this.sortingDirection;
	}

	public void setSortingDirection(final SortingDirection sortingDirection) {
		this.sortingDirection = sortingDirection;
	}

	public static enum SortingDirection {
		ASCENDING,

		DESCENDING,
	}
}