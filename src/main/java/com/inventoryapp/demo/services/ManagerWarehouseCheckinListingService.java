package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ManagerWarehouseCheckinListingDTO;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
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

    public List<ManagerWarehouseCheckinListingDTO> getSoldItemsListByDateRange(LocalDateTime startDate, LocalDateTime endDate){
        System.out.println("start: "+ startDate + " = end: "+endDate);

        List<ManagerWarehouseCheckinListingDTO> allDTOItems = new ArrayList<>();

        try {
            List<WarehouseSupplierItem> allItems = this.warehouseSupplierItemRepository.getItemsByDate(startDate, endDate);
            allDTOItems = mapEntitiesToDTOs(allItems);
            System.out.println("Map convert works! ");
            return allDTOItems;
        } catch (Exception e) {
            System.err.println("Error in catch: " + e);
            ManagerWarehouseCheckinListingDTO emptyDTO =
                    new ManagerWarehouseCheckinListingDTO(
                            "category",
                            500L,
                            500L,
                            500L,
                            "SupplierName",
                            LocalDateTime.now()
                    );
            allDTOItems.add(emptyDTO);
            return allDTOItems;
        }
    }

    private List<ManagerWarehouseCheckinListingDTO> mapEntitiesToDTOs(List<WarehouseSupplierItem> shopsAllSoldItems){
        List<ManagerWarehouseCheckinListingDTO> shopsAllSoldItemsDTOList = new ArrayList<>();

        for(WarehouseSupplierItem item: shopsAllSoldItems){
            ManagerWarehouseCheckinListingDTO itemDTO =
                    new ManagerWarehouseCheckinListingDTO(
                            item.getCategory(),
                            item.getQuantity(),
                            item.getPriceListPerUnit(),
                            item.getPriceSupplierPerUnit(),
                            item.getSupplierName(),
                            item.getCreateDateTime()
                    );

            shopsAllSoldItemsDTOList.add(itemDTO);
        }

        return shopsAllSoldItemsDTOList;
    }

}
