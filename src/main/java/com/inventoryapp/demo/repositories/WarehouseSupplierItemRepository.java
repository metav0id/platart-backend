package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WarehouseSupplierItemRepository extends JpaRepository<WarehouseSupplierItem, Long> {

    @Query( "SELECT item FROM WarehouseSupplierItem item " +
            "WHERE item.createDateTime <= :endDate " +
            "AND item.createDateTime >= :startDate")
    List<WarehouseSupplierItem> getItemsByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);

}
