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

    public ShopsStockItem() {
    }

    public ShopsStockItem(String shop, String category, Long quantity, Long priceListPerUnit, Long priceSalesPerUnit){
        this.shop = shop;
        this.category = category;
        this.quantity = quantity;
        this.priceListPerUnit = priceListPerUnit;
        this.priceSalesPerUnit = priceSalesPerUnit;
    }
}
