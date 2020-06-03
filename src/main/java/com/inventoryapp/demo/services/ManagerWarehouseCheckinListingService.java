package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ShopsAllSoldItemsDTO;
import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.ShopsAllSoldItemsRepository;
import com.inventoryapp.demo.repositories.WarehouseSupplierItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerWarehouseCheckinListingService {

    @Autowired
    private WarehouseSupplierItemRepository warehouseSupplierItemRepository;

    public ManagerWarehouseCheckinListingService() {
    }

    public List<WarehouseSupplierItemDTO> getSoldItemsListByDateRange(LocalDateTime startDate, LocalDateTime endDate){
        System.out.println("start: "+ startDate + " = end: "+endDate);

        List<WarehouseSupplierItem> allItems = new ArrayList<>();
        List<WarehouseSupplierItemDTO> allDTOItems = new ArrayList<>();

        try {
            allItems = this.warehouseSupplierItemRepository.getItemsByDate(startDate, endDate);
            allDTOItems = mapEntitiesToDTOs(allItems);
        } catch (Exception e) {
            System.err.println(e);
        }

        return allDTOItems;
    }

    private List<WarehouseSupplierItemDTO> mapEntitiesToDTOs(List<WarehouseSupplierItem> shopsAllSoldItems){
        List<WarehouseSupplierItemDTO> shopsAllSoldItemsDTOList = new ArrayList<>();

        for(WarehouseSupplierItem item: shopsAllSoldItems){
            WarehouseSupplierItemDTO itemDTO =
                    new WarehouseSupplierItemDTO(
                            item.getCategory(),
                            item.getQuantity(),
                            item.getPriceListPerUnit(),
                            item.getPriceSupplierPerUnit(),
                            item.getSupplierName()
                    );

            shopsAllSoldItemsDTOList.add(itemDTO);
        }

        return shopsAllSoldItemsDTOList;
    }

}
