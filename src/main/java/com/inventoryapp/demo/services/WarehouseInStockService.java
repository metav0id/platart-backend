package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.entities.WarehouseStockItem;
import com.inventoryapp.demo.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseInStockService {

    @Autowired
    private WarehouseRepository warehouseRepository;

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
     * This service removes duplicates in DTO list.
     * Then it loads all categories+pricePerUnit items from stock that matches DTO list.
     * All quantities from DTO list will be added stock inventory.
     * Finally the updated stock list will be saved to database.
     */
    public void saveListToInventoryStock(List<WarehouseSupplierItemDTO> itemListDTO) {
        //Remove all duplicates from DTO list and cumulate quantities per category and pricePerUnit
        List<WarehouseStockItem> listStockItemsWithoutDuplicates = removeDuplicates(itemListDTO);

        // Step 1 Get stock for each category and fill list
        System.out.println("Loading stock inventory for categories and pricePerUnits mentioned in supplier list...");
        List<WarehouseStockItem> listStockDatabase = new ArrayList<>();
        for (WarehouseStockItem item : listStockItemsWithoutDuplicates) {
            WarehouseStockItem newItem = warehouseRepository.findItemByCategoryAndPricePerUnit(item.getCategory(), item.getPricePerUnit());
            //If an item with a certain category and pricePerUnit is not available in stock inventory it will be added
            if (newItem != null) {
                listStockDatabase.add(newItem);
            }
        }
        System.out.println("List loaded.");

        //Step 2 cumulate for each category and PricePerUnit new quantity on list.
        // If not existing in stock initialize as new category and pricePerUnit with quantity
        System.out.println("Cumulating quantities in list...");
        for (WarehouseStockItem item : listStockItemsWithoutDuplicates) {
            listStockDatabase.stream().filter(o -> item.getCategory().equals(o.getCategory()) &&
                    item.getPricePerUnit() == o.getPricePerUnit()).forEach(o -> o.setQuantity(o.getQuantity() + item.getQuantity()));
            if (listStockDatabase.stream().noneMatch(o -> item.getCategory().equals(o.getCategory()) &&
                    item.getPricePerUnit() == o.getPricePerUnit())) {
                listStockDatabase.add(item);
            }
        }
        System.out.println("Cumulation finished.");

        //Step 3 persist new stock list to inventory table
        System.out.println("Saving new cumulated stock list to database...");
        for (WarehouseStockItem item : listStockDatabase) {
            int modifiedRow = warehouseRepository.updateStock(item.getCategory(), item.getPricePerUnit(), item.getQuantity());
            if (modifiedRow == 0) {
                warehouseRepository.save(item);
            }
        }
        System.out.println("Saving successful.");
    }

    /**
     * All duplicates will be removed. Quantities of duplicates will be summarized.
     * @param itemListDTO that is containing duplicates
     * @return list of WarehouseStockItemEntities without duplicates
     */
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
    private WarehouseStockItem covertToEntity(WarehouseSupplierItemDTO itemDto) {
        WarehouseStockItem item = new WarehouseStockItem();
        item.setCategory(itemDto.getCategory());
        item.setPricePerUnit(itemDto.getPricePerUnit());
        boolean isNegativeQuantity = itemDto.getQuantity() < 0;
        if (isNegativeQuantity) {
            item.setQuantity(0);
        } else {
            item.setQuantity(itemDto.getQuantity());
        }
        return item;
    }
}
