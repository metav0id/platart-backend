package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class WarehouseGetAllItemsDTO {
    private String category;
    private int quantitiy;
    private long pricePerUnit;

    public WarehouseGetAllItemsDTO() {
    }
        public WarehouseGetAllItemsDTO (String category, int quantitiy, long pricePerUnit){
        this.category = category;
        this.quantitiy = quantitiy;
        this.pricePerUnit = pricePerUnit;
    }
}
