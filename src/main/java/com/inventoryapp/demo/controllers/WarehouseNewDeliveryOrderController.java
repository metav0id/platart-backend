package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseItemPersistanceErrorDTO;
import com.inventoryapp.demo.dtos.WarehouseNewDeliveryOrderItemDTO;
import com.inventoryapp.demo.dtos.WarehouseNewDeliveryPersistanceResponseDTO;
import com.inventoryapp.demo.dtos.WarehouseVerifyAmountItemsOnStockDTO;
import com.inventoryapp.demo.services.WarehouseNewDeliveryOrderService;
import com.inventoryapp.demo.services.WarehouseVerifyAmountItemsOnStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/warehouse")
public class WarehouseNewDeliveryOrderController {

    @Autowired
    private WarehouseNewDeliveryOrderService warehouseNewDeliveryOrderService;

    @Autowired
    private WarehouseVerifyAmountItemsOnStockService warehouseVerifyAmountItemsOnStockService;

    /**
     * REST Post-Request of all new order items.
     * @return DTO-List with current new order items.
     */
    @PostMapping("/getAllNewOrderItems")
    public List<WarehouseNewDeliveryOrderItemDTO> getAllNewOrderItems(){
        return warehouseNewDeliveryOrderService.getAllDeliveryOrderItems();
    }

    /**
     * Reset the localy persisted NewOrderList.
     * @param newDeliveryOrderItemDTOList
     */
    @PostMapping("/setAllNewOrderItems")
    public void setAllNewOrderItems(@RequestBody List<WarehouseNewDeliveryOrderItemDTO> newDeliveryOrderItemDTOList){
        warehouseNewDeliveryOrderService.setAllDeliveryOrderItems(newDeliveryOrderItemDTOList);
    }

    /**
     * Get an object with the available amount of items, for a given category and price.
     * @param verifyAmountItemsOnStockDTO
     * @return
     */
    @PostMapping("verifyAmountItemsOnStock")
    public WarehouseVerifyAmountItemsOnStockDTO verifyAmountItemsOnStock(@RequestBody WarehouseVerifyAmountItemsOnStockDTO verifyAmountItemsOnStockDTO){
        return this.warehouseVerifyAmountItemsOnStockService.verifyAmountItemsOnStock(verifyAmountItemsOnStockDTO);
    }

    /**
     * Save current list of Orders.
     */
    @PostMapping("sendDeliveryOrder")
    public WarehouseNewDeliveryPersistanceResponseDTO sendDeliveryOrder(@RequestBody List<WarehouseNewDeliveryOrderItemDTO> newDeliveryOrderItemDTOList){
        return this.warehouseNewDeliveryOrderService.sendDeliveryOrder(newDeliveryOrderItemDTOList);

    }

    @PostMapping("deleteItems")
    public boolean deleteItems(@RequestBody List<Long>listTable){
        return this.warehouseNewDeliveryOrderService.deleteItems(listTable);
    }
}
