package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseShopDeliveryOrdersSendRepository extends JpaRepository<WarehouseSendDeliveryOrderItem, Long> {

    @Query("SELECT item FROM WarehouseSendDeliveryOrderItem item WHERE " +
            "item.salesCheckedInProductsFromWarehouse.isAddedToStockOfShop = false")
    List<WarehouseSendDeliveryOrderItem> findAllItemsNotAddedToShopInventory();
}
