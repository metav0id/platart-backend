package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.NewDeliveryOrderItemDTO;
import com.inventoryapp.demo.dtos.VerifyAmountItemsOnStockDTO;
import com.inventoryapp.demo.services.NewDeliveryOrderService;
import com.inventoryapp.demo.services.WarehouseVerifyAmountItemsOnStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/warehouse")
public class NewDeliveryOrderController {

    private NewDeliveryOrderService newDeliveryOrderService;
    private WarehouseVerifyAmountItemsOnStockService warehouseVerifyAmountItemsOnStockService;

    @Autowired
    public NewDeliveryOrderController(NewDeliveryOrderService newDeliveryOrderService, WarehouseVerifyAmountItemsOnStockService warehouseVerifyAmountItemsOnStockService) {
        this.newDeliveryOrderService = newDeliveryOrderService;
        this.warehouseVerifyAmountItemsOnStockService = warehouseVerifyAmountItemsOnStockService;
    }


    /**
     * REST Post-Request of all new order items.
     * @return DTO-List with current new order items.
     */
    @PostMapping("/getAllNewOrderItems")
    public List<NewDeliveryOrderItemDTO> getAllNewOrderItems(){
        List<NewDeliveryOrderItemDTO> newDeliveryOrderItemDTOList = new ArrayList<>();
        System.out.println("Controller1:");
        System.out.println(newDeliveryOrderItemDTOList);
        newDeliveryOrderItemDTOList = newDeliveryOrderService.getAllDeliveryOrderItems();

        System.out.println("Controller2:");
        System.out.println(newDeliveryOrderItemDTOList);

        return newDeliveryOrderItemDTOList;
    }

    /**
     * Reset the localy persisted NewOrderList.
     * @param newDeliveryOrderItemDTOList
     */
    @PostMapping("/setAllNewOrderItems")
    public void setAllNewOrderItems(@RequestBody List<NewDeliveryOrderItemDTO> newDeliveryOrderItemDTOList){
        newDeliveryOrderService.setAllDeliveryOrderItems(newDeliveryOrderItemDTOList);
    }

    /**
     * Get an object with the available amount of items, for a given category and price.
     * @param verifyAmountItemsOnStockDTO
     * @return
     */
    @PostMapping("verifyAmountItemsOnStock")
    public VerifyAmountItemsOnStockDTO verifyAmountItemsOnStock(@RequestBody VerifyAmountItemsOnStockDTO verifyAmountItemsOnStockDTO){

        return this.warehouseVerifyAmountItemsOnStockService.verifyAmountItemsOnStock(verifyAmountItemsOnStockDTO);
    }

    /**
     * Save current list of Orders.
     */
    @PostMapping("sendDeliveryOrder")
    public void sendDeliveryOrder(@RequestBody List<NewDeliveryOrderItemDTO> newDeliveryOrderItemDTOList){
        this.newDeliveryOrderService.sendDeliveryOrder(newDeliveryOrderItemDTOList);
    }
}
