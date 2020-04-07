package com.inventoryapp.demo.services;


import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.entities.Item;
import com.inventoryapp.demo.repositories.WarehouseRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WarehouseServiceTest {


    @Autowired
   private WarehouseRepository warehouseRepository;

    @Test
    public void  getAllStockItemsTest(){





    }

    @Test
    public void  enterNewItemTest(){

        WarehouseGetAllItemsDTO itemDto = new WarehouseGetAllItemsDTO();
        itemDto.setCategory("Aretes");
        itemDto.setPricePerUnit(6);
        itemDto.setQuantity(2);


        WarehouseService warehouseService= new WarehouseService(warehouseRepository);

        warehouseService.enterNewItem(itemDto);

        Item itemDummy = warehouseRepository.findByCategory("Aretes");
        Assert.assertEquals(itemDummy.getCategory(), itemDto.getCategory());


    }
}
