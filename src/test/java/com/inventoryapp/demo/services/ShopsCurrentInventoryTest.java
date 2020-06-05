package com.inventoryapp.demo.services;


import com.inventoryapp.demo.entities.ShopsStockItem;
import com.inventoryapp.demo.repositories.ShopsStockItemRepository;
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
public class ShopsCurrentInventoryTest {

    @Autowired
    private ShopsStockItemRepository shopsStockItemRepository;

    public ShopsCurrentInventoryTest() {
    }

    List<ShopsStockItem> shopsStockItemList = new ArrayList<>();
    @Before
    public void init(){

    }

    @Ignore
    @Test
    public void getAllItemsSpecificShopPositiveTest(){
        /*// 0. define persist test Data to repository
        this.shopsCurrentInventoryRepository.saveAll(this.shopsCurrentInventoryList);

        // 1. Fetch Specific Shop Data from Database data
        String specificShop = this.shopsCurrentInventoryList.get(0).getShop();
        List<ShopsCurrentInventory> inventoryListFetched =  this.shopsCurrentInventoryRepository.findByShop(specificShop);

        for(int i =0; i<inventoryListFetched.size() ; i++){
            Assert.assertEquals(inventoryListFetched.get(i).getShop(), specificShop );
        }

        // 2. transform Entities to Dtos
        List<ShopsCurrentInventoryDTO> shopsCurrentInventoryDTOList = this.shopItemMapEntityToDto(inventoryListFetched);

        for(int i =0; i<inventoryListFetched.size() ; i++){
            Assert.assertEquals(shopsCurrentInventoryDTOList.get(i).getItemInShop(), specificShop );
        }*/
    }

    @Ignore
    @Test
    public void getAllItemsAllShopsPositiveTest(){
        /*// 0. define persist test Data to repository
        this.shopsCurrentInventoryRepository.saveAll(this.shopsCurrentInventoryList);

        // 1. Fetch Data from Database data
        List<ShopsCurrentInventory> inventoryListFetched =  this.shopsCurrentInventoryRepository.findAll();


        for(int i =0; i<inventoryListFetched.size() ; i++){
            Assert.assertEquals(inventoryListFetched.get(i).getCategory(), shopsCurrentInventoryList.get(i).getCategory() );
        }

        // 2. transform Entities to Dtos with mapper method
        List<ShopsCurrentInventoryDTO> shopsCurrentInventoryDTOList = shopItemMapEntityToDto(inventoryListFetched);

        for(int i =0; i<inventoryListFetched.size() ; i++){
            Assert.assertEquals(shopsCurrentInventoryDTOList.get(i).getCategory(), shopsCurrentInventoryList.get(i).getCategory() );
        }*/
    }


    /*public List<ShopsCurrentInventoryDTO> shopItemMapEntityToDto(List<ShopsCurrentInventory> currentInventoryList){
        List<ShopsCurrentInventoryDTO> shopsCurrentInventoryDTOList = new ArrayList<>();

        for(ShopsCurrentInventory item: currentInventoryList){
            ShopsCurrentInventoryDTO newInventoryDTO = new ShopsCurrentInventoryDTO();
            newInventoryDTO.setId(item.getId());
            newInventoryDTO.setCategory(item.getCategory());
            newInventoryDTO.setItemInShop(item.getShop());
            newInventoryDTO.setQuantity(item.getQuantity());
            newInventoryDTO.setPriceListPricePerUnit(item.getPriceListPerUnit());
            newInventoryDTO.setPriceSalesPricePerUnit(item.getPriceListPerUnit());
            newInventoryDTO.setDiscountPercent(item.getDiscountPercent());
            newInventoryDTO.setItemLastDelivery(item.getDeliverySending());
            newInventoryDTO.setItemLastSold(item.getItemLastSold());
            shopsCurrentInventoryDTOList.add(newInventoryDTO);
        }
        return shopsCurrentInventoryDTOList;
    }*/

}
