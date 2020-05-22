package com.inventoryapp.demo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

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

    private LocalDateTime deliverySending;

    private Long revenuePerUnit;
    private LocalDateTime itemLastSold;
    private String comment;
}
