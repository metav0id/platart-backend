package com.inventoryapp.demo.services;


import com.inventoryapp.demo.dtos.ShopsStockItemDto;
import com.inventoryapp.demo.entities.ShopsStockItem;
import com.inventoryapp.demo.repositories.ShopsStockItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ShopsCurrentInventoryServiceTest {

    @Autowired
    private ShopsStockItemRepository shopsStockItemRepository;

    public ShopsCurrentInventoryServiceTest() {
    }

    List<ShopsStockItem> shopsStockItemList = new ArrayList<>();
    @Before
    public void init(){
        // 1. Define Test Items
        ShopsStockItem item1 = new ShopsStockItem();
        item1.setId(1L);
        item1.setCategory("category1");
        item1.setQuantity(100L);
        item1.setPriceListPerUnit(20L);
        item1.setPriceSalesPerUnit(20L);
        item1.setShop("TestShop");
        shopsStockItemList.add(item1);

        ShopsStockItem item2 = new ShopsStockItem();
        item2.setId(2L);
        item2.setCategory("category1");
        item2.setQuantity(100L);
        item2.setPriceListPerUnit(20L);
        item2.setPriceSalesPerUnit(20L);
        item2.setShop("TestShop");
        shopsStockItemList.add(item2);

        ShopsStockItem item3 = new ShopsStockItem();
        item3.setId(2L);
        item3.setCategory("category2");
        item3.setQuantity(100L);
        item3.setPriceListPerUnit(20L);
        item3.setPriceSalesPerUnit(20L);
        item3.setShop("TestShop");
        shopsStockItemList.add(item3);
    }

    @Test
    public void getAllItemsAllShopsPositiveTest(){
        // 0. define persist test Data to repository
        this.shopsStockItemRepository.saveAll(this.shopsStockItemList);

        // 1.1 Fetch Data from Database data
        List<ShopsStockItem> inventoryListFetched =  this.shopsStockItemRepository.findAll();

        // 1.2 Assert the input and output
        for(int i =0; i<inventoryListFetched.size() ; i++){
            String input = inventoryListFetched.get(i).getCategory();
            String output = shopsStockItemList.get(i).getCategory();
            Assert.assertEquals(input , output );
        }

        // 2.1 transform Entities to Dtos with mapper method
        List<ShopsStockItemDto> shopsCurrentInventoryDTOList = shopItemMapEntityToDto(inventoryListFetched);

        for(int i =0; i<inventoryListFetched.size() ; i++){
            String input = shopsCurrentInventoryDTOList.get(i).getCategory();
            String output = shopsStockItemList.get(i).getCategory();
            Assert.assertEquals( input , output );
        }
    }

    @Test
    public void getAllItemsSpecificShopPositiveTest(){
        // 0. define persist test Data to repository
        this.shopsStockItemRepository.saveAll(this.shopsStockItemList);

        // 1.1 Fetch Data from Database data
        String shopChosen = this.shopsStockItemList.get(0).getShop();
        List<ShopsStockItem> inventoryListFetched =  this.shopsStockItemRepository.findAllItemsByShop(shopChosen);

        // 1.2 Assert the input and output
        for(int i =0; i<inventoryListFetched.size() ; i++){
            String output = shopsStockItemList.get(i).getShop();
            Assert.assertEquals(shopChosen , output );
        }

        // 2.1 transform Entities to Dtos with mapper method
        List<ShopsStockItemDto> shopsCurrentInventoryDTOList = shopItemMapEntityToDto(inventoryListFetched);

        for(int i =0; i<inventoryListFetched.size() ; i++){
            String output = shopsStockItemList.get(i).getShop();
            Assert.assertEquals( shopChosen , output );
        }
    }


    public List<ShopsStockItemDto> shopItemMapEntityToDto(List<ShopsStockItem> currentInventoryList){
        List<ShopsStockItemDto> shopsCurrentInventoryDTOList = new ArrayList<>();

        for(ShopsStockItem item: currentInventoryList){
            ShopsStockItemDto newInventoryDTO = new ShopsStockItemDto();
            newInventoryDTO.setPosition(item.getId());
            newInventoryDTO.setCategory(item.getCategory());
            newInventoryDTO.setQuantity(item.getQuantity());
            shopsCurrentInventoryDTOList.add(newInventoryDTO);
        }
        return shopsCurrentInventoryDTOList;
    }

}
