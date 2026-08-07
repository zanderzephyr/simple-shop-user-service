package com.simple_shop.user_service.user.model;

import java.util.Objects;

// TODO Move to common lib
public final class RequestConstraints {
	private final int pageIndex;

	private final int pageSize;

	private final String sortingColumn;

	private final SortingDirection sortingDirection;

	private RequestConstraints(final RequestConstraintsBuilder builder) throws IllegalStateException {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.pageIndex = builder.getPageIndex();
		this.pageSize = builder.getPageSize();
		this.sortingColumn = builder.getSortingColumn();
		this.sortingDirection = builder.getSortingDirection();
	}

	public int getPageIndex() {
		return this.pageIndex;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public String getSortingColumn() {
		return this.sortingColumn;
	}

	public SortingDirection getSortingDirection() {
		return this.sortingDirection;
	}

	public static final class RequestConstraintsBuilder {
		private static final long MAX_PAGE_SIZE = 100;

		private Integer pageIndex;

		private Integer pageSize;

		private String sortingColumn;

		private SortingDirection sortingDirection;

		public RequestConstraintsBuilder() {
			super();
		}

		public Integer getPageIndex() {
			return this.pageIndex;
		}

		public RequestConstraintsBuilder setPageIndex(final Integer pageIndex) {
			this.pageIndex = pageIndex;

			return this;
		}

		public Integer getPageSize() {
			return this.pageSize;
		}

		public RequestConstraintsBuilder setPageSize(final Integer pageSize) {
			this.pageSize = pageSize;

			return this;
		}

		public String getSortingColumn() {
			return this.sortingColumn;
		}

		public RequestConstraintsBuilder setSortingColumn(final String sortingColumn) {
			this.sortingColumn = sortingColumn;

			return this;
		}

		public SortingDirection getSortingDirection() {
			return this.sortingDirection;
		}

		public RequestConstraintsBuilder setSortingDirection(final SortingDirection sortingDirection) {
			this.sortingDirection = sortingDirection;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.pageIndex == null || this.pageIndex < 0) {
				throw new IllegalStateException("Invalid pageIndex!");
			}

			if (this.pageSize == null || this.pageSize < 1 || this.pageSize > RequestConstraintsBuilder.MAX_PAGE_SIZE) {
				throw new IllegalStateException("Invalid page size!");
			}

			if (this.sortingColumn == null || this.sortingColumn.isBlank()) {
				throw new IllegalStateException("Invalid sorting column!");
			}

			if (this.sortingDirection == null) {
				throw new IllegalStateException("Invalid sorting direction!");
			}
		}

		public RequestConstraints build() throws IllegalStateException {
			return new RequestConstraints(this);
		}
	}
}