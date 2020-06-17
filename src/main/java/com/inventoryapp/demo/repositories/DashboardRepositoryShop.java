package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DashboardRepositoryShop extends JpaRepository<ShopsAllSoldItems,Long> {

    @Query("SELECT i FROM ShopsAllSoldItems i WHERE i.itemLastSold <= :end AND i.itemLastSold >= :start")
    List<ShopsAllSoldItems> findByItemLastSoldDateMin(@Param("start")LocalDateTime start, @Param("end")LocalDateTime end);

}


