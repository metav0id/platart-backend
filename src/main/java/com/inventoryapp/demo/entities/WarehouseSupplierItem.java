package com.inventoryapp.demo.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class WarehouseSupplierItem {
    @Id
    @GeneratedValue
    private long id;

    private String category;
    private long quantity;
    private String supplierName;
    private Long priceListPerUnit;
    private Long priceSupplierPerUnit;
    @CreationTimestamp
    private LocalDateTime createDateTime;

    public WarehouseSupplierItem() {
    }

    public WarehouseSupplierItem(String category, long quantity, long priceSupplierPerUnit, long priceListPerUnit, String supplierName){
        this.category = category;
        this.quantity = quantity;
        this.priceSupplierPerUnit = priceSupplierPerUnit;
        this.priceListPerUnit = priceListPerUnit;
        this.supplierName = supplierName;
    }

}
