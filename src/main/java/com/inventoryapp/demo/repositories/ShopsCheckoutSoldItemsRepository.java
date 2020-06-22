package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.ShopsCheckoutSoldItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ShopsCheckoutSoldItemsRepository extends JpaRepository<ShopsCheckoutSoldItems, Long> {

    @Query("SELECT i FROM ShopsCheckoutSoldItems i WHERE i.shop = :shop")
    List<ShopsCheckoutSoldItems> findAllByShop(@Param("shop") String shop);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM ShopsCheckoutSoldItems item WHERE item.shop = :shop")
    void deleteByShop(@Param("shop") String shop);

}
