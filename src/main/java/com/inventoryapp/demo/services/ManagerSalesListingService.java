package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ShopsAllSoldItemsDTO;
import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.repositories.ShopsAllSoldItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerSalesListingService {

    @Autowired
    private ShopsAllSoldItemsRepository shopsAllSoldItemsRepository;

    @Autowired
    private ConvertingValues convertingValues;

    public ManagerSalesListingService() {
    }

    public List<ShopsAllSoldItemsDTO> getSoldItemsListByDateRange(LocalDateTime startDate, LocalDateTime endDate){
        System.out.println("start: "+ startDate + " = end: "+endDate);

        List<ShopsAllSoldItems> allItems = new ArrayList<>();
        List<ShopsAllSoldItemsDTO> allDTOItems = new ArrayList<>();

        try {
            allItems = this.shopsAllSoldItemsRepository.getItemsByDate(startDate, endDate);
            allDTOItems = mapEntitiesToDTOs(allItems);
        } catch (Exception e) {
            System.err.println(e);
        }

        return allDTOItems;
    }

    private List<ShopsAllSoldItemsDTO> mapEntitiesToDTOs(List<ShopsAllSoldItems> shopsAllSoldItems){
        List<ShopsAllSoldItemsDTO> shopsAllSoldItemsDTOList = new ArrayList<>();

        for(ShopsAllSoldItems item: shopsAllSoldItems){
            ShopsAllSoldItemsDTO itemDTO = new ShopsAllSoldItemsDTO();
            itemDTO.setId(item.getId());
            itemDTO.setCategory(item.getCategory());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPriceListPerUnit(convertingValues.convertLongToDoubleForEntityToDTO(item.getPriceListPerUnit()));
            itemDTO.setPriceSalesPerUnit(convertingValues.convertLongToDoubleForEntityToDTO(item.getPriceSalesPerUnit()));
            itemDTO.setRevenuePerUnit(convertingValues.convertLongToDoubleForEntityToDTO(item.getRevenuePerUnit()));
            itemDTO.setDiscountPercent(convertingValues.convertLongToDoubleForEntityToDTO(item.getDiscountPercent()));
            itemDTO.setShop(item.getShop());
            itemDTO.setDeliverySending(item.getDeliverySending());
            itemDTO.setItemLastSold(item.getItemLastSold());
            itemDTO.setComment(item.getComment());

            shopsAllSoldItemsDTOList.add(itemDTO);
        }

        return shopsAllSoldItemsDTOList;
    }
}
