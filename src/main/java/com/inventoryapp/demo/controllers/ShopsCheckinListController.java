package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopSimpleDTO;
import com.inventoryapp.demo.dtos.ShopCheckedInItemDTO;
import com.inventoryapp.demo.services.ShopsCheckinListServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/shops")
public class ShopsCheckinListController {

    @Autowired
    private ShopsCheckinListServices shopsCheckinListServices;

    @PostMapping("/getAllCheckedInItems")
    private List<ShopCheckedInItemDTO> getAllCheckedInItems(){
        return this.shopsCheckinListServices.getAllCheckedInItems();
    }

    @PostMapping("/getSpecificCheckedInItems")
    private List<ShopCheckedInItemDTO> getSpecificCheckedInItems(@RequestBody ShopSimpleDTO shopDTO){
        System.out.println("Input Shop: " + shopDTO.getShop());

        return this.shopsCheckinListServices.getSpecificCheckedInItems(shopDTO.getShop());
    }
}
