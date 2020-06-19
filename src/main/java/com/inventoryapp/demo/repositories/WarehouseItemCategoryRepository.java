package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.WarehouseItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WarehouseItemCategoryRepository extends JpaRepository<WarehouseItemCategory, Long> {
//
//    @Modifying(clearAutomatically = true)
//    @Transactional
//    @Query("delete from WarehouseItemCategory item where item.category=:category")
//    void deleteCategoryByName(@Param("category") String category);

    @Query("SELECT item FROM WarehouseItemCategory item WHERE item.activated = true")
    List<WarehouseItemCategory> findActivatedCategories();

    @Query("SELECT item FROM WarehouseItemCategory item WHERE item.activated = false")
    List<WarehouseItemCategory> findDeactivatedCategories();

    @Modifying
    @Transactional
    @Query("UPDATE WarehouseItemCategory item SET item.activated = false WHERE item.category = :category")
    void deactivateCategory(@Param("category") String category);

    @Modifying
    @Transactional
    @Query("UPDATE WarehouseItemCategory item SET item.activated = true WHERE item.category = :category")
    void activateCategory(@Param("category") String category);

    @Query("SELECT CASE WHEN COUNT(item) > 0 THEN true ELSE false END FROM WarehouseItemCategory item where item.category=:category")
    boolean existsCategoryByName(@Param("category") String category);
}
