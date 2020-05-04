package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.entities.WarehouseStockItem;
import com.inventoryapp.demo.repositories.WarehouseRepository;
import com.inventoryapp.demo.services.WarehouseInStockService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WarehouseInStockControllerTest {

    @Autowired
    private WarehouseInStockService warehouseInStockService;

    @MockBean
    private WarehouseRepository repository;

    private List<WarehouseGetAllItemsDTO> warehouseItemsDTOList = new ArrayList<>();

    @Test
    public void getAllItemsTest(){

        // 1. Define Mock
        List<WarehouseStockItem> listEntities = new ArrayList<>();
        listEntities.add(new WarehouseStockItem("Pulsera", 100, 30));
        listEntities.add(new WarehouseStockItem("Arete", 200, 40));
        Mockito.when(repository.findAll()).thenReturn(listEntities);
        List<WarehouseGetAllItemsDTO> listDTO = warehouseInStockService.getAllStockItems();

        Assert.assertEquals(2, listDTO.size());
        Assert.assertEquals("Pulsera", listDTO.get(0).getCategory());

    }
}
