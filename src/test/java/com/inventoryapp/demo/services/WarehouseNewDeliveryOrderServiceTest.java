package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.WarehouseItemPersistanceErrorDTO;
import com.inventoryapp.demo.dtos.WarehouseNewDeliveryOrderItemDTO;
import com.inventoryapp.demo.dtos.WarehouseNewDeliveryPersistanceResponseDTO;
import com.inventoryapp.demo.entities.WarehouseNewDeliveryOrderItem;
import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import com.inventoryapp.demo.entities.WarehouseStockItem;
import com.inventoryapp.demo.repositories.WarehouseNewDeliveryOrderRepository;
import com.inventoryapp.demo.repositories.WarehouseRepository;
import com.inventoryapp.demo.repositories.WarehouseShopDeliveryOrdersSend;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WarehouseNewDeliveryOrderServiceTest {
    @Autowired
    WarehouseNewDeliveryOrderRepository warehouseNewDeliveryOrderRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private WarehouseShopDeliveryOrdersSend warehouseShopDeliveryOrdersSend;

    List<WarehouseNewDeliveryOrderItem> deliveryOrderItemsEntities = new ArrayList<>();
    List<WarehouseNewDeliveryOrderItemDTO> deliveryOrderItemDTOS = new ArrayList<>();
    @Before
    public void setUp(){
        WarehouseNewDeliveryOrderItem newDeliveryItem1 = new WarehouseNewDeliveryOrderItem();
        newDeliveryItem1.setId(1L);
        newDeliveryItem1.setCategory("Category 1");
        newDeliveryItem1.setQuantity(123);
        newDeliveryItem1.setPriceSalesPerUnit(20L);
        newDeliveryItem1.setDiscountPercent(25);
        newDeliveryItem1.setPriceListPerUnit(15L);
        deliveryOrderItemsEntities.add(newDeliveryItem1);

        WarehouseNewDeliveryOrderItem newDeliveryItem2 = new WarehouseNewDeliveryOrderItem();
        newDeliveryItem2.setId(2L);
        newDeliveryItem2.setCategory("Category 2");
        newDeliveryItem2.setQuantity(123);
        newDeliveryItem2.setPriceSalesPerUnit(20L);
        newDeliveryItem2.setDiscountPercent(25);
        newDeliveryItem2.setPriceListPerUnit(15L);
        deliveryOrderItemsEntities.add(newDeliveryItem2);

        WarehouseNewDeliveryOrderItemDTO newDeliveryItemDTO1 = new WarehouseNewDeliveryOrderItemDTO();
        newDeliveryItemDTO1.setId(1L);
        newDeliveryItemDTO1.setCategory("Category 1");
        newDeliveryItemDTO1.setQuantity(123);
        newDeliveryItemDTO1.setPriceSalesPerUnit(20L);
        newDeliveryItemDTO1.setDiscountPercent(25);
        newDeliveryItemDTO1.setPriceListPerUnit(15L);
        deliveryOrderItemDTOS.add(newDeliveryItemDTO1);

        WarehouseNewDeliveryOrderItemDTO newDeliveryItemDTO2 = new WarehouseNewDeliveryOrderItemDTO();
        newDeliveryItemDTO2.setId(2L);
        newDeliveryItemDTO2.setCategory("Category 2");
        newDeliveryItemDTO2.setQuantity(123);
        newDeliveryItemDTO2.setPriceSalesPerUnit(20L);
        newDeliveryItemDTO2.setDiscountPercent(25);
        newDeliveryItemDTO2.setPriceListPerUnit(15L);
        deliveryOrderItemDTOS.add(newDeliveryItemDTO2);
    }

    @Test
    public void persistanceListTest(){

        warehouseNewDeliveryOrderRepository.saveAll(deliveryOrderItemsEntities);
        Iterable<WarehouseNewDeliveryOrderItem> elementsDatabase = warehouseNewDeliveryOrderRepository.findAll();
        List<WarehouseNewDeliveryOrderItem> deliveryOrderItemsEntitiesTest = new ArrayList<>();

        for(WarehouseNewDeliveryOrderItem item: elementsDatabase){
            deliveryOrderItemsEntitiesTest.add(item);
        }

        Assert.assertEquals(deliveryOrderItemsEntities.size(), deliveryOrderItemsEntitiesTest.size());
    }

    @Test
    public void convertEntitiesToDtosTest(){

        List<WarehouseNewDeliveryOrderItemDTO> deliveryOrderItemDTOSTest = new ArrayList<>();

        for(WarehouseNewDeliveryOrderItem item: deliveryOrderItemsEntities){
            WarehouseNewDeliveryOrderItemDTO newDeliveryOrderItemDTO = new WarehouseNewDeliveryOrderItemDTO();
            newDeliveryOrderItemDTO.setId(item.getId());
            newDeliveryOrderItemDTO.setCategory(item.getCategory());
            newDeliveryOrderItemDTO.setQuantity(item.getQuantity());
            newDeliveryOrderItemDTO.setPriceSalesPerUnit(item.getPriceSalesPerUnit());
            newDeliveryOrderItemDTO.setDiscountPercent(item.getDiscountPercent());
            newDeliveryOrderItemDTO.setPriceListPerUnit(item.getPriceListPerUnit());

            deliveryOrderItemDTOSTest.add(newDeliveryOrderItemDTO);
        }

        for (int i = 0; i < deliveryOrderItemDTOSTest.size(); i++) {
            Assert.assertEquals(deliveryOrderItemDTOSTest.get(i).getCategory(), deliveryOrderItemDTOS.get(i).getCategory());
        }
    }

    @Test
    public void convertDtosToEntitiesTest(){
        List<WarehouseNewDeliveryOrderItem> deliveryOrderItemEntityListsTest = new ArrayList<>();
        for(WarehouseNewDeliveryOrderItemDTO item: deliveryOrderItemDTOS){
            WarehouseNewDeliveryOrderItem newDeliveryOrderItem = new WarehouseNewDeliveryOrderItem();
            newDeliveryOrderItem.setId(item.getId());
            newDeliveryOrderItem.setCategory(item.getCategory());
            newDeliveryOrderItem.setQuantity(item.getQuantity());
            newDeliveryOrderItem.setPriceSalesPerUnit(item.getPriceSalesPerUnit());
            newDeliveryOrderItem.setDiscountPercent(item.getDiscountPercent());
            newDeliveryOrderItem.setPriceListPerUnit(item.getPriceListPerUnit());

            deliveryOrderItemEntityListsTest.add(newDeliveryOrderItem);
        }

        for (int i = 0; i < deliveryOrderItemEntityListsTest.size(); i++) {
            Assert.assertEquals(deliveryOrderItemEntityListsTest.get(i).getCategory(), deliveryOrderItemsEntities.get(i).getCategory());
        }

    }

    @Test
    public void sendDeliveryOrderNegativeTest(){
        // use the following input List: deliveryOrderItemsEntities
        //Setup: simulate stock in database
        List<WarehouseStockItem> tempListDatabase = new ArrayList<>();
        WarehouseStockItem item4 = new WarehouseStockItem("Earring", 100, 10);
        WarehouseStockItem item5 = new WarehouseStockItem("Necklace", 150, 100);
        WarehouseStockItem item6 = new WarehouseStockItem("Ring", 225, 15);
        WarehouseStockItem item7 = new WarehouseStockItem("Ring", 230, 10);
        WarehouseStockItem item8 = new WarehouseStockItem("Bracelet", 100, 25);
        tempListDatabase.add(item4);
        tempListDatabase.add(item5);
        tempListDatabase.add(item6);
        tempListDatabase.add(item7);
        tempListDatabase.add(item8);
        warehouseRepository.saveAll(tempListDatabase);

        // 0. Define return Dto - if this point was reached, set persistance initalized true
        WarehouseNewDeliveryPersistanceResponseDTO responseDTO =  new WarehouseNewDeliveryPersistanceResponseDTO();
        responseDTO.setPersistanceInitialized(true);
        List<WarehouseItemPersistanceErrorDTO> itemPersistanceErrorDtoList = new ArrayList<>();


        // 1.
        this.warehouseNewDeliveryOrderRepository.deleteAll();
        this.warehouseNewDeliveryOrderRepository.saveAll(deliveryOrderItemsEntities);

        // 2. Data Management
        List<WarehouseNewDeliveryOrderItem> currentDeliveryOrderItemEntitiesList = this.warehouseNewDeliveryOrderRepository.findAll();
        currentDeliveryOrderItemEntitiesList.stream().forEach(o-> System.out.println("Delivery Quantity" + o.getQuantity() + " Category "+ o.getCategory() + " price " + o.getPriceListPerUnit()));

        // 3. cumulate for each category and PricePerUnit new quantity on list.
        List<WarehouseNewDeliveryOrderItem> currentDeliveriesAggregated = new ArrayList<>();

        for (WarehouseNewDeliveryOrderItem item : currentDeliveryOrderItemEntitiesList) {

            currentDeliveriesAggregated.stream()
                    .filter(
                            o -> item.getCategory().equals(o.getCategory()) &&
                                    item.getPriceListPerUnit() == item.getPriceListPerUnit())
                    .forEach(o -> o.setQuantity(o.getQuantity() + item.getQuantity()));

            boolean isNotItemFound = currentDeliveriesAggregated.stream()
                    .noneMatch(
                            o -> item.getCategory().equals(o.getCategory()) &&
                                    item.getPriceListPerUnit() == item.getPriceListPerUnit());

            if (isNotItemFound) {
                WarehouseNewDeliveryOrderItem newItem = new WarehouseNewDeliveryOrderItem();
                newItem.setId(item.getId());
                newItem.setCategory(item.getCategory());
                newItem.setQuantity(item.getQuantity());
                newItem.setDiscountPercent(item.getDiscountPercent());
                newItem.setPriceSalesPerUnit(item.getPriceSalesPerUnit());
                newItem.setPriceListPerUnit(item.getPriceListPerUnit());
                newItem.setDeliveryShop(item.getDeliveryShop());

                currentDeliveriesAggregated.add(newItem);
            }
        }

        // 4. verify if transaction is feasible for all items in delivery list
        boolean isTransactionFeasible = true;
        for (WarehouseNewDeliveryOrderItem item : currentDeliveriesAggregated) {
            System.out.println("ITEM ITEM: " + item.getCategory() + " " + item.getQuantity());

            // verify if amount of item in stock
            WarehouseStockItem itemWarehouse = this.warehouseRepository.findItemByCategoryAndPricePerUnit(item.getCategory(), item.getPriceListPerUnit());
            try {
                Long differenceQuantity = itemWarehouse.getQuantity() - item.getQuantity();
                if (differenceQuantity < 0) {
                    isTransactionFeasible = false;

                    WarehouseItemPersistanceErrorDTO error = new WarehouseItemPersistanceErrorDTO();
                    error.setCategory(item.getCategory());
                    error.setPriceListPricePerUnit(item.getPriceListPerUnit());
                    error.setErrorQuantity(differenceQuantity);
                    itemPersistanceErrorDtoList.add(error);
                }
            } catch (NullPointerException exception){
                System.err.println("We have a Nulllpointer Exception");
            }
        }
        responseDTO.setItemPersistanceErrorDtoList(itemPersistanceErrorDtoList);

        // 5. update the item amount on the warehouse table and add them to the OrderSendTable
        //List<Long> modifiedItems = new ArrayList<>();
        if(isTransactionFeasible){

            LocalDateTime newDeliveryDateTime = LocalDateTime.now();
            for (WarehouseNewDeliveryOrderItem itemOnList : currentDeliveryOrderItemEntitiesList) {
                WarehouseStockItem itemWarehouse = this.warehouseRepository.findItemByCategoryAndPricePerUnit(itemOnList.getCategory(), itemOnList.getPriceListPerUnit());
                try {
                    long newWarehouseQuantity = itemWarehouse.getQuantity() - itemOnList.getQuantity();
                    itemWarehouse.setQuantity(newWarehouseQuantity);

                    // modifiedItems.add(itemOnList.getId());
                    this.warehouseRepository.save(itemWarehouse);

                    WarehouseSendDeliveryOrderItem deliveryItemSend = new WarehouseSendDeliveryOrderItem();
                    //deliveryItemSend.setId(itemOnList.getId());
                    deliveryItemSend.setCategory(itemOnList.getCategory());
                    deliveryItemSend.setPriceSalesPerUnit(itemOnList.getPriceSalesPerUnit());
                    deliveryItemSend.setDiscountPercent(itemOnList.getDiscountPercent());
                    deliveryItemSend.setPriceListPerUnit(itemOnList.getPriceListPerUnit());
                    deliveryItemSend.setDeliverySending(newDeliveryDateTime);
                    deliveryItemSend.setQuantity(itemOnList.getQuantity());
                    deliveryItemSend.setShop(itemOnList.getDeliveryShop());
                    System.out.println(itemOnList.getDeliveryShop());
                    this.warehouseShopDeliveryOrdersSend.save(deliveryItemSend);
                } catch (NullPointerException exception){
                    System.err.println("We have a Nulllpointer Exception");
                }
            }
            //delete all items on the temporary item-order list
            this.warehouseNewDeliveryOrderRepository.deleteAll();
            responseDTO.setPersistanceSuccessful(true);
        } else {
            responseDTO.setPersistanceSuccessful(false);
            System.out.println("Peristance of list not possible -> requested amount not available on stock ");
        }

        System.out.println(" Result: "+ responseDTO.toString());
        Assert.assertTrue(responseDTO.isPersistanceInitialized());
        Assert.assertTrue(responseDTO.isPersistanceSuccessful());
        Assert.assertEquals(responseDTO.getItemPersistanceErrorDtoList().size(), 0);
    }

}
