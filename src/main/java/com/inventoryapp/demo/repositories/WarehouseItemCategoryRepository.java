package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.WarehouseItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseItemCategoryRepository extends JpaRepository<WarehouseItemCategory, Long> {
}
