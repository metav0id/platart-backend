package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DashboardRepository extends JpaRepository<ShopsAllSoldItems,Long> {

    @Query("SELECT i FROM ShopsAllSoldItems i WHERE CAST (i.itemLastSold AS date) LIKE YEAR(CURRENT_TIMESTAMP)")
    List<ShopsAllSoldItems> findByItemLastSold(String localDate);

    @Query("SELECT i FROM WarehouseSupplierItem i WHERE i.category="category" AND" )
    List<WarehouseSupplierItem> findByCategoryAndAndPriceListPerUnit();


}
