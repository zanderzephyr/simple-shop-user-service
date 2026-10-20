package com.simple_shop.user_service.shared.dto;

// TODO Move to common lib
/**
 * Must never contain sensitive information, as the properties of this object
 * are passed via URL arguments.
 */
public final class RequestConstraintsDto {
	private Integer pageIndex;

	private Integer pageSize;

	/**
	 * This is the name of a member of the UserDto class.
	 */
	private String sortingColumn;

	private String sortingDirection;

	public RequestConstraintsDto() {
		super();
	}

	public Integer getPageIndex() {
		return this.pageIndex;
	}

	public RequestConstraintsDto setPageIndex(final Integer pageIndex) {
		this.pageIndex = pageIndex;

		return this;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public RequestConstraintsDto setPageSize(final Integer pageSize) {
		this.pageSize = pageSize;

		return this;
	}

	public String getSortingColumn() {
		return this.sortingColumn;
	}

	public RequestConstraintsDto setSortingColumn(final String sortingColumn) {
		this.sortingColumn = sortingColumn;

		return this;
	}

	public String getSortingDirection() {
		return this.sortingDirection;
	}

	public RequestConstraintsDto setSortingDirection(final String sortingDirection) {
		this.sortingDirection = sortingDirection;

		return this;
	}
}