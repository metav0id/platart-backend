package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class WarehouseItemPersistanceErrorDTO {

    private String category;
    private Long priceListPricePerUnit;
    private Long errorQuantity;

}
