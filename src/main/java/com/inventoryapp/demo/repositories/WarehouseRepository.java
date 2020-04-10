package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.WarehouseStockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseStockItem,Long> {
    WarehouseStockItem findByCategory (String category);

    /*@Query("SELECT WarehouseStockItem from warehouse_supplier_item where WarehouseStockItem.category = :category AND WarehouseStockItem.pricePerUnit = :pricePerUnit")
    List<WarehouseStockItem> findAllItemsByCategoryAndPricePerUnit(@Param("category") String category, @Param("pricePerUnit") long pricePerUnit);*/
}
