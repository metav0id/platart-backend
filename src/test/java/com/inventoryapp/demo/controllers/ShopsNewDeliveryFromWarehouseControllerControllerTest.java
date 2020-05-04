package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.entities.ShopsCheckedInProductsFromWarehouse;
import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import com.inventoryapp.demo.repositories.WarehouseShopDeliveryOrdersSendRepository;
import com.inventoryapp.demo.services.ShopsNewDeliveryFromWarehouseService;
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
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShopsNewDeliveryFromWarehouseControllerControllerTest {
    @Autowired
    private ShopsNewDeliveryFromWarehouseService shopsNewDeliveryFromWarehouseService;

    @MockBean
    private WarehouseShopDeliveryOrdersSendRepository warehouseShopDeliveryOrdersSendRepository;

    private List<WarehouseSendDeliveryOrderItem> listSendItems = new ArrayList<>();

    @Before
    public void setUp() {
        WarehouseSendDeliveryOrderItem item1 = new WarehouseSendDeliveryOrderItem();
        item1.setCategory("Pulsera");
        item1.setDeliverySending(LocalDateTime.now());
        item1.setDiscountPercent(10);
        item1.setPriceListPerUnit(100);
        item1.setPriceSalesPerUnit(90);
        item1.setQuantity(80);
        item1.setShop("Shop1");
        item1.setShopsCheckedInProductsFromWarehouse(new ShopsCheckedInProductsFromWarehouse());
        this.listSendItems.add(item1);

        WarehouseSendDeliveryOrderItem item2 = new WarehouseSendDeliveryOrderItem();
        item2.setCategory("Arete");
        item2.setDeliverySending(LocalDateTime.now());
        item2.setDiscountPercent(20);
        item2.setPriceListPerUnit(100);
        item2.setPriceSalesPerUnit(80);
        item2.setQuantity(120);
        item2.setShop("Shop1");
        item2.setShopsCheckedInProductsFromWarehouse(new ShopsCheckedInProductsFromWarehouse());
        this.listSendItems.add(item2);
    }

    @Test
    public void getAllItemsNotInStock() {

        //1. step: prepare data
        Mockito.when(warehouseShopDeliveryOrdersSendRepository.
                findAllItemsNotAddedToShopInventory(Mockito.anyString())).thenReturn(this.listSendItems);

        //2. step: test and check
        Assert.assertEquals(2, shopsNewDeliveryFromWarehouseService.
                getAllItemsNotInShopInventory("Shop1").size());
    }
}
