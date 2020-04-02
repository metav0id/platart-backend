package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/warehouse")
public class WarehouseController {

    /**
     * You get all items which are currently available in stock of the warehouse.
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
    @PostMapping("/getallitems")
    public List<WarehouseGetAllItemsDTO> getAllItems(){
        //TODO implement calling method to all items from service class
        List<WarehouseGetAllItemsDTO> warehouseItemList = new ArrayList<>();
        WarehouseGetAllItemsDTO item1 = new WarehouseGetAllItemsDTO("necklace-5Dollar", 10, 500);
        WarehouseGetAllItemsDTO item2 = new WarehouseGetAllItemsDTO("bracelet-3Dollar", 20, 300);
        WarehouseGetAllItemsDTO item3 = new WarehouseGetAllItemsDTO("shoes-20Dollar", 8, 2000);
        warehouseItemList.add(item1);
        warehouseItemList.add(item2);
        warehouseItemList.add(item3);

        return warehouseItemList;
    }
}
