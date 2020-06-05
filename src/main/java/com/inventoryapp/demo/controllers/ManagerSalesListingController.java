package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.DateRangeDTO;
import com.inventoryapp.demo.dtos.ShopsAllSoldItemsDTO;
import com.inventoryapp.demo.services.ManagerSalesListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/manager")
public class ManagerSalesListingController {

    private final ManagerSalesListingService managerSalesListingService;

    @Autowired
    public ManagerSalesListingController(ManagerSalesListingService managerSalesListingService) {
        this.managerSalesListingService = managerSalesListingService;
    }

    @PostMapping("/getSoldItemsListByDateRange")
    public List<ShopsAllSoldItemsDTO> getSoldItemsListByDateRange(@RequestBody DateRangeDTO dateRangeDTO){
        List<ShopsAllSoldItemsDTO> soldItemsList = new ArrayList<>();

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().minusDays(1);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            startDate = LocalDateTime.parse(dateRangeDTO.getStartDate(), formatter);
            endDate = LocalDateTime.parse(dateRangeDTO.getEndDate() , formatter);
            System.out.println("start: "+ startDate + " = end: "+endDate);
        } catch (Error e){
            System.err.println(e);
        }


        try {
            return this.managerSalesListingService.getSoldItemsListByDateRange(startDate, endDate);
        } catch (Error e) {
            System.err.println(e);
            ShopsAllSoldItemsDTO item = new ShopsAllSoldItemsDTO();
            soldItemsList.add(item);
            return soldItemsList;
        }
    }

}
