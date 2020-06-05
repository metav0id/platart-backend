package com.inventoryapp.demo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class ShopsAllSoldItems {
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
    private LocalDateTime deliverySending;
    private LocalDateTime itemLastSold;
    private String comment;

    public ShopsAllSoldItems() {
    }

    public ShopsAllSoldItems(
            Long id,
            String category,
            Long quantity,
            Long priceListPerUnit,
            Long priceSalesPerUnit,
            Long revenuePerUnit,
            int discountPercent,
            String shop,
            LocalDateTime deliverySending,
            LocalDateTime itemLastSold,
            String comment) {
        this.id = id;
        this.category = category;
        this.quantity = quantity;
        this.priceListPerUnit = priceListPerUnit;
        this.priceSalesPerUnit = priceSalesPerUnit;
        this.revenuePerUnit = revenuePerUnit;
        this.discountPercent = discountPercent;
        this.shop = shop;
        this.deliverySending = deliverySending;
        this.itemLastSold = itemLastSold;
        this.comment = comment;
    }
}
