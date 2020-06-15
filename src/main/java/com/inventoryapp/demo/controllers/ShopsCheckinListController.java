package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopDateSimpleDTO;
import com.inventoryapp.demo.dtos.ShopSimpleDTO;
import com.inventoryapp.demo.dtos.ShopCheckedInItemDTO;
import com.inventoryapp.demo.services.ShopsCheckinListServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/shops")
public class ShopsCheckinListController {

    @Autowired
    private ShopsCheckinListServices shopsCheckinListServices;

    // getSpecificCheckedInItems, getAllCheckedInItems

    @PostMapping("/getSpecificCheckedInItemsDate")
    private List<ShopCheckedInItemDTO> getSpecificCheckedInItemsDate(@RequestBody ShopDateSimpleDTO shopDTO){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime startDate = LocalDateTime.parse(shopDTO.getStartDate(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(shopDTO.getEndDate() , formatter);
        return this.shopsCheckinListServices.getSpecificCheckedInItemsDate(shopDTO.getShop(), startDate, endDate);
    }
}
