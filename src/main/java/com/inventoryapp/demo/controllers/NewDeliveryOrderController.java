package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.NewDeliveryOrderItemDTO;
import com.inventoryapp.demo.services.NewDeliveryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/warehouse")
public class NewDeliveryOrderController {

    private NewDeliveryOrderService newDeliveryOrderService;

    @Autowired
    public NewDeliveryOrderController(NewDeliveryOrderService newDeliveryOrderService) {
        this.newDeliveryOrderService = newDeliveryOrderService;
    }

    /**
     * REST Post-Request of all new order items.
     * @return DTO-List with current new order items.
     */
    @PostMapping("/getAllNewOrderItems")
    public List<NewDeliveryOrderItemDTO> getAllNewOrderItems(){
        return newDeliveryOrderService.getAllDeliveryOrderItems();
    }

    @PostMapping("/setAllNewOrderItems")
    public void setAllNewOrderItems(@RequestBody List<NewDeliveryOrderItemDTO> newDeliveryOrderItemDTOList){
        newDeliveryOrderService.setAllDeliveryOrderItems(newDeliveryOrderItemDTOList);
    }
}
