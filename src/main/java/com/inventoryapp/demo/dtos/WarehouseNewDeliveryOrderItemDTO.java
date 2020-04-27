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

    public WarehouseNewDeliveryOrderItemDTO() {
    }

    public WarehouseNewDeliveryOrderItemDTO(Long id, String category, int quantity, long priceSalesPerUnit, int discountPercent, long priceListPerUnit) {
        this.id = id;
        this.category = category;
        this.quantity = quantity;
        this.priceSalesPerUnit = priceSalesPerUnit;
        this.discountPercent = discountPercent;
        this.priceListPerUnit = priceListPerUnit;
        this.deliveryShop = "ShopTest";
    }

    public WarehouseNewDeliveryOrderItemDTO(Long id, String category, int quantity, long priceSalesPerUnit, int discountPercent, long priceListPerUnit, String deliveryShop) {
        this.id = id;
        this.category = category;
        this.quantity = quantity;
        this.priceSalesPerUnit = priceSalesPerUnit;
        this.discountPercent = discountPercent;
        this.priceListPerUnit = priceListPerUnit;
        this.deliveryShop = deliveryShop;
    }
}
