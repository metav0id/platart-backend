package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class WarehouseSupplierItemDTO {
    private String category;
    private long quantity;
//    private long pricePerUnit;
//    private long price;
    private String supplierName;
    private long priceListPerUnit;
    private long priceSupplierPerUnit;

    public WarehouseSupplierItemDTO(String category, long quantity, long priceListPerUnit, long priceSupplierPerUnit, String supplierName) {
        this.category = category;
        this.quantity = quantity;
        this.priceListPerUnit = priceListPerUnit;
        this.priceSupplierPerUnit = priceSupplierPerUnit;
        this.supplierName = supplierName;
//        this.price = price;
//        this.pricePerUnit = pricePerUnit;
    }
}
