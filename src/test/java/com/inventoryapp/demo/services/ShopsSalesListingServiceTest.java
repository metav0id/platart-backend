package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ShopsAllSoldItemsDTO;
import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.repositories.ShopsAllSoldItemsRepository;
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
public class ShopsSalesListingServiceTest {

    @Autowired
    private ShopsAllSoldItemsRepository shopsAllSoldItemsRepository;

    public List<ShopsAllSoldItems> allItemsInput = new ArrayList<>();
    @Before
    public void setUp() {
        ShopsAllSoldItems item1 = new ShopsAllSoldItems( 1L, "category", 88L, 888L, 666L , 888L, 50, "shop", LocalDateTime.now(), LocalDateTime.now(), "comment 1");
        allItemsInput.add(item1);
        ShopsAllSoldItems item2 = new ShopsAllSoldItems( 2L, "category", 88L, 888L, 666L , 888L, 50, "shop", LocalDateTime.now(), LocalDateTime.now(), "comment 2");
        allItemsInput.add(item2);
        ShopsAllSoldItems item3 = new ShopsAllSoldItems( 3L, "category", 88L, 888L, 666L , 888L, 50, "shop", LocalDateTime.now(), LocalDateTime.now(), "comment 3");
        allItemsInput.add(item3);
        ShopsAllSoldItems item4 = new ShopsAllSoldItems( 4L, "category", 88L, 888L, 666L , 888L, 50, "shop", LocalDateTime.now(), LocalDateTime.now(), "comment 4");
        allItemsInput.add(item4);
        ShopsAllSoldItems item5 = new ShopsAllSoldItems( 5L, "category", 88L, 888L, 666L , 888L, 50, "shop", LocalDateTime.now(), LocalDateTime.now(), "comment 5");
        allItemsInput.add(item5);
        ShopsAllSoldItems item6 = new ShopsAllSoldItems( 6L, "category", 88L, 888L, 666L , 888L, 50, "shop", LocalDateTime.now(), LocalDateTime.now(), "comment 6");
        allItemsInput.add(item6);
    }

    @Test
    public void getSoldItemsListByDateRangePositiveTest() {

        // 1. Prepate data
        this.shopsAllSoldItemsRepository.saveAll(allItemsInput);

        LocalDateTime startDate = LocalDateTime.now().minusYears(10);
        LocalDateTime endDate = LocalDateTime.now();
        String shop = "shop";

        // 2. Execute Test
        System.out.println("start: "+ startDate + " = end: "+endDate);

        List<ShopsAllSoldItems> allItems = new ArrayList<>();
        List<ShopsAllSoldItemsDTO> allDTOItems = new ArrayList<>();

        try {
            allItems = this.shopsAllSoldItemsRepository.getItemsByShopAndByDate(shop, startDate, endDate);
            allDTOItems = mapEntitiesToDTOs(allItems);
        } catch (Exception e) {
            System.err.println(e);
        }

        // 3. Assert/ Verify
        for (int i = 0; i < allDTOItems.size() ; i++) {
            String current = allItemsInput.get(i).getCategory();
            String expected = allDTOItems.get(i).getCategory();

            Assert.assertEquals(current, expected);
        }
    }

    private List<ShopsAllSoldItemsDTO> mapEntitiesToDTOs(List<ShopsAllSoldItems> shopsAllSoldItems){
        List<ShopsAllSoldItemsDTO> shopsAllSoldItemsDTOList = new ArrayList<>();

        for(ShopsAllSoldItems item: shopsAllSoldItems){
            ShopsAllSoldItemsDTO itemDTO = new ShopsAllSoldItemsDTO();
            itemDTO.setId(item.getId());
            itemDTO.setCategory(item.getCategory());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPriceListPerUnit(item.getPriceListPerUnit());
            itemDTO.setPriceSalesPerUnit(item.getPriceSalesPerUnit());
            itemDTO.setRevenuePerUnit(item.getRevenuePerUnit());
            itemDTO.setDiscountPercent(item.getDiscountPercent());
            itemDTO.setShop(item.getShop());
            itemDTO.setDeliverySending(item.getDeliverySending());
            itemDTO.setItemLastSold(item.getItemLastSold());
            itemDTO.setComment(item.getComment());

            shopsAllSoldItemsDTOList.add(itemDTO);
        }

        return shopsAllSoldItemsDTOList;
    }

}
