package com.inventoryapp.demo.dtos;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class WarehouseGetAllItemsDTO {
    private String category;
    private long quantity;
    private Double priceListPerUnit;
    @Id
    @GeneratedValue
    private long id;
    public WarehouseGetAllItemsDTO() {
    }

    public WarehouseGetAllItemsDTO(String category, long quantity, Double priceListPerUnit) {
        this.category = category;
        this.quantity = quantity;
        this.priceListPerUnit = priceListPerUnit;
    }
}
