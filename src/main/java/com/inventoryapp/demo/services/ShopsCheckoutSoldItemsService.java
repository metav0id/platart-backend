package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ShopsCheckoutSoldItemsDTO;
import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.entities.ShopsCheckoutSoldItems;
import com.inventoryapp.demo.entities.ShopsStockItem;
import com.inventoryapp.demo.repositories.ShopsAllSoldItemsRepository;
import com.inventoryapp.demo.repositories.ShopsCheckoutSoldItemsRepository;
import com.inventoryapp.demo.repositories.ShopsStockItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopsCheckoutSoldItemsService {

    private ShopsCheckoutSoldItemsRepository shopsCheckoutSoldItemsRepository;
    private ShopsAllSoldItemsRepository shopsAllSoldItemsRepository;
    private ShopsStockItemRepository shopsStockItemRepository;
    private ConvertingValues convertingValues;

    @Autowired
    public ShopsCheckoutSoldItemsService(
            ShopsCheckoutSoldItemsRepository shopsCheckoutSoldItemsRepository,
            ShopsAllSoldItemsRepository shopsAllSoldItemsRepository,
            ShopsStockItemRepository shopsStockItemRepository,
            ConvertingValues convertingValues
            ) {
        this.shopsCheckoutSoldItemsRepository = shopsCheckoutSoldItemsRepository;
        this.shopsAllSoldItemsRepository = shopsAllSoldItemsRepository;
        this.shopsStockItemRepository = shopsStockItemRepository;
        this.convertingValues = convertingValues;
    }

    public void saveAllSoldItemsList(List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList) {
        // first clear the repository
        this.shopsCheckoutSoldItemsRepository.deleteAll();

        // persist the new (current) items
        List<ShopsCheckoutSoldItems> shopsCheckoutSoldItemsEntitiesList = mapDTOListToEntityList(shopsCheckoutSoldItemsDTOList);
        System.out.println(shopsCheckoutSoldItemsEntitiesList);
        this.shopsCheckoutSoldItemsRepository.saveAll(shopsCheckoutSoldItemsEntitiesList);
    }

    public List<ShopsCheckoutSoldItemsDTO> sendAllSoldItemsList(List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList) {
        System.out.println("ShopsCheckoutSoldItemsService -> sendAllSoldItemsList()");

        // 1. Boolean for verification-Logic, if items are available on the shop inventory
        boolean allSoldItemsAvailable = true ;

        // 2. aggregate items by category
        List<ShopsCheckoutSoldItemsDTO> soldItemsAggregatedDTOList =  new ArrayList<>();
        for(ShopsCheckoutSoldItemsDTO itemDTO: shopsCheckoutSoldItemsDTOList){

            // 2.1 aggregate quantity with streams
            soldItemsAggregatedDTOList.stream()
                    .filter(
                        o ->
                        itemDTO.getCategory().equals(o.getCategory()) &&
                        itemDTO.getPriceListPerUnit()==o.getPriceListPerUnit() &&
                        itemDTO.getPriceSalesPerUnit()==o.getPriceSalesPerUnit() )
                    .forEach(
                        o -> o.setQuantity(o.getQuantity() + itemDTO.getQuantity())
                    );

            // 2.2 check if category and List-price exist
            boolean itemIsNotFound = soldItemsAggregatedDTOList
                        .stream()
                        .noneMatch(
                                o ->
                                o.getCategory().equals(itemDTO.getCategory()) &&
                                o.getPriceListPerUnit() == itemDTO.getPriceListPerUnit() &&
                                o.getPriceSalesPerUnit() == itemDTO.getPriceSalesPerUnit()
                        );

            // 2.3 add new item category and list-price, if not already existant
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
            Long amountItemsShopWarehouse =
                    shopsStockItemRepository.findAmountItemsByAllInfo(itemSold.getShop(), itemSold.getCategory(),
                            convertingValues.convertDoubleToLongForDTOtoEntity(itemSold.getPriceListPerUnit()),
                            convertingValues.convertDoubleToLongForDTOtoEntity(itemSold.getPriceSalesPerUnit()));

            System.out.println("amount wanted: "+ itemSold.getQuantity() + " amount on warehouse: " + amountItemsShopWarehouse);
            if(amountItemsShopWarehouse != null && (amountItemsShopWarehouse -itemSold.getQuantity() >= 0 )){
                System.out.println("transaction possible!");
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
                // 3.2.1 save all sold items and reduce amount from ShopsStockItem table
                List<ShopsAllSoldItems> shopsAllSoldItemsList = mapCheckoutDTOListToSoldItemsList(shopsCheckoutSoldItemsDTOList);
                for(ShopsAllSoldItems shopItem: shopsAllSoldItemsList){
                    Long amountOnShopsStockItem = this.shopsStockItemRepository.findAmountItemsByAllInfo(
                                                                            shopItem.getShop(),
                                                                            shopItem.getCategory(),
                                                                            shopItem.getPriceListPerUnit(),
                                                                            shopItem.getPriceSalesPerUnit() );

                    Long newAmountOnShopsStockItem = amountOnShopsStockItem-shopItem.getQuantity();
                    System.out.println("REDUCE AMOUNT ! -> "+ newAmountOnShopsStockItem);

                    this.shopsStockItemRepository.updateAmountByAllInfo(
                            newAmountOnShopsStockItem,
                            shopItem.getShop(),
                            shopItem.getCategory(),
                            shopItem.getPriceListPerUnit(),
                            shopItem.getPriceSalesPerUnit() );
                }

                this.shopsAllSoldItemsRepository.saveAll(shopsAllSoldItemsList);

                // 3.2.2 Delete items from repository
                this.shopsCheckoutSoldItemsRepository.deleteAll();

                // 3.2.1 Delete current order list before returning empty list
                List<ShopsCheckoutSoldItemsDTO> returnList = new ArrayList<>();
                this.deleteCurrentSoldItemsList();
                return returnList;
            }
        } catch (Exception e){
            System.err.println("ShopsCheckoutSoldItemsService -> sendAllSoldItemsList -> persistance error.");
        }

        // 4. Save new List before returning it
        this.saveAllSoldItemsList(shopsCheckoutSoldItemsDTOList);
        return shopsCheckoutSoldItemsDTOList;
    }



    public List<ShopsCheckoutSoldItemsDTO> getAllSoldItemsList(){
        List<ShopsCheckoutSoldItems> shopsCheckoutSoldItemsList = this.shopsCheckoutSoldItemsRepository.findAll();

        List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList = mapEntityListToDTOList(shopsCheckoutSoldItemsList);

        return shopsCheckoutSoldItemsDTOList;
    }

    public void deleteCurrentSoldItemsList() {
        this.shopsCheckoutSoldItemsRepository.deleteAll();
    }

    // Mapping functions
    public List<ShopsAllSoldItems> mapCheckoutDTOListToSoldItemsList(List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList ){
        List<ShopsAllSoldItems> shopsAllSoldItemsList = new ArrayList<>();

        for(ShopsCheckoutSoldItemsDTO itemDTO:shopsCheckoutSoldItemsDTOList){
            ShopsAllSoldItems newSoldItem = new ShopsAllSoldItems();

            newSoldItem.setId(itemDTO.getPosition());
            newSoldItem.setCategory(itemDTO.getCategory());
            newSoldItem.setQuantity(itemDTO.getQuantity());
            newSoldItem.setPriceListPerUnit(convertingValues.convertDoubleToLongForDTOtoEntity(itemDTO.getPriceListPerUnit()));
            newSoldItem.setPriceSalesPerUnit(convertingValues.convertDoubleToLongForDTOtoEntity(itemDTO.getPriceSalesPerUnit()));
            newSoldItem.setRevenuePerUnit(convertingValues.convertDoubleToLongForDTOtoEntity(itemDTO.getRevenuePerUnit()));
            newSoldItem.setDiscountPercent(convertingValues.convertDoubleToIntForDTOtoEntity(itemDTO.getDiscountPercent()));
            newSoldItem.setShop(itemDTO.getShop());
            newSoldItem.setDeliverySending(itemDTO.getDeliverySending());
            newSoldItem.setItemLastSold(itemDTO.getItemLastSold());
            newSoldItem.setComment(itemDTO.getComment());

            shopsAllSoldItemsList.add(newSoldItem);
        }

        return shopsAllSoldItemsList;
    }

    public List<ShopsCheckoutSoldItems> mapDTOListToEntityList(List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList){
        List<ShopsCheckoutSoldItems> shopsCheckoutSoldItemsList = new ArrayList<>();

        for(ShopsCheckoutSoldItemsDTO itemDTO: shopsCheckoutSoldItemsDTOList){
            ShopsCheckoutSoldItems newEntitity = new ShopsCheckoutSoldItems();
            newEntitity.setId(itemDTO.getPosition());
            newEntitity.setCategory(itemDTO.getCategory());
            newEntitity.setQuantity(itemDTO.getQuantity());
            newEntitity.setPriceListPerUnit(convertingValues.convertDoubleToLongForDTOtoEntity(itemDTO.getPriceListPerUnit()));
            newEntitity.setPriceSalesPerUnit(convertingValues.convertDoubleToLongForDTOtoEntity(itemDTO.getPriceSalesPerUnit()));
            newEntitity.setRevenuePerUnit(convertingValues.convertDoubleToLongForDTOtoEntity(itemDTO.getRevenuePerUnit()));
            newEntitity.setDiscountPercent(convertingValues.convertDoubleToIntForDTOtoEntity(itemDTO.getDiscountPercent()));
            newEntitity.setShop(itemDTO.getShop());
            newEntitity.setDeliverySending(itemDTO.getDeliverySending());
            newEntitity.setItemLastSold(itemDTO.getItemLastSold());
            newEntitity.setComment(itemDTO.getComment());

            shopsCheckoutSoldItemsList.add(newEntitity);
        }

        return shopsCheckoutSoldItemsList;
    }

    public List<ShopsCheckoutSoldItemsDTO> mapEntityListToDTOList(List<ShopsCheckoutSoldItems> shopsCheckoutSoldItemsEntityList){
        List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsList = new ArrayList<>();

        for(ShopsCheckoutSoldItems itemDTO: shopsCheckoutSoldItemsEntityList){
            ShopsCheckoutSoldItemsDTO newDTO = new ShopsCheckoutSoldItemsDTO();
            newDTO.setPosition(itemDTO.getId());
            newDTO.setCategory(itemDTO.getCategory());
            newDTO.setQuantity(itemDTO.getQuantity());
            newDTO.setPriceListPerUnit(convertingValues.convertLongToDoubleForEntityToDTO(itemDTO.getPriceListPerUnit()));
            newDTO.setPriceSalesPerUnit(convertingValues.convertLongToDoubleForEntityToDTO(itemDTO.getPriceSalesPerUnit()));
            newDTO.setRevenuePerUnit(convertingValues.convertLongToDoubleForEntityToDTO(itemDTO.getRevenuePerUnit()));
            newDTO.setDiscountPercent(convertingValues.convertLongToDoubleForEntityToDTO(itemDTO.getDiscountPercent()));
            newDTO.setShop(itemDTO.getShop());
            newDTO.setDeliverySending(itemDTO.getDeliverySending());
            newDTO.setItemLastSold(itemDTO.getItemLastSold());
            newDTO.setComment(itemDTO.getComment());

            shopsCheckoutSoldItemsList.add(newDTO);
        }
        return shopsCheckoutSoldItemsList;
    }
}
