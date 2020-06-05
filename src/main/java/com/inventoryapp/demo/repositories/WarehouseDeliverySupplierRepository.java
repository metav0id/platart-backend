package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseDeliverySupplierRepository extends CrudRepository<WarehouseSupplierItem, Long> {
}
