package com.inventoryapp.demo.controllers;


import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.WarehouseDeliverySupplierRepository;
import com.inventoryapp.demo.services.ConvertingValues;
import com.inventoryapp.demo.services.WarehouseDeliverySupplierService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WarehouseDeliverySupplierControllerDeliverySupplierTest {

    @Autowired
    private WarehouseDeliverySupplierService service;

    @MockBean
    private WarehouseDeliverySupplierRepository repository;

    @Autowired
    private ConvertingValues convertingValues;

    @Test
    public void saveListDeliverySuppliersTest() {
        WarehouseSupplierItemDTO itemDTO1 =
                new WarehouseSupplierItemDTO("Earring", 50, 30.0,
                        1500.0, "Enrico");
        WarehouseSupplierItemDTO itemDTO2 =
                new WarehouseSupplierItemDTO("Necklace", 10, 200.0,
                        2000.0, "Alonzo");

        List<WarehouseSupplierItemDTO> listDTO = new ArrayList<>();
        listDTO.add(itemDTO1);
        listDTO.add(itemDTO2);

        service.saveListDeliverySuppliers(listDTO);
        List<WarehouseSupplierItem> listEntity = new ArrayList<>();
        Mockito.verify(repository, Mockito.times(1)).saveAll(Mockito.anyList());
    }
}
