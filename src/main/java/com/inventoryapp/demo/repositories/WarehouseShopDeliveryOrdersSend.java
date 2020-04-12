package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseShopDeliveryOrdersSend extends JpaRepository<WarehouseSendDeliveryOrderItem, Long> {
}
