package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.WarehouseNewDeliveryOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface WarehouseNewDeliveryOrderRepository extends JpaRepository<WarehouseNewDeliveryOrderItem, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM WarehouseNewDeliveryOrderItem item WHERE item.id = :id")
    void deleteById(@Param("id") long id);
}
