package com.inventoryapp.demo.controllers;


import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.services.WarehouseDeliverySupplierService;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WarehouseDeliverySupplierControllerDeliverySupplierTest {
    @Mock
    private WarehouseDeliverySupplierService warehouseDeliverySupplierService;

    @Mock
    private WarehouseInStockService warehouseInStockService;

    @InjectMocks
    private WarehouseDeliverySupplierController warehouseDeliverySupplierController;

    @InjectMocks
    private WarehouseInStockController warehouseInStockController;

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

        Mockito.doNothing().when(warehouseDeliverySupplierService).saveListDeliverySuppliers(Mockito.anyList());
        this.warehouseDeliverySupplierController.saveListDeliverySuppliers(listDTO);
        this.warehouseDeliverySupplierController.saveListDeliverySuppliers(listDTO);
        Mockito.verify(warehouseDeliverySupplierService, Mockito.times(2)).saveListDeliverySuppliers(Mockito.anyList());
    }

    @Ignore
    @Test
    public void getAllItems(){
        List<WarehouseGetAllItemsDTO> returnList = new ArrayList<>();
        Mockito.when(warehouseInStockService.getAllStockItems()).thenReturn(returnList);
        List<WarehouseGetAllItemsDTO> assertList = this.warehouseInStockController.getAllItems();
        Assert.assertNotNull(assertList);
    }
}
