package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopsCheckoutSoldItemsDTO;
import com.inventoryapp.demo.dtos.ShopsCheckoutSoldItemsSaveSoldItemsListDTO;
import com.inventoryapp.demo.dtos.ShopsCheckoutSoldItemsShopDTO;
import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
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
    @PostMapping("/saveAllSoldItemsList")
    public void saveAllSoldItemsList(@RequestBody ShopsCheckoutSoldItemsSaveSoldItemsListDTO requestDTO) {
        System.out.println("Controller: Save current Sold item list.");
        this.shopsCheckoutSoldItemsService.saveShopSpecificSoldItemsList(
                requestDTO.getShop(),
                requestDTO.getItemsDTOList()
        );
    }

    /**
     * save current list of sold items.
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
    @PostMapping("/saveShopSpecificSoldItemsList")
    public void saveShopSpecificSoldItemsList(@RequestBody List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList) {
        System.out.println("Controller: Save current Sold item list.");
        this.shopsCheckoutSoldItemsService.saveAllSoldItemsList(shopsCheckoutSoldItemsDTOList);
    }

    /**
     * get currenty saved list of sold items.
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
    @PostMapping("/getAllSoldItemsList")
    public List<ShopsCheckoutSoldItemsDTO> getAllSoldItemsList() {
        System.out.println("Controller: Get current Sold item list.");
        return this.shopsCheckoutSoldItemsService.getAllSoldItemsList();
    }

    /**
     * get currenty saved list of sold items.
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
    @PostMapping("/getShopSpecificSoldItemsList")
    public List<ShopsCheckoutSoldItemsDTO> getShopSpecificSoldItemsList(@RequestBody ShopsCheckoutSoldItemsShopDTO shopDTO) {
        return this.shopsCheckoutSoldItemsService.getShopSpecificSoldItemsList(shopDTO.getShop());
    }

    /**
     * get currenty saved list of sold items.
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
    @PostMapping("/deleteCurrentSoldItemsList")
    public void deleteCurrentSoldItemsList() {
        System.out.println("Controller: Delete current Sold item list.");
        this.shopsCheckoutSoldItemsService.deleteCurrentSoldItemsList();
    }

    /**
     * get currenty saved list of sold items.
     * @return List of WarehouseGetAllItemsDTO with "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
    @PostMapping("/sendAllSoldItemsList")
    public List<ShopsCheckoutSoldItemsDTO> sendCurrentSoldItemsList(@RequestBody List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList) {
        System.out.println("Controller: Send current Sold item list.");
        return this.shopsCheckoutSoldItemsService.sendAllSoldItemsList(shopsCheckoutSoldItemsDTOList);
    }


}
