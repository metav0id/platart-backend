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
import java.util.regex.MatchResult;

@Service
public class MapMarkersService {

    @Autowired
    MapMarkerRapository mapMarkerRapository;
    @Autowired
    ComerceRepository comerceRepository;

    public MapMarkersService() {
    }

    public MapMarkersService(MapMarkerRapository mapMarkerRapository) {
        this.mapMarkerRapository = mapMarkerRapository;
    }

    public MapMarkersService(MapMarkerRapository mapMarkerRapository, ComerceRepository comerceRepository) {
        this.mapMarkerRapository = mapMarkerRapository;
        this.comerceRepository = comerceRepository;
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

    /**
     * Finds all the markers with no coords in the database
     *
     * @return a List of markerdto
     */
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

    /**
     * Convert List of entity markers to list of DTO markers
     *
     * @return a List of markeDTO
     */
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
            listMarkerDTO.add(markerDTO);
        }
        return listMarkerDTO;


    }

    /**
     * Convert entity marker to  DTO marker
     *
     * @return a MarkerDTO
     */
    public MarkerDTO covertUnitEntityToDTO(MapMarker marker){

        MarkerDTO markerDTO = new MarkerDTO();
        markerDTO.setLat(marker.getLat());
        markerDTO.setLng(marker.getLng());
        markerDTO.setName(marker.getName());
        markerDTO.setAddress(marker.getAddress());
        markerDTO.setId(marker.getId());
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

    /**
     * Convert DTO marker TO entity marker
     *
     * @return a entity Marker
     */
    public MapMarker covertUnitDTOToEntity(MarkerDTO marker){
        MapMarker mapMarker = new MapMarker();
        mapMarker.setLat(marker.getLat());
        mapMarker.setLng(marker.getLng());
        mapMarker.setCategory(marker.getCategory());
        mapMarker.setName(marker.getName());
        mapMarker.setAddress(marker.getAddress());
        return mapMarker;
    }

    /**
     * Convert List of  DTO markers to list of entity markers
     *
     * @return a List of entity Markers
     */
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

    /**
     * Finds marker by an id
     *
     * @return entity Marker
     */
    @Transactional(readOnly = true)
    public MapMarker findById(Long id) {
        return mapMarkerRapository.findById(id).orElse(null);
    }

    /**
     * Finds marker by name of a DTO marker
     *
     * @return Marker DTO
     */
    @Transactional(readOnly = true)
    public MarkerDTO findById2(MarkerDTO markerDTO) {
        String name = markerDTO.getName();
        Long id = markerDTO.getId();

        MapMarker mapMarker= mapMarkerRapository.findByName(name);

        return covertUnitEntityToDTO(mapMarker);

    }

    /**
     * Updates a merker giving it coords
     *
     * @return void
     */
    @Transactional
    public void update (MarkerDTO markerDTO, MarkerDTO markerToGetCoords){

        if (markerDTO == null){
            System.out.println("no marker found in data base");
        }else {
            MapMarker marker = findById(markerDTO.getId());
            marker.setLat(markerToGetCoords.getLat());
            marker.setLng(markerToGetCoords.getLng());
            mapMarkerRapository.save(marker);

        }
    }

    /**
     * Edits a marker with new info from form
     *
     * @return void
     */
//    @Transactional
//    public void edit (MarkerDTO markerDTO){
//        System.out.println(markerDTO.getName());
//        MapMarker marker = findById3(markerDTO);
//        this.name =marker.getName();
//        System.out.println(marker.getName());
//        marker.setName(markerDTO.getName());
//        marker.setAddress(markerDTO.getAddress());
//        marker.setLat(markerDTO.getLat());
//        marker.setLng(markerDTO.getLng());
//        marker.setCategory(markerDTO.getCategory());
//        System.out.println(marker.getName());
//        mapMarkerRapository.save(marker);
//        findComerce(markerDTO);
//
//    }
@Transactional
    public void edit(MarkerDTO markerDTO){
        System.out.println("1" + markerDTO.getName());
        MapMarker marker = findById3(markerDTO);
        String name =marker.getName();
        System.out.println("2" + marker.getName());
        marker.setName(markerDTO.getName());
        marker.setAddress(markerDTO.getAddress());
        marker.setLat(markerDTO.getLat());
        marker.setLng(markerDTO.getLng());
        marker.setCategory(markerDTO.getCategory());
        System.out.println("3" + marker.getName());
        mapMarkerRapository.save(marker);
        findComerce(markerDTO, name);

    }

    /**
     * Edits a the comerce correspondant to the edited marker from method above
     *
     * @return Comerce entity
     */
//    @Transactional
//    public Comerce findComerce(MarkerDTO mapMarker) {
//        System.out.println(this.name);
//        Comerce comerce = comerceRepository.findByName(this.name);
//        System.out.println(comerce.getName());
//        comerce.setName(mapMarker.getName());
//        comerce.setCategory(mapMarker.getCategory());
//        comerce.setAddress(mapMarker.getAddress());
//      comerceRepository.save(comerce);
//      return comerce;
//    }

    @Transactional(readOnly = true)
    public Comerce findComerce(MarkerDTO mapMarker, String name) {
        System.out.println("4" + name);
        Comerce comerce = comerceRepository.findByName(name);
        System.out.println(comerce.getName());
        comerce.setName(mapMarker.getName());
        comerce.setCategory(mapMarker.getCategory());
        comerce.setAddress(mapMarker.getAddress());
        comerceRepository.save(comerce);
        return comerce;
    }


    /**
     * Finds a marker by its coords
     *
     * @return a entity Marker
     */
    @Transactional(readOnly = true)
    public MapMarker findById3(MarkerDTO markerDTO) {
        String name = markerDTO.getName();
        String lng = markerDTO.getLng();
        MapMarker mapMarker= mapMarkerRapository.findByLng(lng);
        return mapMarker;
    }
}
