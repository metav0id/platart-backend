package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.Comerce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ComerceRepository extends JpaRepository<Comerce, Long> {

    @Query(value = "SELECT entry FROM Comerce entry WHERE entry.category = 'store'")
    List<Comerce> findByCategory();
}
