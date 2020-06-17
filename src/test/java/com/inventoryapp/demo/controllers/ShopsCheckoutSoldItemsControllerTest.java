package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.ShopsCheckoutSoldItemsDTO;
import com.inventoryapp.demo.entities.ShopsCheckoutSoldItems;
import com.inventoryapp.demo.entities.ShopsStockItem;
import com.inventoryapp.demo.repositories.ShopsAllSoldItemsRepository;
import com.inventoryapp.demo.repositories.ShopsCheckoutSoldItemsRepository;
import com.inventoryapp.demo.repositories.ShopsStockItemRepository;
import com.inventoryapp.demo.services.ShopsCheckoutSoldItemsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopsCheckoutSoldItemsControllerTest {

    @Autowired
    private ShopsCheckoutSoldItemsService shopsCheckoutSoldItemsService;

    @MockBean
    private ShopsCheckoutSoldItemsRepository shopsCheckoutSoldItemsRepository;

    @MockBean
    private ShopsStockItemRepository shopsStockItemRepository;

    @MockBean
    private ShopsAllSoldItemsRepository shopsAllSoldItemsRepository;

    private List<ShopsCheckoutSoldItems> shopsCheckoutSoldItemsEntitiesList = new ArrayList<>();
    private List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOSList = new ArrayList<>();

    @Before
    public void setUp(){
        // Entities List
        ShopsCheckoutSoldItems item1 = new ShopsCheckoutSoldItems();
        item1.setId(1L);
        item1.setShop("shop1");
        item1.setCategory("anillo");
        item1.setQuantity(100L);
        item1.setPriceSalesPerUnit(100L);
        item1.setPriceListPerUnit(95L);
        item1.setDiscountPercent(5);
        item1.setDeliverySending(LocalDateTime.now());
        item1.setRevenuePerUnit(95L);
        item1.setItemLastSold(LocalDateTime.now());
        item1.setComment("NoComment");
        shopsCheckoutSoldItemsEntitiesList.add(item1);

        ShopsCheckoutSoldItems item2 = new ShopsCheckoutSoldItems();
        item2.setId(2L);
        item2.setShop("shop1");
        item2.setCategory("anillo");
        item2.setQuantity(100L);
        item2.setPriceSalesPerUnit(100L);
        item2.setPriceListPerUnit(95L);
        item2.setDiscountPercent(5);
        item2.setDeliverySending(LocalDateTime.now());
        item2.setRevenuePerUnit(95L);
        item2.setItemLastSold(LocalDateTime.now());
        item2.setComment("NoComment");
        shopsCheckoutSoldItemsEntitiesList.add(item2);

        ShopsCheckoutSoldItems item3 = new ShopsCheckoutSoldItems();
        item3.setId(3L);
        item3.setShop("shop1");
        item3.setCategory("anillo");
        item3.setQuantity(100L);
        item3.setPriceSalesPerUnit(100L);
        item3.setPriceListPerUnit(95L);
        item3.setDiscountPercent(5);
        item3.setDeliverySending(LocalDateTime.now());
        item3.setRevenuePerUnit(95L);
        item3.setItemLastSold(LocalDateTime.now());
        item3.setComment("NoComment");
        shopsCheckoutSoldItemsEntitiesList.add(item3);

        //Dtos-List
        ShopsCheckoutSoldItemsDTO itemDTO1 = new ShopsCheckoutSoldItemsDTO();
        itemDTO1.setPosition(1L);
        itemDTO1.setShop("shop1");
        itemDTO1.setCategory("anillo");
        itemDTO1.setQuantity(100L);
        itemDTO1.setPriceSalesPerUnit((double)100);
        itemDTO1.setPriceListPerUnit((double)95);
        itemDTO1.setDiscountPercent((double)5);
        itemDTO1.setDeliverySending(LocalDateTime.now());
        itemDTO1.setRevenuePerUnit((double)95);
        itemDTO1.setItemLastSold(LocalDateTime.now());
        itemDTO1.setComment("NoComment");
        shopsCheckoutSoldItemsDTOSList.add(itemDTO1);

        ShopsCheckoutSoldItemsDTO itemDTO2 = new ShopsCheckoutSoldItemsDTO();
        itemDTO2.setPosition(2L);
        itemDTO2.setShop("shop1");
        itemDTO2.setCategory("anillo");
        itemDTO2.setQuantity(100L);
        itemDTO2.setPriceSalesPerUnit((double)100);
        itemDTO2.setPriceListPerUnit((double)95);
        itemDTO2.setDiscountPercent((double)5);
        itemDTO2.setDeliverySending(LocalDateTime.now());
        itemDTO2.setRevenuePerUnit((double)95);
        itemDTO2.setItemLastSold(LocalDateTime.now());
        itemDTO2.setComment("NoComment");
        shopsCheckoutSoldItemsDTOSList.add(itemDTO2);

        ShopsCheckoutSoldItemsDTO itemDTO3 = new ShopsCheckoutSoldItemsDTO();
        itemDTO3.setPosition(3L);
        itemDTO3.setShop("shop1");
        itemDTO3.setCategory("anillo");
        itemDTO3.setQuantity(100L);
        itemDTO3.setPriceSalesPerUnit((double)100);
        itemDTO3.setPriceListPerUnit((double)95);
        itemDTO3.setDiscountPercent((double)5);
        itemDTO3.setDeliverySending(LocalDateTime.now());
        itemDTO3.setRevenuePerUnit((double)95);
        itemDTO3.setItemLastSold(LocalDateTime.now());
        itemDTO3.setComment("NoComment");
        shopsCheckoutSoldItemsDTOSList.add(itemDTO3);
    }

    @Test
    public void getAllSoldItemsListPositiveTest(){
        // 1. Step: prepare data
        when(shopsCheckoutSoldItemsRepository.findAll()).thenReturn(this.shopsCheckoutSoldItemsEntitiesList);

        // 2. Fetch the Data
        List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList = shopsCheckoutSoldItemsService.getAllSoldItemsList();

        for(int i=0; i<shopsCheckoutSoldItemsDTOList.size(); i++){
            String fetchedItemShop = shopsCheckoutSoldItemsDTOList.get(i).getShop();
            String savedItemShop = shopsCheckoutSoldItemsEntitiesList.get(i).getShop();
            Assert.assertEquals(savedItemShop, fetchedItemShop);

            String fetchedItemCategory = shopsCheckoutSoldItemsDTOList.get(i).getCategory();
            String savedItemCategory = shopsCheckoutSoldItemsEntitiesList.get(i).getCategory();
            Assert.assertEquals(savedItemCategory, fetchedItemCategory);

            Double fetchedItemPriceList = shopsCheckoutSoldItemsDTOList.get(i).getPriceListPerUnit();
            Double savedItemPriceList = (double)shopsCheckoutSoldItemsEntitiesList.get(i).getPriceListPerUnit();
            Assert.assertEquals(savedItemPriceList, fetchedItemPriceList);
        }
    }

    @Test
    public void saveListDeliverySuppliersTestPositiveTest(){
        // 1. Step: prepare the data
        shopsCheckoutSoldItemsService.saveAllSoldItemsList(shopsCheckoutSoldItemsDTOSList);

        // 2. Verify:
        Mockito.verify(shopsCheckoutSoldItemsRepository, Mockito.times(1)).saveAll(Mockito.anyList());
    }

    @Test
    public void deleteCurrentSoldItemsListPositiveTest(){
        // 1. Step: prepare the data
        shopsCheckoutSoldItemsService.deleteCurrentSoldItemsList();

        // 2. Verify:
        Mockito.verify(shopsCheckoutSoldItemsRepository, Mockito.times(1)).deleteAll();
    }

    @Test
    public void sendCurrentSoldItemsListPositiveTest(){
        // 1. Step: prepare the mock
        ShopsCheckoutSoldItemsDTO newItemDTO1 = new ShopsCheckoutSoldItemsDTO();
        newItemDTO1.setPosition(1L);
        newItemDTO1.setShop("shop1");
        newItemDTO1.setCategory("anillo");
        newItemDTO1.setQuantity(100L);
        newItemDTO1.setPriceSalesPerUnit((double)100);
        newItemDTO1.setPriceListPerUnit((double)95);
        newItemDTO1.setDiscountPercent((double)5);
        newItemDTO1.setDeliverySending(LocalDateTime.now());
        newItemDTO1.setRevenuePerUnit((double)95);
        newItemDTO1.setItemLastSold(LocalDateTime.now());
        newItemDTO1.setComment("NoComment");

        List<ShopsStockItem> shopsStockItemList =  new ArrayList<>();
        ShopsStockItem shopsStockItem1 = new ShopsStockItem();
        shopsStockItem1.setId(1L);
        shopsStockItem1.setShop("shop1");
        shopsStockItem1.setCategory("anillo");
        shopsStockItem1.setPriceListPerUnit(100L);
        shopsStockItem1.setPriceSalesPerUnit(95L);
        shopsStockItem1.setQuantity(1000L);
        shopsStockItemList.add(shopsStockItem1);

        // 2. Define Repo-Mocks
        Mockito.when(shopsStockItemRepository.findAllItemsByShop(Mockito.anyString())).thenReturn(shopsStockItemList);
        Mockito.when(shopsStockItemRepository.findAmountItemsByAllInfo(Mockito.anyString(), Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(1000L);
        Mockito.doNothing().when(shopsCheckoutSoldItemsRepository).deleteAll();

        // 3. Execute method
        List<ShopsCheckoutSoldItemsDTO> soldItemsListFetched = shopsCheckoutSoldItemsService.sendAllSoldItemsList(shopsCheckoutSoldItemsDTOSList);

        // 4. Assert the data is fetched correctly
        Assert.assertTrue(soldItemsListFetched.size()==0);

        Mockito.verify(shopsStockItemRepository, Mockito.times(1)).findAllItemsByShop(Mockito.anyString());
    }

}
