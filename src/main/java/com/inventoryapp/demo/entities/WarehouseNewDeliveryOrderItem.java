package com.inventoryapp.demo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class WarehouseNewDeliveryOrderItem {

    @Id
    @GeneratedValue
    private Long id;

    private String category;
    private int quantity;
    private long priceSalesPerUnit;
    private int discountPercent;
    private long priceListPerUnit;
    private String deliveryShop;
}
