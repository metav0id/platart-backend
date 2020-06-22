package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.*;
import com.inventoryapp.demo.services.ShopsCheckoutSoldItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/shops")
public class ShopsCheckoutSoldItemsController {

    @Autowired
    private ShopsCheckoutSoldItemsService shopsCheckoutSoldItemsService;

    /**
     * save current list of sold items.
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
     @PostMapping("/saveShopSpecificSoldItemsList")
    public void saveShopSpecificSoldItemsList(
            @RequestBody ShopsCheckoutSoldItemsSaveSoldItemsListDTO requestDTO) {
        System.out.println("Controller: Save current Sold item list.");
        this.shopsCheckoutSoldItemsService.saveShopSpecificSoldItemsList(
                requestDTO.getShop(),
                requestDTO.getItemsDTOList()
        );
    }

    /**
     * get currenty saved list of sold items.
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
    @PostMapping("/getShopSpecificSoldItemsList")
    public List<ShopsCheckoutSoldItemsDTO> getShopSpecificSoldItemsList(
            @RequestBody ShopsCheckoutSoldItemsShopDTO shopDTO) {
        return this.shopsCheckoutSoldItemsService.getShopSpecificSoldItemsList(shopDTO.getShop());
    }

    /**
     * get currenty saved list of sold items.
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
    @PostMapping("/deleteShopSpecificCheckoutSoldItemsList")
    public void deleteShopSpecificCheckoutSoldItemsList(

            @RequestBody ShopsCheckoutSoldItemsSimpleShopDTO shopDTO) {
        System.out.println("Controller: Delete current Sold item list.");
        this.shopsCheckoutSoldItemsService.deleteSpecificShopCurrentSoldItemsList(shopDTO.getShop());
    }

    /**
     * get currenty saved list of sold items.
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
    @PostMapping("/sendSpecificShopSoldItemsList")
    public List<ShopsCheckoutSoldItemsDTO> sendSpecificShopSoldItemsList(

            @RequestBody ShopsCheckoutSoldItemsSaveSoldItemsListDTO requestDTO) {

        System.out.println("Controller: Send current shop-specific Sold item list.");
        return this.shopsCheckoutSoldItemsService.sendSpecificShopSoldItemsList(
                requestDTO.getShop(),
                requestDTO.getItemsDTOList());
    }


}
