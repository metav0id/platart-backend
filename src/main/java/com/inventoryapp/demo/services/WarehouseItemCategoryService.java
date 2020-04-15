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

        List<WarehouseItemCategory> allCategoryEntities = this.warehouseItemCategoryRepository.findAll();

        for(WarehouseItemCategory entity: allCategoryEntities){
            WarehouseItemCategoryDTO warehouseItem = new WarehouseItemCategoryDTO();
            warehouseItem.setCategory(entity.getCategory());

            allCategories.add(warehouseItem);
        }

        return allCategories;
    }

    public void saveNewCategory(WarehouseItemCategoryDTO newCategory){
        WarehouseItemCategory newCategoryItem = new WarehouseItemCategory();
        newCategoryItem.setCategory(newCategory.getCategory());

        this.warehouseItemCategoryRepository.save(newCategoryItem);
    }


    public void deleteCategory(WarehouseItemCategoryDTO categoryToBeDeletedDTO) {

        System.out.println("Test delete category Service1"+ categoryToBeDeletedDTO.getCategory());

        this.warehouseItemCategoryRepository.deleteCategoryByName(categoryToBeDeletedDTO.getCategory());
        System.out.println("Test delete category Service2"+ categoryToBeDeletedDTO.getCategory());
        //System.out.println("Number elements deleted: "+ numDeleted);
    }
}
