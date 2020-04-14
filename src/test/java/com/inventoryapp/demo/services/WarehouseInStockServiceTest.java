package com.inventoryapp.demo.services;


import com.inventoryapp.demo.dtos.WarehouseGetAllItemsDTO;
import com.inventoryapp.demo.dtos.WarehouseSupplierItemDTO;
import com.inventoryapp.demo.entities.WarehouseStockItem;
import com.inventoryapp.demo.repositories.WarehouseRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WarehouseInStockServiceTest {


    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test
    public void getAllStockItemsTest() {
        WarehouseStockItem itemEntity = new WarehouseStockItem();
        WarehouseStockItem itemEntity1 = new WarehouseStockItem();

        warehouseRepository.save(itemEntity);
        warehouseRepository.save(itemEntity1);

        WarehouseInStockService warehouseInStockService = new WarehouseInStockService(warehouseRepository);

        List<WarehouseGetAllItemsDTO> listItems = warehouseInStockService.getAllStockItems();
        System.out.println(listItems.size());

        Assert.assertEquals(listItems.size(), 2);
    }

   /* @Test
    public void enterNewItemTest() {

        WarehouseGetAllItemsDTO itemDto = new WarehouseGetAllItemsDTO();
        itemDto.setCategory("Aretes");
        itemDto.setPricePerUnit(6);
        itemDto.setQuantity(2);


//        WarehouseInStockService warehouseInStockService = new WarehouseInStockService(warehouseRepository);
        WarehouseInStockService warehouseInStockService = new WarehouseInStockService();

        warehouseInStockService.enterNewItem(itemDto);

        WarehouseStockItem itemDummy = warehouseRepository.findByCategory("Aretes");
        Assert.assertEquals(itemDummy.getCategory(), itemDto.getCategory());


    }*/

    @Test
    public void removeDuplicatesFromListDtoTest() {
        //Setup
        List<WarehouseSupplierItemDTO> itemListDTO = new ArrayList<>();
        WarehouseSupplierItemDTO item1 = new WarehouseSupplierItemDTO("Earring", 50, 30, 1500, "Enrico");
        WarehouseSupplierItemDTO item2 = new WarehouseSupplierItemDTO("Necklace", 10, 200, 2000, "Alonzo");
        WarehouseSupplierItemDTO item3 = new WarehouseSupplierItemDTO("Necklace", 11, 200, 2000, "Steffi");
        WarehouseSupplierItemDTO item4 = new WarehouseSupplierItemDTO("Necklace", 20, 201, 2000, "Alonzo");
        WarehouseSupplierItemDTO item5 = new WarehouseSupplierItemDTO("Earring", 40, 30, 2000, "Michael");
        WarehouseSupplierItemDTO item6 = new WarehouseSupplierItemDTO("Shoes", 50, 200, 2000, "Michael");

        itemListDTO.add(item1);
        itemListDTO.add(item2);
        itemListDTO.add(item3);
        itemListDTO.add(item4);
        itemListDTO.add(item5);
        itemListDTO.add(item6);

        // Step 1 transfer WarehouseSupplierDTO to WarehouseStockItem List as reference table
        List<WarehouseStockItem> listStockItemsContainingDuplicates = new ArrayList<>();

        for (WarehouseSupplierItemDTO item : itemListDTO) {
            listStockItemsContainingDuplicates.add(this.covertToEntity(item));
        }

        //Step 2 Identify duplicates by category and price. So cumulate quantity.
        List<WarehouseStockItem> listStockItemsWithoutDuplicates = new ArrayList<>();
        //Build list without duplicates containing categories and pricePerUnit
        for (WarehouseStockItem outerItem : listStockItemsContainingDuplicates) {
            if (listStockItemsWithoutDuplicates.stream().noneMatch(o -> o.getCategory().equals(outerItem.getCategory()) &&
                    o.getPricePerUnit() == outerItem.getPricePerUnit())) {
                WarehouseStockItem newItem = new WarehouseStockItem();
                newItem.setCategory(outerItem.getCategory());
                newItem.setPricePerUnit(outerItem.getPricePerUnit());
                listStockItemsWithoutDuplicates.add(newItem);
            }
        }

        // Step 3 Iterrate through reference stock list and cumulate quantities in new list without duplicates
        for (WarehouseStockItem outerItem : listStockItemsContainingDuplicates) {
            listStockItemsWithoutDuplicates.stream().filter(o -> o.getCategory().equals(outerItem.getCategory()) &&
                    o.getPricePerUnit() == outerItem.getPricePerUnit()).forEach(o -> {
                o.setQuantity(o.getQuantity() + outerItem.getQuantity());
            });
        }

        //Test
        Assert.assertEquals(90, listStockItemsWithoutDuplicates.get(0).getQuantity());
        System.out.println(listStockItemsWithoutDuplicates.get(0));
        Assert.assertEquals(21, listStockItemsWithoutDuplicates.get(1).getQuantity());
        System.out.println(listStockItemsWithoutDuplicates.get(1));
        Assert.assertEquals(20, listStockItemsWithoutDuplicates.get(2).getQuantity());
        System.out.println(listStockItemsWithoutDuplicates.get(2));
        Assert.assertEquals(50, listStockItemsWithoutDuplicates.get(3).getQuantity());
        System.out.println(listStockItemsWithoutDuplicates.get(3));
    }

    private WarehouseStockItem covertToEntity(WarehouseSupplierItemDTO itemDto) {
        WarehouseStockItem item = new WarehouseStockItem();
        item.setCategory(itemDto.getCategory());
        item.setPricePerUnit(itemDto.getPricePerUnit());
        item.setQuantity(itemDto.getQuantity());
        return item;
    }

    @Test
    public void persistNewDeliveryToWarehouseStockTest() {
        //Setup: built new supplier list without duplicates
        List<WarehouseStockItem> listStockItemsWithoutDuplicates = new ArrayList<>();
        WarehouseStockItem item1 = new WarehouseStockItem("Earring", 50, 10);
        WarehouseStockItem item2 = new WarehouseStockItem("Necklace", 25, 100);
        WarehouseStockItem item3 = new WarehouseStockItem("Ring", 100, 10);
        WarehouseStockItem item9 = new WarehouseStockItem("Shoes", 30, 45);
        WarehouseStockItem item10 = new WarehouseStockItem("Bracelet", 113, 30);
        listStockItemsWithoutDuplicates.add(item1);
        listStockItemsWithoutDuplicates.add(item2);
        listStockItemsWithoutDuplicates.add(item3);
        listStockItemsWithoutDuplicates.add(item9);
        listStockItemsWithoutDuplicates.add(item10);

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

        // Step 1 Get stock for each category and fill list
        List<WarehouseStockItem> listStockDatabase = new ArrayList<>();
        for (WarehouseStockItem item : listStockItemsWithoutDuplicates) {
            WarehouseStockItem newItem = warehouseRepository.findItemByCategoryAndPricePerUnit(item.getCategory(), item.getPricePerUnit());
            if(newItem != null){
                listStockDatabase.add(newItem);
            }
        }
        System.out.println(listStockDatabase);

        // Test 1
        boolean isEqualCategoryAndPricePerUnitItem1 = listStockDatabase.get(0).getCategory().equals(item1.getCategory()) &&
                listStockDatabase.get(0).getPricePerUnit() == item1.getPricePerUnit();
        Assert.assertTrue(isEqualCategoryAndPricePerUnitItem1);
        Assert.assertEquals(100, listStockDatabase.get(0).getQuantity());
        boolean isEqualCategoryAndPricePerUnitItem2 = listStockDatabase.get(1).getCategory().equals(item2.getCategory()) &&
                listStockDatabase.get(1).getPricePerUnit() == item2.getPricePerUnit();
        Assert.assertTrue(isEqualCategoryAndPricePerUnitItem2);
        Assert.assertEquals(150, listStockDatabase.get(1).getQuantity());
        boolean isEqualCategoryAndPricePerUnitItem3 = listStockDatabase.get(2).getCategory().equals(item3.getCategory()) &&
                listStockDatabase.get(2).getPricePerUnit() == item3.getPricePerUnit();
        Assert.assertTrue(isEqualCategoryAndPricePerUnitItem3);
        Assert.assertEquals(230, listStockDatabase.get(2).getQuantity());

        Assert.assertEquals(3, listStockDatabase.size());

        //Step 2 cumulate for each category and PricePerUnit new quantity on list.
        // If not existing in stock initialize as new category and pricePerUnit with quantity
        for (WarehouseStockItem item : listStockItemsWithoutDuplicates) {
            listStockDatabase.stream().filter(o -> item.getCategory().equals(o.getCategory()) &&
                    item.getPricePerUnit() == o.getPricePerUnit()).forEach(o -> o.setQuantity(o.getQuantity() + item.getQuantity()));
            if(listStockDatabase.stream().noneMatch(o -> item.getCategory().equals(o.getCategory()) &&
                    item.getPricePerUnit() == o.getPricePerUnit())){
                listStockDatabase.add(item);
            }
        }
        //Test 2
        Assert.assertEquals(150,listStockDatabase.get(0).getQuantity());
        Assert.assertEquals(175,listStockDatabase.get(1).getQuantity());
        Assert.assertEquals(330,listStockDatabase.get(2).getQuantity());
        //Test for new added items on stock
        Assert.assertEquals("Shoes", listStockDatabase.get(3).getCategory());
        Assert.assertEquals(30, listStockDatabase.get(3).getQuantity());
        Assert.assertEquals("Bracelet", listStockDatabase.get(4).getCategory());
        Assert.assertEquals(113, listStockDatabase.get(4).getQuantity());

        //Step 3 persist new stock list to inventory table
        for(WarehouseStockItem item : listStockDatabase){
            int modifiedRow = warehouseRepository.updateStock(item.getCategory(), item.getPricePerUnit(), item.getQuantity());
            if(modifiedRow == 0){
                warehouseRepository.save(item);
            }
        }

        //Test 3
        System.out.println("Table from database:");
        List<WarehouseStockItem> completeListStockDatabase = new ArrayList<>();
        completeListStockDatabase = warehouseRepository.findAll();
        Assert.assertEquals(7, completeListStockDatabase.size());
        for(WarehouseStockItem item : listStockDatabase){
            completeListStockDatabase.stream().filter(o -> item.getCategory().equals(o.getCategory()) &&
                    item.getPricePerUnit() == o.getPricePerUnit()).forEach(o -> Assert.assertEquals(item.getQuantity(), o.getQuantity()));;
        }
        for (WarehouseStockItem item: completeListStockDatabase) {
            System.out.println(item);
        }
    }
}
