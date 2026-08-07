package com.simple_shop.user_service.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simple_shop.user_service.user.persistence.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	// Spring Data JPA automatically provides standard CRUD and Paging methods.

	Optional<UserEntity> findByUsername(String username);
}
