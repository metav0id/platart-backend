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

    @Autowired
    public ShopsCheckoutSoldItemsService(
            ShopsCheckoutSoldItemsRepository shopsCheckoutSoldItemsRepository,
            ShopsAllSoldItemsRepository shopsAllSoldItemsRepository,
            ShopsStockItemRepository shopsStockItemRepository
            ) {
        this.shopsCheckoutSoldItemsRepository = shopsCheckoutSoldItemsRepository;
        this.shopsAllSoldItemsRepository = shopsAllSoldItemsRepository;
        this.shopsStockItemRepository = shopsStockItemRepository;
    }

    public void saveAllSoldItemsList(List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList) {
        // first clear the repository
        this.shopsCheckoutSoldItemsRepository.deleteAll();

        // persist the new (current) items
        List<ShopsCheckoutSoldItems> shopsCheckoutSoldItemsEntitiesList = mapDTOListToEntityList(shopsCheckoutSoldItemsDTOList);
        System.out.println(shopsCheckoutSoldItemsEntitiesList);
        this.shopsCheckoutSoldItemsRepository.saveAll(shopsCheckoutSoldItemsEntitiesList);
    }

    public void sendAllSoldItemsList(List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList) {
        System.out.println("List of stock loaded in Service Backend.");

        List<ShopsCheckoutSoldItems> shopsCheckoutSoldItemsEntitiesList = mapDTOListToEntityList(shopsCheckoutSoldItemsDTOList);
        System.out.println(shopsCheckoutSoldItemsEntitiesList);

        boolean allSoldItemsAvailable = true ;
        // TODO: Logic for verification, if items are available on the

        // aggregate items by category
        List<ShopsCheckoutSoldItemsDTO> soldItemsAggregatedDTOList =  new ArrayList<>();
        for(ShopsCheckoutSoldItemsDTO itemDTO: shopsCheckoutSoldItemsDTOList){

            // aggregate quantity with streams
            soldItemsAggregatedDTOList.stream()
                    .filter(
                        o ->
                        itemDTO.getCategory().equals(o.getCategory()) &&
                        itemDTO.getPriceListPerUnit()==o.getPriceListPerUnit() &&
                        itemDTO.getPriceSalesPerUnit()==o.getPriceSalesPerUnit() )
                    .forEach(
                        o -> o.setQuantity(o.getQuantity() + itemDTO.getQuantity())
                    );

            // check if category and List-price exist
            boolean itemIsNotFound = soldItemsAggregatedDTOList
                        .stream()
                        .noneMatch(
                                o ->
                                o.getCategory().equals(itemDTO.getCategory()) &&
                                o.getPriceListPerUnit() == itemDTO.getPriceListPerUnit() &&
                                o.getPriceSalesPerUnit() == itemDTO.getPriceSalesPerUnit()
                        );

            // add new item category and list-price, if not already existant
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

        // verify if transaction is feasible for all items in sold-item-list

        // for comparison fetch shop-related items from shop warehouse list
        String shopRelevant = shopsCheckoutSoldItemsDTOList.get(0).getShop();
        List<ShopsStockItem> shopsStockItemList = this.shopsStockItemRepository.findAllItemsByShop(shopRelevant);

        for(ShopsCheckoutSoldItemsDTO itemSold: soldItemsAggregatedDTOList){

            // todo finish comparison
            /*boolean itemAvailable = shopsStockItemList.stream()
                            .*/

        }


        // Persist data, if List available on database
        if(allSoldItemsAvailable){
            List<ShopsAllSoldItems> shopsAllSoldItemsList = mapCheckoutDTOListToSoldItemsList(shopsCheckoutSoldItemsDTOList);
            this.shopsAllSoldItemsRepository.saveAll(shopsAllSoldItemsList);
            this.shopsCheckoutSoldItemsRepository.deleteAll();
        }
    }

    //
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

    public List<ShopsCheckoutSoldItemsDTO> getAllSoldItemsList(){
        List<ShopsCheckoutSoldItems> shopsCheckoutSoldItemsList = this.shopsCheckoutSoldItemsRepository.findAll();

        List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList = mapEntityListToDTOList(shopsCheckoutSoldItemsList);

        return shopsCheckoutSoldItemsDTOList;
    }

    public void deleteCurrentSoldItemsList() {
        this.shopsCheckoutSoldItemsRepository.deleteAll();
    }

    public List<ShopsCheckoutSoldItems> mapDTOListToEntityList(List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList){
        List<ShopsCheckoutSoldItems> shopsCheckoutSoldItemsList = new ArrayList<>();

        for(ShopsCheckoutSoldItemsDTO itemDTO: shopsCheckoutSoldItemsDTOList){
            ShopsCheckoutSoldItems newEntitity = new ShopsCheckoutSoldItems();
            newEntitity.setId(itemDTO.getPosition());
            newEntitity.setCategory(itemDTO.getCategory());
            newEntitity.setQuantity(itemDTO.getQuantity());
            newEntitity.setPriceListPerUnit(itemDTO.getPriceListPerUnit());
            newEntitity.setPriceSalesPerUnit(itemDTO.getPriceSalesPerUnit());
            newEntitity.setRevenuePerUnit(itemDTO.getRevenuePerUnit());
            newEntitity.setDiscountPercent(itemDTO.getDiscountPercent());
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
            newDTO.setPriceListPerUnit(itemDTO.getPriceListPerUnit());
            newDTO.setPriceSalesPerUnit(itemDTO.getPriceSalesPerUnit());
            newDTO.setRevenuePerUnit(itemDTO.getRevenuePerUnit());
            newDTO.setDiscountPercent(itemDTO.getDiscountPercent());
            newDTO.setShop(itemDTO.getShop());
            newDTO.setDeliverySending(itemDTO.getDeliverySending());
            newDTO.setItemLastSold(itemDTO.getItemLastSold());
            newDTO.setComment(itemDTO.getComment());

            shopsCheckoutSoldItemsList.add(newDTO);
        }
        return shopsCheckoutSoldItemsList;
    }
}
