package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import org.springframework.data.repository.CrudRepository;

public interface WarehouseNewDeliverySupplierRepository extends CrudRepository<WarehouseSupplierItem, Long> {
}
