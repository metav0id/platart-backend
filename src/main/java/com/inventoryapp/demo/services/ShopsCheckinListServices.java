package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.ShopCheckedInItemDTO;
import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import com.inventoryapp.demo.repositories.ShopsCheckinListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopsCheckinListServices {

    @Autowired
    private ShopsCheckinListRepository shopsCheckinListRepository;

    public List<ShopCheckedInItemDTO> getAllCheckedInItems() {

        List<WarehouseSendDeliveryOrderItem> listRepo = this.shopsCheckinListRepository.findAll();
        List<ShopCheckedInItemDTO> listDTOs = this.mapEntityToDto(listRepo);

        return listDTOs;
    }

    public List<ShopCheckedInItemDTO> getSpecificCheckedInItems(String shopName) {

        List<WarehouseSendDeliveryOrderItem> listRepo = this.shopsCheckinListRepository.findAllItemsNotAddedToShopInventory(shopName);
        List<ShopCheckedInItemDTO> listDTOs = this.mapEntityToDto(listRepo);

        return listDTOs;
    }



    public List<ShopCheckedInItemDTO> mapEntityToDto(List<WarehouseSendDeliveryOrderItem> entityList){
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
                                    item.getShopsCheckedInProductsFromWarehouse().getComment(),
                                    item.getComment()
                                    );
            listDto.add(itemDTO);

        }

        return listDto;
    }
}
