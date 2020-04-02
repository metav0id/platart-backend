package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
     private WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    /**
     * You get all items which are currently available in stock of the warehouse.
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */


    @GetMapping("/getallitems")
    public List<WarehouseGetAllItemsDTO> getAllItems(){
        //TODO implement calling method to all items from service class

        List<WarehouseGetAllItemsDTO> warehouseItemList = warehouseService.getAllStockItems();

//        List<WarehouseGetAllItemsDTO> warehouseItemList = new ArrayList<>();
////        WarehouseGetAllItemsDTO item1 = new WarehouseGetAllItemsDTO("necklace-5Dollar", 10, 500);
////        WarehouseGetAllItemsDTO item2 = new WarehouseGetAllItemsDTO("bracelet-3Dollar", 20, 300);
////        WarehouseGetAllItemsDTO item3 = new WarehouseGetAllItemsDTO("shoes-20Dollar", 8, 2000);
////        warehouseItemList.add(item1);
////        warehouseItemList.add(item2);
////        warehouseItemList.add(item3);

        return warehouseItemList;
    }
}
