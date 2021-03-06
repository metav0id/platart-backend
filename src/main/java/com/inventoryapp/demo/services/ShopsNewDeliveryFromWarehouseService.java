package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ShopDeliveryItemFromWarehouseDTO;
import com.inventoryapp.demo.dtos.ShopSaveToStockDTO;
import com.inventoryapp.demo.entities.ShopsStockItem;
import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import com.inventoryapp.demo.repositories.ShopsNewDeliveryFromWarehouseRepository;
import com.inventoryapp.demo.repositories.WarehouseShopDeliveryOrdersSendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShopsNewDeliveryFromWarehouseService {
    @Autowired
    private WarehouseShopDeliveryOrdersSendRepository warehouseShopDeliveryOrdersSendRepository;

    @Autowired
    ShopsNewDeliveryFromWarehouseRepository shopsNewDeliveryFromWarehouseRepository;

    @Autowired
    ConvertingValues convertingValues;

    public List<ShopDeliveryItemFromWarehouseDTO> getAllItemsNotInShopInventory(String shop) {
        List<WarehouseSendDeliveryOrderItem> listEntity = warehouseShopDeliveryOrdersSendRepository.
                findAllItemsNotAddedToShopInventory(shop);
        List<ShopDeliveryItemFromWarehouseDTO> listDTO = convertEntityToDTO(listEntity);
        return convertEntityToDTO(listEntity);
    }

    private List<ShopDeliveryItemFromWarehouseDTO> convertEntityToDTO(List<WarehouseSendDeliveryOrderItem> listEntity) {
        List<ShopDeliveryItemFromWarehouseDTO> listDTO = new ArrayList<>();
        listEntity.stream().forEach(item -> {
            ShopDeliveryItemFromWarehouseDTO itemDTO = new ShopDeliveryItemFromWarehouseDTO(
                    item.getId(),
                    item.getCategory(),
                    convertingValues.convertLongToDoubleForEntityToDTO(item.getPriceListPerUnit()),
                    convertingValues.convertLongToDoubleForEntityToDTO(item.getPriceSalesPerUnit()),
                    item.getQuantity(),
                    item.getDeliverySending(),
                    item.getComment());
            listDTO.add(itemDTO);
        });
        return listDTO;
    }

    /**
     * Updates delivery table from warehouse to shop and persists stock in shop inventory table.
     * @param listDTO list
     * @return true
     */

    public boolean saveList(List<ShopSaveToStockDTO> listDTO){
        Boolean successfullyUpdatedInventory = persistToStock(listDTO);
        Boolean successfullyUpdatedDeliveryTable = updateWarehouseSendDeliveryTable(listDTO);
        return successfullyUpdatedDeliveryTable && successfullyUpdatedInventory;
    }

    /**
     * Updates all entries in delivery table in database by id.
     * @param listDTO list
     * @return boolean. True when it worked.
     */
    private boolean updateWarehouseSendDeliveryTable(List<ShopSaveToStockDTO> listDTO){
        System.out.println("Service: Updating entries status, so that they are arrived in stock...");
        for(ShopSaveToStockDTO itemDTO : listDTO){
            Optional<WarehouseSendDeliveryOrderItem> item = warehouseShopDeliveryOrdersSendRepository.
                    findById(itemDTO.getIdentifierOnDeliveryList());
            System.out.println("Checked for item in database...");
            if(item.isPresent()){
                WarehouseSendDeliveryOrderItem itemEntity = item.get();
//                System.out.println(itemEntity);
                itemEntity.getShopsCheckedInProductsFromWarehouse().setIsArrivedAtShop(true);
                itemEntity.getShopsCheckedInProductsFromWarehouse().setIsAddedToStockOfShop(true);
                itemEntity.getShopsCheckedInProductsFromWarehouse().setTimestampIsAddedToStockOfShop(LocalDateTime.now());
                itemEntity.getShopsCheckedInProductsFromWarehouse().setQuantityCheckedIn(itemDTO.getQuantity());
                itemEntity.getShopsCheckedInProductsFromWarehouse().setComment(itemDTO.getComment());
                warehouseShopDeliveryOrdersSendRepository.save(itemEntity);
            }
        }
        System.out.println("Service: Update process finished for each entry in database.");
        return true;
    }


    private boolean persistToStock(List<ShopSaveToStockDTO> listDTO){
        System.out.println("Update shop inventory...");
        List<ShopsStockItem> listEntity = new ArrayList<>();
        for (ShopSaveToStockDTO itemDTO : listDTO){
            listEntity.add(convertDTOtoEntity(itemDTO));
        }

        for (ShopsStockItem itemEntity : listEntity){
            ShopsStockItem itemDatabase = shopsNewDeliveryFromWarehouseRepository.getShopsStockItemsBySelectors(itemEntity.getShop(),
                    itemEntity.getCategory(), itemEntity.getPriceListPerUnit(), itemEntity.getPriceSalesPerUnit());
            if (itemDatabase != null){
                itemDatabase.setQuantity(itemDatabase.getQuantity()+itemEntity.getQuantity());
                shopsNewDeliveryFromWarehouseRepository.save(itemDatabase);
            } else {
                shopsNewDeliveryFromWarehouseRepository.save(itemEntity);
            }
        }
        return true;
    }

    private ShopsStockItem convertDTOtoEntity(ShopSaveToStockDTO itemDto) {
        return new ShopsStockItem(itemDto.getShop(), itemDto.getCategory(),
                itemDto.getQuantity(), convertingValues.convertDoubleToLongForDTOtoEntity(itemDto.getPriceListPerUnit()),
                convertingValues.convertDoubleToLongForDTOtoEntity(itemDto.getPriceSalesPerUnit()));
    }
}
