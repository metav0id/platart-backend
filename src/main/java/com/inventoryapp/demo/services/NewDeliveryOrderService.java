package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.NewDeliveryOrderItemDTO;
import com.inventoryapp.demo.entities.NewDeliveryOrderItem;
import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import com.inventoryapp.demo.entities.WarehouseStockItem;
import com.inventoryapp.demo.repositories.NewDeliveryOrderRepository;
import com.inventoryapp.demo.repositories.WarehouseRepository;
import com.inventoryapp.demo.repositories.WarehouseShopDeliveryOrdersSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewDeliveryOrderService {

    private NewDeliveryOrderRepository newDeliveryOrderRepository;
    private WarehouseRepository warehouseRepository;

    public NewDeliveryOrderService(NewDeliveryOrderRepository newDeliveryOrderRepository, WarehouseRepository warehouseRepository) {
        this.newDeliveryOrderRepository = newDeliveryOrderRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Autowired


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
        List<NewDeliveryOrderItem> newDeliveryOrderItemEntitiesList = convertDtosToEntities(newDeliveryOrderItemDTOList);

        System.out.println("New Delivery Order Service:");
        this.newDeliveryOrderRepository.deleteAll();
        this.newDeliveryOrderRepository.saveAll(newDeliveryOrderItemEntitiesList);
    }

    public void sendDeliveryOrder(){
        List<NewDeliveryOrderItem> currentDeliveryOrderItemEntitiesList = this.newDeliveryOrderRepository.findAll();
        System.out.println("sendDeliveryOrder - service");

        System.out.println("WarehouseRepository item output:");
        List<Long> modifiedItems =  new ArrayList<>();

        //update the item amount on the warehouse table and add them to the OrderSendTable
        LocalDateTime newDeliveryDateTime = LocalDateTime.now();
        for(NewDeliveryOrderItem itemOnList: currentDeliveryOrderItemEntitiesList){
            WarehouseStockItem itemWarehouse = this.warehouseRepository.findItemByCategoryAndPricePerUnit(itemOnList.getCategory(), itemOnList.getDeliveryFinalPricePerUnit());

            if(itemWarehouse.getQuantity() >= itemOnList.getDeliveryQuantity()){
                long newWarehouseQuantity = itemWarehouse.getQuantity()-itemOnList.getDeliveryQuantity();
                itemWarehouse.setQuantity(newWarehouseQuantity);

                modifiedItems.add(itemOnList.getId());
                this.warehouseRepository.save(itemWarehouse);

                WarehouseSendDeliveryOrderItem deliveryItemSend = new WarehouseSendDeliveryOrderItem();
                deliveryItemSend.setId(itemOnList.getId());
                deliveryItemSend.setCategory(itemOnList.getCategory());
                deliveryItemSend.setDeliveryDisplayPricePerUnit(itemOnList.getDeliveryDisplayPricePerUnit());
                deliveryItemSend.setDeliveryDiscount(itemOnList.getDeliveryDiscount());
                deliveryItemSend.setDeliveryFinalPricePerUnit(itemOnList.getDeliveryFinalPricePerUnit());
                deliveryItemSend.setDeliverySending(newDeliveryDateTime);
                deliveryItemSend.setDeliveryQuantity(itemOnList.getDeliveryQuantity());
                deliveryItemSend.setShop("SomeShop");
            } else {
                System.out.println("The operation is not possible for element" + itemOnList.getCategory() + ", "+ itemOnList.getCategory());
            }
        }

        //remove the items, which were removable from the order list
        for(NewDeliveryOrderItem itemOnList: currentDeliveryOrderItemEntitiesList){
            if(modifiedItems.contains(itemOnList.getId())){
                this.newDeliveryOrderRepository.delete(itemOnList);
                System.out.println("Element deleted from order list");
            }
        }

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
