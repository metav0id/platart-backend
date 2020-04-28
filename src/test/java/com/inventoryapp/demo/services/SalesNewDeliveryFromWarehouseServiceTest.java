package com.inventoryapp.demo.services;

import com.inventoryapp.demo.entities.SalesCheckedInProductsFromWarehouse;
import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import com.inventoryapp.demo.repositories.WarehouseShopDeliveryOrdersSendRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
//@DataJpaTest
public class SalesNewDeliveryFromWarehouseServiceTest {
    //@Autowired
//    private WarehouseShopDeliveryOrdersSendRepository warehouseShopDeliveryOrdersSendRepository = new Ware;

    @Test
    public void getAllItemsFromSendDelivery(){
        WarehouseSendDeliveryOrderItem item1 = new WarehouseSendDeliveryOrderItem();
        item1.setCategory("Pulsera");
        item1.setDeliverySending(LocalDateTime.now());
        item1.setDiscountPercent(10);
        item1.setPriceListPerUnit(100);
        item1.setPriceSalesPerUnit(90);
        item1.setQuantity(80);
        item1.setShop("Shop1");
        item1.setSalesCheckedInProductsFromWarehouse(new SalesCheckedInProductsFromWarehouse());

    }
}
