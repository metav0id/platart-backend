package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.WarehouseStockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseStockItem,Long> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE WarehouseStockItem item SET item.quantity = :quantity WHERE item.category = :category AND item.pricePerUnit = :pricePerUnit")
    int updateStock(@Param("category") String category, @Param("pricePerUnit") long pricePerUnit, @Param("quantity") long quantity);

    WarehouseStockItem findByCategory (String category);

    @Query("SELECT item from WarehouseStockItem item where item.category = :category AND item.pricePerUnit = :pricePerUnit")
    WarehouseStockItem findItemByCategoryAndPricePerUnit(@Param("category") String category, @Param("pricePerUnit") long pricePerUnit);


}
