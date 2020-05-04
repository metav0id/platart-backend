package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.ShopsCheckoutSoldItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopsCheckoutSoldItemsRepository extends JpaRepository<ShopsCheckoutSoldItems, Long> {
}
