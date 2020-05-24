package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseItemCategoryDTO;
import com.inventoryapp.demo.services.WarehouseItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/warehouse")
public class WarehouseItemCategoriesController {

    @Autowired
    private final WarehouseItemCategoryService warehouseItemCategoryService;

    public WarehouseItemCategoriesController(WarehouseItemCategoryService warehouseItemCategoryService) {
        this.warehouseItemCategoryService = warehouseItemCategoryService;
    }

    // returns all active categories
    @PostMapping("/getAllCategories")
    public List<WarehouseItemCategoryDTO> getAllCategories(){
        System.out.println("Test this RESTful API");
        List<WarehouseItemCategoryDTO> allCategories = this.warehouseItemCategoryService.getAllCategories();

        return allCategories;
    }

    @PostMapping("/getAllActiveCategories")
    public List<WarehouseItemCategoryDTO> getAllActiveCategories(){

        System.out.println("Test this RESTful API");
        List<WarehouseItemCategoryDTO> allCategories = this.warehouseItemCategoryService.getAllCategories();

        return allCategories;
    }

    @PostMapping("/saveNewCategory")
    public void saveNewCategory(@RequestBody WarehouseItemCategoryDTO newWarehouseItemCategory){
        System.out.println("Create new Category");
        this.warehouseItemCategoryService.saveNewCategory(newWarehouseItemCategory);
    }

    @PostMapping("/deleteCategory")
    public void deleteCategory(@RequestBody WarehouseItemCategoryDTO categoryToBeDeletedDTO){
        System.out.println("Test Delete Category");

        this.warehouseItemCategoryService.deactivateCategory(categoryToBeDeletedDTO);
    }
}
