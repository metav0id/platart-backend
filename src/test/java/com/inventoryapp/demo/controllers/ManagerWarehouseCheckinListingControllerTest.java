package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.WarehouseSupplierItemRepository;
import com.inventoryapp.demo.services.ManagerWarehouseCheckinListingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagerWarehouseCheckinListingControllerTest {

    @Autowired
    private ManagerWarehouseCheckinListingService managerWarehouseCheckinListingService;

    @MockBean
    private WarehouseSupplierItemRepository warehouseSupplierItemRepository;

    List<WarehouseSupplierItem> warehouseSupplierItemList = new ArrayList<>();
    @Before
    public void setUp(){
        WarehouseSupplierItem item1 = new WarehouseSupplierItem();


    }

    @Test
    public void getCheckinItemsListByDateRangePositiveTest(){

        // 0. Data

        // 1. Define Repository-Mocks

        // 2. Execute Method

        // 3. Assert/ Verify correctnes


    }

}
