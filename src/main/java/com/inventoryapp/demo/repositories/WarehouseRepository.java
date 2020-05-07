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

    /**
     * Updates a dataset in database by category and pricePerUnit
     * @param category
     * @param priceListPerUnit
     * @param quantity
     * @return int of affected rows
     */
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE WarehouseStockItem item SET item.quantity = :quantity WHERE item.category = :category AND item.priceListPerUnit = :priceListPerUnit")
    int updateStock(@Param("category") String category, @Param("priceListPerUnit") long priceListPerUnit, @Param("quantity") long quantity);

    /**
     * Returns a list of stock items filtered by category and pricePerUnit
     * @param category
     * @param pricePerUnit
     * @return list of stock items
     */
    @Query("SELECT item from WarehouseStockItem item where item.category = :category AND item.priceListPerUnit = :pricePerUnit")
    WarehouseStockItem findItemByCategoryAndPricePerUnit(@Param("category") String category, @Param("pricePerUnit") long pricePerUnit);


}
