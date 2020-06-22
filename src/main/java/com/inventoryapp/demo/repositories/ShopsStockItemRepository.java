package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.ShopsStockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShopsStockItemRepository extends JpaRepository<ShopsStockItem, Long> {

    /**
     * Get quantity for the given parameters
     * @param shop
     * @param category
     * @param priceListPerUnit
     * @param priceSalesPerUnit
     * @return
     */
    @Query( "SELECT SUM(item.quantity) " +
            "FROM ShopsStockItem item " +
            "WHERE " +
            "item.shop = :shop " +
            "AND item.category = :category " +
            "AND item.priceListPerUnit = :priceListPerUnit " +
            "AND item.priceSalesPerUnit = :priceSalesPerUnit"
    )
    Long findAmountItemsByAllInfo(
            @Param("shop") String shop,
            @Param("category") String category,
            @Param("priceListPerUnit") Long priceListPerUnit,
            @Param("priceSalesPerUnit") Long priceSalesPerUnit
    );

    /**
     * Get list of items in specific shop
     * @param shop
     * @return
     */
    @Query("SELECT item FROM ShopsStockItem item WHERE item.shop = :shop")
    List<ShopsStockItem> findAllItemsByShop(@Param("shop") String shop);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query( "UPDATE ShopsStockItem item " +
            "SET item.quantity = :quantity " +
            "WHERE "+
            "item.shop = :shop " +
            "AND item.category = :category " +
            "AND item.priceListPerUnit = :priceListPerUnit " +
            "AND item.priceSalesPerUnit = :priceSalesPerUnit")
    void    updateAmountByAllInfo(
            @Param("quantity") Long quantity,
            @Param("shop") String shop,
            @Param("category") String category,
            @Param("priceListPerUnit") Long priceListPerUnit,
            @Param("priceSalesPerUnit") Long priceSalesPerUnit
    );

    /**
     * Returns a list of items in shop inventory, filtered by shop
     * @param itemInShop
     * @return
     */
    @Query("SELECT item FROM ShopsStockItem item WHERE item.shop = :itemInShop")
    List<ShopsStockItem> findByShop(@Param("itemInShop") String itemInShop);

//    @Modifying(clearAutomatically = true)
//    @Transactional
    @Query( "SELECT item FROM  ShopsStockItem item WHERE " +
            "item.shop = :shop " +
            "AND item.category = :category " +
            "AND item.priceListPerUnit = :priceListPerUnit " +
            "AND item.priceSalesPerUnit = :priceSalesPerUnit")
    ShopsStockItem findItemByCriteria(
            @Param("shop") String shop,
            @Param("category") String category,
            @Param("priceListPerUnit") Long priceListPerUnit,
            @Param("priceSalesPerUnit") Long priceSalesPerUnit
    );

}
