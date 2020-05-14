package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ShopsCheckoutSoldItemsDTO;
import com.inventoryapp.demo.dtos.ShopsStockItemDto;
import com.inventoryapp.demo.entities.ShopsStockItem;
import com.inventoryapp.demo.repositories.ShopsStockItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopsCurrentInventoryService {

    @Autowired
    private ShopsStockItemRepository shopsStockItemRepository;

    public List<ShopsStockItemDto> getAllItemsSpecificShop(String specificShop){

        // 1. Fetch Specific Shop Data from Database data
        List<ShopsStockItem> inventoryListFetched =  this.shopsStockItemRepository.findByShop(specificShop);

        // 2. transform Entities to Dtos
        List<ShopsStockItemDto> shopsCurrentInventoryDTOList = this.shopItemMapEntityToDto(inventoryListFetched);

        return shopsCurrentInventoryDTOList;
    }


    public List<ShopsStockItemDto> shopItemMapEntityToDto(List<ShopsStockItem> shopsStockItems){
        List<ShopsStockItemDto> shopsCurrentInventoryDTOList = new ArrayList<>();

        for(ShopsStockItem item: shopsStockItems){
            ShopsStockItemDto newInventoryDTO = new ShopsStockItemDto();
            newInventoryDTO.setPosition(item.getId());
            newInventoryDTO.setShop(item.getShop());
            newInventoryDTO.setCategory(item.getCategory());
            newInventoryDTO.setQuantity(item.getQuantity());
            newInventoryDTO.setPriceListPerUnit(item.getPriceListPerUnit());
            newInventoryDTO.setPriceSalesPerUnit(item.getPriceSalesPerUnit());
            shopsCurrentInventoryDTOList.add(newInventoryDTO);
        }

        return shopsCurrentInventoryDTOList;
    }

    public ShopsCheckoutSoldItemsDTO getShopInventoryAvailability(ShopsCheckoutSoldItemsDTO shopsCheckoutSoldItemsDTO){

        Long amountItems = this.shopsStockItemRepository.findAmountItemsByAllInfo(
                shopsCheckoutSoldItemsDTO.getShop(),
                shopsCheckoutSoldItemsDTO.getCategory(),
                shopsCheckoutSoldItemsDTO.getPriceListPerUnit(),
                shopsCheckoutSoldItemsDTO.getPriceSalesPerUnit()
                );

        shopsCheckoutSoldItemsDTO.setQuantity(amountItems);

        return shopsCheckoutSoldItemsDTO;
    }

}
