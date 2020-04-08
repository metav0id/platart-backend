package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class WarehouseSupplierItemDTO {
    private String category;
    private long quantity;
    private long pricePerUnit;
    private long price;
    private String supplierName;

    public WarehouseSupplierItemDTO(String category, long quantity, long pricePerUnit, long price, String supplierName) {
        this.category = category;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.price = price;
        this.supplierName = supplierName;
    }
}
