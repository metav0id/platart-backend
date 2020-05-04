package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.Comerce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComerceRepository extends JpaRepository<Comerce, Long> {
}
