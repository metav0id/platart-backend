package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopAndDateRangeDTO;
import com.inventoryapp.demo.dtos.ShopsAllSoldItemsDTO;
import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.repositories.ShopsAllSoldItemsRepository;
import com.inventoryapp.demo.services.ShopsSalesListingService;
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
public class ShopsSalesListingControllerTest {

    @Autowired
    private ShopsSalesListingService managerSalesListingService;

    @MockBean
    private ShopsAllSoldItemsRepository shopsAllSoldItemsRepository;


    public List<ShopsAllSoldItems> allItems = new ArrayList<>();
    @Before
    public void setUp() {
        ShopsAllSoldItems item1 = new ShopsAllSoldItems( 1L, "category", 88L, 888L, 666L , 888L, 50, "shop", LocalDateTime.now(), LocalDateTime.now(), "comment 1");
        allItems.add(item1);
        ShopsAllSoldItems item2 = new ShopsAllSoldItems( 2L, "category", 88L, 888L, 666L , 888L, 50, "shop", LocalDateTime.now(), LocalDateTime.now(), "comment 2");
        allItems.add(item2);
        ShopsAllSoldItems item3 = new ShopsAllSoldItems( 3L, "category", 88L, 888L, 666L , 888L, 50, "shop", LocalDateTime.now(), LocalDateTime.now(), "comment 3");
        allItems.add(item3);
        ShopsAllSoldItems item4 = new ShopsAllSoldItems( 4L, "category", 88L, 888L, 666L , 888L, 50, "shop", LocalDateTime.now(), LocalDateTime.now(), "comment 4");
        allItems.add(item4);
        ShopsAllSoldItems item5 = new ShopsAllSoldItems( 5L, "category", 88L, 888L, 666L , 888L, 50, "shop", LocalDateTime.now(), LocalDateTime.now(), "comment 5");
        allItems.add(item5);
        ShopsAllSoldItems item6 = new ShopsAllSoldItems( 6L, "category", 88L, 888L, 666L , 888L, 50, "shop", LocalDateTime.now(), LocalDateTime.now(), "comment 6");
        allItems.add(item6);
    }

    @Test
    public void getSoldItemsListByDateRangePositiveTest() {

        // 0. Input Data

        ShopAndDateRangeDTO shopAndDateRangeDTO = new ShopAndDateRangeDTO();
        shopAndDateRangeDTO.setStartDate("2010-06-04T12:55:29.041Z");
        shopAndDateRangeDTO.setEndDate("2020-06-04T12:55:29.041Z");

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().minusYears(1);
        String shop = "shop";

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
        Mockito.when(shopsAllSoldItemsRepository.getItemsByShopAndByDate(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(allItems);

        // 2. Execute Method
        List<ShopsAllSoldItemsDTO> fetchedDTOList = this.managerSalesListingService.getSoldItemsListByShopAndDateRange(shop, startDate, endDate);

        // 3. Assert/ Verify correctnes
        for(int i = 0 ; i<fetchedDTOList.size(); i++){
            String current = fetchedDTOList.get(i).getCategory();
            String expected = allItems.get(i).getCategory();

            Assert.assertEquals(expected, current);
        }

        Mockito.verify(shopsAllSoldItemsRepository, Mockito.times(1)).getItemsByShopAndByDate(Mockito.any(), Mockito.any(), Mockito.any());


    }

}
