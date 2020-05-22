package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ComerceDTO;
import com.inventoryapp.demo.dtos.MarkerDTO;
import com.inventoryapp.demo.entities.Comerce;
import com.inventoryapp.demo.entities.MapMarker;
import com.inventoryapp.demo.repositories.ComerceRepository;
import com.inventoryapp.demo.repositories.MapMarkerRapository;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MapMarkersService {

    @Autowired
    MapMarkerRapository mapMarkerRapository;
    @Autowired
    ComerceRepository comerceRepository;

    public MapMarkersService(MapMarkerRapository mapMarkerRapository) {
        this.mapMarkerRapository = mapMarkerRapository;
    }

    /**
     * Finds all the markers in the database
     *
     * @return a List of markerdto
     */

    public List<MarkerDTO> getAllMarkers() {

        List<MapMarker> findAllMarkers = mapMarkerRapository.findAll();
        List<MarkerDTO> findAllMarkersDTO = new ArrayList<>();

        if (findAllMarkers.size() >= 0) {
            findAllMarkersDTO =covertListEntityToDTO(findAllMarkers);
            return findAllMarkersDTO;
        } else {
            System.out.println("----> No items found in the Data Base<----");
        }
        return findAllMarkersDTO;
    }
    public List<MarkerDTO> getAllMarkersWithNoCoords() {

        List<MapMarker> findAllMarkers = mapMarkerRapository.findByCategory();
        List<MarkerDTO> findAllMarkersDTO = new ArrayList<>();

        if (findAllMarkers.size() >= 0) {
            findAllMarkersDTO =covertListEntityToDTO(findAllMarkers);
            return findAllMarkersDTO;
        } else {
            System.out.println("----> No items found in the Data Base<----");
        }
        return findAllMarkersDTO;
    }

    public List <MarkerDTO> covertListEntityToDTO (List<MapMarker> listMarkers){
        List <MarkerDTO> listMarkerDTO = new ArrayList<>();
        for (MapMarker marker : listMarkers) {
            MarkerDTO markerDTO = new MarkerDTO();
            markerDTO.setLat(marker.getLat());
            markerDTO.setLng(marker.getLng());
            markerDTO.setName(marker.getName());
            markerDTO.setAddress(marker.getAddress());
            markerDTO.setCategory(marker.getCategory());
            markerDTO.setId(marker.getId());
//            markerDTO.setLink(marker.getLink());



            listMarkerDTO.add(markerDTO);
        }
        return listMarkerDTO;


    }

    public MarkerDTO covertUnitEntityToDTO(MapMarker marker){

        MarkerDTO markerDTO = new MarkerDTO();
        markerDTO.setLat(marker.getLat());
        markerDTO.setLng(marker.getLng());
        markerDTO.setName(marker.getName());
        markerDTO.setAddress(marker.getAddress());
        markerDTO.setId(marker.getId());
//            markerDTO.setLink(marker.getLink());

        return markerDTO;

    }
    /**
     * Creates a new marker
     *
     * @return void
     */

    //create new marker methods
    public void createNewMarker(MarkerDTO markerDTO){
        MapMarker mapMarker = covertUnitDTOToEntity(markerDTO);

        mapMarkerRapository.save(mapMarker);
    }
    public MapMarker save (MapMarker mapMarker){

        return mapMarkerRapository.save(mapMarker);
    }

    public MapMarker covertUnitDTOToEntity(MarkerDTO marker){
        MapMarker mapMarker = new MapMarker();
        mapMarker.setLat(marker.getLat());
        mapMarker.setLng(marker.getLng());
        mapMarker.setCategory(marker.getCategory());
        mapMarker.setName(marker.getName());
        mapMarker.setAddress(marker.getAddress());
        return mapMarker;
    }


    public List <MapMarker> covertListDTOToEntity(List<MarkerDTO> listMarkers){
        List <MapMarker> listMarker = new ArrayList<>();
        for (MarkerDTO marker : listMarkers) {
            MapMarker mapMarker = new MapMarker();
            mapMarker.setLat(marker.getLat());
            mapMarker.setLng(marker.getLng());
            mapMarker.setName(marker.getName());
            mapMarker.setAddress(marker.getAddress());
//            mapMarker.setLink(marker.getLink());

            listMarker.add(mapMarker);
        }
        return listMarker;
    }
    /**
     * Deletes a merker from database
     *
     * @return void
     */

    @Transactional
    public void deleteMarker(Long id) {

        MapMarker mapMarker = findById(id);
        if (mapMarker!= null){
            mapMarkerRapository.deleteById(id);
        }else {
            System.out.println("The marker doesnt exists");
        }
    }


    @Transactional(readOnly = true)
    public MapMarker findById(Long id) {
        return mapMarkerRapository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public MarkerDTO findById2(MarkerDTO markerDTO) {
        String name = markerDTO.getName();
        Long id = markerDTO.getId();

        MapMarker mapMarker= mapMarkerRapository.findByName(name);

        return covertUnitEntityToDTO(mapMarker);

    }

    @Transactional
    public void update (MarkerDTO markerDTO, MarkerDTO markerToGetCoords){

        if (markerDTO == null){
            System.out.println("no marker found in data base");
        }else {
            MapMarker marker = findById(markerDTO.getId());
            marker.setLat(markerToGetCoords.getLat());
            marker.setLng(markerToGetCoords.getLng());
            mapMarkerRapository.save(marker);

//        MarkerDTO markerDTO1 = findById2(markerDTO);
//        markerDTO1.setLat(markerToGetCoords.getLat());
//        markerDTO1.setLng(markerToGetCoords.getLng());
//        System.out.println(markerDTO1.getCategory());
//
//        MapMarker marker = covertUnitDTOToEntity(markerDTO1);
//        System.out.println(marker.getLat());


//        mapMarkerRapository.save(marker);

//        return markerDTO1;
        }
    }




    /**
     * Updates a marker with the info of a comerce
     *
     * @return a List of markerdto
     */
    @Transactional(readOnly = true)

    public Comerce findById2(ComerceDTO comerceDTO) {
        Long id = comerceDTO.getId();
        return comerceRepository.findById(id).orElse(null);
    }

//@Transactional
//    public MapMarker update (MarkerDTO markerDTO, String lat, String lng){
//    System.out.println(markerDTO.getName());
//    Long id = markerDTO.getId();
//    MapMarker marketToEdit = mapMarkerRapository.findById(id);
//
//    marketToEdit.setLng(lng);
//    markerDTO.setLat(lat);
//    System.out.println(markerDTO.getLat());
//
//    MapMarker marker = covertUnitDTOToEntity(markerDTO);
//    System.out.println(marker.getLat());
//
//    mapMarkerRapository.save(marker);
//
//        return marker;
//    }

}
