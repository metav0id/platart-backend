package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseItemCategoryDTO;
import com.inventoryapp.demo.services.WarehouseItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/warehouse")
public class WarehouseItemCategoriesController {
    @Autowired
    private WarehouseItemCategoryService warehouseItemCategoryService;

    @GetMapping("/getAllCategories")
    public List<WarehouseItemCategoryDTO> getAllCategories() {
        return this.warehouseItemCategoryService.getAllCategories();
    }

    @GetMapping("/getAllActivatedCategories")
    public List<WarehouseItemCategoryDTO> getAllActivatedCategories() {
        return this.warehouseItemCategoryService.getActivatedCategories();
    }

    @GetMapping("/getAllDeactivatedCategories")
    public List<WarehouseItemCategoryDTO> getAllDeactivatedCategories() {
        return this.warehouseItemCategoryService.getDeactivatedCategories();
    }

    @PostMapping("/saveNewCategory")
    public boolean saveNewCategory(@RequestBody WarehouseItemCategoryDTO newWarehouseItemCategory) {
        return this.warehouseItemCategoryService.saveNewCategory(newWarehouseItemCategory);
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
