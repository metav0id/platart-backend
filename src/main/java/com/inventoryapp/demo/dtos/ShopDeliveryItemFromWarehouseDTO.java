package com.inventoryapp.demo.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopDeliveryItemFromWarehouseDTO {
    private long identifierOnDeliveryList;
    private String category;
    private long listPrice;
    private long salesPrice;
    private long quantity;
    private LocalDateTime timestamp;
    private String comment;

    public ShopDeliveryItemFromWarehouseDTO(long identifierOnDeliveryList, String category, long listPrice, long salesPrice,
                                            long quantity, LocalDateTime timestamp, String comment) {
        this.identifierOnDeliveryList = identifierOnDeliveryList;
        this.category = category;
        this.listPrice = listPrice;
        this.salesPrice = salesPrice;
        this.quantity = quantity;
        this.timestamp = timestamp;
        this.comment = comment;
    }
}
