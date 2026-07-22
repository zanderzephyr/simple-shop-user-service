package com.simple_shop.item_service.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simple_shop.item_service.item.persistence.ItemHistoryStateEntity;

@Repository
public interface ItemHistoryStateEntityRepository extends JpaRepository<ItemHistoryStateEntity, Long> {
	// Spring Data JPA automatically provides standard CRUD and Paging methods.
}
