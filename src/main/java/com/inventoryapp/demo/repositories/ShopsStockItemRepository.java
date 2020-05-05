package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.ShopsStockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopsStockItemRepository extends JpaRepository<ShopsStockItem, Long> {

    @Query( "SELECT SUM(item.quantity) " +
            "FROM ShopsStockItem item " +
            "WHERE " +
            "item.shop = :shop AND item.category = :category " +
            "AND item.priceListPerUnit = :priceListPerUnit " +
            "AND item.priceSalesPerUnit = :priceSalesPerUnit"
    )
    Long findAmountItemsByAllInfo(
            @Param("shop") String shop,
            @Param("category") String category,
            @Param("priceListPerUnit") Long priceListPerUnit,
            @Param("priceSalesPerUnit") Long priceSalesPerUnit
    );

    @Query("SELECT item FROM ShopsStockItem item WHERE item.shop = :shop")
    List<ShopsStockItem> findAllItemsByShop(@Param("shop") String shop);
}
