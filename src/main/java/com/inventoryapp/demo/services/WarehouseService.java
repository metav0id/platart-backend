package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.entities.Item;
import com.inventoryapp.demo.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;


    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * This service gets all items in table Item from data base and turns them into a DTO list.
     */

    public List<WarehouseGetAllItemsDTO> getAllStockItems() {
        List<Item> findAllItemsList = warehouseRepository.findAll();
        List<WarehouseGetAllItemsDTO> findAllItemsDTOList = new ArrayList<>();
        if (findAllItemsList.size() > 0) {
            findAllItemsDTOList = covertToDTO(findAllItemsList);
            return findAllItemsDTOList;
        } else {
            System.out.println("----> No items found in the Data Base<----");
        }
        return findAllItemsDTOList;

    }

    /**
     * This service turns a list of entities into a list of DTOS.
     */

    public List<WarehouseGetAllItemsDTO> covertToDTO(List<Item> findAllItems) {
        List<WarehouseGetAllItemsDTO> listWarehouseItemsDTO = new ArrayList<>();
        for (Item item : findAllItems) {
            WarehouseGetAllItemsDTO getAllItemsDTO = new WarehouseGetAllItemsDTO();
            getAllItemsDTO.setCategory(item.getCategory());
            getAllItemsDTO.setPricePerUnit(item.getPricePerUnit());
            getAllItemsDTO.setQuantity(item.getQuantity());
            listWarehouseItemsDTO.add(getAllItemsDTO);
        }
        return listWarehouseItemsDTO;

    }


}
