package com.inventoryapp.demo.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopsCheckoutSoldItemsDTO {
    private Long position;
    private String category;
    private Long quantity;
    private Double priceListPerUnit;
    private Double priceSalesPerUnit;
    private Double revenuePerUnit;
    private Double discountPercent;
    private String shop;
    private LocalDateTime deliverySending;
    private LocalDateTime itemLastSold;
    private String comment;
}
