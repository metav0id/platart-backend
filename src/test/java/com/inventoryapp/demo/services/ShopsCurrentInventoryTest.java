package com.inventoryapp.demo.services;


import com.inventoryapp.demo.dtos.ShopsCurrentInventoryDTO;
import com.inventoryapp.demo.entities.ShopsCurrentInventory;
import com.inventoryapp.demo.repositories.ShopsCurrentInventoryRepository;
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
public class ShopsCurrentInventoryTest {

    @Autowired
    private ShopsCurrentInventoryRepository shopsCurrentInventoryRepository;

    public ShopsCurrentInventoryTest() {
    }

    List<ShopsCurrentInventory> shopsCurrentInventoryList = new ArrayList<>();
    @Before
    public void init(){
        // data for repository
        ShopsCurrentInventory item1 = new ShopsCurrentInventory();
        item1.setId(1L);
        item1.setCategory("Category 1");
        item1.setShop("Shop1");
        item1.setPriceSalesPerUnit(20);
        item1.setPriceListPerUnit(10);
        item1.setDiscountPercent(50);
        item1.setDeliverySending(LocalDateTime.now().minusDays(7));
        item1.setItemLastSold(LocalDateTime.now().minusDays(1));
        item1.setQuantity(100);
        shopsCurrentInventoryList.add(item1);

        ShopsCurrentInventory item2 = new ShopsCurrentInventory();
        item2.setId(2L);
        item2.setCategory("Category 2");
        item2.setShop("Shop2");
        item2.setPriceSalesPerUnit(30);
        item2.setPriceListPerUnit(15);
        item2.setDiscountPercent(50);
        item2.setDeliverySending(LocalDateTime.now().minusDays(7));
        item2.setItemLastSold(LocalDateTime.now().minusDays(1));
        item2.setQuantity(100);
        shopsCurrentInventoryList.add(item2);
    }

    @Test
    public void getAllItemsSpecificShopPositiveTest(){
        // 0. define persist test Data to repository
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
        }
    }

    @Test
    public void getAllItemsAllShopsPositiveTest(){
        // 0. define persist test Data to repository
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
        }
    }

    @Test
    public void setItemsShopsPositiveTest(){
        // 0.1 Define Item to be persisted
        List<ShopsCurrentInventoryDTO> shopsCurrentInventoryDTOList = new ArrayList<>();

        ShopsCurrentInventoryDTO shopsCurrentDTO1 = new ShopsCurrentInventoryDTO();
        shopsCurrentDTO1.setId(1L);
        shopsCurrentDTO1.setCategory("Category 1");
        shopsCurrentDTO1.setItemInShop("Shop1");
        shopsCurrentDTO1.setItemDisplayPricePerUnit(20);
        shopsCurrentDTO1.setItemFinalPricePerUnit(10);
        shopsCurrentDTO1.setItemDiscount(50);
        shopsCurrentDTO1.setItemInShop("Shop1");
        shopsCurrentDTO1.setItemLastDelivery(LocalDateTime.now().minusDays(7));
        shopsCurrentDTO1.setItemLastSold(LocalDateTime.now().minusDays(1));
        shopsCurrentInventoryDTOList.add(shopsCurrentDTO1);

        // 0.2 map Dto to Entity
        List<ShopsCurrentInventory> shopsCurrentInventoryList = shopItemMapDtoToEntity(shopsCurrentInventoryDTOList);

        // 1. Persist new Item
        this.shopsCurrentInventoryRepository.saveAll(shopsCurrentInventoryList);

        // 2. Verify if elements were saved correctly
        List<ShopsCurrentInventory> shopsCurrentInventoryListFetched = this.shopsCurrentInventoryRepository.findAll();
        for(int i=0;i< shopsCurrentInventoryListFetched.size(); i++){
            Assert.assertEquals(shopsCurrentInventoryListFetched.get(i).getShop(), shopsCurrentInventoryDTOList.get(i).getItemInShop());
        }

    }

    public List<ShopsCurrentInventoryDTO> shopItemMapEntityToDto(List<ShopsCurrentInventory> currentInventoryList){
        List<ShopsCurrentInventoryDTO> shopsCurrentInventoryDTOList = new ArrayList<>();

        for(ShopsCurrentInventory item: currentInventoryList){
            ShopsCurrentInventoryDTO newInventoryDTO = new ShopsCurrentInventoryDTO();
            newInventoryDTO.setId(item.getId());
            newInventoryDTO.setCategory(item.getCategory());
            newInventoryDTO.setItemInShop(item.getShop());
            newInventoryDTO.setQuantity(item.getQuantity());
            newInventoryDTO.setItemFinalPricePerUnit(item.getPriceListPerUnit());
            newInventoryDTO.setItemDisplayPricePerUnit(item.getPriceListPerUnit());
            newInventoryDTO.setItemDiscount(item.getDiscountPercent());
            newInventoryDTO.setItemLastDelivery(item.getDeliverySending());
            newInventoryDTO.setItemLastSold(item.getItemLastSold());
            shopsCurrentInventoryDTOList.add(newInventoryDTO);
        }
        return shopsCurrentInventoryDTOList;
    }

    public List<ShopsCurrentInventory> shopItemMapDtoToEntity(List<ShopsCurrentInventoryDTO> currentInventoryList){
        List<ShopsCurrentInventory> shopsCurrentInventoryList = new ArrayList<>();

        for(ShopsCurrentInventoryDTO item: currentInventoryList){
            ShopsCurrentInventory newInventoryDTO = new ShopsCurrentInventory();
            newInventoryDTO.setId(item.getId());
            newInventoryDTO.setCategory(item.getCategory());
            newInventoryDTO.setShop(item.getItemInShop());
            newInventoryDTO.setQuantity(item.getQuantity());
            newInventoryDTO.setPriceSalesPerUnit(item.getItemFinalPricePerUnit());
            newInventoryDTO.setPriceListPerUnit(item.getItemDisplayPricePerUnit());
            newInventoryDTO.setDiscountPercent(item.getItemDiscount());
            newInventoryDTO.setDeliverySending(item.getItemLastDelivery());
            newInventoryDTO.setItemLastSold(item.getItemLastSold());
            shopsCurrentInventoryList.add(newInventoryDTO);
        }
        return shopsCurrentInventoryList;
    }

}
