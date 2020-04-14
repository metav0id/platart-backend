package com.inventoryapp.demo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class WarehouseStockItem {

    @Id
    @GeneratedValue
    private long id;

    private String category;
    private long quantity;
    private long pricePerUnit;

    public WarehouseStockItem() {
    }

    public WarehouseStockItem(String category, long quantity, long pricePerUnit) {
        this.category = category;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }
}
