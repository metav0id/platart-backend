package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.services.WarehouseDeliverySupplierService;
import com.inventoryapp.demo.services.WarehouseInStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/warehouse")
public class WarehouseDeliverySupplierController {
    @Autowired
    private WarehouseDeliverySupplierService warehouseDeliverySupplierService;

    /**
     * Saves a list of new products delivered by supplier to database.
     * @param listDeliverySupplier DTO as list of delivered products from suppliers
     */
    @PostMapping("savelistdeliverysupplier")
    public boolean saveListDeliverySuppliers(@RequestBody List<WarehouseSupplierItemDTO> listDeliverySupplier){
        return warehouseDeliverySupplierService.saveListDeliverySuppliers(listDeliverySupplier);
    }
}
