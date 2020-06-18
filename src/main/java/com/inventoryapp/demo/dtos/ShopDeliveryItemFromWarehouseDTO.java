package com.inventoryapp.demo.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopDeliveryItemFromWarehouseDTO {
    private long identifierOnDeliveryList;
    private String category;
    private Double listPrice;
    private Double salesPrice;
    private long quantity;
    private LocalDateTime timestamp;
    private String comment;

    public ShopDeliveryItemFromWarehouseDTO(long identifierOnDeliveryList, String category, Double listPrice, Double salesPrice,
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
