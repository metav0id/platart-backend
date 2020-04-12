package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.NewDeliveryOrderItemDTO;
import com.inventoryapp.demo.entities.NewDeliveryOrderItem;
import com.inventoryapp.demo.repositories.NewDeliveryOrderRepository;
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
public class NewDeliveryOrderServiceTest {
    @Autowired
    NewDeliveryOrderRepository newDeliveryOrderRepository;

    List<NewDeliveryOrderItem> deliveryOrderItemsEntities = new ArrayList<>();
    List<NewDeliveryOrderItemDTO> deliveryOrderItemDTOS = new ArrayList<>();
    @Before
    public void setUp(){
        NewDeliveryOrderItem newDeliveryItem1 = new NewDeliveryOrderItem();
        newDeliveryItem1.setId(1L);
        newDeliveryItem1.setCategory("Category 1");
        newDeliveryItem1.setDeliveryQuantity(123);
        newDeliveryItem1.setDeliveryDisplayPricePerUnit(20L);
        newDeliveryItem1.setDeliveryDiscount(25);
        newDeliveryItem1.setDeliveryFinalPricePerUnit(15L);
        deliveryOrderItemsEntities.add(newDeliveryItem1);

        NewDeliveryOrderItem newDeliveryItem2 = new NewDeliveryOrderItem();
        newDeliveryItem2.setId(2L);
        newDeliveryItem2.setCategory("Category 2");
        newDeliveryItem2.setDeliveryQuantity(123);
        newDeliveryItem2.setDeliveryDisplayPricePerUnit(20L);
        newDeliveryItem2.setDeliveryDiscount(25);
        newDeliveryItem2.setDeliveryFinalPricePerUnit(15L);
        deliveryOrderItemsEntities.add(newDeliveryItem2);

        NewDeliveryOrderItemDTO newDeliveryItemDTO1 = new NewDeliveryOrderItemDTO();
        newDeliveryItemDTO1.setId(1L);
        newDeliveryItemDTO1.setCategory("Category 1");
        newDeliveryItemDTO1.setDeliveryQuantity(123);
        newDeliveryItemDTO1.setDeliveryDisplayPricePerUnit(20L);
        newDeliveryItemDTO1.setDeliveryDiscount(25);
        newDeliveryItemDTO1.setDeliveryFinalPricePerUnit(15L);
        deliveryOrderItemDTOS.add(newDeliveryItemDTO1);

        NewDeliveryOrderItemDTO newDeliveryItemDTO2 = new NewDeliveryOrderItemDTO();
        newDeliveryItemDTO2.setId(2L);
        newDeliveryItemDTO2.setCategory("Category 2");
        newDeliveryItemDTO2.setDeliveryQuantity(123);
        newDeliveryItemDTO2.setDeliveryDisplayPricePerUnit(20L);
        newDeliveryItemDTO2.setDeliveryDiscount(25);
        newDeliveryItemDTO2.setDeliveryFinalPricePerUnit(15L);
        deliveryOrderItemDTOS.add(newDeliveryItemDTO2);
    }

    @Test
    public void persistanceListTest(){

        newDeliveryOrderRepository.saveAll(deliveryOrderItemsEntities);
        Iterable<NewDeliveryOrderItem> elementsDatabase = newDeliveryOrderRepository.findAll();
        List<NewDeliveryOrderItem> deliveryOrderItemsEntitiesTest = new ArrayList<>();

        for(NewDeliveryOrderItem item: elementsDatabase){
            deliveryOrderItemsEntitiesTest.add(item);
        }

        Assert.assertEquals(deliveryOrderItemsEntities.size(), deliveryOrderItemsEntitiesTest.size());
    }

    @Test
    public void convertEntitiesToDtosTest(){

        List<NewDeliveryOrderItemDTO> deliveryOrderItemDTOSTest = new ArrayList<>();

        for(NewDeliveryOrderItem item: deliveryOrderItemsEntities){
            NewDeliveryOrderItemDTO newDeliveryOrderItemDTO = new NewDeliveryOrderItemDTO();
            newDeliveryOrderItemDTO.setId(item.getId());
            newDeliveryOrderItemDTO.setCategory(item.getCategory());
            newDeliveryOrderItemDTO.setDeliveryQuantity(item.getDeliveryQuantity());
            newDeliveryOrderItemDTO.setDeliveryDisplayPricePerUnit(item.getDeliveryDisplayPricePerUnit());
            newDeliveryOrderItemDTO.setDeliveryDiscount(item.getDeliveryDiscount());
            newDeliveryOrderItemDTO.setDeliveryFinalPricePerUnit(item.getDeliveryFinalPricePerUnit());

            deliveryOrderItemDTOSTest.add(newDeliveryOrderItemDTO);
        }

        for (int i = 0; i < deliveryOrderItemDTOSTest.size(); i++) {
            Assert.assertEquals(deliveryOrderItemDTOSTest.get(i).getCategory(), deliveryOrderItemDTOS.get(i).getCategory());
        }
    }

    @Test
    public void convertDtosToEntitiesTest(){
        List<NewDeliveryOrderItem> deliveryOrderItemEntityListsTest = new ArrayList<>();
        for(NewDeliveryOrderItemDTO item: deliveryOrderItemDTOS){
            NewDeliveryOrderItem newDeliveryOrderItem = new NewDeliveryOrderItem();
            newDeliveryOrderItem.setId(item.getId());
            newDeliveryOrderItem.setCategory(item.getCategory());
            newDeliveryOrderItem.setDeliveryQuantity(item.getDeliveryQuantity());
            newDeliveryOrderItem.setDeliveryDisplayPricePerUnit(item.getDeliveryDisplayPricePerUnit());
            newDeliveryOrderItem.setDeliveryDiscount(item.getDeliveryDiscount());
            newDeliveryOrderItem.setDeliveryFinalPricePerUnit(item.getDeliveryFinalPricePerUnit());

            deliveryOrderItemEntityListsTest.add(newDeliveryOrderItem);
        }

        for (int i = 0; i < deliveryOrderItemEntityListsTest.size(); i++) {
            Assert.assertEquals(deliveryOrderItemEntityListsTest.get(i).getCategory(), deliveryOrderItemsEntities.get(i).getCategory());
        }

    }

}
