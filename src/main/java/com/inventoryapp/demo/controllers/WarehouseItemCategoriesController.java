package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseItemCategoryDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/warehouse")
public class WarehouseItemCategoriesController {

    @PostMapping("/getAllCategories")
    public WarehouseItemCategoryDTO getAllCategories(){

        System.out.println("Test this RESTful API");
        WarehouseItemCategoryDTO itemCategoryDTO = new WarehouseItemCategoryDTO();
        itemCategoryDTO.setCategory("dije");
        return itemCategoryDTO;
    }

}
