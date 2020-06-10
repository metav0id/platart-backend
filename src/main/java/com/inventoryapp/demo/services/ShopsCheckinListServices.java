package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.WarehouseSendDeliveryOrderItemDTO;
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

    public List<WarehouseSendDeliveryOrderItemDTO> getAllCheckedInItems() {

        List<WarehouseSendDeliveryOrderItem> listRepo = this.shopsCheckinListRepository.findAll();

        List<WarehouseSendDeliveryOrderItemDTO> listDTOs = this.mapEntityToDto(listRepo);

        // System.out.println(list);

        WarehouseSendDeliveryOrderItemDTO item1 = new WarehouseSendDeliveryOrderItemDTO(1L, "shop", "category", 100, 100, 200, 50, LocalDateTime.now().minusDays(1));
        listDTOs.add(item1);

        return listDTOs;
    }

    public List<WarehouseSendDeliveryOrderItemDTO> getSpecificCheckedInItems(String shopName) {

        List<WarehouseSendDeliveryOrderItem> listRepo = this.shopsCheckinListRepository.findAllItemsNotAddedToShopInventory(shopName);


        // listRepo.get(0).getShopsCheckedInProductsFromWarehouse().getComment();

        List<WarehouseSendDeliveryOrderItemDTO> listDTOs = this.mapEntityToDto(listRepo);

        return listDTOs;
    }



    public List<WarehouseSendDeliveryOrderItemDTO> mapEntityToDto(List<WarehouseSendDeliveryOrderItem> entityList){
        List<WarehouseSendDeliveryOrderItemDTO> listDto = new ArrayList<>();

        for(WarehouseSendDeliveryOrderItem item: entityList){
            WarehouseSendDeliveryOrderItemDTO itemDTO =
                    new WarehouseSendDeliveryOrderItemDTO(
                                    item.getId(),
                                    item.getShop(),
                                    item.getCategory(),
                                    item.getQuantity(),
                                    item.getPriceSalesPerUnit(),
                                    item.getPriceListPerUnit(),
                                    item.getDiscountPercent(),
                                    item.getDeliverySending()
                                    );
            listDto.add(itemDTO);

        }

        return listDto;
    }
}
