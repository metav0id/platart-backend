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

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("delete from WarehouseItemCategory item where item.category=:category")
    void deleteCategoryByName(@Param("category") String category);

    @Query("SELECT item FROM WarehouseItemCategory item WHERE item.Activated = true")
    List<WarehouseItemCategory> findActiveCategories();

    @Modifying
    @Transactional
    @Query("UPDATE WarehouseItemCategory item SET item.Activated = false WHERE item.category = :category")
    void deactivateCategory(@Param("category") String category);

    @Modifying
    @Transactional
    @Query("UPDATE WarehouseItemCategory item SET item.Activated = true WHERE item.category = :category")
    void activateCategory(@Param("category") String category);

    @Query(value = "SELECT count(item) FROM WarehouseItemCategory item where item.category=:category")
    int existsCategoryByName(@Param("category") String category);
}
