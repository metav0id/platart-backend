package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.WarehouseVerifyAmountItemsOnStockDTO;
import com.inventoryapp.demo.entities.WarehouseStockItem;
import com.inventoryapp.demo.repositories.WarehouseRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WarehouseVerifyAmountItemsOnStockServiceTest {

    @Autowired
    private WarehouseRepository warehouseRepository;

    List<WarehouseStockItem> warehouseStockItemsEntities = new ArrayList<>();
    @Before
    public void onInit(){
        WarehouseStockItem warehouseStockItem1 = new WarehouseStockItem();
        warehouseStockItem1.setId(1L);
        warehouseStockItem1.setCategory("Category1");
        warehouseStockItem1.setQuantity(100);
        warehouseStockItem1.setPricePerUnit(10);
        warehouseStockItemsEntities.add(warehouseStockItem1);

        WarehouseStockItem warehouseStockItem2 = new WarehouseStockItem();
        warehouseStockItem2.setId(2L);
        warehouseStockItem2.setCategory("Category2");
        warehouseStockItem2.setQuantity(200);
        warehouseStockItem2.setPricePerUnit(20);
        warehouseStockItemsEntities.add(warehouseStockItem2);

        WarehouseStockItem warehouseStockItem3 = new WarehouseStockItem();
        warehouseStockItem3.setId(3L);
        warehouseStockItem3.setCategory("Category3");
        warehouseStockItem3.setQuantity(300);
        warehouseStockItem3.setPricePerUnit(30);
        warehouseStockItemsEntities.add(warehouseStockItem3);

        WarehouseStockItem warehouseStockItem4 = new WarehouseStockItem();
        warehouseStockItem4.setId(4L);
        warehouseStockItem4.setCategory("Category4");
        warehouseStockItem4.setQuantity(400);
        warehouseStockItem4.setPricePerUnit(40);
        warehouseStockItemsEntities.add(warehouseStockItem4);

        WarehouseStockItem warehouseStockItem5 = new WarehouseStockItem();
        warehouseStockItem5.setId(5L);
        warehouseStockItem5.setCategory("Category4");
        warehouseStockItem5.setQuantity(500);
        warehouseStockItem5.setPricePerUnit(50);
        warehouseStockItemsEntities.add(warehouseStockItem5);
    }

    @Test
    public void verifyAmountItemsOnStockPositiveTest(){
        // 0. Persist data to repository
        warehouseRepository.saveAll(warehouseStockItemsEntities);

        // 1. Define a DTO from a random element of warehouseStockItemsEntities
        Random random = new Random();
        int randomPosition = random.nextInt(warehouseStockItemsEntities.size());
        WarehouseStockItem warehouseStockItemRandom = warehouseStockItemsEntities.get(randomPosition);

        WarehouseVerifyAmountItemsOnStockDTO verifyAmountItemsOnStockDTO = new WarehouseVerifyAmountItemsOnStockDTO();
        verifyAmountItemsOnStockDTO.setCategory(warehouseStockItemRandom.getCategory());
        verifyAmountItemsOnStockDTO.setQuantity(warehouseStockItemRandom.getQuantity());
        verifyAmountItemsOnStockDTO.setPricePerUnit(warehouseStockItemRandom.getPricePerUnit());
        verifyAmountItemsOnStockDTO.setQuantity(warehouseStockItemRandom.getQuantity());

        WarehouseStockItem amountVerificationItem
                = this.warehouseRepository.findItemByCategoryAndPricePerUnit(
                verifyAmountItemsOnStockDTO.getCategory(),
                verifyAmountItemsOnStockDTO.getPricePerUnit()
        );

        // 2. Check if the quantity is correct
        if (amountVerificationItem != null) {
            verifyAmountItemsOnStockDTO.setQuantity(amountVerificationItem.getQuantity());
        } else {
            System.out.println("No elements of category found.");
            verifyAmountItemsOnStockDTO.setQuantity(0);
        }

        Assert.assertEquals(verifyAmountItemsOnStockDTO.getCategory(), warehouseStockItemRandom.getCategory() );
        Assert.assertEquals(verifyAmountItemsOnStockDTO.getQuantity(), warehouseStockItemRandom.getQuantity() );
    }
}
