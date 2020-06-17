package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.WarehouseItemPersistanceErrorDTO;
import com.inventoryapp.demo.dtos.WarehouseNewDeliveryOrderItemDTO;
import com.inventoryapp.demo.dtos.WarehouseNewDeliveryPersistanceResponseDTO;
import com.inventoryapp.demo.dtos.WarehouseVerifyAmountItemsOnStockDTO;
import com.inventoryapp.demo.services.WarehouseNewDeliveryOrderService;
import com.inventoryapp.demo.services.WarehouseVerifyAmountItemsOnStockService;
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
public class WarehouseNewDeliveryOrderControllerTest {

    @Mock
    private WarehouseNewDeliveryOrderService warehouseNewDeliveryOrderService;

    @InjectMocks
    private WarehouseNewDeliveryOrderController warehouseNewDeliveryOrderController;

    @Mock
    private WarehouseVerifyAmountItemsOnStockService warehouseVerifyAmountItemsOnStockService;

    List<WarehouseNewDeliveryOrderItemDTO> warehouseDeliveryOrderDTOList = new ArrayList<>();
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        // 0.1 Initailize Test Data for positive Test
        WarehouseNewDeliveryOrderItemDTO warehouseDeliveryOrderDTO1positiveTest =  new WarehouseNewDeliveryOrderItemDTO();
        warehouseDeliveryOrderDTO1positiveTest.setId(1L);
        warehouseDeliveryOrderDTO1positiveTest.setCategory("Category1");
        warehouseDeliveryOrderDTO1positiveTest.setDeliveryShop("Shop1");
        warehouseDeliveryOrderDTO1positiveTest.setQuantity(10);
        warehouseDeliveryOrderDTO1positiveTest.setPriceSalesPerUnit(100.0);
        warehouseDeliveryOrderDTO1positiveTest.setDiscountPercent(50.0);
        warehouseDeliveryOrderDTO1positiveTest.setPriceListPerUnit(50.0);
        warehouseDeliveryOrderDTOList.add(warehouseDeliveryOrderDTO1positiveTest);

        WarehouseNewDeliveryOrderItemDTO warehouseDeliveryOrderDTO2 =  new WarehouseNewDeliveryOrderItemDTO();
        warehouseDeliveryOrderDTO2.setId(2L);
        warehouseDeliveryOrderDTO2.setCategory("Category2");
        warehouseDeliveryOrderDTO2.setDeliveryShop("Shop2");
        warehouseDeliveryOrderDTO2.setQuantity(20);
        warehouseDeliveryOrderDTO2.setPriceSalesPerUnit(200.0);
        warehouseDeliveryOrderDTO2.setDiscountPercent(50.0);
        warehouseDeliveryOrderDTO2.setPriceListPerUnit(100.0);
        warehouseDeliveryOrderDTOList.add(warehouseDeliveryOrderDTO2);

