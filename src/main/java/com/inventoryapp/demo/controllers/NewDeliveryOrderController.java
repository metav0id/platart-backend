package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.NewDeliveryOrderItemDTO;
import com.inventoryapp.demo.services.NewDeliveryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<NewDeliveryOrderItemDTO> newDeliveryOrderItemDTOList = new ArrayList<>();
        System.out.println("Controller1:");
        System.out.println(newDeliveryOrderItemDTOList);
        newDeliveryOrderItemDTOList = newDeliveryOrderService.getAllDeliveryOrderItems();

        System.out.println("Controller2:");
        System.out.println(newDeliveryOrderItemDTOList);

        return newDeliveryOrderItemDTOList;
    }

    @PostMapping("/setAllNewOrderItems")
    public void setAllNewOrderItems(@RequestBody List<NewDeliveryOrderItemDTO> newDeliveryOrderItemDTOList){
        newDeliveryOrderService.setAllDeliveryOrderItems(newDeliveryOrderItemDTOList);
    }
}
