package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ComerceDTO;
import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.dtos.WarehouseItemCategoryDTO;
import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.entities.Comerce;
import com.inventoryapp.demo.entities.WarehouseStockItem;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.ComerceRepository;
import com.inventoryapp.demo.services.ComerceService;
import org.junit.Assert;
import org.junit.Before;
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

@RunWith(MockitoJUnitRunner.class)
public class ComerceControllerTest {

    @Mock
    private ComerceService comerceService;

    @InjectMocks
    private ComerceController comerceController;

    @Mock
    private ComerceRepository comerceRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getAllComercesTest(){

        // 1. Define Data
        List<ComerceDTO> listDTOS = new ArrayList<>();
        ComerceDTO comerce = new ComerceDTO();
        comerce.setName("testName");
        listDTOS.add( comerce);
        listDTOS.add(new ComerceDTO());

        // 2. Define Mock
        Mockito.when(comerceService.getAllComerces()).thenReturn(listDTOS);

        // 3. Execute
        List<ComerceDTO> listDTOFetched = comerceService.getAllComerces();

        //4. Evaluation
        Assert.assertEquals(2, listDTOFetched.size());
        Assert.assertEquals("testName", listDTOFetched.get(0).getName());
        Mockito.verify(comerceService, Mockito.times(1)).getAllComerces();

    }
    @Test
    public void getAllShopsTest(){

        // 1. Define Data
        List<ComerceDTO> listDTOS = new ArrayList<>();
        ComerceDTO comerce = new ComerceDTO();
        comerce.setName("testName");
        comerce.setCategory("store");
        listDTOS.add(comerce);


        // 2. Define Mock
        Mockito.when(comerceService.getAllShops()).thenReturn(listDTOS);

        // 3. Execute
        List<ComerceDTO> listDTOFetched = comerceService.getAllShops();

        //4. Evaluation
        Assert.assertEquals(1, listDTOFetched.size());
        Assert.assertEquals("testName", listDTOFetched.get(0).getName());
        Mockito.verify(comerceService, Mockito.times(1)).getAllShops();

    }

    @Test
    public void saveComerceTest(){
        ComerceDTO comerceDTO = new ComerceDTO();

        Mockito.doNothing().when(comerceService).createNewComerce(Mockito.any());
        comerceService.createNewComerce(comerceDTO);
        comerceService.createNewComerce(comerceDTO);
        Mockito.verify(comerceService, Mockito.times(2)).createNewComerce(Mockito.any());

    }
}
