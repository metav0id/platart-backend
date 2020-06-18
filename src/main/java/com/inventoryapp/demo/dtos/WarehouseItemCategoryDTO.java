package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class WarehouseItemCategoryDTO {
    private String category;
    private boolean isActivated;

    public WarehouseItemCategoryDTO(String category, boolean isActivated) {
        this.category = category;
        this.isActivated = isActivated;
    }

    public WarehouseItemCategoryDTO() {
    }
}
