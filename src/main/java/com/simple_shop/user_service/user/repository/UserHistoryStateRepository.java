package com.simple_shop.user_service.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simple_shop.user_service.user.persistence.UserHistoryStateEntity;

@Repository
public interface UserHistoryStateRepository extends JpaRepository<UserHistoryStateEntity, Long> {
	// Spring Data JPA automatically provides standard CRUD and Paging methods.
}
