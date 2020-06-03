package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.DateRangeDTO;
import com.inventoryapp.demo.dtos.ShopsAllSoldItemsDTO;
import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
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
    public List<WarehouseSupplierItemDTO> getCheckinItemsListByDateRange(@RequestBody DateRangeDTO dateRangeDTO){

        List<WarehouseSupplierItemDTO> soldItemsList = new ArrayList<>();

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
            return this.managerWarehouseCheckinListingService.getSoldItemsListByDateRange(startDate, endDate);
        } catch (Error e) {
            System.err.println(e);
            WarehouseSupplierItemDTO item = new WarehouseSupplierItemDTO("category", 400, 400, 400, "supplierName");
            soldItemsList.add(item);
            return soldItemsList;
        }
    }

}
