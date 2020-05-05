package com.inventoryapp.demo.dtos;

import lombok.Data;

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
    private String deliverySending;
    private String itemLastSold;
    private String comment;
}
