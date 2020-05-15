package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DashboardRepositoryWarehouse extends JpaRepository<WarehouseSupplierItem,Long> {

    @Query("SELECT i FROM WarehouseSupplierItem i WHERE i.category = :category AND i.priceListPerUnit = :listPrice" )
    List<WarehouseSupplierItem> findByCategoryAndAndPriceListPerUnit(String category, Long listPrice);
}
