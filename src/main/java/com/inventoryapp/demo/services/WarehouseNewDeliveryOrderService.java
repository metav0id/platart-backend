package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.WarehouseItemPersistanceErrorDTO;
import com.inventoryapp.demo.dtos.WarehouseNewDeliveryOrderItemDTO;
import com.inventoryapp.demo.dtos.WarehouseNewDeliveryPersistanceResponseDTO;
import com.inventoryapp.demo.entities.ShopsCheckedInProductsFromWarehouse;
import com.inventoryapp.demo.entities.WarehouseNewDeliveryOrderItem;
import com.inventoryapp.demo.entities.WarehouseSendDeliveryOrderItem;
import com.inventoryapp.demo.entities.WarehouseStockItem;
import com.inventoryapp.demo.repositories.WarehouseNewDeliveryOrderRepository;
import com.inventoryapp.demo.repositories.WarehouseRepository;
import com.inventoryapp.demo.repositories.WarehouseShopDeliveryOrdersSendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseNewDeliveryOrderService {

    private WarehouseShopDeliveryOrdersSendRepository warehouseShopDeliveryOrdersSendRepository;
    private WarehouseNewDeliveryOrderRepository warehouseNewDeliveryOrderRepository;
    private WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseNewDeliveryOrderService(WarehouseShopDeliveryOrdersSendRepository warehouseShopDeliveryOrdersSendRepository,
                                            WarehouseNewDeliveryOrderRepository newDeliveryOrderRepository,
                                            WarehouseRepository warehouseRepository) {
        this.warehouseShopDeliveryOrdersSendRepository = warehouseShopDeliveryOrdersSendRepository;
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


        if (deliveryOrderItemsEntities.size() > 0) {
            deliveryOrderItemDTOS = convertEntitiesToDtos(deliveryOrderItemsEntities);
        } else {
            System.out.println("----> No items found in Data Base for new order <----");
        }

        return deliveryOrderItemDTOS;
    }

    /**
     * Persist all items in the current delivery order list for improved later use.
     *
     * @return
     */
    public void setAllDeliveryOrderItems(List<WarehouseNewDeliveryOrderItemDTO> newDeliveryOrderItemDTOList) {
        List<WarehouseNewDeliveryOrderItem> newDeliveryOrderItemEntitiesList = convertDtosToEntities(newDeliveryOrderItemDTOList);
        this.warehouseNewDeliveryOrderRepository.deleteAll();
        this.warehouseNewDeliveryOrderRepository.saveAll(newDeliveryOrderItemEntitiesList);
    }

    public WarehouseNewDeliveryPersistanceResponseDTO sendDeliveryOrder(List<WarehouseNewDeliveryOrderItemDTO> newDeliveryOrderItemDTOList) {
        // 0. Define return Dto - if this point was reached, set persistance initalized true
        WarehouseNewDeliveryPersistanceResponseDTO responseDTO = new WarehouseNewDeliveryPersistanceResponseDTO();
        responseDTO.setPersistanceInitialized(true);
        List<WarehouseItemPersistanceErrorDTO> itemPersistanceErrorDtoList = new ArrayList<>();

        // 1. Reset the current new deliveries Database-table
        setAllDeliveryOrderItems(newDeliveryOrderItemDTOList);

        // 2. Data Management
        List<WarehouseNewDeliveryOrderItem> currentDeliveryOrderItemEntitiesList = this.warehouseNewDeliveryOrderRepository.findAll();
        currentDeliveryOrderItemEntitiesList.stream().forEach(o -> System.out.println("Delivery Quantity" + o.getQuantity() + " Category " + o.getCategory() + " price " + o.getPriceListPerUnit()));

        // 3. cumulate for each category and PricePerUnit new quantity on list.
        List<WarehouseNewDeliveryOrderItem> currentDeliveriesAggregated = new ArrayList<>();

        for (WarehouseNewDeliveryOrderItem item : currentDeliveryOrderItemEntitiesList) {

            currentDeliveriesAggregated.stream()
                    .filter(
                            o -> item.getCategory().equals(o.getCategory()) &&
                                    item.getPriceListPerUnit() == item.getPriceListPerUnit())
                    .forEach(o -> o.setQuantity(o.getQuantity() + item.getQuantity()));

            boolean isNotItemFound = currentDeliveriesAggregated.stream()
                    .noneMatch(
                            o -> item.getCategory().equals(o.getCategory()) &&
                                    o.getPriceListPerUnit() == item.getPriceListPerUnit());

            if (isNotItemFound) {
                WarehouseNewDeliveryOrderItem newItem = new WarehouseNewDeliveryOrderItem();
                newItem.setId(item.getId());
                newItem.setCategory(item.getCategory());
                newItem.setQuantity(item.getQuantity());
                newItem.setDiscountPercent(item.getDiscountPercent());
                newItem.setPriceSalesPerUnit(item.getPriceSalesPerUnit());
                newItem.setPriceListPerUnit(item.getPriceListPerUnit());
                newItem.setDeliveryShop(item.getDeliveryShop());
                newItem.setComment(item.getComment());

                currentDeliveriesAggregated.add(newItem);
            }
        }

        // 4. verify if transaction is feasible for all items in delivery list
        boolean isTransactionFeasible = true;
        for (WarehouseNewDeliveryOrderItem item : currentDeliveriesAggregated) {
            System.out.println("ITEM ITEM: " + item.getCategory() + " " + item.getQuantity());

            // verify if amount of item in stock
            WarehouseStockItem itemWarehouse = this.warehouseRepository.findItemByCategoryAndPricePerUnit(item.getCategory(), item.getPriceListPerUnit());

            Long differenceQuantity = itemWarehouse.getQuantity() - item.getQuantity();
            if (differenceQuantity < 0) {
                isTransactionFeasible = false;

                WarehouseItemPersistanceErrorDTO error = new WarehouseItemPersistanceErrorDTO();
                error.setCategory(item.getCategory());
                error.setPriceListPricePerUnit(item.getPriceListPerUnit());
                error.setErrorQuantity(differenceQuantity);
                itemPersistanceErrorDtoList.add(error);
            }
        }
        responseDTO.setItemPersistanceErrorDtoList(itemPersistanceErrorDtoList);

        // 5. update the item amount on the warehouse table and add them to the OrderSendTable
        if(isTransactionFeasible){

            LocalDateTime newDeliveryDateTime = LocalDateTime.now();
            for (WarehouseNewDeliveryOrderItem itemOnList : currentDeliveryOrderItemEntitiesList) {
                WarehouseStockItem itemWarehouse = this.warehouseRepository.
                        findItemByCategoryAndPricePerUnit(itemOnList.getCategory(), itemOnList.getPriceListPerUnit());

                long newWarehouseQuantity = itemWarehouse.getQuantity() - itemOnList.getQuantity();
                itemWarehouse.setQuantity(newWarehouseQuantity);

                this.warehouseRepository.save(itemWarehouse);

                WarehouseSendDeliveryOrderItem deliveryItemSend = new WarehouseSendDeliveryOrderItem();
                deliveryItemSend.setCategory(itemOnList.getCategory());
                deliveryItemSend.setPriceSalesPerUnit(itemOnList.getPriceSalesPerUnit());
                deliveryItemSend.setDiscountPercent(itemOnList.getDiscountPercent());
                deliveryItemSend.setPriceListPerUnit(itemOnList.getPriceListPerUnit());
                deliveryItemSend.setDeliverySending(newDeliveryDateTime);
                deliveryItemSend.setQuantity(itemOnList.getQuantity());
                deliveryItemSend.setShop(itemOnList.getDeliveryShop());
                deliveryItemSend.setComment(itemOnList.getComment());

                //Test one-one-relationship
                deliveryItemSend.setShopsCheckedInProductsFromWarehouse(new ShopsCheckedInProductsFromWarehouse());

                System.out.println(itemOnList.getDeliveryShop());
                this.warehouseShopDeliveryOrdersSendRepository.save(deliveryItemSend);
            }
            //delete all items on the temporary item-order list
            this.warehouseNewDeliveryOrderRepository.deleteAll();
            responseDTO.setPersistanceSuccessful(true);
        } else {
            responseDTO.setPersistanceSuccessful(false);
            System.out.println("Peristance of list not possible -> requested amount not available on stock ");
        }

        return responseDTO;
    }

    /**
     * Mapper function: NewDeliveryOrderItem entities-list to NewDeliveryOrderItem DTO-List
     * @param deliveryOrderItemsEntities
     * @return
     */
    public List<WarehouseNewDeliveryOrderItemDTO> convertEntitiesToDtos(List<WarehouseNewDeliveryOrderItem> deliveryOrderItemsEntities) {
        List<WarehouseNewDeliveryOrderItemDTO> deliveryOrderItemDTOS = new ArrayList<>();

        for (WarehouseNewDeliveryOrderItem item : deliveryOrderItemsEntities) {
            WarehouseNewDeliveryOrderItemDTO newDeliveryOrderItemDTO = new WarehouseNewDeliveryOrderItemDTO();
            newDeliveryOrderItemDTO.setId(item.getId());
            newDeliveryOrderItemDTO.setCategory(item.getCategory());
            newDeliveryOrderItemDTO.setQuantity(item.getQuantity());
            newDeliveryOrderItemDTO.setPriceSalesPerUnit(item.getPriceSalesPerUnit());
            newDeliveryOrderItemDTO.setDiscountPercent(item.getDiscountPercent());
            newDeliveryOrderItemDTO.setPriceListPerUnit(item.getPriceListPerUnit());
            newDeliveryOrderItemDTO.setComment(item.getComment());

            deliveryOrderItemDTOS.add(newDeliveryOrderItemDTO);
        }
        return deliveryOrderItemDTOS;
    }

    /**
     * Mapper function: NewDeliveryOrderItem DTO-list to NewDeliveryOrderItem entitiy-List
     * @param deliveryOrderItemDtoList
     * @return
     */
    public List<WarehouseNewDeliveryOrderItem> convertDtosToEntities(List<WarehouseNewDeliveryOrderItemDTO> deliveryOrderItemDtoList) {

        List<WarehouseNewDeliveryOrderItem> deliveryOrderItemEntityLists = new ArrayList<>();
        for (WarehouseNewDeliveryOrderItemDTO item : deliveryOrderItemDtoList) {
            WarehouseNewDeliveryOrderItem newDeliveryOrderItem = new WarehouseNewDeliveryOrderItem();
            newDeliveryOrderItem.setId(item.getId());
            newDeliveryOrderItem.setCategory(item.getCategory());
            newDeliveryOrderItem.setQuantity(item.getQuantity());
            newDeliveryOrderItem.setPriceSalesPerUnit(item.getPriceSalesPerUnit());
            newDeliveryOrderItem.setDiscountPercent(item.getDiscountPercent());
            newDeliveryOrderItem.setPriceListPerUnit(item.getPriceListPerUnit());
            newDeliveryOrderItem.setDeliveryShop(item.getDeliveryShop());
            newDeliveryOrderItem.setComment(item.getComment());

            deliveryOrderItemEntityLists.add(newDeliveryOrderItem);
        }
        return deliveryOrderItemEntityLists;
    }

}
