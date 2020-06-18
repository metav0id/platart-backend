package com.inventoryapp.demo.dtos;

import lombok.Data;

import javax.persistence.Id;

@Data
public class WarehouseNewDeliveryOrderItemDTO {

    @Id
    private Long id;

    private String category;
    private int quantity;
    private Double priceSalesPerUnit;
    private Double discountPercent;
    private Double priceListPerUnit;
    private String deliveryShop;
    private String comment;

    public WarehouseNewDeliveryOrderItemDTO() {
    }
}
