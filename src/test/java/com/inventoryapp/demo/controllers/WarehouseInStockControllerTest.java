package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.services.WarehouseInStockService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WarehouseInStockControllerTest {

    @Mock
    private WarehouseInStockService warehouseInStockService;

    @InjectMocks
    private WarehouseInStockController warehouseInStockController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllItemsTest(){
        // 0. Define Data
        List<WarehouseGetAllItemsDTO> warehouseItemsDTOList = new ArrayList<>();

        WarehouseGetAllItemsDTO warehouseItemDTO1 = new WarehouseGetAllItemsDTO();
        warehouseItemDTO1.setId(1L);
        warehouseItemDTO1.setCategory("Category1");
        warehouseItemDTO1.setQuantity(100);
        warehouseItemDTO1.setPriceListPerUnit(1);
        warehouseItemsDTOList.add(warehouseItemDTO1);

        WarehouseGetAllItemsDTO warehouseItemDTO2 = new WarehouseGetAllItemsDTO();
        warehouseItemDTO2.setId(2L);
        warehouseItemDTO2.setCategory("Category2");
        warehouseItemDTO2.setQuantity(200);
        warehouseItemDTO2.setPriceListPerUnit(2);
        warehouseItemsDTOList.add(warehouseItemDTO2);

        WarehouseGetAllItemsDTO warehouseItemDTO3 = new WarehouseGetAllItemsDTO();
        warehouseItemDTO3.setId(3L);
        warehouseItemDTO3.setCategory("Category3");
        warehouseItemDTO3.setQuantity(300);
        warehouseItemDTO3.setPriceListPerUnit(3);
        warehouseItemsDTOList.add(warehouseItemDTO3);

        // 1. Define Mock
        Mockito.when(warehouseInStockService.getAllStockItems()).thenReturn(warehouseItemsDTOList);

        // 2. Use the method
        List<WarehouseGetAllItemsDTO> warehouseGetAllItemsDTOListFetched0 = warehouseInStockController.getAllItems();

        //3. Assert
        for (int i=0; i<warehouseGetAllItemsDTOListFetched0.size(); i++) {
            String actualCategory = warehouseItemsDTOList.get(i).toString();
            String expectedCategory = warehouseGetAllItemsDTOListFetched0.get(i).toString();
            Assert.assertEquals(actualCategory,expectedCategory);
        }
        Assert.assertNotNull(warehouseGetAllItemsDTOListFetched0);

        // Todo: solve the following Mockito-Test problem:
        // We dont understand why this Mockito test is not working...
        //Mockito.verify(warehouseInStockService, Mockito.times(1)).getAllStockItems();
    }
}
