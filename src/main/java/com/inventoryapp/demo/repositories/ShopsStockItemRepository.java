package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.ShopsStockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopsStockItemRepository extends JpaRepository<ShopsStockItem, Long> {
}
