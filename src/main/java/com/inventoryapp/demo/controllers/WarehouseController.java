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
public class WarehouseController {

    @Autowired
    private final WarehouseInStockService warehouseInStockService;

    @Autowired
    private WarehouseDeliverySupplierService warehouseDeliverySupplierService;

    public WarehouseController(WarehouseInStockService warehouseInStockService) {
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

//        List<WarehouseGetAllItemsDTO> warehouseItemList = new ArrayList<>();
////        WarehouseGetAllItemsDTO item1 = new WarehouseGetAllItemsDTO("necklace-5Dollar", 10, 500);
////        WarehouseGetAllItemsDTO item2 = new WarehouseGetAllItemsDTO("bracelet-3Dollar", 20, 300);
////        WarehouseGetAllItemsDTO item3 = new WarehouseGetAllItemsDTO("shoes-20Dollar", 8, 2000);
////        warehouseItemList.add(item1);
////        warehouseItemList.add(item2);
////        warehouseItemList.add(item3);

        return warehouseItemList;
    }

    /**
     * Saves a list of new products delivered by supplier to database.
     * @param listDeliverySupplier DTO as list of delivered products from suppliers
     */
    @PostMapping("savelistdeliverysupplier")
    public void saveListDeliverySuppliers(@RequestBody List<WarehouseSupplierItemDTO> listDeliverySupplier){
        System.out.println("New List of fresh items is coming...it's xmas, isn't it?");
        for(WarehouseSupplierItemDTO item : listDeliverySupplier){
            System.out.println("Item: " + item);
        }
        warehouseDeliverySupplierService.saveListDeliverySuppliers(listDeliverySupplier);
    }
}
