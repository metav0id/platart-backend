package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.WarehouseNewDeliveryOrderItemDTO;
import com.inventoryapp.demo.entities.WarehouseNewDeliveryOrderItem;
import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import com.inventoryapp.demo.entities.WarehouseStockItem;
import com.inventoryapp.demo.repositories.WarehouseNewDeliveryOrderRepository;
import com.inventoryapp.demo.repositories.WarehouseRepository;
import com.inventoryapp.demo.repositories.WarehouseShopDeliveryOrdersSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseNewDeliveryOrderService {

    private WarehouseShopDeliveryOrdersSend warehouseShopDeliveryOrdersSend;
    private WarehouseNewDeliveryOrderRepository warehouseNewDeliveryOrderRepository;
    private WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseNewDeliveryOrderService(WarehouseShopDeliveryOrdersSend warehouseShopDeliveryOrdersSend, WarehouseNewDeliveryOrderRepository newDeliveryOrderRepository, WarehouseRepository warehouseRepository) {
        this.warehouseShopDeliveryOrdersSend = warehouseShopDeliveryOrdersSend;
        this.warehouseNewDeliveryOrderRepository = newDeliveryOrderRepository;
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * Get all items in the current delivery order list.
     *
     * @return List with data transfer objects
     */
    public List<WarehouseNewDeliveryOrderItemDTO> getAllDeliveryOrderItems() {
        List<WarehouseNewDeliveryOrderItemDTO> deliveryOrderItemDTOS = new ArrayList<>();
        List<WarehouseNewDeliveryOrderItem> deliveryOrderItemsEntities = this.warehouseNewDeliveryOrderRepository.findAll();
        System.out.println("Service1:");
        System.out.println(deliveryOrderItemsEntities);


        if (deliveryOrderItemsEntities.size() > 0) {
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
     *
     * @return
     */
    public void setAllDeliveryOrderItems(List<WarehouseNewDeliveryOrderItemDTO> newDeliveryOrderItemDTOList) {
        List<WarehouseNewDeliveryOrderItem> newDeliveryOrderItemEntitiesList = convertDtosToEntities(newDeliveryOrderItemDTOList);

        System.out.println("New Delivery Order Service:");
        this.warehouseNewDeliveryOrderRepository.deleteAll();
        this.warehouseNewDeliveryOrderRepository.saveAll(newDeliveryOrderItemEntitiesList);
    }

    public void sendDeliveryOrder(List<WarehouseNewDeliveryOrderItemDTO> newDeliveryOrderItemDTOList) {
        // 1. Reset the current new deliveries Database-table
        setAllDeliveryOrderItems(newDeliveryOrderItemDTOList);

        // 2. Data Management
        List<WarehouseNewDeliveryOrderItem> currentDeliveryOrderItemEntitiesList = this.warehouseNewDeliveryOrderRepository.findAll();
        currentDeliveryOrderItemEntitiesList.stream().forEach(o-> System.out.println("Delivery Quantity" + o.getDeliveryQuantity() + " Category "+ o.getCategory() + " price " + o.getDeliveryFinalPricePerUnit()));


        System.out.println("sendDeliveryOrder - service");

        System.out.println("WarehouseRepository item output:");
        List<Long> modifiedItems = new ArrayList<>();

        // 3. cumulate for each category and PricePerUnit new quantity on list.
        List<WarehouseNewDeliveryOrderItem> currentDeliveriesAggregated = new ArrayList<>();

        for (WarehouseNewDeliveryOrderItem item : currentDeliveryOrderItemEntitiesList) {

            currentDeliveriesAggregated.stream()
                    .filter(
                            o -> item.getCategory().equals(o.getCategory()) &&
                                    item.getDeliveryFinalPricePerUnit() == item.getDeliveryFinalPricePerUnit())
                    .forEach(o -> o.setDeliveryQuantity(o.getDeliveryQuantity() + item.getDeliveryQuantity()));

            boolean isNotItemFound = currentDeliveriesAggregated.stream()
                    .noneMatch(
                            o -> item.getCategory().equals(o.getCategory()) &&
                                    item.getDeliveryFinalPricePerUnit() == item.getDeliveryFinalPricePerUnit());

            if (isNotItemFound) {
                currentDeliveriesAggregated.add(item);
            }
        }

        // 4. verify if transaction is feasible for all items in delivery list
        boolean isTransactionFeasible = true;
        for (WarehouseNewDeliveryOrderItem item : currentDeliveriesAggregated) {
            System.out.println("ITEM ITEM: " + item.getCategory() + " " + item.getDeliveryQuantity());

            // verify if amount of item in stock
            WarehouseStockItem itemWarehouse = this.warehouseRepository.findItemByCategoryAndPricePerUnit(item.getCategory(), item.getDeliveryFinalPricePerUnit());

            if (itemWarehouse.getQuantity() < item.getDeliveryQuantity()) {
                isTransactionFeasible = false;
                break;
            }
        }

        if(isTransactionFeasible){
            // 5. update the item amount on the warehouse table and add them to the OrderSendTable
            LocalDateTime newDeliveryDateTime = LocalDateTime.now();
            for (WarehouseNewDeliveryOrderItem itemOnList : currentDeliveryOrderItemEntitiesList) {
                WarehouseStockItem itemWarehouse = this.warehouseRepository.findItemByCategoryAndPricePerUnit(itemOnList.getCategory(), itemOnList.getDeliveryFinalPricePerUnit());

                if (itemWarehouse.getQuantity() >= itemOnList.getDeliveryQuantity()) {
                    long newWarehouseQuantity = itemWarehouse.getQuantity() - itemOnList.getDeliveryQuantity();
                    itemWarehouse.setQuantity(newWarehouseQuantity);

                    modifiedItems.add(itemOnList.getId());
                    this.warehouseRepository.save(itemWarehouse);

                    WarehouseSendDeliveryOrderItem deliveryItemSend = new WarehouseSendDeliveryOrderItem();
                    //deliveryItemSend.setId(itemOnList.getId());
                    deliveryItemSend.setCategory(itemOnList.getCategory());
                    deliveryItemSend.setDeliveryDisplayPricePerUnit(itemOnList.getDeliveryDisplayPricePerUnit());
                    deliveryItemSend.setDeliveryDiscount(itemOnList.getDeliveryDiscount());
                    deliveryItemSend.setDeliveryFinalPricePerUnit(itemOnList.getDeliveryFinalPricePerUnit());
                    deliveryItemSend.setDeliverySending(newDeliveryDateTime);

                    //deliveryItemSend.setDeliveryQuantity(123456789);
                    deliveryItemSend.setDeliveryQuantity(itemOnList.getDeliveryQuantity());
                    deliveryItemSend.setShop(itemOnList.getDeliveryShop());
                    System.out.println(itemOnList.getDeliveryShop());
                    this.warehouseShopDeliveryOrdersSend.save(deliveryItemSend);
                } else {
                    System.out.println("The operation is not possible for element" + itemOnList.getCategory() + ", " + itemOnList.getCategory());
                }
            }

            //remove the items, which were removable from the order list
            for (WarehouseNewDeliveryOrderItem itemOnList : currentDeliveryOrderItemEntitiesList) {
                if (modifiedItems.contains(itemOnList.getId())) {
                    this.warehouseNewDeliveryOrderRepository.delete(itemOnList);
                    System.out.println("Element deleted from order list");
                }
            }
        } else {
            System.out.println("Peristance of list not possible -> requested amount not available on stock ");
        }

    }
    /**
     * Mapper function: NewDeliveryOrderItem entities-list to NewDeliveryOrderItem DTO-List
     * @param deliveryOrderItemsEntities
     * @return
     */
    public List<WarehouseNewDeliveryOrderItemDTO> convertEntitiesToDtos(List<WarehouseNewDeliveryOrderItem> deliveryOrderItemsEntities){
        List<WarehouseNewDeliveryOrderItemDTO> deliveryOrderItemDTOS = new ArrayList<>();

        for(WarehouseNewDeliveryOrderItem item: deliveryOrderItemsEntities){
            WarehouseNewDeliveryOrderItemDTO newDeliveryOrderItemDTO = new WarehouseNewDeliveryOrderItemDTO();
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
    public List<WarehouseNewDeliveryOrderItem> convertDtosToEntities(List<WarehouseNewDeliveryOrderItemDTO> deliveryOrderItemDtoList){
        List<WarehouseNewDeliveryOrderItem> deliveryOrderItemEntityLists = new ArrayList<>();
        for(WarehouseNewDeliveryOrderItemDTO item: deliveryOrderItemDtoList){
            WarehouseNewDeliveryOrderItem newDeliveryOrderItem = new WarehouseNewDeliveryOrderItem();
            newDeliveryOrderItem.setId(item.getId());
            newDeliveryOrderItem.setCategory(item.getCategory());
            newDeliveryOrderItem.setDeliveryQuantity(item.getDeliveryQuantity());
            newDeliveryOrderItem.setDeliveryDisplayPricePerUnit(item.getDeliveryDisplayPricePerUnit());
            newDeliveryOrderItem.setDeliveryDiscount(item.getDeliveryDiscount());
            newDeliveryOrderItem.setDeliveryFinalPricePerUnit(item.getDeliveryFinalPricePerUnit());
            newDeliveryOrderItem.setDeliveryShop(item.getDeliveryShop());

            deliveryOrderItemEntityLists.add(newDeliveryOrderItem);
        }
        return deliveryOrderItemEntityLists;
    }

}
