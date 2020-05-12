package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.ShopsStockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopsNewDeliveryFromWarehouseRepository extends JpaRepository<ShopsStockItem, Long> {

    /**
     * Return an item by shop, category, priceListPerUnit and priceSalesPerUnit
     * @param
     * @return
     */
    @Query("SELECT item FROM ShopsStockItem item " +
            "WHERE item.shop = :shop AND item.category = :category " +
            "AND item.priceListPerUnit = :priceListPerUnit AND item.priceSalesPerUnit = :priceSalesPerUnit")
    public ShopsStockItem getShopsStockItemsBySelectors(@Param("shop") String shop, @Param("category") String category,
                                                        @Param("priceListPerUnit") long priceListPerUnit,
                                                        @Param("priceSalesPerUnit") long priceSalesPerUnit);
}
