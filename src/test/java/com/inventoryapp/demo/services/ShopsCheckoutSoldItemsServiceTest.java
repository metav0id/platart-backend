package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ShopsCheckoutSoldItemsDTO;
import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.entities.ShopsCheckoutSoldItems;
import com.inventoryapp.demo.entities.ShopsStockItem;
import com.inventoryapp.demo.repositories.ShopsAllSoldItemsRepository;
import com.inventoryapp.demo.repositories.ShopsCheckoutSoldItemsRepository;
import com.inventoryapp.demo.repositories.ShopsStockItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ShopsCheckoutSoldItemsServiceTest {

    @Autowired
    private ShopsCheckoutSoldItemsRepository shopsCheckoutSoldItemsRepository;

    @Autowired
    private ShopsAllSoldItemsRepository shopsAllSoldItemsRepository;

    @Autowired
    private ShopsStockItemRepository shopsStockItemRepository;

    public ShopsCheckoutSoldItemsServiceTest() {
    }

    // define necessary data
    List<ShopsCheckoutSoldItems> shopsCheckoutSoldItemsList =  new ArrayList<>();
    List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList =  new ArrayList<>();
    List<ShopsStockItem> shopsStockItemList = new ArrayList<>();
    @Before
    public void setUp(){
        // 1. Add Items to shopsCheckoutSoldItemsList
        ShopsCheckoutSoldItems item1 = new ShopsCheckoutSoldItems();
        item1.setId(1L);
        item1.setCategory("anillo");
        item1.setQuantity(10L);
        item1.setPriceListPerUnit(100L);
        item1.setPriceSalesPerUnit(95L);
        item1.setDiscountPercent(5);
        item1.setDeliverySending("2016-11-09T11:44:44.797");
        item1.setRevenuePerUnit(95L);
        item1.setItemLastSold("2016-11-09T11:44:44.797");
        item1.setComment("Comment1");
        shopsCheckoutSoldItemsList.add(item1);

        ShopsCheckoutSoldItems item2 = new ShopsCheckoutSoldItems();
        item2.setId(2L);
        item2.setCategory("pulsera");
        item2.setQuantity(10L);
        item2.setPriceListPerUnit(100L);
        item2.setPriceSalesPerUnit(95L);
        item2.setDiscountPercent(5);
        item2.setDeliverySending("2016-11-09T11:44:44.797");
        item2.setRevenuePerUnit(95L);
        item2.setItemLastSold("2016-11-09T11:44:44.797");
        item2.setComment("Comment2");
        shopsCheckoutSoldItemsList.add(item2);

        // 2. Add Items to shopsStockItemList
        ShopsStockItem itemStock1 =  new ShopsStockItem();
        itemStock1.setId(1L);
        itemStock1.setShop("shop1");
        itemStock1.setCategory("anillo");
        itemStock1.setQuantity(1000L);
        itemStock1.setPriceListPerUnit(100L);
        itemStock1.setPriceSalesPerUnit(95L);
        shopsStockItemList.add(itemStock1);

        ShopsStockItem itemStock2 =  new ShopsStockItem();
        itemStock2.setId(2L);
        itemStock2.setShop("shop1");
        itemStock2.setCategory("pulsera");
        itemStock2.setQuantity(1000L);
        itemStock2.setPriceListPerUnit(100L);
        itemStock2.setPriceSalesPerUnit(95L);
        shopsStockItemList.add(itemStock2);

        // 3. Add Items to
        ShopsCheckoutSoldItemsDTO itemDTO1 = new ShopsCheckoutSoldItemsDTO();
        itemDTO1.setPosition(1L);
        itemDTO1.setCategory("anillo");
        itemDTO1.setQuantity(10L);
        itemDTO1.setPriceListPerUnit(100L);
        itemDTO1.setPriceSalesPerUnit(95L);
        itemDTO1.setRevenuePerUnit(95L);
        itemDTO1.setDiscountPercent(5);
        itemDTO1.setShop("shop1");
        itemDTO1.setComment("Comment1");
        shopsCheckoutSoldItemsDTOList.add(itemDTO1);

        ShopsCheckoutSoldItemsDTO itemDTO2 = new ShopsCheckoutSoldItemsDTO();
        itemDTO2.setPosition(2L);
        itemDTO2.setCategory("pulsera");
        itemDTO2.setQuantity(10L);
        itemDTO2.setPriceListPerUnit(100L);
        itemDTO2.setPriceSalesPerUnit(95L);
        itemDTO2.setRevenuePerUnit(95L);
        itemDTO2.setDiscountPercent(5);
        itemDTO2.setShop("shop1");
        itemDTO2.setComment("Comment1");
        shopsCheckoutSoldItemsDTOList.add(itemDTO2);

    }

    @Test
    public void getAllSoldItemsListPositiveTest(){
        // 1. Step: prepare data
        shopsCheckoutSoldItemsRepository.saveAll(shopsCheckoutSoldItemsList);
        Assert.assertEquals(2, shopsCheckoutSoldItemsRepository.findAll().size());

        // 2. Step: Verify fetched data is equal to the saved data
        List<ShopsCheckoutSoldItems> shopsCheckoutSoldItemsListFetched = shopsCheckoutSoldItemsRepository.findAll();

        // 3. Step: Assert Test works correctly
        for(int i=0; i<shopsCheckoutSoldItemsListFetched.size(); i++){
            String savedItem = shopsCheckoutSoldItemsList.get(i).toString();
            String fetchedItem = shopsCheckoutSoldItemsListFetched.get(i).toString();

            Assert.assertEquals(savedItem, fetchedItem);
        }
    }

    @Test
    public void deleteCurrentSoldItemsListPositiveTest(){
        // 1. Step: prepare data
        shopsCheckoutSoldItemsRepository.saveAll(shopsCheckoutSoldItemsList);
        Assert.assertEquals(2, shopsCheckoutSoldItemsRepository.findAll().size());

        // 2. Step: Delete repository items
        shopsCheckoutSoldItemsRepository.deleteAll();

        // 3. Step: Assert Test works correctly
        List<ShopsCheckoutSoldItems> shopsCheckoutSoldItemsListFetched = shopsCheckoutSoldItemsRepository.findAll();
        Assert.assertEquals(0, shopsCheckoutSoldItemsListFetched.size());
    }

    @Test
    public void sendAllSoldItemsListPositiveTest(){
        // 1. Step: prepare data

        shopsCheckoutSoldItemsRepository.saveAll(shopsCheckoutSoldItemsList);
        Assert.assertEquals(shopsCheckoutSoldItemsList.size(), shopsCheckoutSoldItemsRepository.findAll().size());
        shopsStockItemRepository.saveAll(shopsStockItemList);
        Assert.assertEquals(shopsCheckoutSoldItemsList.size(), shopsCheckoutSoldItemsRepository.findAll().size());

        // 2. Step: Execute method-logic

        // 2.1: Boolean for verification-Logic,
        boolean allSoldItemsAvailable = true ;

        // 2.2: aggregate items by category
        List<ShopsCheckoutSoldItemsDTO> soldItemsAggregatedDTOList =  new ArrayList<>();
        for(ShopsCheckoutSoldItemsDTO itemDTO: shopsCheckoutSoldItemsDTOList){

            // 2.2.1 aggregate quantity with streams
            soldItemsAggregatedDTOList.stream()
                    .filter(
                            o ->
                                    itemDTO.getCategory().equals(o.getCategory()) &&
                                            itemDTO.getPriceListPerUnit()==o.getPriceListPerUnit() &&
                                            itemDTO.getPriceSalesPerUnit()==o.getPriceSalesPerUnit() )
                    .forEach(
                            o -> o.setQuantity(o.getQuantity() + itemDTO.getQuantity())
                    );

            // 2.2.2 check if category and List-price exist
            boolean itemIsNotFound = soldItemsAggregatedDTOList
                    .stream()
                    .noneMatch(
                            o ->
                                    o.getCategory().equals(itemDTO.getCategory()) &&
                                            o.getPriceListPerUnit() == itemDTO.getPriceListPerUnit() &&
                                            o.getPriceSalesPerUnit() == itemDTO.getPriceSalesPerUnit()
                    );

            // 2.2.3 add new item category and list-price, if not already existant
            if(itemIsNotFound){
                ShopsCheckoutSoldItemsDTO newItemToAggregatedList = new ShopsCheckoutSoldItemsDTO();
                newItemToAggregatedList.setPosition(itemDTO.getPosition());
                newItemToAggregatedList.setCategory(itemDTO.getCategory());
                newItemToAggregatedList.setQuantity(itemDTO.getQuantity());
                newItemToAggregatedList.setPriceListPerUnit(itemDTO.getPriceListPerUnit());
                newItemToAggregatedList.setPriceSalesPerUnit(itemDTO.getPriceSalesPerUnit());
                newItemToAggregatedList.setRevenuePerUnit(itemDTO.getRevenuePerUnit());
                newItemToAggregatedList.setDiscountPercent(itemDTO.getDiscountPercent());
                newItemToAggregatedList.setShop(itemDTO.getShop());
                newItemToAggregatedList.setDeliverySending(itemDTO.getDeliverySending());
                newItemToAggregatedList.setItemLastSold(itemDTO.getItemLastSold());
                newItemToAggregatedList.setComment(itemDTO.getComment());

                soldItemsAggregatedDTOList.add(newItemToAggregatedList);
            }
        }

        // 3. verify if transaction is feasible for all items in sold-item-list

        // 3.1 for comparison fetch shop-related items from shop warehouse list
        String shopRelevant = shopsCheckoutSoldItemsDTOList.get(0).getShop();
        List<ShopsStockItem> shopsStockItemList = this.shopsStockItemRepository.findAllItemsByShop(shopRelevant);

        for(ShopsCheckoutSoldItemsDTO itemSold: soldItemsAggregatedDTOList){
            Long amountItemsShopWarehouse = shopsStockItemRepository.findAmountItemsByAllInfo(itemSold.getShop(), itemSold.getCategory(), itemSold.getPriceListPerUnit(), itemSold.getPriceSalesPerUnit());

            System.out.println("amount wanted: "+ itemSold.getQuantity() + " amount on warehouse: " + amountItemsShopWarehouse);
            if(amountItemsShopWarehouse != null && (amountItemsShopWarehouse -itemSold.getQuantity() >= 0 )){
                System.out.println("transaction possible!");
                Assert.assertTrue(amountItemsShopWarehouse -itemSold.getQuantity() >= 0 );

            } else if (amountItemsShopWarehouse == null || amountItemsShopWarehouse <= 0 ) {
                System.out.println("transaction NOT possible!");
                allSoldItemsAvailable = false;
            } else {
                allSoldItemsAvailable = false;
                System.out.println("transaction NOT possible!");
            }

        }

        // 3.2 Persist data, if List available on database
        try {
            if(allSoldItemsAvailable){
                List<ShopsAllSoldItems> shopsAllSoldItemsList = mapCheckoutDTOListToSoldItemsList(shopsCheckoutSoldItemsDTOList);
                this.shopsAllSoldItemsRepository.saveAll(shopsAllSoldItemsList);
                this.shopsCheckoutSoldItemsRepository.deleteAll();
            }
        } catch (Exception e){
            System.err.println("ShopsCheckoutSoldItemsService -> sendAllSoldItemsList -> persistance error.");
        }


        if(allSoldItemsAvailable){
            List<ShopsAllSoldItems> shopsAllSoldItemsListFetched = shopsAllSoldItemsRepository.findAll();
            Assert.assertTrue(shopsAllSoldItemsListFetched.size()>0);
        }

    }

    // Mapping functions
    public List<ShopsAllSoldItems> mapCheckoutDTOListToSoldItemsList(List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList ){
        List<ShopsAllSoldItems> shopsAllSoldItemsList = new ArrayList<>();

        for(ShopsCheckoutSoldItemsDTO itemDTO:shopsCheckoutSoldItemsDTOList){
            ShopsAllSoldItems newSoldItem = new ShopsAllSoldItems();

            newSoldItem.setId(itemDTO.getPosition());
            newSoldItem.setCategory(itemDTO.getCategory());
            newSoldItem.setQuantity(itemDTO.getQuantity());
            newSoldItem.setPriceListPerUnit(itemDTO.getPriceListPerUnit());
            newSoldItem.setPriceSalesPerUnit(itemDTO.getPriceSalesPerUnit());
            newSoldItem.setRevenuePerUnit(itemDTO.getRevenuePerUnit());
            newSoldItem.setDiscountPercent(itemDTO.getDiscountPercent());
            newSoldItem.setShop(itemDTO.getShop());
            newSoldItem.setDeliverySending(itemDTO.getDeliverySending());
            newSoldItem.setItemLastSold(itemDTO.getItemLastSold());
            newSoldItem.setComment(itemDTO.getComment());

            shopsAllSoldItemsList.add(newSoldItem);
        }

        return shopsAllSoldItemsList;
    }
}
