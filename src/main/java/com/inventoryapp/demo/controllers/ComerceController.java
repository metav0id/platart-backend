package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ComerceDTO;
import com.inventoryapp.demo.dtos.MarkerDTO;
import com.inventoryapp.demo.entities.Comerce;
import com.inventoryapp.demo.services.ComerceService;
import com.inventoryapp.demo.services.MapMarkersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/comerce")
public class ComerceController {
    @Autowired
    private final ComerceService comerceService;

    public ComerceController(ComerceService comerceService) {
        this.comerceService = comerceService;
    }
    /**
     * You get all comerces which are currently available in database.
     *
     * @return List of ComerceDTO with ** ,"category":string, "name": String, "address": String,
     */
    @GetMapping("/getallcomerces")
    public List<ComerceDTO> getAllComerces() {

        List<ComerceDTO> comerceDTOS = comerceService.getAllComerces();
        System.out.println("List of stock loaded.");
        return comerceDTOS;
    }
    /**
     * You get all shops which are currently available in database.
     *
     * @return List of ComerceDTO with ** ,"category":string, "name": String, "address": String,
     */

    @GetMapping("/getallshops")
    public List<ComerceDTO> getAllShops() {

        List<ComerceDTO> comerceDTOS = comerceService.getAllShops();
        System.out.println("List of shops loaded.");
        return comerceDTOS;
    }
    /**
     * You create a new comerce that will be saved in the database.
     *
     * @return List of void
     */
    @PostMapping("/savecomerce")
    public void saveComerce(@RequestBody ComerceDTO comerceDTO) {
        comerceService.createNewComerce(comerceDTO);

    }

    @PostMapping("/find")
    public void findComerce (@RequestBody ComerceDTO comerceDTO) {
        ComerceDTO comerce= comerceService.findById2(comerceDTO);
        System.out.println(comerce);
    }



}
