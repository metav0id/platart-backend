package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DashboardRepository extends JpaRepository<ShopsAllSoldItems,Long> {

    @Query("FROM ShopsAllSoldItems WHERE itemLastSold = localDate")
    List<ShopsAllSoldItems> findByItemLastSold(String localDate);

}
