package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class WarehouseVerifyAmountItemsOnStockDTO {

    private String category;
    private long quantity;
    private long priceListPerUnit;
}
