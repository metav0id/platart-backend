package com.inventoryapp.demo.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ManagerWarehouseCheckinListingDTO {

    private String category;
    private long quantity;
    private String supplierName;
    private Double priceListPerUnit;
    private Double priceSupplierPerUnit;
    private LocalDateTime createDateTime;

    public ManagerWarehouseCheckinListingDTO(String category, long quantity, Double priceListPerUnit, Double priceSupplierPerUnit, String supplierName, LocalDateTime createDateTime) {
        this.category = category;
        this.quantity = quantity;
        this.priceListPerUnit = priceListPerUnit;
        this.priceSupplierPerUnit = priceSupplierPerUnit;
        this.supplierName = supplierName;
        this.createDateTime  = createDateTime;
    }
}
