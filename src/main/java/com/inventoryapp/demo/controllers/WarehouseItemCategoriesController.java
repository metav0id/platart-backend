package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseItemCategoryDTO;
import com.inventoryapp.demo.services.WarehouseItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/warehouse")
public class WarehouseItemCategoriesController {
    @Autowired
    private WarehouseItemCategoryService warehouseItemCategoryService;

    @PostMapping("/getAllCategories")
    public List<WarehouseItemCategoryDTO> getAllCategories() {
        return this.warehouseItemCategoryService.getAllCategories();
    }

    @PostMapping("/getAllActivatedCategories")
    public List<WarehouseItemCategoryDTO> getAllActivatedCategories() {
        return this.warehouseItemCategoryService.getActivatedCategories();
    }

    @PostMapping("/getAllDeactivatedCategories")
    public List<WarehouseItemCategoryDTO> getAllDeactivatedCategories() {
        return this.warehouseItemCategoryService.getDeactivatedCategories();
    }

    @PostMapping("/saveNewCategory")
    public void saveNewCategory(@RequestBody WarehouseItemCategoryDTO newWarehouseItemCategory) {
        this.warehouseItemCategoryService.saveNewCategory(newWarehouseItemCategory);
    }

    @PostMapping("/deactivateCategory")
    public void deactivateCategory(@RequestBody WarehouseItemCategoryDTO categoryToBeDeactivatedDTO) {
        this.warehouseItemCategoryService.deactivateCategory(categoryToBeDeactivatedDTO);
    }

    @PostMapping("/activateCategory")
    public void activateCategory(@RequestBody WarehouseItemCategoryDTO categoryToBeActivatedDTO) {
        this.warehouseItemCategoryService.activateCategory(categoryToBeActivatedDTO);
    }
}
