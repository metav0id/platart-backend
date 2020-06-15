package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShopsCheckinListRepository extends JpaRepository<WarehouseSendDeliveryOrderItem, Long> {

    @Query( "SELECT item FROM WarehouseSendDeliveryOrderItem item " +
            "WHERE " +
            "item.shopsCheckedInProductsFromWarehouse.isAddedToStockOfShop = true " +
            "AND item.shop = :shop " +
            "AND item.deliverySending >= :startDate " +
            "AND item.deliverySending <= :endDate"
            )
    List<WarehouseSendDeliveryOrderItem> findAllItemsNotAddedToShopInventoryDate(
                                                        @Param("shop") String shop,
                                                        @Param("startDate") LocalDateTime startDate,
                                                        @Param("endDate") LocalDateTime endDate
                                                        );

}
