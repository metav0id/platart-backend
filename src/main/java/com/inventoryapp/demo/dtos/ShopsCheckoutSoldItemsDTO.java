package com.inventoryapp.demo.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopsCheckoutSoldItemsDTO {
    private Long position;
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
}
