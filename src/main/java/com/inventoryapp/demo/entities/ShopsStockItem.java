package com.inventoryapp.demo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class ShopsStockItem {
    @Id
    @GeneratedValue
    private Long id;

    private String shop;
    private String category;
    private Long quantity;
    private Long priceSalesPerUnit;
    private Long priceListPerUnit;
    private int discountPercent;
}
