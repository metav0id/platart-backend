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

    public void saveListToIntenvoryStock(List<WarehouseSupplierItemDTO> itemListDTO) {
        //Remove all duplicates from DTO list and cumulate quantities per category and pricePerUnit
        List<WarehouseStockItem> itemListEntity = removeDuplicates(itemListDTO);

    }

    private List<WarehouseStockItem> removeDuplicates(List<WarehouseSupplierItemDTO> itemListDTO) {
        // 1. transfer WarehouseSupplierDTO to WarehouseStockItem List as reference table
        List<WarehouseStockItem> listStockItemsContainingDuplicates = new ArrayList<>();

        for (WarehouseSupplierItemDTO item : itemListDTO) {
            listStockItemsContainingDuplicates.add(this.covertToEntity(item));
        }

        // 2. Identify duplicates by category and price. So cumulate quantity.
        List<WarehouseStockItem> listStockItemsWithoutDuplicates = new ArrayList<>();
        //Build list without duplicates containing categories and pricePerUnit
        for (WarehouseStockItem outerItem : listStockItemsContainingDuplicates) {
            if (listStockItemsWithoutDuplicates.stream().noneMatch(o -> o.getCategory().equals(outerItem.getCategory()) &&
                    o.getPricePerUnit() == outerItem.getPricePerUnit())) {
                WarehouseStockItem newItem = new WarehouseStockItem();
                newItem.setCategory(outerItem.getCategory());
                newItem.setPricePerUnit(outerItem.getPricePerUnit());
                listStockItemsWithoutDuplicates.add(newItem);
            }
        }

        // 3. Iterrate through stock list with duplicates and cumulate quantities in new list without duplicates
        for (WarehouseStockItem outerItem : listStockItemsContainingDuplicates) {
            listStockItemsWithoutDuplicates.stream().filter(o -> o.getCategory().equals(outerItem.getCategory()) &&
                    o.getPricePerUnit() == outerItem.getPricePerUnit()).forEach(o -> {
                o.setQuantity(o.getQuantity() + outerItem.getQuantity());
            });
        }

        return listStockItemsWithoutDuplicates;
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
