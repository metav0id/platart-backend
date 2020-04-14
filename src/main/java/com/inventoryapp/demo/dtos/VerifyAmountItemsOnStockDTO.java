package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class VerifyAmountItemsOnStockDTO {

    private String category;
    private long quantity;
    private Long pricePerUnit;
}
