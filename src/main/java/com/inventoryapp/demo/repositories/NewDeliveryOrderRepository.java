package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.NewDeliveryOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewDeliveryOrderRepository extends JpaRepository<NewDeliveryOrderItem, Long> {
}
