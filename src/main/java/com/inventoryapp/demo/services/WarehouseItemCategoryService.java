package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.WarehouseItemCategoryDTO;
import com.inventoryapp.demo.entities.WarehouseItemCategory;
import com.inventoryapp.demo.repositories.WarehouseItemCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseItemCategoryService {

    @Autowired
    private WarehouseItemCategoryRepository warehouseItemCategoryRepository;

    /**
     * Get all elements from database.
     * @return List<WarehouseItemCategoryDTO> allCategories
     */
    public List<WarehouseItemCategoryDTO> getAllCategories() {
        List<WarehouseItemCategoryDTO> allCategories = new ArrayList<>();

        List<WarehouseItemCategory> allCategoryEntities = this.warehouseItemCategoryRepository.findActiveCategories();

        for(WarehouseItemCategory entity: allCategoryEntities){
            WarehouseItemCategoryDTO warehouseItem = new WarehouseItemCategoryDTO();
            warehouseItem.setCategory(entity.getCategory());

            allCategories.add(warehouseItem);
        }

        return allCategories;
    }

    public void saveNewCategory(WarehouseItemCategoryDTO newCategory){

        int itemInCategoryList = this.warehouseItemCategoryRepository.existsCategoryByName(newCategory.getCategory());

        if ( itemInCategoryList>0 ) {
            WarehouseItemCategory newCategoryItem = new WarehouseItemCategory();
            newCategoryItem.setCategory(newCategory.getCategory());
            newCategoryItem.setActivated(true);

            this.warehouseItemCategoryRepository.save(newCategoryItem);
        } else {
            this.warehouseItemCategoryRepository.activateCategory(newCategory.getCategory());
        }

    }


    public void deleteCategory(WarehouseItemCategoryDTO categoryToBeDeletedDTO) {

        System.out.println("Test delete category Service1"+ categoryToBeDeletedDTO.getCategory());

        this.warehouseItemCategoryRepository.deleteCategoryByName(categoryToBeDeletedDTO.getCategory());
        System.out.println("Test delete category Service2"+ categoryToBeDeletedDTO.getCategory());
    }

    // deactivateCategory
    public void deactivateCategory(WarehouseItemCategoryDTO categoryToBeDeletedDTO) {

        System.out.println("Test delete category Service1"+ categoryToBeDeletedDTO.getCategory());

        this.warehouseItemCategoryRepository.deactivateCategory(categoryToBeDeletedDTO.getCategory());
    }
}
