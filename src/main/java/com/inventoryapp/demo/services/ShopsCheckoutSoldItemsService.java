package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ShopsCheckoutSoldItemsDTO;
import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.entities.ShopsCheckoutSoldItems;
import com.inventoryapp.demo.repositories.ShopsAllSoldItemsRepository;
import com.inventoryapp.demo.repositories.ShopsCheckoutSoldItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopsCheckoutSoldItemsService {


    @Autowired
    private ShopsCheckoutSoldItemsRepository shopsCheckoutSoldItemsRepository;

    @Autowired
    private ShopsAllSoldItemsRepository shopsAllSoldItemsRepository;

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
