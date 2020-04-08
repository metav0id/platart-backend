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
    private long pricePerUnit;
    private long price;
    private String supplierName;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    public WarehouseSupplierItem() {
    }

    public WarehouseSupplierItem(String category, long quantity, long pricePerUnit, long price, String supplierName){
        this.category = category;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.price = price;
        this.supplierName = supplierName;
    }
}
