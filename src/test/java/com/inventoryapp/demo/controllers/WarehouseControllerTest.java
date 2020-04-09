package com.inventoryapp.demo.controllers;


import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.WarehouseDeliverySupplierRepository;
import com.inventoryapp.demo.services.WarehouseDeliverySupplierService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class WarehouseControllerTest {
    @Mock
    private WarehouseDeliverySupplierRepository warehouseDeliverySupplierRepository;

    @InjectMocks
    private WarehouseDeliverySupplierService warehouseDeliverySupplierService;

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
        Mockito.when(warehouseDeliverySupplierRepository.saveAll(Mockito.anyList())).thenReturn(warehouseSupplierItem);
        warehouseDeliverySupplierService.saveListDeliverySuppliers(listDTO);
        Mockito.verify(warehouseDeliverySupplierRepository, Mockito.times(1)).saveAll(Mockito.anyList());
    }
}
