package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ComerceDTO;
import com.inventoryapp.demo.dtos.MarkerDTO;
import com.inventoryapp.demo.repositories.ComerceRepository;
import com.inventoryapp.demo.repositories.MapMarkerRapository;
import com.inventoryapp.demo.services.ComerceService;
import com.inventoryapp.demo.services.MapMarkersService;
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
public class MarkerControllerTest {

        @Mock
        private MapMarkersService mapMarkersService;

        @InjectMocks
        private MapMarkersController mapMarkersController;

        @Mock
        private MapMarkerRapository mapMarkerRapository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getAllMarkersTest(){

        // 1. Define Data
        List<MarkerDTO> listDTOS = new ArrayList<>();
        MarkerDTO comerce = new MarkerDTO();
        comerce.setName("testName");
        listDTOS.add( comerce);
        listDTOS.add(new MarkerDTO());

        // 2. Define Mock
        Mockito.when(mapMarkersService.getAllMarkers()).thenReturn(listDTOS);

        // 3. Execute
        List<MarkerDTO> listDTOFetched = mapMarkersService.getAllMarkers();

        //4. Evaluation
        Assert.assertEquals(2, listDTOFetched.size());
        Assert.assertEquals("testName", listDTOFetched.get(0).getName());
        Mockito.verify(mapMarkersService, Mockito.times(1)).getAllMarkers();

    }
    @Test
    public void getAllMarkersNoCoordsTest(){

        // 1. Define Data
        List<MarkerDTO> listDTOS = new ArrayList<>();
        List<MarkerDTO> listDTOSNoCoords = new ArrayList<>();
        MarkerDTO marker = new MarkerDTO();
        MarkerDTO marker2 = new MarkerDTO();
        marker.setName("testName");
        marker2.setLng("123");
        marker2.setLat("696");
        listDTOS.add( marker);
        listDTOS.add(marker2);
        listDTOSNoCoords.add(marker);

        // 2. Define Mock
        Mockito.when(mapMarkersService.getAllMarkersWithNoCoords()).thenReturn(listDTOSNoCoords);

        // 3. Execute
        List<MarkerDTO> listDTOFetched = mapMarkersService.getAllMarkersWithNoCoords();

        //4. Evaluation
        Assert.assertEquals(1, listDTOFetched.size());
        Assert.assertEquals("testName", listDTOFetched.get(0).getName());
        Mockito.verify(mapMarkersService, Mockito.times(1)).getAllMarkersWithNoCoords();

    }
    @Test
    public void saveMarkerTest(){
        MarkerDTO markerDTO = new MarkerDTO();
        Mockito.doNothing().when(mapMarkersService).createNewMarker(Mockito.any());
        mapMarkersService.createNewMarker(markerDTO);
        Mockito.verify(mapMarkersService, Mockito.times(1)).createNewMarker(Mockito.any());
    }


    @Test
    public void deleteTest(){
        MarkerDTO markerDTO = new MarkerDTO();
        Long id = 56L;
        markerDTO.setId(id);
        Mockito.doNothing().when(mapMarkersService).deleteMarker(Mockito.any());
        mapMarkersService.deleteMarker(id);
        Mockito.verify(mapMarkersService, Mockito.times(1)).deleteMarker(Mockito.any());


    }

    @Test
    public void deleteCoordsTest(){
        MarkerDTO markerDTO = new MarkerDTO();
        markerDTO.setLat("464");
        markerDTO.setLng("646");
        Long id = 56L;
        markerDTO.setId(id);
        Mockito.doNothing().when(mapMarkersService).deleteCoords(Mockito.any());
        mapMarkersService.deleteCoords(markerDTO);
        Mockito.verify(mapMarkersService, Mockito.times(1)).deleteCoords(Mockito.any());


    }

    @Test
    public void updateTest(){

        MarkerDTO markerDTO = new MarkerDTO();
       markerDTO.setName("test");
        MarkerDTO markerDTO1 = new MarkerDTO();
        markerDTO1.setName("test");
        markerDTO1.setLat("464");
        markerDTO1.setLng("646");

        Mockito.doNothing().when(mapMarkersService).update(Mockito.any(), Mockito.any());
        mapMarkersService.update(markerDTO, markerDTO1);
        Mockito.verify(mapMarkersService, Mockito.times(1)).update(Mockito.any(), Mockito.any());
    }

    @Test
    public void editMarkerTest(){
        MarkerDTO markerDTO = new MarkerDTO();
        markerDTO.setLat("464");
        markerDTO.setLng("646");
        Mockito.doNothing().when(mapMarkersService).edit(Mockito.any());
        mapMarkersService.edit(markerDTO);
        Mockito.verify(mapMarkersService, Mockito.times(1)).edit(Mockito.any());
    }
}
