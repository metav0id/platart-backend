package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.entities.WarehouseStockItem;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.WarehouseDeliverySupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseDeliverySupplierService {

    @Autowired
    WarehouseDeliverySupplierRepository warehouseDeliverySupplierRepository;

    @Autowired
    WarehouseInStockService warehouseInStockService;

    /**
     * This method persists the list from warehouse of new items from suppliers
     * @param listDTO DTO of new item list
     */
    public void saveListDeliverySuppliers(List<WarehouseSupplierItemDTO> listDTO){
        System.out.println("Saving new item list from supplier to database...");
        List<WarehouseSupplierItem> listEntity = new ArrayList<>();
        for(WarehouseSupplierItemDTO entry : listDTO){
            WarehouseSupplierItem itemEntity = new WarehouseSupplierItem(entry.getCategory(),
                    entry.getQuantity(), entry.getPricePerUnit(),
                    entry.getPrice(), entry.getSupplierName());
            listEntity.add(itemEntity);
        }
        warehouseDeliverySupplierRepository.saveAll(listEntity);
        System.out.println("Successfully saved!");
        this.transferListToStock(listDTO);
    }

    public void transferListToStock(List<WarehouseSupplierItemDTO> listDTO){
        System.out.println("Transferring list to stock inventory...");
        warehouseInStockService.enterNewList(listDTO);
    }
}
