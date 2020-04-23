package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.MarkerDTO;
import com.inventoryapp.demo.entities.MapMarker;
import com.inventoryapp.demo.repositories.MapMarkerRapository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapMarkersService {

    @Autowired
    MapMarkerRapository mapMarkerRapository;


    public List<MarkerDTO> getAllMarkers() {

        List<MapMarker> findAllMarkers = mapMarkerRapository.findAll();
        List<MarkerDTO> findAllMarkersDTO = new ArrayList<>();

        if (findAllMarkers.size() > 0) {
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
            markerDTO.setLink(marker.getLink());
            markerDTO.setLink(marker.getLink());
            markerDTO.setId( marker.getId());

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
            markerDTO.setLink(marker.getLink());
            markerDTO.setLink(marker.getLink());
            markerDTO.setId( marker.getId());

        return markerDTO;

    }

    //create new marker methods
    public void createNewMarker(MarkerDTO markerDTO){
        MapMarker mapMarker = covertUnitDTOToEntity(markerDTO);

        mapMarkerRapository.save(mapMarker);
    }

    public MapMarker covertUnitDTOToEntity(MarkerDTO marker){
            MapMarker mapMarker = new MapMarker();
            mapMarker.setLat(marker.getLat());
            mapMarker.setLng(marker.getLng());
            mapMarker.setCategory(marker.getCategory());
            mapMarker.setName(marker.getName());
            mapMarker.setAddress(marker.getAddress());
            mapMarker.setLink(marker.getLink());
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
            mapMarker.setLink(marker.getLink());
            mapMarker.setLink(marker.getLink());
            listMarker.add(mapMarker);
        }
        return listMarker;
    }

    @Transactional
    public void delete(Long id) {

        MapMarker mapMarker = findById(id);
        if (mapMarker!= null){
            mapMarkerRapository.deleteById(id);
        }else {
            System.out.println("The user doesnt exists");
        }
    }

    @Transactional(readOnly = true)
    public MapMarker findById(Long id) {
        return mapMarkerRapository.findById(id).orElse(null);
    }

@Transactional
    public MapMarker update (MarkerDTO marker, Long id){
    System.out.println("in method ");
        MapMarker mapMarker = findById(id);
    System.out.println(id);
        mapMarker.setLat(marker.getLat());
        mapMarker.setLng(marker.getLng());
        mapMarkerRapository.save(mapMarker);
        return mapMarker;
    }

}
