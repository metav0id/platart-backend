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

    private String shop;
    private String category;
    private Long quantity;
    private Long priceSalesPerUnit;
    private Long priceListPerUnit;
    private int discountPercent;

    private String deliverySending;

    private Long revenuePerUnit;
    private String itemLastSold;
    private String comment;
}
