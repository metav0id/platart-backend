package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopsCurrentInventoryDTO;
import com.inventoryapp.demo.services.ShopsCurrentInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/shops")
public class ShopsCurrentInventoryController {

    @Autowired
    private ShopsCurrentInventoryService shopsCurrentInventoryService;

    public ShopsCurrentInventoryController(ShopsCurrentInventoryService shopsCurrentInventoryService) {
        this.shopsCurrentInventoryService = shopsCurrentInventoryService;
    }

    @PostMapping("/getAllItemsAllShops")
    private List<ShopsCurrentInventoryDTO> getAllItemsAllShops(){
        return this.shopsCurrentInventoryService.getAllItemsAllShops();
    }

    @PostMapping("/setItemsShops")
    private void setItemsShops(@RequestBody List<ShopsCurrentInventoryDTO> newShopItemsList){
        this.shopsCurrentInventoryService.setItemsShops(newShopItemsList);
    }

}
