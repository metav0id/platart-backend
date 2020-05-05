package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopDeliveryDTO;
import com.inventoryapp.demo.dtos.ShopDeliveryItemFromWarehouseDTO;
import com.inventoryapp.demo.services.ShopsNewDeliveryFromWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/shops")
public class ShopsNewDeliveryFromWarehouseController {
    @Autowired
    private ShopsNewDeliveryFromWarehouseService service;

    @PostMapping("/getalldeliveriesnotinstock")
    public List<ShopDeliveryItemFromWarehouseDTO> getAllDeliveriesNotInStock(@RequestBody ShopDeliveryDTO shop){
        return service.getAllItemsNotInShopInventory(shop.getShop());
    }
}
