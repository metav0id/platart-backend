package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopAndDateRangeDTO;
import com.inventoryapp.demo.dtos.ManagerWarehouseCheckinListingDTO;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.WarehouseSupplierItemRepository;
import com.inventoryapp.demo.services.ManagerWarehouseCheckinListingService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagerWarehouseCheckinListingControllerTest {

    @Autowired
    private ManagerWarehouseCheckinListingService managerWarehouseCheckinListingService;

    @MockBean
    private WarehouseSupplierItemRepository warehouseSupplierItemRepository;

    public List<WarehouseSupplierItem> allItems = new ArrayList<>();
    @Before
    public void setUp(){
        WarehouseSupplierItem item1 = new WarehouseSupplierItem("Test category 1", 111, 333, 888, "Test Supplier");
        allItems.add(item1);
        WarehouseSupplierItem item2 = new WarehouseSupplierItem("Test category 2", 111, 333, 888, "Test Supplier");
        allItems.add(item2);
        WarehouseSupplierItem item3 = new WarehouseSupplierItem("Test category 3", 111, 333, 888, "Test Supplier");
        allItems.add(item3);
        WarehouseSupplierItem item4 = new WarehouseSupplierItem("Test category 4", 111, 333, 888, "Test Supplier");
        allItems.add(item4);
        WarehouseSupplierItem item5 = new WarehouseSupplierItem("Test category 5", 111, 333, 888, "Test Supplier");
        allItems.add(item5);
        WarehouseSupplierItem item6 = new WarehouseSupplierItem("Test category 6", 111, 333, 888, "Test Supplier");
        allItems.add(item6);
    }

    @Test
    public void getCheckinItemsListByDateRangePositiveTest(){

        // 0. Input Data

        ShopAndDateRangeDTO shopAndDateRangeDTO = new ShopAndDateRangeDTO();
        shopAndDateRangeDTO.setStartDate("2010-06-04T12:55:29.041Z");
        shopAndDateRangeDTO.setEndDate("2020-06-04T12:55:29.041Z");

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().minusYears(1);

        // 0.1. get Date
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            startDate = LocalDateTime.parse(shopAndDateRangeDTO.getStartDate(), formatter);
            endDate = LocalDateTime.parse(shopAndDateRangeDTO.getEndDate() , formatter);
            System.out.println("start: "+ startDate + " = end: "+endDate);
        } catch (Error e){
            System.err.println(e);
        }

        // 1. Define Repository-Mocks
        Mockito.when(warehouseSupplierItemRepository.getItemsByDate(Mockito.any(), Mockito.any())).thenReturn(allItems);

        // 2. Execute Method
        List<ManagerWarehouseCheckinListingDTO> fetchedDTOList = this.managerWarehouseCheckinListingService.getSoldItemsListByDateRange(startDate, endDate);

        // 3. Assert/ Verify correctnes
        for(int i = 0 ; i<fetchedDTOList.size(); i++){
            String current = fetchedDTOList.get(i).getCategory();
            String expected = allItems.get(i).getCategory();

            Assert.assertEquals(expected, current);
        }

        Mockito.verify(warehouseSupplierItemRepository, Mockito.times(1)).getItemsByDate(Mockito.any(), Mockito.any());

    }

}
