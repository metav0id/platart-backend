package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ShopDeliveryItemFromWarehouseDTO;
import com.inventoryapp.demo.dtos.ShopSaveToStockDTO;
import com.inventoryapp.demo.entities.ShopsCheckedInProductsFromWarehouse;
import com.inventoryapp.demo.entities.ShopsStockItem;
import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import com.inventoryapp.demo.repositories.ShopsNewDeliveryFromWarehouseRepository;
import com.inventoryapp.demo.repositories.WarehouseShopDeliveryOrdersSendRepository;
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
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ShopsNewDeliveryFromWarehouseControllerServiceTest {
    @Autowired
    private WarehouseShopDeliveryOrdersSendRepository warehouseShopDeliveryOrdersSendRepository;

    @Autowired
    private ShopsNewDeliveryFromWarehouseRepository newDeliveryFromWarehouseRepository;

    private List<WarehouseSendDeliveryOrderItem> listSendItems = new ArrayList<>();

    private ConvertingValues convertingValues;


    @Before
    public void setUp() {
        WarehouseSendDeliveryOrderItem item1 = new WarehouseSendDeliveryOrderItem();
        item1.setId(10L);
        item1.setCategory("Pulsera");
        item1.setDeliverySending(LocalDateTime.now());
        item1.setDiscountPercent(10);
        item1.setPriceListPerUnit(100);
        item1.setPriceSalesPerUnit(90);
        item1.setQuantity(80);
        item1.setShop("Shop1");
        item1.setComment("Wichtig beachten bei Verkaufsaktion");
        item1.setShopsCheckedInProductsFromWarehouse(new ShopsCheckedInProductsFromWarehouse());
        this.listSendItems.add(item1);

        WarehouseSendDeliveryOrderItem item2 = new WarehouseSendDeliveryOrderItem();
        item2.setId(35L);
        item2.setCategory("Arete");
        item2.setDeliverySending(LocalDateTime.now());
        item2.setDiscountPercent(20);
        item2.setPriceListPerUnit(100);
        item2.setPriceSalesPerUnit(80);
        item2.setQuantity(120);
        item2.setShop("Shop1");
        item2.setComment("Beachten bei Verkauf!");
        item2.setShopsCheckedInProductsFromWarehouse(new ShopsCheckedInProductsFromWarehouse());
        this.listSendItems.add(item2);

        WarehouseSendDeliveryOrderItem item3 = new WarehouseSendDeliveryOrderItem();
        item3.setId(55L);
        item3.setCategory("Sabato");
        item3.setDeliverySending(LocalDateTime.now());
        item3.setDiscountPercent(5);
        item3.setPriceListPerUnit(200);
        item3.setPriceSalesPerUnit(190);
        item3.setQuantity(200);
        item3.setShop("Shop2");
        item3.setComment("Das ist total wichtig");
        item3.setShopsCheckedInProductsFromWarehouse(new ShopsCheckedInProductsFromWarehouse());
        this.listSendItems.add(item3);

        WarehouseSendDeliveryOrderItem item4 = new WarehouseSendDeliveryOrderItem();
        item4.setId(80L);
        item4.setCategory("Arete");
        item4.setDeliverySending(LocalDateTime.now());
        item4.setDiscountPercent(5);
        item4.setPriceListPerUnit(200);
        item4.setPriceSalesPerUnit(190);
        item4.setQuantity(200);
        item4.setShop("Shop2");
        item4.setShopsCheckedInProductsFromWarehouse(new ShopsCheckedInProductsFromWarehouse());
        item4.getShopsCheckedInProductsFromWarehouse().setIsAddedToStockOfShop(true);
        this.listSendItems.add(item4);
        convertingValues = new ConvertingValues();
    }

    @Test
    public void getAllItemsNotInShopInventory() {

        // 1. step: prepare data
        warehouseShopDeliveryOrdersSendRepository.saveAll(this.listSendItems);
        Assert.assertEquals(4, warehouseShopDeliveryOrdersSendRepository.findAll().size());

        // 2. step: execute, find all items that are not in stock yet for particular Shop
        List<WarehouseSendDeliveryOrderItem> listItemsNotInShopStock1 = warehouseShopDeliveryOrdersSendRepository.
                findAllItemsNotAddedToShopInventory("Shop1");

        List<WarehouseSendDeliveryOrderItem> listItemsNotInShopStock2 = warehouseShopDeliveryOrdersSendRepository.
                findAllItemsNotAddedToShopInventory("Shop2");

        List<WarehouseSendDeliveryOrderItem> listItemsNotInShopStock3 = warehouseShopDeliveryOrdersSendRepository.
                findAllItemsNotAddedToShopInventory("Shop3");

        List<ShopDeliveryItemFromWarehouseDTO> listDTO1 = convertEntityToDTO(listItemsNotInShopStock1);
        List<ShopDeliveryItemFromWarehouseDTO> listDTO3 = convertEntityToDTO(listItemsNotInShopStock3);

        // 3. step: test
        Assert.assertEquals(2, listItemsNotInShopStock1.size());
        Assert.assertEquals("Wichtig beachten bei Verkaufsaktion", listItemsNotInShopStock1.get(0).getComment());
        Assert.assertEquals(1, listItemsNotInShopStock2.size());
        Assert.assertEquals(0, listItemsNotInShopStock3.size());
        Assert.assertEquals(2, listDTO1.size());
        Assert.assertEquals("Arete", listDTO1.get(1).getCategory());
        Assert.assertEquals("Beachten bei Verkauf!", listDTO1.get(1).getComment());
        Assert.assertEquals(0, listDTO3.size());
    }

    private List<ShopDeliveryItemFromWarehouseDTO> convertEntityToDTO(List<WarehouseSendDeliveryOrderItem> listEntity) {
        List<ShopDeliveryItemFromWarehouseDTO> listDTO = new ArrayList<>();
        listEntity.stream().forEach(item -> {
            ShopDeliveryItemFromWarehouseDTO itemDTO = new ShopDeliveryItemFromWarehouseDTO(
                    item.getId(),
                    item.getCategory(),
                    convertingValues.convertLongToDoubleForEntityToDTO(item.getPriceListPerUnit()),
                    convertingValues.convertLongToDoubleForEntityToDTO(item.getPriceSalesPerUnit()),
                    item.getQuantity(),
                    item.getDeliverySending(),
                    item.getComment());
            listDTO.add(itemDTO);
        });
        return listDTO;
    }

    @Test
    public void changeStatusAfterSaving() {
        //Test Data Preparation

        //Delivery table in database
        List<WarehouseSendDeliveryOrderItem> listDatabase = warehouseShopDeliveryOrdersSendRepository.saveAll(this.listSendItems);
        List<WarehouseSendDeliveryOrderItem> listBeforeSaving = warehouseShopDeliveryOrdersSendRepository.findAllItemsNotAddedToShopInventory("Shop1");
        System.out.println("List Database:");
        System.out.println(listDatabase);
        //DTO List
        List<ShopSaveToStockDTO> listDTO = new ArrayList<>();
        listDTO.add(new ShopSaveToStockDTO(listDatabase.get(0).getId(), "Shop1", "Pulsera",
                70, 100.0, 90.0, 80,
                "6.5.2020", "Da fehlt was"));
        listDTO.add(new ShopSaveToStockDTO(listDatabase.get(1).getId(), "Shop1", "Arete",
                120, 100.0, 80.0, 120,
                "8.5.2020", "Alles gut"));
        //Item which was added manually as delivery
        listDTO.add(new ShopSaveToStockDTO(-1, "shop1", "Pulsera",
                50, 120.0, 80.0, 90,
                "7.5.2020", "Kam ausversehen am Shop an"));


        //TEST Logic: items from listDTO are updated by id in deliveryList in database.
        for (ShopSaveToStockDTO itemDTO : listDTO) {
            System.out.println("Print Entry in Database by id");
            Optional<WarehouseSendDeliveryOrderItem> item = warehouseShopDeliveryOrdersSendRepository.
                    findById(itemDTO.getIdentifierOnDeliveryList());
            if (item.isPresent()) {
                WarehouseSendDeliveryOrderItem itemEntity = item.get();
                itemEntity.getShopsCheckedInProductsFromWarehouse().setIsArrivedAtShop(true);
                itemEntity.getShopsCheckedInProductsFromWarehouse().setIsAddedToStockOfShop(true);
                itemEntity.getShopsCheckedInProductsFromWarehouse().setTimestampIsAddedToStockOfShop(LocalDateTime.now());
                itemEntity.getShopsCheckedInProductsFromWarehouse().setQuantityCheckedIn(itemDTO.getQuantity());
                itemEntity.getShopsCheckedInProductsFromWarehouse().setComment(itemDTO.getComment());
                warehouseShopDeliveryOrdersSendRepository.save(itemEntity);
            }
        }

        //TEST result
        List<WarehouseSendDeliveryOrderItem> listAfterSaving = warehouseShopDeliveryOrdersSendRepository.findAllItemsNotAddedToShopInventory("Shop1");
        Assert.assertEquals(2, listBeforeSaving.size());
        Assert.assertEquals(0, listAfterSaving.size());

    }

    @Test
    public void saveListTest() {
        //Test data preparation
        //Database Stock
        List<ShopsStockItem> database = new ArrayList<>();
        database.add(new ShopsStockItem("Shop1", "Pulsera",
                50L, 100L, 90L));
        database.add(new ShopsStockItem("Shop1", "Arete",
                10L, 100L, 80L));
        database.add(new ShopsStockItem("Shop1", "Arete",
                20L, 80L, 60L));
        database.add(new ShopsStockItem("Shop1", "Sabato",
                50L, 100L, 90L));
        newDeliveryFromWarehouseRepository.saveAll(database);
        //List from frontend
        List<ShopSaveToStockDTO> listDTO = new ArrayList<>();
        //Existing entry --> update with new quantity
        listDTO.add(new ShopSaveToStockDTO(1, "Shop1", "Pulsera",
                70, 100.0, 90.0, 80,
                "6.5.2020", "Da fehlt was"));
        //Existing entry --> update with new quantity
        listDTO.add(new ShopSaveToStockDTO(2, "Shop1", "Arete",
                120, 100.0, 80.0, 120,
                "8.5.2020", "Alles gut"));
        //Item which was added manually as delivery --> new entry in database
        listDTO.add(new ShopSaveToStockDTO(-1, "Shop1", "Pulsera",
                50, 120.0, 80.0, 90,
                "7.5.2020", "Kam ausversehen am Shop an"));
        //Not in database --> new entry in database
        listDTO.add(new ShopSaveToStockDTO(4, "Shop1", "Arete",
                35, 100.0, 70.0, 35,
                "8.5.2020", "Top"));

        // Build logic
        List<ShopsStockItem> listEntity = new ArrayList<>();
        for (ShopSaveToStockDTO itemDTO : listDTO){
            listEntity.add(convertDTOtoEntity(itemDTO));
        }

        for (ShopsStockItem itemEntity : listEntity){
            ShopsStockItem itemDatabase = newDeliveryFromWarehouseRepository.getShopsStockItemsBySelectors(itemEntity.getShop(),
                    itemEntity.getCategory(), itemEntity.getPriceListPerUnit(), itemEntity.getPriceSalesPerUnit());
            if (itemDatabase != null){
                itemDatabase.setQuantity(itemDatabase.getQuantity()+itemEntity.getQuantity());
                newDeliveryFromWarehouseRepository.save(itemDatabase);
            } else {
                newDeliveryFromWarehouseRepository.save(itemEntity);
            }
        }

        //TEST
        Assert.assertEquals(6, newDeliveryFromWarehouseRepository.findAll().size());
        ShopsStockItem itemPulseraExisting = newDeliveryFromWarehouseRepository.getShopsStockItemsBySelectors("Shop1", "Pulsera", 100, 90);
        long quantityPulseraExisting = itemPulseraExisting.getQuantity();
        Assert.assertEquals(120, quantityPulseraExisting);
        ShopsStockItem itemAreteExisting = newDeliveryFromWarehouseRepository.getShopsStockItemsBySelectors("Shop1", "Arete", 100, 80);
        long quantityAreteExisting = itemAreteExisting.getQuantity();
        Assert.assertEquals(130, quantityAreteExisting);

        ShopsStockItem itemPulseraNew = newDeliveryFromWarehouseRepository.getShopsStockItemsBySelectors("Shop1", "Pulsera", 120, 80);
        Assert.assertNotNull(itemPulseraNew);

        ShopsStockItem itemAreteNew = newDeliveryFromWarehouseRepository.getShopsStockItemsBySelectors("Shop1", "Arete", 100, 70);
        Assert.assertNotNull(itemAreteNew);
    }

    private ShopsStockItem convertDTOtoEntity(ShopSaveToStockDTO itemDto) {
        return new ShopsStockItem(itemDto.getShop(), itemDto.getCategory(),
                itemDto.getQuantity(), convertingValues.convertDoubleToLongForDTOtoEntity(itemDto.getPriceListPerUnit()),
                convertingValues.convertDoubleToLongForDTOtoEntity(itemDto.getPriceSalesPerUnit()));
    }
}
