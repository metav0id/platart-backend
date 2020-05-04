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

    @PostMapping("/getAllCategories")
    public List<WarehouseItemCategoryDTO> getAllCategories(){

        System.out.println("Test this RESTful API");
        List<WarehouseItemCategoryDTO> allCategories = this.warehouseItemCategoryService.getAllCategories();

        return allCategories;
    }

    @PostMapping("/saveNewCategory")
    public void saveNewCategory(@RequestBody WarehouseItemCategoryDTO newWarehouseItemCategory){
        this.warehouseItemCategoryService.saveNewCategory(newWarehouseItemCategory);
    }

    @PostMapping("/Ware")
    public void deleteCategory(@RequestBody WarehouseItemCategoryDTO categoryToBeDeletedDTO){
        System.out.println("Test delete category Contoller"+ categoryToBeDeletedDTO.getCategory());
        this.warehouseItemCategoryService.deleteCategory(categoryToBeDeletedDTO);
    }

}
