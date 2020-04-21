package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ComerceDTO;
import com.inventoryapp.demo.dtos.MarkerDTO;
import com.inventoryapp.demo.entities.Comerce;
import com.inventoryapp.demo.entities.MapMarker;
import com.inventoryapp.demo.repositories.ComerceRepository;
import com.inventoryapp.demo.repositories.MapMarkerRapository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComerceService {

    @Autowired
    ComerceRepository comerceRepository;
    @Autowired
    MapMarkersService mapMarkersService;

    public List<ComerceDTO> getAllComerces() {

        List<Comerce> findAllComerces = comerceRepository.findAll();
        List<ComerceDTO> findAllMarkersDTO = new ArrayList<>();

        if (findAllComerces.size() > 0) {
            findAllMarkersDTO =covertListEntityToDTO(findAllComerces);
            return findAllMarkersDTO;
        } else {
            System.out.println("----> No items found in the Data Base<----");
        }
        return findAllMarkersDTO;
    }

    public void createNewComerce(ComerceDTO comerceDTO){

        Comerce comerce = covertUnitDTOToEntity(comerceDTO);
        comerceRepository.save(comerce);
        mapMarkersService.createNewMarker(covertComerceToMarker(comerceDTO));


    }
    public Comerce covertUnitDTOToEntity(ComerceDTO comerceDTO){
        Comerce comerce = new Comerce();
        comerce.setAddress(comerceDTO.getAddress());
        comerce.setCategory(comerceDTO.getCategory());
        comerce.setName(comerceDTO.getName());

        return comerce;
    }

    public List<ComerceDTO> covertListEntityToDTO(List<Comerce> comerceEntity){
        List<ComerceDTO> comerceDTOList = new ArrayList<>();
        for(Comerce comerce: comerceEntity){
            ComerceDTO comerceDTO = new ComerceDTO();
            comerceDTO.setAddress(comerce.getAddress());
            comerceDTO.setCategory(comerce.getCategory());
            comerceDTO.setName(comerce.getName());
            comerceDTOList.add(comerceDTO);
        }

        return comerceDTOList;
    }

    public MarkerDTO covertComerceToMarker(ComerceDTO comerceDTO){
        MarkerDTO markerDTO = new MarkerDTO();
        markerDTO.setAddress(comerceDTO.getAddress());
        markerDTO.setCategory(comerceDTO.getCategory());
        markerDTO.setName(comerceDTO.getName());

        return markerDTO;
    }
}
