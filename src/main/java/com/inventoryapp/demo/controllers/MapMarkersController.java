package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.MarkerDTO;
import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.entities.MapMarker;
import com.inventoryapp.demo.services.MapMarkersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/marker")
public class MapMarkersController {

    @Autowired
   private final MapMarkersService mapMarkersService;

    public MapMarkersController(MapMarkersService mapMarkersService) {
        this.mapMarkersService = mapMarkersService;
    }
    /**
     * You get all markers which are currently available in database.
     *
     * @return List of MarkerDTO with **TODO "category":string, "quantity": int, "pricePerUnit":long. Price is in pence.
     */
    @PostMapping("/getallmarkers")
    public List<MarkerDTO> getAllMarkers() {

        List<MarkerDTO> markerDTOS = mapMarkersService.getAllMarkers();
        System.out.println("List of stock loaded.");
        return markerDTOS;
    }

    @PostMapping("/savemarker")
    public void saveMarker(@RequestBody MarkerDTO markerDTO) {
        mapMarkersService.createNewMarker(markerDTO);

    }

    /**
     * You will delete a marker
     * http://localhost:8081/marker/delete/1  in postman. the 1 is an example.
     * @return is void.
     */
//methods to delete a marker
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    //resquesbody asks for the whole entity
    public void delete(@PathVariable Long id){

        mapMarkersService.delete(id);
    }

    /**
     * You can update the info of a marker
     *
     * @return the modified Entity of the Marker.
     */
    @PostMapping("/update/{id}")
    @ResponseStatus (HttpStatus.CREATED)
    public MapMarker update (@RequestBody MarkerDTO selectedMarker, @PathVariable Long id){

        MapMarker mapMarker = mapMarkersService.update(selectedMarker, id);
        return mapMarker;

    }


}
