package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DashboardRepositoryShop extends JpaRepository<ShopsAllSoldItems,Long> {

    @Query("SELECT i FROM ShopsAllSoldItems i WHERE i.itemLastSold > :localDateEnd AND i.itemLastSold < :localDateStart")
    List<ShopsAllSoldItems> findByItemLastSoldDateMin(LocalDateTime localDateStart, LocalDateTime localDateEnd);




}


