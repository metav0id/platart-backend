package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ComerceDTO;
import com.inventoryapp.demo.dtos.MarkerDTO;
import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.entities.Comerce;
import com.inventoryapp.demo.entities.MapMarker;
import com.inventoryapp.demo.repositories.ComerceRepository;
import com.inventoryapp.demo.repositories.MapMarkerRapository;
import com.inventoryapp.demo.services.ComerceService;
import com.inventoryapp.demo.services.MapMarkersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/marker")
public class MapMarkersController {

    @Autowired
    private final MapMarkersService mapMarkersService;
    @Autowired
    MapMarkerRapository mapMarkerRapository;
    @Autowired
    ComerceRepository comerceRepository;
    @Autowired
    ComerceService comerceService;

    public MapMarkersController(MapMarkersService mapMarkersService) {
        this.mapMarkersService = mapMarkersService;
    }
    /**
     * You get all markers which are currently available in database.
     *
     * @return List of MarkerDTO with ** "category":string, "name": String, "address": String,"lat": String, "lng": String, "link": String,
     *
     */

    @GetMapping("/getallmarkers")
    public List<MarkerDTO> getAllMarkers() {

        List<MarkerDTO> markerDTOS = mapMarkersService.getAllMarkers();
        System.out.println("List of markers loaded.");
        return markerDTOS;
    }
    /**
     * You get all markers with no coords which are currently available in database.
     *
     * @return List of MarkerDTO with ** "category":string, "name": String, "address": String,"lat": String, "lng": String, "link": String,
     *
     */
    @GetMapping("/getallmarkersNoCoords")
    public List<MarkerDTO> getAllMarkersNoCoords() {

        List<MarkerDTO> markerDTOS = mapMarkersService.getAllMarkersWithNoCoords();
        System.out.println("List of markers loaded.");
        return markerDTOS;
    }

    /**
     * You create a new marker that will be saved in the database.
     *
     * @return List of void
     */
    @PostMapping("/savemarker")
    public void saveMarker(@RequestBody MarkerDTO markerDTO) {
        mapMarkersService.createNewMarker(markerDTO);

    }

    /**
     * You will delete a marker
     * http://localhost:8081/marker/delete/1  in postman. the 1 is an example.
     * @return is void.
     */

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    /**resquesbody asks for the whole entity**/
    public void delete(@RequestBody MarkerDTO markerDTO){

        MapMarker mapMarker = mapMarkerRapository.findByName(markerDTO.getName());
        if (mapMarker == null){
            System.out.println("merker has not assigned comerce");

        }else {
            Comerce comerce = comerceRepository.findByName(markerDTO.getName());
            Long idComerce = comerce.getId();
            Long idMarker = mapMarker.getId();
            comerceService.deleteComerce(idComerce);

            mapMarkersService.deleteMarker(idMarker);
        }
    }
    /**
     * Delete the coords from a amrker making asignable to a marker in the map again.
     *
     * @return void.
     */

    @PostMapping("/deleteCoords")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //resquesbody asks for the whole entity
    public void deleteCoords(@RequestBody MarkerDTO markerDTO){
        mapMarkersService.deleteCoords(markerDTO);

    }

    /**
     * You can update the info of a marker
     *
     * @return the modified Entity of the Marker.
     */
    @PostMapping("/update")
    @ResponseStatus (HttpStatus.CREATED)
    public void update (@RequestBody MarkerDTO[] markerDTO){
        System.out.println("in controller");
        mapMarkersService.update(markerDTO[0],markerDTO[1]);
    }
    /**
     * Edits a marker from the data base keeping its coords and also edits correspondant comerce.
     *
     * @return void.
     */

    @PostMapping("/edit")
    public void editMarker (@RequestBody MarkerDTO markerDTO) {
        System.out.println(markerDTO.getName());
        System.out.println(markerDTO.getLat());
        mapMarkersService.edit(markerDTO);

    }

}
