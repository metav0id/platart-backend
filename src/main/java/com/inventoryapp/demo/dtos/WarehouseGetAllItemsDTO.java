package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class WarehouseGetAllItemsDTO {
    private String category;
    private int quantity;
    private long pricePerUnit;

    public WarehouseGetAllItemsDTO() {
    }

    public WarehouseGetAllItemsDTO(String category, int quantity, long pricePerUnit) {
        this.category = category;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }
}
