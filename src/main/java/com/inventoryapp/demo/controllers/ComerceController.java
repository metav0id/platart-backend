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
    public List<ComerceDTO> getAllMarkers() {

        List<ComerceDTO> comerceDTOS = comerceService.getAllComerces();
        System.out.println("List of stock loaded.");
        return comerceDTOS;
    }

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

    /**
     * You will delete a comerce
     * http://localhost:8081/comerce/delete/1  in postman. the 1 is an example.
     * @return is void.
     */

/**methods to delete a marker**/
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    /**resquesbody asks for the whole entity**/
    public void delete(@PathVariable Long id){

        comerceService.deleteComerce(id);
    }
//    @PostMapping("/edit")
//    public void editComerce (@RequestBody Comerce comerce) {
//        comerceService.update(comerce);
//
//
//
//    }

}
