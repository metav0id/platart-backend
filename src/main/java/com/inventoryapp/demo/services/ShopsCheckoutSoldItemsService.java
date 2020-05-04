package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ShopsCheckoutSoldItemsDTO;
import com.inventoryapp.demo.entities.ShopsCheckoutSoldItems;
import com.inventoryapp.demo.repositories.ShopsCheckoutSoldItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopsCheckoutSoldItemsService {


    @Autowired
    private ShopsCheckoutSoldItemsRepository shopsCheckoutSoldItemsRepository;

    public void saveAllSoldItemsList(List<ShopsCheckoutSoldItemsDTO> shopsCheckoutSoldItemsDTOList) {
        System.out.println("List of stock loaded in Service Backend.");

        List<ShopsCheckoutSoldItems> shopsCheckoutSoldItemsEntitiesList = mapDTOListToEntityList(shopsCheckoutSoldItemsDTOList);
        System.out.println(shopsCheckoutSoldItemsEntitiesList);

        this.shopsCheckoutSoldItemsRepository.saveAll(shopsCheckoutSoldItemsEntitiesList);
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
