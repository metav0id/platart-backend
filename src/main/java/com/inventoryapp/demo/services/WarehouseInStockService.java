package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.entities.WarehouseStockItem;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class WarehouseInStockService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    //TODO why is in constructor warehouseRepository needed when it is already declared as @Autowired?
    public WarehouseInStockService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * This service gets all items in table Item from data base and turns them into a DTO list.
     */

    public List<WarehouseGetAllItemsDTO> getAllStockItems() {
        List<WarehouseStockItem> findAllItemsList = warehouseRepository.findAll();
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

    private List<WarehouseGetAllItemsDTO> covertToDTO(List<WarehouseStockItem> findAllWarehouseStockItems) {
        List<WarehouseGetAllItemsDTO> listWarehouseItemsDTO = new ArrayList<>();
        for (WarehouseStockItem item : findAllWarehouseStockItems) {
            WarehouseGetAllItemsDTO getAllItemsDTO = new WarehouseGetAllItemsDTO();
            getAllItemsDTO.setCategory(item.getCategory());
            getAllItemsDTO.setPricePerUnit(item.getPricePerUnit());
            getAllItemsDTO.setQuantity(item.getQuantity());
            listWarehouseItemsDTO.add(getAllItemsDTO);
        }
        return listWarehouseItemsDTO;

    }

    /**
     * This service allows to enter a new item to the list of items.
     */
/*
    public void enterNewItem(WarehouseGetAllItemsDTO itemDTO) {
        Item itemEntity = covertToEntity(itemDTO);
        warehouseRepository.save(itemEntity);
    }*/

    public void enterNewList(List<WarehouseSupplierItemDTO> itemListDTO) {
        // 1. transfer WarehouseSupplierDTO to WarehouseStockItem List
        List<WarehouseStockItem> listStockItem = new ArrayList<>();
        for(WarehouseSupplierItemDTO item : itemListDTO){
            listStockItem.add(this.covertToEntity(item));
        }
        // 2, Identify duplicates by category and price. So cumulate quantity.
        //TODO Enrico-09.04.2020: summarize all items on list by category and price. So first identify duplicates.
        List<WarehouseStockItem> newListStockItem = new ArrayList<>();
        for(WarehouseStockItem item : listStockItem){
            if(newListStockItem.contains(item)){
                int tempIndex = newListStockItem.indexOf(item);
                System.out.println("Duplicate at index: " + tempIndex);
                System.out.println("Old quantitiy: " + newListStockItem.get(tempIndex).getQuantity());
                long tempSumQuantity = item.getQuantity() + newListStockItem.get(tempIndex).getQuantity();
                newListStockItem.get(tempIndex).setQuantity(tempSumQuantity);
                System.out.println("New quantitiy: " + newListStockItem.get(tempIndex).getQuantity());
            }
        }

        //TODO find products in stock with same category and pricePerUnit and cumulate


        /*List<WarehouseStockItem> listFromStock = warehouseRepository.findByCategory()
        warehouseRepository.save(itemEntity);*/
    }

    /**
     * This method turns a DTO into an entity.
     */

    /*public Item covertToEntity(WarehouseGetAllItemsDTO itemDto) {
        Item item = new Item();
        item.setCategory(itemDto.getCategory());
        item.setId(itemDto.getId());
        item.setPricePerUnit(itemDto.getPricePerUnit());
        item.setQuantity(itemDto.getQuantity());
        return item;
    }*/

    private WarehouseStockItem covertToEntity(WarehouseSupplierItemDTO itemDto) {
        WarehouseStockItem item = new WarehouseStockItem();
        item.setCategory(itemDto.getCategory());
        item.setPricePerUnit(itemDto.getPricePerUnit());
        item.setQuantity(itemDto.getQuantity());
        return item;
    }
}
