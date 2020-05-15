package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopsCheckoutSoldItemsDTO;
import com.inventoryapp.demo.dtos.ShopsCurrentInventoryDTO;
import com.inventoryapp.demo.dtos.ShopsStockItemDto;
import com.inventoryapp.demo.services.ShopsCurrentInventoryService;
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
        // 1. Define Test Items
        List<ShopsStockItemDto> soldItemsDTOList = new ArrayList<>();

        ShopsStockItemDto itemDTO1 = new ShopsStockItemDto();
        itemDTO1.setPosition(1L);
        itemDTO1.setCategory("category");
        itemDTO1.setQuantity(100L);
        itemDTO1.setPriceListPerUnit(20L);
        itemDTO1.setPriceSalesPerUnit(20L);
        itemDTO1.setShop("TestShop");
        soldItemsDTOList.add(itemDTO1);

        ShopsStockItemDto itemDTO2 = new ShopsStockItemDto();
        itemDTO2.setPosition(2L);
        itemDTO2.setCategory("category");
        itemDTO2.setQuantity(100L);
        itemDTO2.setPriceListPerUnit(20L);
        itemDTO2.setPriceSalesPerUnit(20L);
        itemDTO2.setShop("TestShop");
        soldItemsDTOList.add(itemDTO2);

        // 2. Define Mock-functioning
        Mockito.when(
                this.shopsCurrentInventoryService
                        .getAllItemsAllShops()
        ).thenReturn(soldItemsDTOList);

        // 3. Apply tested method
        List<ShopsStockItemDto> soldItemsDTOListFetched = this.shopsCurrentInventoryService.getAllItemsAllShops();

        // 4. Assert items are correct
        for (int i = 0; i < soldItemsDTOListFetched.size(); i++) {
            String savedItem = soldItemsDTOList.get(i).toString();
            String savedItemFetched = soldItemsDTOListFetched.get(i).toString();

            Assert.assertEquals(savedItem, savedItemFetched);
        }
    }

    @Test
    public void getShopInventoryItemsPositiveTest(){
        // 1. Define Test Items
        List<ShopsStockItemDto> soldItemsDTOList = new ArrayList<>();

        ShopsStockItemDto itemDTO1 = new ShopsStockItemDto();
        itemDTO1.setPosition(1L);
        itemDTO1.setCategory("category");
        itemDTO1.setQuantity(100L);
        itemDTO1.setPriceListPerUnit(20L);
        itemDTO1.setPriceSalesPerUnit(20L);
        itemDTO1.setShop("TestShop");
        soldItemsDTOList.add(itemDTO1);

        ShopsStockItemDto itemDTO2 = new ShopsStockItemDto();
        itemDTO2.setPosition(2L);
        itemDTO2.setCategory("category");
        itemDTO2.setQuantity(100L);
        itemDTO2.setPriceListPerUnit(20L);
        itemDTO2.setPriceSalesPerUnit(20L);
        itemDTO2.setShop("TestShop");
        soldItemsDTOList.add(itemDTO2);

        // 2. Define Mock-functioning
        Mockito.when(
                this.shopsCurrentInventoryService
                        .getAllItemsSpecificShop(Mockito.anyString())
        ).thenReturn(soldItemsDTOList);

        // 3. Apply tested method
        List<ShopsStockItemDto> soldItemsDTOListFetched = this.shopsCurrentInventoryService.getAllItemsSpecificShop(itemDTO1.getShop());

        // 4. Assert items are correct
        for (int i = 0; i < soldItemsDTOListFetched.size(); i++) {
            String savedItem = soldItemsDTOList.get(i).toString();
            String savedItemFetched = soldItemsDTOListFetched.get(i).toString();

            Assert.assertEquals(savedItem, savedItemFetched);
        }
    }

    @Test
    public void getShopInventoryAvailabilityPositiveTest() {
        // 0. Define Data
        ShopsCheckoutSoldItemsDTO itemDTOInput = new ShopsCheckoutSoldItemsDTO();
        itemDTOInput.setPosition(1L);
        itemDTOInput.setCategory("category");
        itemDTOInput.setQuantity(100L);
        itemDTOInput.setPriceListPerUnit(20L);
        itemDTOInput.setPriceSalesPerUnit(20L);
        itemDTOInput.setRevenuePerUnit(20L);
        itemDTOInput.setDiscountPercent(0);
        itemDTOInput.setShop("TestShop");
        itemDTOInput.setDeliverySending(LocalDateTime.now());
        itemDTOInput.setItemLastSold(LocalDateTime.now());
        itemDTOInput.setComment("Test comment");

        // 1. Define Mock of method
        Mockito.when(this.shopsCurrentInventoryService.getShopInventoryAvailability(Mockito.any())).thenReturn(itemDTOInput);

        // 2. Use tested method and respective test
        ShopsCheckoutSoldItemsDTO itemDTOFetched = this.shopsCurrentInventoryService.getShopInventoryAvailability(itemDTOInput);

        // 3. Verify correct functioning of method
        String input = itemDTOInput.toString();
        String output = itemDTOFetched.toString();

        Assert.assertEquals(input, output);
    }

}
