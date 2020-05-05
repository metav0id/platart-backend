package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopsCheckoutSoldItemsDTO;
import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.services.ShopsCheckoutSoldItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/shops")
public class ShopsCheckoutSoldItemsController {

    @Autowired
    private ShopsCheckoutSoldItemsService shopsCheckoutSoldItemsService;

    /**
     * save current list of sold items.
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
    @PostMapping("/saveAllSoldItemsList")
    public void saveAllSoldItemsList(@RequestBody List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList) {

        //List<WarehouseGetAllItemsDTO> warehouseItemList = warehouseInStockService.getAllStockItems();
        System.out.println("List of stock loaded.");

        this.shopsCheckoutSoldItemsService.saveAllSoldItemsList(shopsCheckoutSoldItemsDTOList);
    }


    /**
     * get currenty saved list of sold items.
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
    @PostMapping("/getAllSoldItemsList")
    public List<ShopsCheckoutSoldItemsDTO> getAllSoldItemsList() {
        System.out.println("Controller: Get current Sold item list.");
        return this.shopsCheckoutSoldItemsService.getAllSoldItemsList();
    }

    /**
     * get currenty saved list of sold items.
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
    @PostMapping("/deleteCurrentSoldItemsList")
    public void deleteCurrentSoldItemsList() {
        System.out.println("Controller: Delete current Sold item list.");
        this.shopsCheckoutSoldItemsService.deleteCurrentSoldItemsList();
    }

}
