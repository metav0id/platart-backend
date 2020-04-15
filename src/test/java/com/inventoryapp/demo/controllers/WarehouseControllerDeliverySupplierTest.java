package com.inventoryapp.demo.controllers;


import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.WarehouseDeliverySupplierRepository;
import com.inventoryapp.demo.services.WarehouseDeliverySupplierService;
import com.inventoryapp.demo.services.WarehouseInStockService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WarehouseControllerDeliverySupplierTest {
    @Mock
    private WarehouseDeliverySupplierService warehouseDeliverySupplierService;

    @Mock
    private WarehouseInStockService warehouseInStockService;

    @InjectMocks
    private WarehouseController warehouseController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveListDeliverySuppliers(){
        WarehouseSupplierItemDTO itemDTO1 = new WarehouseSupplierItemDTO("Earring", 50, 30, 1500, "Enrico");
        WarehouseSupplierItemDTO itemDTO2 = new WarehouseSupplierItemDTO("Necklace", 10, 200, 2000, "Alonzo");

        List<WarehouseSupplierItemDTO> listDTO = new ArrayList<>();
        listDTO.add(itemDTO1);
        listDTO.add(itemDTO2);

        List<WarehouseSupplierItem> warehouseSupplierItem = new ArrayList<>();
        Mockito.doNothing().when(warehouseDeliverySupplierService).saveListDeliverySuppliers(Mockito.anyList());
        this.warehouseController.saveListDeliverySuppliers(listDTO);
        Mockito.verify(warehouseDeliverySupplierService, Mockito.times(1)).saveListDeliverySuppliers(Mockito.anyList());
    }

    @Test
    public void getAllItems(){
        List<WarehouseGetAllItemsDTO> returnList = new ArrayList<>();
        Mockito.when(warehouseInStockService.getAllStockItems()).thenReturn(returnList);
        List<WarehouseGetAllItemsDTO> assertList = this.warehouseController.getAllItems();
        Assert.assertNotNull(assertList);
    }
}
