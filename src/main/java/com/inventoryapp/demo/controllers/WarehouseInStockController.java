package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.services.WarehouseDeliverySupplierService;
import com.inventoryapp.demo.services.WarehouseInStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://platart.com", allowedHeaders = "*")
@RestController("")
@RequestMapping("/warehouse")
public class WarehouseInStockController {

    @Autowired
    private final WarehouseInStockService warehouseInStockService;

    public WarehouseInStockController(WarehouseInStockService warehouseInStockService) {
        this.warehouseInStockService = warehouseInStockService;
    }

    /**
     * You get all items which are currently available in stock of the warehouse.
     *
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
    @PostMapping("/getallitems")
    public List<WarehouseGetAllItemsDTO> getAllItems() {
        List<WarehouseGetAllItemsDTO> warehouseItemList = warehouseInStockService.getAllStockItems();
        System.out.println("List of stock loaded.");
        return warehouseItemList;
    }

}
