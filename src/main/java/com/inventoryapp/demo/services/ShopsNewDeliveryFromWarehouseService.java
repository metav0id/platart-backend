package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ShopDeliveryItemFromWarehouseDTO;
import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import com.inventoryapp.demo.repositories.WarehouseNewDeliveryOrderRepository;
import com.inventoryapp.demo.repositories.WarehouseShopDeliveryOrdersSendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopsNewDeliveryFromWarehouseService {
    @Autowired
    private WarehouseShopDeliveryOrdersSendRepository warehouseShopDeliveryOrdersSendRepository;

    public List<ShopDeliveryItemFromWarehouseDTO> getAllItemsNotInShopInventory(String shop) {
        List<WarehouseSendDeliveryOrderItem> listEntity = warehouseShopDeliveryOrdersSendRepository.
                findAllItemsNotAddedToShopInventory(shop);
        List<ShopDeliveryItemFromWarehouseDTO> listDTO = convertEntityToDTO(listEntity);
        System.out.println("Shop value: + " + shop + " and list items " + listDTO);
        return convertEntityToDTO(listEntity);
    }

    private List<ShopDeliveryItemFromWarehouseDTO> convertEntityToDTO(List<WarehouseSendDeliveryOrderItem> listEntity) {
        List<ShopDeliveryItemFromWarehouseDTO> listDTO = new ArrayList<>();
        listEntity.stream().forEach(item -> {
            ShopDeliveryItemFromWarehouseDTO itemDTO = new ShopDeliveryItemFromWarehouseDTO(
                    item.getId(), item.getCategory(), item.getPriceListPerUnit(),
                    item.getPriceSalesPerUnit(), item.getQuantity(),
                    item.getDeliverySending(), "");
            listDTO.add(itemDTO);
        });
        return listDTO;
    }
}
