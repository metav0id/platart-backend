package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopAndDateRangeDTO;
import com.inventoryapp.demo.dtos.ManagerWarehouseCheckinListingDTO;
import com.inventoryapp.demo.services.ManagerWarehouseCheckinListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/manager")
public class ManagerWarehouseCheckinListingController {

    private ManagerWarehouseCheckinListingService managerWarehouseCheckinListingService;

    @Autowired
    public ManagerWarehouseCheckinListingController(ManagerWarehouseCheckinListingService managerWarehouseCheckinListingService) {
        this.managerWarehouseCheckinListingService = managerWarehouseCheckinListingService;
    }

    @PostMapping("/getCheckinItemsListByDateRange")
    public List<ManagerWarehouseCheckinListingDTO> getCheckinItemsListByDateRange(@RequestBody ShopAndDateRangeDTO shopAndDateRangeDTO){

        List<ManagerWarehouseCheckinListingDTO> soldItemsList = new ArrayList<>();

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().minusYears(1);

        // 1. get Date
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            startDate = LocalDateTime.parse(shopAndDateRangeDTO.getStartDate(), formatter);
            endDate = LocalDateTime.parse(shopAndDateRangeDTO.getEndDate() , formatter);
            System.out.println("start: "+ startDate + " = end: "+endDate);
        } catch (Error e){
            System.err.println(e);
        }

        // 2. Fetch and return Data
        try {
            System.out.println("error in try");
            return this.managerWarehouseCheckinListingService.getSoldItemsListByDateRange(startDate, endDate);
        } catch (Error e) {
            System.out.println("error in catch");
            System.err.println(e);
            ManagerWarehouseCheckinListingDTO item = new ManagerWarehouseCheckinListingDTO("category", 400, (double)400, (double)400, "supplierName", LocalDateTime.now() );
            soldItemsList.add(item);
            return soldItemsList;
        }
    }

}
