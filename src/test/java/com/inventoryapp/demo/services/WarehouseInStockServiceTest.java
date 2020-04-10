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
    public void enterNewListTest(){
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

        // 1. transfer WarehouseSupplierDTO to WarehouseStockItem List as reference table
        List<WarehouseStockItem> listStockItemsContainingDuplicates = new ArrayList<>();

        for(WarehouseSupplierItemDTO item : itemListDTO){
            listStockItemsContainingDuplicates.add(this.covertToEntity(item));
        }

        // 2. Identify duplicates by category and price. So cumulate quantity.
        List<WarehouseStockItem> listStockItemsWithoutDuplicates = new ArrayList<>();
        //Build list without duplicates containing categories and pricePerUnit
        for(WarehouseStockItem outerItem : listStockItemsContainingDuplicates){
            if(listStockItemsWithoutDuplicates.stream().noneMatch(o -> o.getCategory().equals(outerItem.getCategory()) &&
                    o.getPricePerUnit() == outerItem.getPricePerUnit())){
                WarehouseStockItem newItem = new WarehouseStockItem();
                newItem.setCategory(outerItem.getCategory());
                newItem.setPricePerUnit(outerItem.getPricePerUnit());
                listStockItemsWithoutDuplicates.add(newItem);
            }
        }

        // 3. Iterrate through reference stock list and cumulate quantities in new list without duplicates
        for(WarehouseStockItem outerItem : listStockItemsContainingDuplicates){
            listStockItemsWithoutDuplicates.stream().filter(o -> o.getCategory().equals(outerItem.getCategory()) &&
                    o.getPricePerUnit() == outerItem.getPricePerUnit()).forEach(
                            o -> {
                                o.setQuantity(o.getQuantity()+outerItem.getQuantity());
                            });
        }

        Assert.assertEquals(90,listStockItemsWithoutDuplicates.get(0).getQuantity());
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
}
