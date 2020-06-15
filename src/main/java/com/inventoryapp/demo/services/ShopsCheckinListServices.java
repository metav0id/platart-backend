package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ShopCheckedInItemDTO;
import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import com.inventoryapp.demo.repositories.ShopsCheckinListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopsCheckinListServices {

    @Autowired
    private ShopsCheckinListRepository shopsCheckinListRepository;

    public List<ShopCheckedInItemDTO> getSpecificCheckedInItemsDate(String shopName, LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: Set timezone to UIO
        /*System.out.println("startDate: "+startDate);
        System.out.println("endDate: "+endDate);*/
        List<WarehouseSendDeliveryOrderItem> listRepoDate = this.shopsCheckinListRepository.findAllItemsNotAddedToShopInventoryDate(shopName, startDate, endDate);
        List<ShopCheckedInItemDTO> listDateDTOs = this.mapEntityToDto(listRepoDate);
        return listDateDTOs;
    }

    private List<ShopCheckedInItemDTO> mapEntityToDto(List<WarehouseSendDeliveryOrderItem> entityList){
        List<ShopCheckedInItemDTO> listDto = new ArrayList<>();

        for(WarehouseSendDeliveryOrderItem item: entityList){
            ShopCheckedInItemDTO itemDTO =
                    new ShopCheckedInItemDTO(
                                    item.getShop(),
                                    item.getCategory(),
                                    item.getQuantity(),
                                    item.getPriceSalesPerUnit(),
                                    item.getPriceListPerUnit(),
                                    item.getDiscountPercent(),
                                    item.getDeliverySending(),
                                    item.getComment(),
                                    item.getShopsCheckedInProductsFromWarehouse().getComment()
                            );
            listDto.add(itemDTO);

        }

        return listDto;
    }
}
