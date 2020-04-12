package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class VerifyAmountItemsOnStockDTO {

    private String category;
    private int quantity;
    private Long pricePerUnit;
}
