package com.inventoryapp.demo.dtos;

import lombok.Data;

import javax.persistence.Id;

@Data
public class WarehouseNewDeliveryOrderItemDTO {

    @Id
    private Long id;

    private String category;
    private int quantity;
    private long priceSalesPerUnit;
    private int discountPercent;
    private long priceListPerUnit;
    private String deliveryShop;
    private String comment;

    public WarehouseNewDeliveryOrderItemDTO() {
    }
}
