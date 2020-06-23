package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.WarehouseDeliverySupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseDeliverySupplierService {

    @Autowired
    WarehouseDeliverySupplierRepository warehouseDeliverySupplierRepository;

    @Autowired
    WarehouseInStockService warehouseInStockService;

    @Autowired
    ConvertingValues convertingValues;

    /**
     * This method persists the list from warehouse of new items from suppliers
     * @param listDTO DTO of new item list
     */
    public boolean saveListDeliverySuppliers(List<WarehouseSupplierItemDTO> listDTO){
        try{
            List<WarehouseSupplierItem> listEntity = new ArrayList<>();
            for(WarehouseSupplierItemDTO entry : listDTO){
                WarehouseSupplierItem itemEntity = new WarehouseSupplierItem(entry.getCategory(),
                        entry.getQuantity(),
                        convertingValues.convertDoubleToLongForDTOtoEntity(entry.getPriceSupplierPerUnit()),
                        convertingValues.convertDoubleToLongForDTOtoEntity(entry.getPriceListPerUnit()),
                        entry.getSupplierName());
                listEntity.add(itemEntity);
            }
            warehouseDeliverySupplierRepository.saveAll(listEntity);
            this.transferListToStock(listDTO);
            return true;
        } catch (Exception e){
            System.out.println("saveListDeliverySuppliers: An error occured during saving list in");
            return false;
        }
    }

    public void transferListToStock(List<WarehouseSupplierItemDTO> listDTO){
        warehouseInStockService.saveListToInventoryStock(listDTO);
    }
}
