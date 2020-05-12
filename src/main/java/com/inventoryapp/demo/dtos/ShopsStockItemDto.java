package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class ShopsStockItemDto {
    private Long position;
    private String shop;
    private String category;
    private Long quantity;
    private Long priceSalesPerUnit;
    private Long priceListPerUnit;
}
