package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShopsAllSoldItemsRepository extends JpaRepository<ShopsAllSoldItems, Long> {

    @Query( "SELECT item FROM ShopsAllSoldItems item " +
            "WHERE item.itemLastSold <= :endDate " +
            "AND item.itemLastSold >= :startDate")
    List<ShopsAllSoldItems> getItemsByDate(@Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);

}