        WarehouseNewDeliveryOrderItemDTO warehouseDeliveryOrderDTO3 =  new WarehouseNewDeliveryOrderItemDTO();
        warehouseDeliveryOrderDTO3.setId(3L);
        warehouseDeliveryOrderDTO3.setCategory("Category3");
        warehouseDeliveryOrderDTO3.setDeliveryShop("Shop3");
        warehouseDeliveryOrderDTO3.setQuantity(30);
        warehouseDeliveryOrderDTO3.setPriceSalesPerUnit(300.0);
        warehouseDeliveryOrderDTO3.setDiscountPercent(50.0);
        warehouseDeliveryOrderDTO3.setPriceListPerUnit(150.0);
        warehouseDeliveryOrderDTOList.add(warehouseDeliveryOrderDTO3);
    }

    @Test
    public void getAllNewOrderItemsPositiveTest(){
        // 1. Define Service-Mock
        Mockito.when(warehouseNewDeliveryOrderService.getAllDeliveryOrderItems()).
                thenReturn(warehouseDeliveryOrderDTOList);

        // 2. Call method in Controlle
        List<WarehouseNewDeliveryOrderItemDTO> warehouseDeliveryOrderDTOListFetched1 =
                this.warehouseNewDeliveryOrderController.getAllNewOrderItems();
        List<WarehouseNewDeliveryOrderItemDTO> warehouseDeliveryOrderDTOListFetched2 =
                this.warehouseNewDeliveryOrderController.getAllNewOrderItems();
        List<WarehouseNewDeliveryOrderItemDTO> warehouseDeliveryOrderDTOListFetched3 =
                this.warehouseNewDeliveryOrderController.getAllNewOrderItems();

        // 3. Assert Correctness of methods
        Mockito.verify(warehouseNewDeliveryOrderService, Mockito.times(3)).
                getAllDeliveryOrderItems();

        for (int i = 0; i < warehouseDeliveryOrderDTOListFetched1.size() ; i++) {
            String fetchedCategory = warehouseDeliveryOrderDTOListFetched1.get(i).getCategory();
            String expectedCategory = warehouseDeliveryOrderDTOList.get(i).getCategory();
            Assert.assertEquals(expectedCategory, fetchedCategory);

            String fetchedItem = warehouseDeliveryOrderDTOListFetched1.get(i).toString();
            String expectedItem = warehouseDeliveryOrderDTOList.get(i).toString();
            Assert.assertEquals(expectedItem, fetchedItem);
        }
    }

    @Test
    public void getAllNewOrderItemsNegativeTest(){
        // 0. Define Dto for negative-test Assertion
        WarehouseNewDeliveryOrderItemDTO negativeTestDto = new WarehouseNewDeliveryOrderItemDTO();
        negativeTestDto.setId(2020L);
        negativeTestDto.setCategory("CategoryNegativeTest");
        negativeTestDto.setDeliveryShop("ShopNegativeTest");
        negativeTestDto.setQuantity(2020);
        negativeTestDto.setPriceSalesPerUnit(2020.0);
        negativeTestDto.setDiscountPercent(20.0);
        negativeTestDto.setPriceListPerUnit(1616.0);

        // 1. Define Service-Mock
        Mockito.when(warehouseNewDeliveryOrderService.getAllDeliveryOrderItems()).thenReturn(warehouseDeliveryOrderDTOList);

        // 2. Call method in Controlle
        List<WarehouseNewDeliveryOrderItemDTO> warehouseDeliveryOrderDTOListFetched1 = this.warehouseNewDeliveryOrderController.getAllNewOrderItems();
        List<WarehouseNewDeliveryOrderItemDTO> warehouseDeliveryOrderDTOListFetched2 = this.warehouseNewDeliveryOrderController.getAllNewOrderItems();
        List<WarehouseNewDeliveryOrderItemDTO> warehouseDeliveryOrderDTOListFetched3 = this.warehouseNewDeliveryOrderController.getAllNewOrderItems();

        // 3. Assert Correctness of methods
        Mockito.verify(warehouseNewDeliveryOrderService, Mockito.times(3)).getAllDeliveryOrderItems();

        for (int i = 0; i < warehouseDeliveryOrderDTOListFetched1.size() ; i++) {
            String fetchedCategory = warehouseDeliveryOrderDTOListFetched1.get(i).toString();
            String notExpectedCategory = negativeTestDto.toString();
            Assert.assertNotEquals(notExpectedCategory, fetchedCategory);
        }
    }

    @Test
    public void setAllOrderItemsPositiveTest(){
        // 1. Define Mocking function
        Mockito.doNothing().when(warehouseNewDeliveryOrderService).setAllDeliveryOrderItems(Mockito.anyList());

        // 2. Test the Controller-Method
        warehouseNewDeliveryOrderService.setAllDeliveryOrderItems(this.warehouseDeliveryOrderDTOList);
        warehouseNewDeliveryOrderService.setAllDeliveryOrderItems(this.warehouseDeliveryOrderDTOList);
        warehouseNewDeliveryOrderService.setAllDeliveryOrderItems(this.warehouseDeliveryOrderDTOList);

        // 3. Verify with Mockito
        Mockito.verify(warehouseNewDeliveryOrderService, Mockito.times(3)).setAllDeliveryOrderItems(Mockito.anyList());
    }

    @Test
    public void verifyAmountItemsOnStockPositiveTest(){
        // 0. Define DTO instance
        WarehouseVerifyAmountItemsOnStockDTO verifyAmountOnStockDTOInput =  new WarehouseVerifyAmountItemsOnStockDTO();
        verifyAmountOnStockDTOInput.setCategory("Category1");
        verifyAmountOnStockDTOInput.setQuantity(10);
        verifyAmountOnStockDTOInput.setPriceListPerUnit(100L);

        // 1. Define Service-Method-Mock
        Mockito.when(warehouseVerifyAmountItemsOnStockService.verifyAmountItemsOnStock(Mockito.any(WarehouseVerifyAmountItemsOnStockDTO.class))).thenReturn(verifyAmountOnStockDTOInput);

        // 2. Test the Controller method
        WarehouseVerifyAmountItemsOnStockDTO verifyAmountOnStockDTOOutput1 = warehouseNewDeliveryOrderController.verifyAmountItemsOnStock(verifyAmountOnStockDTOInput);
        WarehouseVerifyAmountItemsOnStockDTO verifyAmountOnStockDTOOutput2 = warehouseNewDeliveryOrderController.verifyAmountItemsOnStock(verifyAmountOnStockDTOInput);
        WarehouseVerifyAmountItemsOnStockDTO verifyAmountOnStockDTOOutput3 = warehouseNewDeliveryOrderController.verifyAmountItemsOnStock(verifyAmountOnStockDTOInput);

        // 3. Assert the controller
        Mockito.verify(warehouseVerifyAmountItemsOnStockService, Mockito.times(3)).verifyAmountItemsOnStock(verifyAmountOnStockDTOInput);

        String expectedDto = verifyAmountOnStockDTOInput.toString();
        String fetcheddDto = verifyAmountOnStockDTOOutput1.toString();
        Assert.assertEquals(expectedDto, fetcheddDto);
    }

    @Test
    public void verifyAmountItemsOnStockNegativeTest(){
        // 0.1 Define positive input DTO instance
        WarehouseVerifyAmountItemsOnStockDTO verifyAmountOnStockDTOInput =  new WarehouseVerifyAmountItemsOnStockDTO();
        verifyAmountOnStockDTOInput.setCategory("Category1");
        verifyAmountOnStockDTOInput.setQuantity(10);
        verifyAmountOnStockDTOInput.setPriceListPerUnit(100L);

        // 0.2 Define negative output DTO instance
        WarehouseVerifyAmountItemsOnStockDTO verifyAmountOnStockDTONegativeInput =  new WarehouseVerifyAmountItemsOnStockDTO();
        verifyAmountOnStockDTONegativeInput.setCategory("CategoryNegativeValue");
        verifyAmountOnStockDTONegativeInput.setQuantity(2020);
        verifyAmountOnStockDTONegativeInput.setPriceListPerUnit(2020L);

        // 1. Define Service-Method-Mock
        Mockito.when(warehouseVerifyAmountItemsOnStockService.verifyAmountItemsOnStock(Mockito.any(WarehouseVerifyAmountItemsOnStockDTO.class))).thenReturn(verifyAmountOnStockDTOInput);

        // 2. Test the Controller method
        WarehouseVerifyAmountItemsOnStockDTO verifyAmountOnStockDTOOutput1 = warehouseNewDeliveryOrderController.verifyAmountItemsOnStock(verifyAmountOnStockDTOInput);
        WarehouseVerifyAmountItemsOnStockDTO verifyAmountOnStockDTOOutput2 = warehouseNewDeliveryOrderController.verifyAmountItemsOnStock(verifyAmountOnStockDTOInput);
        WarehouseVerifyAmountItemsOnStockDTO verifyAmountOnStockDTOOutput3 = warehouseNewDeliveryOrderController.verifyAmountItemsOnStock(verifyAmountOnStockDTOInput);

        // 3. Assert the controller
        Mockito.verify(warehouseVerifyAmountItemsOnStockService, Mockito.times(3)).verifyAmountItemsOnStock(verifyAmountOnStockDTOInput);

        String expectedFalseDto = verifyAmountOnStockDTONegativeInput.toString();
        String fetcheddDto = verifyAmountOnStockDTOOutput1.toString();
        Assert.assertNotEquals(expectedFalseDto, fetcheddDto);
    }

    @Test
    public void sendDeliveryOrderPositiveTest(){

        // 0. Define Data Dto
        WarehouseNewDeliveryPersistanceResponseDTO responseDTO =  new WarehouseNewDeliveryPersistanceResponseDTO();
        responseDTO.setPersistanceInitialized(true);
        responseDTO.setPersistanceSuccessful(false);

        List<WarehouseItemPersistanceErrorDTO> itemPersistanceErrorDtoList = new ArrayList<>();
        WarehouseItemPersistanceErrorDTO error1 = new WarehouseItemPersistanceErrorDTO();
        error1.setCategory("Category1");
        error1.setPriceListPricePerUnit(10L);
        error1.setErrorQuantity(10L);
        itemPersistanceErrorDtoList.add(error1);

        WarehouseItemPersistanceErrorDTO error2 = new WarehouseItemPersistanceErrorDTO();
        error2.setCategory("Category2");
        error2.setPriceListPricePerUnit(20L);
        error2.setErrorQuantity(20L);
        itemPersistanceErrorDtoList.add(error2);

        responseDTO.setItemPersistanceErrorDtoList(itemPersistanceErrorDtoList);

        // 1. Define Service-Method Mock
        Mockito.when(warehouseNewDeliveryOrderService.sendDeliveryOrder(Mockito.anyList())).thenReturn(responseDTO);

        // 2. Use the Controller-method
        WarehouseNewDeliveryPersistanceResponseDTO responseDTOfetched =  warehouseNewDeliveryOrderController.sendDeliveryOrder(warehouseDeliveryOrderDTOList);

        // 3. Verify
        Mockito.verify(warehouseNewDeliveryOrderService, Mockito.times(1)).sendDeliveryOrder(Mockito.anyList());

        Assert.assertEquals(responseDTO.toString(), responseDTOfetched.toString());

    }
}
