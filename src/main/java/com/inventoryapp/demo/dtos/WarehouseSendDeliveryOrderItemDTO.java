package com.inventoryapp.demo.dtos;

import com.inventoryapp.demo.entities.ShopsCheckedInProductsFromWarehouse;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class WarehouseSendDeliveryOrderItemDTO {

    private Long id;

    private String shop;
    private String category;
    private int quantity;
    private long priceSalesPerUnit;
    private long priceListPerUnit;
    private int discountPercent;
    private LocalDateTime deliverySending;

        public WarehouseSendDeliveryOrderItemDTO(Long id, String shop, String category, int quantity, long priceSalesPerUnit, long priceListPerUnit, int discountPercent, LocalDateTime deliverySending) {
        this.id = id;
        this.shop = shop;
        this.category = category;
        this.quantity = quantity;
        this.priceSalesPerUnit = priceSalesPerUnit;
        this.priceListPerUnit = priceListPerUnit;
        this.discountPercent = discountPercent;
        this.deliverySending = deliverySending;
    }
}
