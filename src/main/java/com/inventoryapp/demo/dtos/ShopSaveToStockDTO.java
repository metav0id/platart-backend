package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class ShopSaveToStockDTO {
    private long identifierOnDeliveryList;
    private String shop;
    private String category;
    private long quantity;
    private long priceSalesPerUnit;
    private long priceListPerUnit;
    private long originalQuantity;
    private String timestamp;
    private String comment;

    public ShopSaveToStockDTO(long identifierOnDeliveryList,
                              String shop,
                              String category,
                              long quantity,
                              long priceListPerUnit,
                              long priceSalesPerUnit,
                              long originalQuantity,
                              String timestamp,
                              String comment) {
        this.identifierOnDeliveryList = identifierOnDeliveryList;
        this.shop = shop;
        this.category = category;
        this.quantity = quantity;
        this.priceListPerUnit = priceListPerUnit;
        this.priceSalesPerUnit = priceSalesPerUnit;
        this.originalQuantity = originalQuantity;
        this.timestamp = timestamp;
        this.comment = comment;
    }
}
