package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseShopDeliveryOrdersSendRepository extends JpaRepository<WarehouseSendDeliveryOrderItem, Long> {

    @Query("SELECT item FROM WarehouseSendDeliveryOrderItem item WHERE " +
            "item.shopsCheckedInProductsFromWarehouse.isAddedToStockOfShop = false AND item.shop = :shop")
    List<WarehouseSendDeliveryOrderItem> findAllItemsNotAddedToShopInventory(@Param("shop") String shop);

}
