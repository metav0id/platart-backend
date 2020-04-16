package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.WarehouseNewDeliveryOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseNewDeliveryOrderRepository extends JpaRepository<WarehouseNewDeliveryOrderItem, Long> {
}
