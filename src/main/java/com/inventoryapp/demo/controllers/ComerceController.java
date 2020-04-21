package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ComerceDTO;
import com.inventoryapp.demo.dtos.MarkerDTO;
import com.inventoryapp.demo.services.ComerceService;
import com.inventoryapp.demo.services.MapMarkersService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/getallcomerces")
    public List<ComerceDTO> getAllMarkers() {

        List<ComerceDTO> comerceDTOS = comerceService.getAllComerces();
        System.out.println("List of stock loaded.");
        return comerceDTOS;
    }

    @PostMapping("/savecomerce")
    public void saveMarker(@RequestBody ComerceDTO comerceDTO) {
        comerceService.createNewComerce(comerceDTO);

    }




}
