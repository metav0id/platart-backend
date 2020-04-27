package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopsCurrentInventoryDTO;
import com.inventoryapp.demo.services.ShopsCurrentInventoryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ShopsCurrentInventoryControllerTest {

    @Mock
    private ShopsCurrentInventoryService shopsCurrentInventoryService;

    @InjectMocks
    private ShopsCurrentInventoryController shopsCurrentInventoryController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllItemsAllShops(){
        // 0.1 Define Item to be persisted
        List<ShopsCurrentInventoryDTO> shopsCurrentInventoryDTOList = new ArrayList<>();

        ShopsCurrentInventoryDTO shopsCurrentDTO1 = new ShopsCurrentInventoryDTO();
        shopsCurrentDTO1.setId(1L);
        shopsCurrentDTO1.setCategory("Category 1");
        shopsCurrentDTO1.setItemInShop("Shop1");
        shopsCurrentDTO1.setPriceSalesPricePerUnit(20);
        shopsCurrentDTO1.setPriceListPricePerUnit(10);
        shopsCurrentDTO1.setDiscountPercent(50);
        shopsCurrentDTO1.setItemInShop("Shop1");
        shopsCurrentDTO1.setItemLastDelivery(LocalDateTime.now().minusDays(7));
        shopsCurrentDTO1.setItemLastSold(LocalDateTime.now().minusDays(1));
        shopsCurrentInventoryDTOList.add(shopsCurrentDTO1);

        ShopsCurrentInventoryDTO shopsCurrentDTO2 = new ShopsCurrentInventoryDTO();
        shopsCurrentDTO2.setId(2L);
        shopsCurrentDTO2.setCategory("Category2");
        shopsCurrentDTO2.setItemInShop("Shop2");
        shopsCurrentDTO2.setPriceSalesPricePerUnit(20);
        shopsCurrentDTO2.setPriceListPricePerUnit(10);
        shopsCurrentDTO2.setDiscountPercent(50);
        shopsCurrentDTO2.setItemInShop("Shop2");
        shopsCurrentDTO2.setItemLastDelivery(LocalDateTime.now().minusDays(7));
        shopsCurrentDTO2.setItemLastSold(LocalDateTime.now().minusDays(1));
        shopsCurrentInventoryDTOList.add(shopsCurrentDTO2);

        // 1. Define Mocks
        Mockito.when(shopsCurrentInventoryService.getAllItemsAllShops()).thenReturn(shopsCurrentInventoryDTOList);
        
        // 2. Use the method
        List<ShopsCurrentInventoryDTO> shopsCurrentInventoryDTOListFetched = shopsCurrentInventoryService.getAllItemsAllShops();
        
        // 3. Assert
        for (int i = 0; i < shopsCurrentInventoryDTOListFetched.size(); i++) {
            Assert.assertEquals(shopsCurrentInventoryDTOListFetched.get(i).getCategory(), shopsCurrentInventoryDTOList.get(i).getCategory());
        }
    }

    @Test
    public void setItemsShopsPositiveTest(){
        // 0.1 Define Item to be persisted
        List<ShopsCurrentInventoryDTO> shopsCurrentInventoryDTOList = new ArrayList<>();

        ShopsCurrentInventoryDTO shopsCurrentDTO1 = new ShopsCurrentInventoryDTO();
        shopsCurrentDTO1.setId(1L);
        shopsCurrentDTO1.setCategory("Category 1");
        shopsCurrentDTO1.setItemInShop("Shop1");
        shopsCurrentDTO1.setPriceSalesPricePerUnit(20);
        shopsCurrentDTO1.setPriceListPricePerUnit(10);
        shopsCurrentDTO1.setDiscountPercent(50);
        shopsCurrentDTO1.setItemInShop("Shop1");
        shopsCurrentDTO1.setItemLastDelivery(LocalDateTime.now().minusDays(7));
        shopsCurrentDTO1.setItemLastSold(LocalDateTime.now().minusDays(1));
        shopsCurrentInventoryDTOList.add(shopsCurrentDTO1);

        ShopsCurrentInventoryDTO shopsCurrentDTO2 = new ShopsCurrentInventoryDTO();
        shopsCurrentDTO2.setId(2L);
        shopsCurrentDTO2.setCategory("Category2");
        shopsCurrentDTO2.setItemInShop("Shop2");
        shopsCurrentDTO2.setPriceSalesPricePerUnit(20);
        shopsCurrentDTO2.setPriceListPricePerUnit(10);
        shopsCurrentDTO2.setDiscountPercent(50);
        shopsCurrentDTO2.setItemInShop("Shop2");
        shopsCurrentDTO2.setItemLastDelivery(LocalDateTime.now().minusDays(7));
        shopsCurrentDTO2.setItemLastSold(LocalDateTime.now().minusDays(1));
        shopsCurrentInventoryDTOList.add(shopsCurrentDTO2);

        // 1. Define Mock
        Mockito.doNothing().when(shopsCurrentInventoryService).setItemsShops(Mockito.anyList());

        // 2. Use method
        shopsCurrentInventoryService.setItemsShops(shopsCurrentInventoryDTOList);

        // 3. Verify
        Mockito.verify(shopsCurrentInventoryService, Mockito.times(1)).setItemsShops(shopsCurrentInventoryDTOList);

    }

}
