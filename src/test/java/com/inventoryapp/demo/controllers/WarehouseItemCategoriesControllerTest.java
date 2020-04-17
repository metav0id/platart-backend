package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseItemCategoryDTO;
import com.inventoryapp.demo.services.WarehouseItemCategoryService;
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
public class WarehouseItemCategoriesControllerTest {

    //Guide:
    // 1. Mock Service --> Mockito Services Methods
    // 2. InjectMocks Controller

    @Mock
    private WarehouseItemCategoryService warehouseItemCategoryService;

    @InjectMocks
    private WarehouseItemCategoriesController warehouseItemCategoriesController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllCategoriesPositiveTest(){
        // 0. generate some test data
        List<WarehouseItemCategoryDTO> warehouseItemList = new ArrayList<>();

        WarehouseItemCategoryDTO warehouseItemDto1 = new WarehouseItemCategoryDTO();
        warehouseItemDto1.setCategory("Category1");
        warehouseItemList.add(warehouseItemDto1);

        WarehouseItemCategoryDTO warehouseItemDto2 = new WarehouseItemCategoryDTO();
        warehouseItemDto2.setCategory("Category2");
        warehouseItemList.add(warehouseItemDto2);

        WarehouseItemCategoryDTO warehouseItemDto3 = new WarehouseItemCategoryDTO();
        warehouseItemDto3.setCategory("Category3");
        warehouseItemList.add(warehouseItemDto3);

        // 1. define service-mocking-function
        Mockito.when(warehouseItemCategoryService.getAllCategories()).thenReturn(warehouseItemList);

        // 2. Call the method
        List<WarehouseItemCategoryDTO> warehouseItemListControllerFetched = this.warehouseItemCategoriesController.getAllCategories();

        // 3. JUnit Assert method
        Assert.assertNotNull(warehouseItemListControllerFetched);
    }

    @Test
    public void getAllCategoriesNegativeTest(){
        // 0. generate empty test data
        List<WarehouseItemCategoryDTO> warehouseItemList = new ArrayList<>();

        // 1. define service-mocking-function
        Mockito.when(warehouseItemCategoryService.getAllCategories()).thenReturn(warehouseItemList);

        // 2. Call the method
        List<WarehouseItemCategoryDTO> warehouseItemListControllerFetched = this.warehouseItemCategoriesController.getAllCategories();

        // 3. JUnit Assert method
        int expectedNumberElements = warehouseItemListControllerFetched.size();
        Assert.assertEquals(0,  expectedNumberElements);
    }

    @Test
    public void saveNewCategoryPositiveTest(){
        // 0. Define Data
        WarehouseItemCategoryDTO warehouseItemDto1 = new WarehouseItemCategoryDTO();
        warehouseItemDto1.setCategory("Category1");

        // 1. Service-function mock
        Mockito.doNothing().when(warehouseItemCategoryService).saveNewCategory(Mockito.any());

        // 2. Use the method
        warehouseItemCategoryService.saveNewCategory(warehouseItemDto1);

        // 3. Verify with Mockito
        Mockito.verify(warehouseItemCategoryService, Mockito.times(1)).saveNewCategory(Mockito.any());
    }

    @Test
    public void deleteCategoryPositiveTest(){
        // 0. generate some test data
        List<WarehouseItemCategoryDTO> warehouseItemList = new ArrayList<>();

        WarehouseItemCategoryDTO warehouseItemDto1 = new WarehouseItemCategoryDTO();
        warehouseItemDto1.setCategory("Category1");
        warehouseItemList.add(warehouseItemDto1);

        WarehouseItemCategoryDTO warehouseItemDto2 = new WarehouseItemCategoryDTO();
        warehouseItemDto2.setCategory("Category2");
        warehouseItemList.add(warehouseItemDto2);

        WarehouseItemCategoryDTO warehouseItemDto3 = new WarehouseItemCategoryDTO();
        warehouseItemDto3.setCategory("Category3");
        warehouseItemList.add(warehouseItemDto3);

        // 1. Define Service Mock
        Mockito.doNothing().when(warehouseItemCategoryService).deleteCategory(Mockito.any());

        // 2. Use the method
        warehouseItemCategoryService.deleteCategory(warehouseItemDto1);

        // 3. Verify with Mockito
        Mockito.verify(warehouseItemCategoryService, Mockito.times(1)).deleteCategory(Mockito.any());
    }

}
