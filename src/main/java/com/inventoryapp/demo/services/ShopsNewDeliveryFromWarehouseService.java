package com.inventoryapp.demo.services;

import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import com.inventoryapp.demo.repositories.WarehouseNewDeliveryOrderRepository;
import com.inventoryapp.demo.repositories.WarehouseShopDeliveryOrdersSendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopsNewDeliveryFromWarehouseService {
    @Autowired
    private WarehouseShopDeliveryOrdersSendRepository warehouseShopDeliveryOrdersSendRepository;

    public List<WarehouseSendDeliveryOrderItem> getAllItemsNotInShopInventory(String shop){
        return warehouseShopDeliveryOrdersSendRepository.
                findAllItemsNotAddedToShopInventory(shop);
    }
}
