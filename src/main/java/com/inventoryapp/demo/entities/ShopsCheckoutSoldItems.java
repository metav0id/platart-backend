package com.inventoryapp.demo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ShopsCheckoutSoldItems {
    @Id
    @GeneratedValue
    private Long id;

    private String category;
    private Long quantity;
    private Long priceListPerUnit;
    private Long priceSalesPerUnit;
    private Long revenuePerUnit;
    private int discountPercent;
    private String shop;
    private String deliverySending;
    private String itemLastSold;
    private String comment;
}