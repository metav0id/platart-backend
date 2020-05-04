package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseItemCategoryDTO;
import com.inventoryapp.demo.entities.WarehouseItemCategory;
import com.inventoryapp.demo.services.WarehouseItemCategoryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WarehouseItemCategoriesControllerTest {

    //Guide:
    // 1. Mock Service --> Mockito Services Methods
    // 2. InjectMocks Controller

//    @Mock
//    private WarehouseItemCategoryService warehouseItemCategoryService;
//
//    @InjectMocks
//    private WarehouseItemCategoriesController warehouseItemCategoriesController;
//
//    @Before
//    public void setUp(){
//        MockitoAnnotations.initMocks(this);
//    }

    @Autowired
    private WarehouseItemCategoriesController controller;

    @MockBean
    private WarehouseItemCategoryService service;

    @Test
    public void getAllCategoriesPositiveTest() {
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

        Mockito.when(service.getAllCategories()).thenReturn(warehouseItemList);

        Assert.assertEquals(3, controller.getAllCategories().size());

    }

    @Test
    public void getAllCategoriesNegativeTest() {
        // 0. generate empty test data
        List<WarehouseItemCategoryDTO> warehouseItemList = new ArrayList<>();

        // 1. define service-mocking-function
        Mockito.when(service.getAllCategories()).thenReturn(warehouseItemList);

        // 2. Call the method
        Assert.assertEquals(0, controller.getAllCategories().size());
    }

    @Test
    public void saveNewCategoryPositiveTest() {
        // 0. Define Data
        WarehouseItemCategoryDTO warehouseItemDto1 = new WarehouseItemCategoryDTO();
        warehouseItemDto1.setCategory("Category1");

        controller.saveNewCategory(warehouseItemDto1);
        Mockito.verify(service, Mockito.times(1)).
                saveNewCategory(Mockito.any(WarehouseItemCategoryDTO.class));
    }

    @Test
    public void deleteCategoryPositiveTest(){
        // 0. generate some test data
        List<WarehouseItemCategoryDTO> warehouseItemList = new ArrayList<>();

        WarehouseItemCategoryDTO warehouseItemDto1 = new WarehouseItemCategoryDTO();
        warehouseItemDto1.setCategory("Category1");
        warehouseItemList.add(warehouseItemDto1);

        // 2. Use the method
        controller.deleteCategory(warehouseItemDto1);

        // 3. Verify with Mockito
        Mockito.verify(service, Mockito.times(1)).
                deleteCategory(Mockito.any(WarehouseItemCategoryDTO.class));
    }

}
