package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ComerceDTO;
import com.inventoryapp.demo.dtos.MarkerDTO;
import com.inventoryapp.demo.entities.Comerce;
import com.inventoryapp.demo.entities.MapMarker;
import com.inventoryapp.demo.repositories.ComerceRepository;
import com.inventoryapp.demo.repositories.MapMarkerRapository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ComerceServiceTest {
    @Autowired
    private ComerceRepository comerceRepository;

    @Test
    public void getAllComercesTest(){
        Comerce comerce = new Comerce();
        Comerce comerce1 = new Comerce();
        comerceRepository.save(comerce);
        comerceRepository.save(comerce1);

        List<Comerce> listItems3 =  comerceRepository.findAll();
        System.out.println(listItems3);
        ComerceService comerceService = new ComerceService(comerceRepository);

        List<ComerceDTO> listItems = comerceService.getAllComerces();
        System.out.println(listItems.size());

        Assert.assertEquals(listItems.size(), 2);

    }

    @Test
    public void getAllShopsTest(){
        Comerce comerce = new Comerce();
        comerce.setCategory("store");
        Comerce comerce1 = new Comerce();
        comerceRepository.save(comerce);
        comerceRepository.save(comerce1);

        List<Comerce> listItems3 =  comerceRepository.findAll();
        System.out.println(listItems3);
        ComerceService comerceService = new ComerceService(comerceRepository);

        List<ComerceDTO> listItems = comerceService.getAllShops();
        System.out.println(listItems.size());

        Assert.assertEquals(listItems.size(), 1);

    }

    @Test
    public void createNewComerceTest(){
        ComerceDTO comerceDTO = new ComerceDTO();
        comerceDTO.setName("ComerceTest");

        ComerceService comerceService = new ComerceService(comerceRepository);
        comerceService.createNewComerceTest(comerceDTO);

        Comerce comerce= comerceRepository.findByName("ComerceTest");

        Assert.assertEquals(comerce.getName(), "ComerceTest");

    }

    @Test
    public void deleteComerceTest(){
        Comerce comerce = new Comerce();
        comerce.setName("Test");
        comerceRepository.save(comerce);
      Comerce recoveredComerce = comerceRepository.findByName("Test");
        Long id = recoveredComerce.getId();

        List<Comerce> listBefore = comerceRepository.findAll();

        ComerceService comerceService = new ComerceService(comerceRepository);
        comerceService.deleteComerce(id);


        List<Comerce> listAfter = comerceRepository.findAll();
        Assert.assertEquals(listAfter.size(), listBefore.size() - 1);

    }
}
