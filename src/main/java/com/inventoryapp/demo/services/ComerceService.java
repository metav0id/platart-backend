package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ComerceDTO;
import com.inventoryapp.demo.dtos.MarkerDTO;
import com.inventoryapp.demo.entities.Comerce;
import com.inventoryapp.demo.entities.MapMarker;
import com.inventoryapp.demo.repositories.ComerceRepository;
import com.inventoryapp.demo.repositories.MapMarkerRapository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComerceService {

    @Autowired
    ComerceRepository comerceRepository;
    @Autowired
    MapMarkersService mapMarkersService;

    public ComerceService(ComerceRepository comerceRepository) {
        this.comerceRepository = comerceRepository;
    }

    /**
     * Gets you all the comerces saved in DataBase
     *
     * @return a list of ComerceDTOs
     */
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

    /**
     * Gets you all the comerce: shops saved in DataBase
     *
     * @return a list of ComerceDTOs
     */
    public List<ComerceDTO> getAllShops() {

        List<Comerce> findAllComerces = comerceRepository.findByCategory();
        List<ComerceDTO> findAllComerceDTO = new ArrayList<>();

        if (findAllComerces.size() > 0) {
            findAllComerceDTO =covertListEntityToDTO(findAllComerces);
            return findAllComerceDTO;
        } else {
            System.out.println("----> No items found in the Data Base<----");
        }
        return findAllComerceDTO;
    }

    /**
     * Created a new comerce
     *
     * @return void
     */
    public void createNewComerce(ComerceDTO comerceDTO){
        Comerce comerce = covertUnitDTOToEntity(comerceDTO);
        comerceRepository.save(comerce);
        mapMarkersService.createNewMarker(covertComerceToMarker(comerceDTO));

    }

    public void createNewComerceTest(ComerceDTO comerceDTO){
        Comerce comerce = covertUnitDTOToEntity(comerceDTO);
        comerceRepository.save(comerce);

    }

    /**
     * Convert DTO comerce TO entity comerce
     *
     * @return a entity comerce
     */
    public Comerce covertUnitDTOToEntity(ComerceDTO comerceDTO){
        Comerce comerce = new Comerce();
        comerce.setAddress(comerceDTO.getAddress());
        comerce.setCategory(comerceDTO.getCategory());
        comerce.setName(comerceDTO.getName());
        comerce.setId(comerceDTO.getId());

        return comerce;
    }

    /**
     * Convert entity marker to DTO marker
     *
     * @return a DTO comerce
     */
    public ComerceDTO covertUnitEntityToDTO(Comerce comerceDTO){
        ComerceDTO comerce = new ComerceDTO();
        comerce.setAddress(comerceDTO.getAddress());
        comerce.setCategory(comerceDTO.getCategory());
        comerce.setName(comerceDTO.getName());
        comerce.setId(comerceDTO.getId());
        return comerce;
    }

    /**
     * Convert a list of entity marker to a list of DTO marker
     *
     * @return a list of DTO comerce
     */
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

    /**
     * Convert a DTO comerce to a DTO marker
     *
     * @return a list of DTO marker
     */
    public MarkerDTO covertComerceToMarker(ComerceDTO comerceDTO){
        MarkerDTO markerDTO = new MarkerDTO();
        markerDTO.setAddress(comerceDTO.getAddress());
        markerDTO.setCategory(comerceDTO.getCategory());
        markerDTO.setName(comerceDTO.getName());
        markerDTO.setId(comerceDTO.getId());

        return markerDTO;
    }

    /**
     * Delets a comerce when id given (todo update by giving complete entitiy)
     *
     * @return void
     */
    @Transactional
    public void deleteComerce(Long id) {

        Comerce comerce = findById(id);
        if (comerce!= null){
            comerceRepository.deleteById(id);
        }else {
            System.out.println("The comerce doesnt exists");
        }
    }

    /**
     * Gets you a comerce by id
     *
     * @return a Comerce
     */
    @Transactional(readOnly = true)
    public Comerce findById(Long id) {
        return comerceRepository.findById(id).orElse(null);
    }

    /**
     * Gets you a comerce by comercedto
     *
     * @return a Comerde
     */
    @Transactional(readOnly = true)
    public ComerceDTO findById2(ComerceDTO comerceDTO) {
        Long id = comerceDTO.getId();
        Comerce comerce = comerceRepository.findById(id).orElse(null);
        return covertUnitEntityToDTO(comerce);
    }

}
