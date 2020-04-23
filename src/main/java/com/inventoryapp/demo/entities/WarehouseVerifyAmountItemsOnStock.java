package com.inventoryapp.demo.entities;

import lombok.Data;

@Data
public class WarehouseVerifyAmountItemsOnStock {

    private String category;
    private int quantity;
    private Long priceListPerUnit;
}
