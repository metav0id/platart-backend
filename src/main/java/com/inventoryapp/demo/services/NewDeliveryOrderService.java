package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.NewDeliveryOrderItemDTO;
import com.inventoryapp.demo.entities.NewDeliveryOrderItem;
import com.inventoryapp.demo.repositories.NewDeliveryOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewDeliveryOrderService {

    private NewDeliveryOrderRepository newDeliveryOrderRepository;

    @Autowired
    public NewDeliveryOrderService(NewDeliveryOrderRepository newDeliveryOrderRepository) {
        this.newDeliveryOrderRepository = newDeliveryOrderRepository;
    }

    /**
     * Get all items in the current delivery order list.
     * @return List with data transfer objects
     */
    public List<NewDeliveryOrderItemDTO> getAllDeliveryOrderItems(){
        List<NewDeliveryOrderItemDTO> deliveryOrderItemDTOS = new ArrayList<>();
        List<NewDeliveryOrderItem> deliveryOrderItemsEntities = this.newDeliveryOrderRepository.findAll();

        if(deliveryOrderItemsEntities.size()>0){
            deliveryOrderItemDTOS = convertEntitiesToDtos(deliveryOrderItemsEntities);
        } else {
            System.out.println("----> No items found in Data Base for new order <----");
        }
        return deliveryOrderItemDTOS;
    }

    /**
     * Persist all items in the current delivery order list for improved later use.
     * @return
     */
    public void setAllDeliveryOrderItems(List<NewDeliveryOrderItemDTO> newDeliveryOrderItemDTOList) {
        List<NewDeliveryOrderItem> newDeliveryOrderItemEntitiesList = new ArrayList<>();
        newDeliveryOrderItemEntitiesList = convertDtosToEntities(newDeliveryOrderItemDTOList);

        this.newDeliveryOrderRepository.deleteAll();
    }

    /**
     * Mapper function: NewDeliveryOrderItem entities-list to NewDeliveryOrderItem DTO-List
     * @param deliveryOrderItemsEntities
     * @return
     */
    public List<NewDeliveryOrderItemDTO> convertEntitiesToDtos(List<NewDeliveryOrderItem> deliveryOrderItemsEntities){
        List<NewDeliveryOrderItemDTO> deliveryOrderItemDTOS = new ArrayList<>();
        for(NewDeliveryOrderItem item: deliveryOrderItemsEntities){
            NewDeliveryOrderItemDTO newDeliveryOrderItemDTO = new NewDeliveryOrderItemDTO();
            newDeliveryOrderItemDTO.setId(item.getId());
            newDeliveryOrderItemDTO.setCategory(item.getCategory());
            newDeliveryOrderItemDTO.setDeliveryQuantity(item.getDeliveryQuantity());
            newDeliveryOrderItemDTO.setDeliveryPricePerUnit(item.getDeliveryPricePerUnit());
            newDeliveryOrderItemDTO.setDeliveryDiscount(item.getDeliveryDiscount());
            newDeliveryOrderItemDTO.setDeliveryFinalPricePerUnit(item.getDeliveryFinalPricePerUnit());
        }
        return deliveryOrderItemDTOS;
    }

    /**
     * Mapper function: NewDeliveryOrderItem DTO-list to NewDeliveryOrderItem entitiy-List
     * @param deliveryOrderItemDtoList
     * @return
     */
    public List<NewDeliveryOrderItem> convertDtosToEntities(List<NewDeliveryOrderItemDTO> deliveryOrderItemDtoList){
        List<NewDeliveryOrderItem> deliveryOrderItemEntityLists = new ArrayList<>();
        for(NewDeliveryOrderItemDTO item: deliveryOrderItemDtoList){
            NewDeliveryOrderItem newDeliveryOrderItem = new NewDeliveryOrderItem();
            newDeliveryOrderItem.setId(item.getId());
            newDeliveryOrderItem.setCategory(item.getCategory());
            newDeliveryOrderItem.setDeliveryQuantity(item.getDeliveryQuantity());
            newDeliveryOrderItem.setDeliveryPricePerUnit(item.getDeliveryPricePerUnit());
            newDeliveryOrderItem.setDeliveryDiscount(item.getDeliveryDiscount());
            newDeliveryOrderItem.setDeliveryFinalPricePerUnit(item.getDeliveryFinalPricePerUnit());
        }
        return deliveryOrderItemEntityLists;
    }

}
