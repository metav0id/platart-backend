package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.MarkerDTO;
import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.entities.MapMarker;
import com.inventoryapp.demo.entities.WarehouseStockItem;
import com.inventoryapp.demo.repositories.MapMarkerRapository;
import com.inventoryapp.demo.repositories.WarehouseRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MapMarkerServiceTest {

    @Autowired
    private MapMarkerRapository mapMarkerRapository;

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


}
