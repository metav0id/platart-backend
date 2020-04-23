package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ShopsCurrentInventoryDTO;
import com.inventoryapp.demo.entities.ShopsCurrentInventory;
import com.inventoryapp.demo.repositories.ShopsCurrentInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopsCurrentInventoryService {

    @Autowired
    private ShopsCurrentInventoryRepository shopsCurrentInventoryRepository;

    public List<ShopsCurrentInventoryDTO> getAllItemsSpecificShop(String specificShop){
        // 0. define persist test Data to repository

        // 1. Fetch Specific Shop Data from Database data
        List<ShopsCurrentInventory> inventoryListFetched =  this.shopsCurrentInventoryRepository.findByShop(specificShop);

        // 2. transform Entities to Dtos
        List<ShopsCurrentInventoryDTO> shopsCurrentInventoryDTOList = this.shopItemMapEntityToDto(inventoryListFetched);

        return shopsCurrentInventoryDTOList;
    }

    public List<ShopsCurrentInventoryDTO> getAllItemsAllShops(){
        // 0. define persist test Data to repository

        // 1. Fetch Specific Shop Data from Database data
        List<ShopsCurrentInventory> inventoryListFetched =  this.shopsCurrentInventoryRepository.findAll();

        // 2. transform Entities to Dtos
        List<ShopsCurrentInventoryDTO> shopsCurrentInventoryDTOList = this.shopItemMapEntityToDto(inventoryListFetched);

        return shopsCurrentInventoryDTOList;
    }

    public void setItemsShops(List<ShopsCurrentInventoryDTO> shopItemDtoList){
        List<ShopsCurrentInventory> shopsCurrentInventoryList = this.shopItemMapDtoToEntity(shopItemDtoList);

        this.shopsCurrentInventoryRepository.saveAll(shopsCurrentInventoryList);
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
            newInventoryDTO.setItemDisplayPricePerUnit(item.getPriceSalesPerUnit());
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
            newInventoryDTO.setPriceListPerUnit(item.getItemFinalPricePerUnit());
            newInventoryDTO.setPriceSalesPerUnit(item.getItemDisplayPricePerUnit());
            newInventoryDTO.setDiscountPercent(item.getItemDiscount());
            newInventoryDTO.setDeliverySending(item.getItemLastDelivery());
            newInventoryDTO.setItemLastSold(item.getItemLastSold());
            shopsCurrentInventoryList.add(newInventoryDTO);
        }
        return shopsCurrentInventoryList;
    }

}
