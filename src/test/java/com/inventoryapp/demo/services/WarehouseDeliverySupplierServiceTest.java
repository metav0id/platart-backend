package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.WarehouseDeliverySupplierRepository;
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
public class WarehouseDeliverySupplierServiceTest {
    @Autowired
    WarehouseDeliverySupplierRepository warehouseDeliverySupplierRepository;

    @Before
    public void setUp(){

    }

    @Test
    public void persistList(){
        WarehouseSupplierItem item1 = new WarehouseSupplierItem("Earring", 50, 30, 1500, "Enrico");
        WarehouseSupplierItem item2 = new WarehouseSupplierItem("Necklace", 10, 200, 2000, "Alonzo");

        List<WarehouseSupplierItem> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);

        warehouseDeliverySupplierRepository.saveAll(list);
        Iterable<WarehouseSupplierItem> listDatabase = warehouseDeliverySupplierRepository.findAll();
        List<WarehouseSupplierItem> listNew = new ArrayList<>();
        for(WarehouseSupplierItem item : listDatabase){
            listNew.add(item);
        }
        Assert.assertEquals(2, listNew.size());
    }
}
