package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ManagerWarehouseCheckinListingDTO;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.WarehouseSupplierItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ManagerWarehouseCheckinListingServiceTest {

    @Autowired
    private WarehouseSupplierItemRepository warehouseSupplierItemRepository;

    List<WarehouseSupplierItem> allItemsInput = new ArrayList<>();
    @Before
    public void setUp(){
        WarehouseSupplierItem item1 = new WarehouseSupplierItem("Test category 1", 111, 333, 888, "Test Supplier");
        allItemsInput.add(item1);
        WarehouseSupplierItem item2 = new WarehouseSupplierItem("Test category 2", 111, 333, 888, "Test Supplier");
        allItemsInput.add(item2);
        WarehouseSupplierItem item3 = new WarehouseSupplierItem("Test category 3", 111, 333, 888, "Test Supplier");
        allItemsInput.add(item3);
        WarehouseSupplierItem item4 = new WarehouseSupplierItem("Test category 4", 111, 333, 888, "Test Supplier");
        allItemsInput.add(item4);
        WarehouseSupplierItem item5 = new WarehouseSupplierItem("Test category 5", 111, 333, 888, "Test Supplier");
        allItemsInput.add(item5);
        WarehouseSupplierItem item6 = new WarehouseSupplierItem("Test category 6", 111, 333, 888, "Test Supplier");
        allItemsInput.add(item6);
    }

    @Test
    public void getSoldItemsListByDateRangePositiveTest() {

        // 1. Step: prepare data
        this.warehouseSupplierItemRepository.saveAll(this.allItemsInput);

        LocalDateTime startDate = LocalDateTime.now().minusYears(10);
        LocalDateTime endDate = LocalDateTime.now();

        // 2. Service Logic
        System.out.println("start: "+ startDate + " = end: "+endDate);

        List<ManagerWarehouseCheckinListingDTO> allDTOItems = new ArrayList<>();

        try {
            List<WarehouseSupplierItem> allItems = this.warehouseSupplierItemRepository.getItemsByDate(startDate, endDate);
            allDTOItems = mapEntitiesToDTOs(allItems);
            System.out.println("Map convert works! ");
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
        }

        // 3. Assert
        for (int i = 0; i < allDTOItems.size() ; i++) {
            String current = allItemsInput.get(i).getCategory();
            String expected = allDTOItems.get(i).getCategory();

            Assert.assertEquals(current, expected);
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
