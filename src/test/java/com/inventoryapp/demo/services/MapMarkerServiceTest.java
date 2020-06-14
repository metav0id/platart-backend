package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.MarkerDTO;
import com.inventoryapp.demo.entities.Comerce;
import com.inventoryapp.demo.entities.MapMarker;
import com.inventoryapp.demo.repositories.ComerceRepository;
import com.inventoryapp.demo.repositories.MapMarkerRapository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MapMarkerServiceTest {

    @Autowired
    private MapMarkerRapository mapMarkerRapository;
    @Autowired
    private ComerceRepository comerceRepository;

    @Test
    public void getAllMarkersTest(){
        MapMarker mapMarker = new MapMarker();
        MapMarker mapMarker1 = new MapMarker();
        mapMarkerRapository.save(mapMarker);
        mapMarkerRapository.save(mapMarker1);
        List<MapMarker> listItems3 =  mapMarkerRapository.findAll();
        System.out.println(listItems3);

        MapMarkersService mapMarkersService = new MapMarkersService(mapMarkerRapository);

        List<MarkerDTO> listItems = mapMarkersService.getAllMarkers();
        System.out.println(listItems.size());

        Assert.assertEquals(listItems.size(), 2);
    }

    @Test
    public void getAllMarkersWithNoCoordsTest(){
        MapMarker mapMarker = new MapMarker();
        mapMarker.setLng("26");
        mapMarker.setLat("96");
        MapMarker mapMarker1 = new MapMarker();
        mapMarkerRapository.save(mapMarker);
        mapMarkerRapository.save(mapMarker1);
        List<MapMarker> listItems3 =  mapMarkerRapository.findAll();
        System.out.println(listItems3);

        MapMarkersService mapMarkersService = new MapMarkersService(mapMarkerRapository);

        List<MarkerDTO> listItems = mapMarkersService.getAllMarkersWithNoCoords();
        System.out.println(listItems.size());

        Assert.assertEquals(listItems.size(), 1);
    }

    @Test
    public void createNewMarkerTest () {
        MarkerDTO markerDTO = new MarkerDTO();
        markerDTO.setName("MarkerTest");

        MapMarkersService mapMarkersService = new MapMarkersService(mapMarkerRapository);
        mapMarkersService.createNewMarker(markerDTO);

        MapMarker mapMarker = mapMarkerRapository.findByName("MarkerTest");
        Assert.assertEquals(mapMarker.getName(), "MarkerTest");
    }

    @Test
    public void deleteMarkerTest () {
        MapMarker mapMarker = new MapMarker();
        mapMarker.setName("Test");
        mapMarkerRapository.save(mapMarker);
        MapMarker mapMarkerRecovered = mapMarkerRapository.findByName("Test");
        Long id = mapMarkerRecovered.getId();
        List<MapMarker> listBefore = mapMarkerRapository.findAll();

        MapMarkersService mapMarkersService = new MapMarkersService(mapMarkerRapository);
        mapMarkersService.deleteMarker(id);

        List<MapMarker> listAfter = mapMarkerRapository.findAll();
        Assert.assertEquals(listAfter.size(), listBefore.size() - 1);
    }
    @Test
    public void deleteCoordsTest(){
        MapMarker mapMarker = new MapMarker();
        mapMarker.setName("Test");
        mapMarker.setLng("45");
        mapMarker.setLat("54615");
        mapMarkerRapository.save(mapMarker);

        MapMarkersService mapMarkersService = new MapMarkersService(mapMarkerRapository);
        MarkerDTO markerDTO = mapMarkersService.covertUnitEntityToDTO(mapMarker);

        mapMarkersService.deleteCoords(markerDTO);

        List<MapMarker> listAfter = mapMarkerRapository.findAll();

        Assert.assertEquals(null, mapMarker.getLng());


    }

    @Test
    public void updateTest (){
        MapMarker mapMarker = new MapMarker();
        mapMarker.setName("test");
        MapMarker mapMarker1 = new MapMarker();
        mapMarker1.setLng("2");
        mapMarker1.setLat("3");
        mapMarkerRapository.save(mapMarker);
        mapMarkerRapository.save(mapMarker1);

        MapMarkersService mapMarkersService = new MapMarkersService(mapMarkerRapository);
        MarkerDTO markerDTO=  mapMarkersService.covertUnitEntityToDTO(mapMarker);
        MarkerDTO markerDTO1=  mapMarkersService.covertUnitEntityToDTO(mapMarker1);

        mapMarkersService.update(markerDTO,markerDTO1);

        MapMarker mapMarker3 = mapMarkerRapository.findByName("test");
        Assert.assertEquals(mapMarker.getLat(),"3");
    }

    @Test
    public void editTest (){
        MapMarker marker = new MapMarker();
        marker.setLng("23");
        marker.setName("TestName");
        mapMarkerRapository.save(marker);
        Comerce comerce = new Comerce();
        comerce.setName("TestName");
        comerceRepository.save(comerce);
        Comerce comerce1 = comerceRepository.findByName("TestName");
        System.out.println("5" + comerce1.getName());

        MarkerDTO markerDTO = new MarkerDTO();
        markerDTO.setName("ChangedName");
        markerDTO.setLng("23");

        MapMarkersService mapMarkersService = new MapMarkersService(mapMarkerRapository, comerceRepository);
        mapMarkersService.edit(markerDTO);

        Assert.assertEquals(markerDTO.getName(),marker.getName());

    }
    @Test
    public void findComerceTest (){
        Comerce comerce = new Comerce();
        comerce.setName("TestName");
        comerceRepository.save(comerce);
        Comerce comerce1 = comerceRepository.findByName("TestName");
        System.out.println(comerce1.getName());
        comerce1.getName();
        System.out.println(comerce.getName());
        String name = "TestName";
        MarkerDTO markerDTO = new MarkerDTO();
        markerDTO.setName("NewName");

        MapMarkersService mapMarkersService = new MapMarkersService(mapMarkerRapository,comerceRepository);
        mapMarkersService.findComerce(markerDTO, name);

        Assert.assertEquals(comerce.getName(),markerDTO.getName());


    }


}
