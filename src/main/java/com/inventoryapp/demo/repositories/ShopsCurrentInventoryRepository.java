package com.inventoryapp.demo.repositories;


import com.inventoryapp.demo.entities.ShopsCurrentInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShopsCurrentInventoryRepository extends JpaRepository<ShopsCurrentInventory,Long> {

    /**
     * Returns a list of items in shop inventory, filtered by shop
     * @param itemInShop
     * @return
     */
    @Query("SELECT item FROM ShopsCurrentInventory item WHERE item.shop = :itemInShop")
    List<ShopsCurrentInventory> findByShop(@Param("itemInShop") String itemInShop);

}
