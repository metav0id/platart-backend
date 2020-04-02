package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Item,Long> {
}
