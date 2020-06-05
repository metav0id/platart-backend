package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DashboardRepositoryShop extends JpaRepository<ShopsAllSoldItems,Long> {

    @Query("SELECT i FROM ShopsAllSoldItems i WHERE i.itemLastSold <= :end AND i.itemLastSold >= :start")
    List<ShopsAllSoldItems> findByItemLastSoldDateMin(java.sql.Date start, java.sql.Date end);




}


