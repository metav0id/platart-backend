package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopsCheckoutSoldItemsDTO;
import com.inventoryapp.demo.dtos.ShopsCurrentInventoryDTO;
import com.inventoryapp.demo.dtos.ShopsRequestInventoryDTO;
import com.inventoryapp.demo.dtos.ShopsStockItemDto;
import com.inventoryapp.demo.services.ShopsCurrentInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/shops")
public class ShopsCurrentInventoryController {

    @Autowired
    private ShopsCurrentInventoryService shopsCurrentInventoryService;

    public ShopsCurrentInventoryController(ShopsCurrentInventoryService shopsCurrentInventoryService) {
        this.shopsCurrentInventoryService = shopsCurrentInventoryService;
    }

    @PostMapping("/getShopInventoryItems")
    private List<ShopsStockItemDto> getShopInventoryItems(@RequestBody ShopsRequestInventoryDTO requestedShop){
        String shop = requestedShop.getShop();
        return this.shopsCurrentInventoryService.getAllItemsSpecificShop(shop);
    }

    @PostMapping("/getShopInventoryAvailability")
    private ShopsCheckoutSoldItemsDTO getShopInventoryAvailability(@RequestBody ShopsCheckoutSoldItemsDTO shopsCheckoutSoldItemsDTO){
        System.out.println("test -> getShopInventoryAvailability");
        System.out.println(shopsCheckoutSoldItemsDTO);
        return this.shopsCurrentInventoryService.getShopInventoryAvailability(shopsCheckoutSoldItemsDTO);
    }
}
