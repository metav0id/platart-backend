package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopDeliveryItemFromWarehouseDTO;
import com.inventoryapp.demo.services.ShopsNewDeliveryFromWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/shops")
public class ShopsNewDeliveryFromWarehouseController {
    @Autowired
    private ShopsNewDeliveryFromWarehouseService service;

    @PostMapping("/getalldeliveriesnotinstock")
    public List<ShopDeliveryItemFromWarehouseDTO> getAllDeliveriesNotInStock(@Param("shop") String shop){
        return service.getAllItemsNotInShopInventory(shop);
    }
}
