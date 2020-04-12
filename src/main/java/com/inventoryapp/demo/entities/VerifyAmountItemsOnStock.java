package com.inventoryapp.demo.entities;

import lombok.Data;

@Data
public class VerifyAmountItemsOnStock {

    private String category;
    private int quantity;
    private Long pricePerUnit;
}
