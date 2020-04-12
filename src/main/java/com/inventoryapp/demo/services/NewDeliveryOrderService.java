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
        System.out.println("Service1:");
        System.out.println(deliveryOrderItemsEntities);


        if(deliveryOrderItemsEntities.size()>0){
            deliveryOrderItemDTOS = convertEntitiesToDtos(deliveryOrderItemsEntities);
            System.out.println("Service if");
        } else {
            System.out.println("----> No items found in Data Base for new order <----");
        }
        System.out.println("Service:");
        System.out.println(deliveryOrderItemDTOS);
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
        System.out.println("New Delivery Order Service:");
        System.out.println(newDeliveryOrderItemDTOList);
        System.out.println(newDeliveryOrderItemEntitiesList);
        // TODO: persist newDeliveryOrderItemEntitiesList
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
            newDeliveryOrderItemDTO.setDeliveryDisplayPricePerUnit(item.getDeliveryDisplayPricePerUnit());
            newDeliveryOrderItemDTO.setDeliveryDiscount(item.getDeliveryDiscount());
            newDeliveryOrderItemDTO.setDeliveryFinalPricePerUnit(item.getDeliveryFinalPricePerUnit());

            deliveryOrderItemDTOS.add(newDeliveryOrderItemDTO);
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
            newDeliveryOrderItem.setDeliveryDisplayPricePerUnit(item.getDeliveryDisplayPricePerUnit());
            newDeliveryOrderItem.setDeliveryDiscount(item.getDeliveryDiscount());
            newDeliveryOrderItem.setDeliveryFinalPricePerUnit(item.getDeliveryFinalPricePerUnit());

            deliveryOrderItemEntityLists.add(newDeliveryOrderItem);
        }
        return deliveryOrderItemEntityLists;
    }

}
