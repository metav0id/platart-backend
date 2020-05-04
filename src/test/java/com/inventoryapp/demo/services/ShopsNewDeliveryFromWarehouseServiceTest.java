package com.inventoryapp.demo.services;

import com.inventoryapp.demo.entities.ShopsCheckedInProductsFromWarehouse;
import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import com.inventoryapp.demo.repositories.WarehouseShopDeliveryOrdersSendRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ShopsNewDeliveryFromWarehouseServiceTest {
    @Autowired
    private WarehouseShopDeliveryOrdersSendRepository warehouseShopDeliveryOrdersSendRepository;

    private List<WarehouseSendDeliveryOrderItem> listSendItems = new ArrayList<>();

    @Before
    public void setUp(){
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

        WarehouseSendDeliveryOrderItem item3 = new WarehouseSendDeliveryOrderItem();
        item3.setCategory("Sabato");
        item3.setDeliverySending(LocalDateTime.now());
        item3.setDiscountPercent(5);
        item3.setPriceListPerUnit(200);
        item3.setPriceSalesPerUnit(190);
        item3.setQuantity(200);
        item3.setShop("Shop2");
        item3.setShopsCheckedInProductsFromWarehouse(new ShopsCheckedInProductsFromWarehouse());
        this.listSendItems.add(item3);

        WarehouseSendDeliveryOrderItem item4 = new WarehouseSendDeliveryOrderItem();
        item4.setCategory("Arete");
        item4.setDeliverySending(LocalDateTime.now());
        item4.setDiscountPercent(5);
        item4.setPriceListPerUnit(200);
        item4.setPriceSalesPerUnit(190);
        item4.setQuantity(200);
        item4.setShop("Shop2");
        item4.setShopsCheckedInProductsFromWarehouse(new ShopsCheckedInProductsFromWarehouse());
        item4.getShopsCheckedInProductsFromWarehouse().setIsAddedToStockOfShop(true);
        this.listSendItems.add(item4);
    }

    @Test
    public void getAllItemsNotInShopInventory(){

        // 1. step: prepare data
        warehouseShopDeliveryOrdersSendRepository.saveAll(this.listSendItems);
        Assert.assertEquals(4, warehouseShopDeliveryOrdersSendRepository.findAll().size());

        // 2. step: execute, find all items that are not in stock yet for particular Shop
        List<WarehouseSendDeliveryOrderItem> listItemsNotInShopStock1 = warehouseShopDeliveryOrdersSendRepository.
                findAllItemsNotAddedToShopInventory("Shop1");

        List<WarehouseSendDeliveryOrderItem> listItemsNotInShopStock2 = warehouseShopDeliveryOrdersSendRepository.
                findAllItemsNotAddedToShopInventory("Shop2");

        List<WarehouseSendDeliveryOrderItem> listItemsNotInShopStock3 = warehouseShopDeliveryOrdersSendRepository.
                findAllItemsNotAddedToShopInventory("Shop3");

        // 3. step: test
        Assert.assertEquals(2, listItemsNotInShopStock1.size());
        Assert.assertEquals(1, listItemsNotInShopStock2.size());
        Assert.assertEquals(0, listItemsNotInShopStock3.size());
    }
}
