package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.WarehouseItemCategoryDTO;
import com.inventoryapp.demo.entities.WarehouseItemCategory;
import com.inventoryapp.demo.repositories.WarehouseItemCategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WarehouseItemCategoryServiceTest {

    @Autowired
    private WarehouseItemCategoryRepository warehouseItemCategoryRepository;

    public List<WarehouseItemCategory> allCategoryEntitiesPositiveTest = new ArrayList<>();
    public List<WarehouseItemCategory> allCategoryEntitiesNegativeTest = new ArrayList<>();

    @Before
    public void setUp(){
        // 0. generate data for positive Test Array
        WarehouseItemCategory warehouseItemCategory1 = new WarehouseItemCategory();
        warehouseItemCategory1.setId(1L);
        warehouseItemCategory1.setCategory("category1");
        allCategoryEntitiesPositiveTest.add(warehouseItemCategory1);

        WarehouseItemCategory warehouseItemCategory2 = new WarehouseItemCategory();
        warehouseItemCategory2.setId(2L);
        warehouseItemCategory2.setCategory("category2");
        allCategoryEntitiesPositiveTest.add(warehouseItemCategory2);

        WarehouseItemCategory warehouseItemCategory3 = new WarehouseItemCategory();
        warehouseItemCategory3.setId(3L);
        warehouseItemCategory3.setCategory("category3");
        allCategoryEntitiesPositiveTest.add(warehouseItemCategory3);

        // 0. generate data for negative Test Array
        WarehouseItemCategory warehouseItemCategory1negative = new WarehouseItemCategory();
        warehouseItemCategory1negative.setId(1L);
        warehouseItemCategory1negative.setCategory("category1negative");
        allCategoryEntitiesNegativeTest.add(warehouseItemCategory1negative);

        WarehouseItemCategory warehouseItemCategory2negative = new WarehouseItemCategory();
        warehouseItemCategory2negative.setId(2L);
        warehouseItemCategory2negative.setCategory("category2negative");
        allCategoryEntitiesNegativeTest.add(warehouseItemCategory2negative);

        WarehouseItemCategory warehouseItemCategory3negative = new WarehouseItemCategory();
        warehouseItemCategory3negative.setId(3L);
        warehouseItemCategory3negative.setCategory("category3negative");
        allCategoryEntitiesNegativeTest.add(warehouseItemCategory3negative);
    }

    @Test
    public void getAllCategoriesPositiveTest(){
        // 1. fill Database with test-data
        warehouseItemCategoryRepository.saveAll(allCategoryEntitiesPositiveTest);

        // 2. fetch the data and convert to DTOs
        List<WarehouseItemCategoryDTO> allCategoryDTOs = new ArrayList<>();

        List<WarehouseItemCategory> allCategoryEntitiesFetched = warehouseItemCategoryRepository.findAll();
        for(WarehouseItemCategory item: allCategoryEntitiesFetched){
            WarehouseItemCategoryDTO warehouseItemDto = new WarehouseItemCategoryDTO();
            warehouseItemDto.setCategory(item.getCategory());
            allCategoryDTOs.add(warehouseItemDto);
        }

        // 3. Assert values are fetched correctly
        Assert.assertEquals(allCategoryEntitiesFetched.size(), 3);
        for (int i = 0; i < allCategoryDTOs.size(); i++) {
            String categoryDto = allCategoryDTOs.get(i).getCategory();
            String categoryEntity = allCategoryEntitiesPositiveTest.get(i).getCategory();

            Assert.assertEquals(categoryDto, categoryEntity);
        }
    }

    @Test
    public void getAllCategoriesNegativeTest() {

        // 1. fill Database with positive test-data
        warehouseItemCategoryRepository.saveAll(allCategoryEntitiesPositiveTest);

        // 2. fetch the data and convert to DTOs
        List<WarehouseItemCategoryDTO> allCategoryDTOs = new ArrayList<>();

        List<WarehouseItemCategory> allCategoryEntitiesFetched = warehouseItemCategoryRepository.findAll();
        for(WarehouseItemCategory item: allCategoryEntitiesFetched){
            WarehouseItemCategoryDTO warehouseItemDto = new WarehouseItemCategoryDTO();
            warehouseItemDto.setCategory(item.getCategory());
            allCategoryDTOs.add(warehouseItemDto);
        }

        // 3. Assert fetched values are not equal to a false array
        Assert.assertEquals(allCategoryEntitiesFetched.size(), 3);
        for (int i = 0; i < allCategoryDTOs.size(); i++) {
            String categoryDto = allCategoryDTOs.get(i).getCategory();
            String categoryEntity = allCategoryEntitiesNegativeTest.get(i).getCategory();

            Assert.assertNotEquals(categoryDto, categoryEntity);
        }
    }

    @Test
    public void saveNewCategoryPositiveTest(){
        // 0. Define a category DTO and respective entity
        WarehouseItemCategoryDTO warehouseItemCategoryDTO = new WarehouseItemCategoryDTO();
        warehouseItemCategoryDTO.setCategory("Category1");

        WarehouseItemCategory warehouseItemCategoryEntity = new WarehouseItemCategory();
        warehouseItemCategoryEntity.setId(1L);
        warehouseItemCategoryEntity.setCategory(warehouseItemCategoryDTO.getCategory());

        // 1. Persist entity to database
        this.warehouseItemCategoryRepository.save(warehouseItemCategoryEntity);

        // 2. fetch entity from database
        List<WarehouseItemCategory> warehouseItemCategoryEntityFetchedList = this.warehouseItemCategoryRepository.findAll();
        WarehouseItemCategory warehouseItemCategoryEntityFetched = warehouseItemCategoryEntityFetchedList.get(0);

        String itemCategoryFetched = warehouseItemCategoryEntityFetched.getCategory();
        String itemCategorySaved = warehouseItemCategoryDTO.getCategory();

        Assert.assertEquals(itemCategoryFetched, itemCategorySaved);
    }

    @Test
    public void saveNewCategoryNegativeTest(){
        // 0.1 Define a category DTO and respective entity
        WarehouseItemCategoryDTO warehouseItemCategoryDTO = new WarehouseItemCategoryDTO();
        warehouseItemCategoryDTO.setCategory("Category1");

        WarehouseItemCategory warehouseItemCategoryEntity = new WarehouseItemCategory();
        warehouseItemCategoryEntity.setId(1L);
        warehouseItemCategoryEntity.setCategory(warehouseItemCategoryDTO.getCategory());

        // 0.2 Define a false category Item
        WarehouseItemCategoryDTO warehouseItemCategoryDTOnegative = new WarehouseItemCategoryDTO();
        warehouseItemCategoryDTO.setCategory("Category1negative");

        // 1. Persist entity to database
        this.warehouseItemCategoryRepository.save(warehouseItemCategoryEntity);

        // 2. fetch entity from database
        List<WarehouseItemCategory> warehouseItemCategoryEntityFetchedList = this.warehouseItemCategoryRepository.findAll();
        WarehouseItemCategory warehouseItemCategoryEntityFetched = warehouseItemCategoryEntityFetchedList.get(0);

        String itemCategoryFetched = warehouseItemCategoryEntityFetched.getCategory();
        String itemCategorySavedNegative = warehouseItemCategoryDTOnegative.getCategory();

        Assert.assertNotEquals(itemCategoryFetched, itemCategorySavedNegative);
    }

    @Test
    public void deleteCategoryPositiveTest(){
        // 1. Persist the allCategoryEntitiesPositiveTest Data into the repository
        warehouseItemCategoryRepository.saveAll(allCategoryEntitiesPositiveTest);

        // 2. Delete the first element from the allCategoryEntitiesPositiveTest-array by the category field
        String categoryOfRemovedFirstItem = allCategoryEntitiesPositiveTest.get(0).getCategory();
        warehouseItemCategoryRepository.deactivateCategory(categoryOfRemovedFirstItem);

        // 3. Fetch the remaining entities and check, if removed item, not in fetched list
        List<WarehouseItemCategory> warehouseItemCategoriesFetched = warehouseItemCategoryRepository.findAll();

        for (WarehouseItemCategory item: warehouseItemCategoriesFetched){
            Assert.assertNotEquals(item.getCategory(), categoryOfRemovedFirstItem);
        }
    }
}
