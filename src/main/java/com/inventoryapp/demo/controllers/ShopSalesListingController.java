package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopAndDateRangeDTO;
import com.inventoryapp.demo.dtos.ShopsAllSoldItemsDTO;
import com.inventoryapp.demo.services.ShopsSalesListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/manager")
public class ShopSalesListingController {

    private final ShopsSalesListingService managerSalesListingService;

    @Autowired
    public ShopSalesListingController(ShopsSalesListingService managerSalesListingService) {
        this.managerSalesListingService = managerSalesListingService;
    }

    @PostMapping("/getSoldItemsListByShopAndDateRange")
    public List<ShopsAllSoldItemsDTO> getSoldItemsListByShopAndDateRange(@RequestBody ShopAndDateRangeDTO shopAndDateRangeDTO){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime startDate = LocalDateTime.parse(shopAndDateRangeDTO.getStartDate(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(shopAndDateRangeDTO.getEndDate() , formatter);
        String selectedShop = shopAndDateRangeDTO.getShop();

        try {
            return this.managerSalesListingService.getSoldItemsListByShopAndDateRange(selectedShop, startDate, endDate);
        } catch (Error e) {
            System.err.println(e);
            List<ShopsAllSoldItemsDTO> soldItemsList = new ArrayList<>();
            return soldItemsList;
        }
    }

}
