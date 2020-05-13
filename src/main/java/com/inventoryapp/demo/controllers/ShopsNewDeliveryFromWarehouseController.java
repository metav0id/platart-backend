package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopDeliveryDTO;
import com.inventoryapp.demo.dtos.ShopDeliveryItemFromWarehouseDTO;
import com.inventoryapp.demo.dtos.ShopSaveToStockDTO;
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

    /**
     * Get all Items which are not already in the inventory of a shop.
     * @param shop
     * @return
     */
    @PostMapping("/getalldeliveriesnotinstock")
    public List<ShopDeliveryItemFromWarehouseDTO> getAllDeliveriesNotInStock(@RequestBody ShopDeliveryDTO shop){
        return service.getAllItemsNotInShopInventory(shop.getShop());
    }

    /**
     * You can save a list of all items to the inventory stock of an certain shop.
     * All the current item quantities in stock will be updated and new items will be created in stock.
     * @param listDTO
     * @return
     */
    @PostMapping("/saveNewDeliveryFromWarehouse")
    public boolean saveNewDeliveryFromWarehouseList(@RequestBody List<ShopSaveToStockDTO> listDTO){
        System.out.println("Controller: Saving list to database...");
        return service.saveList(listDTO);
    }
}
