package com.inventoryapp.demo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class ShopsCurrentInventory {

    @Id
    @GeneratedValue
    private Long id;

    private String category;
    private long quantity;
    private long priceListPerUnit;
    private long priceSalesPerUnit;
    private int discountPercent;
    private String shop;
    private LocalDateTime deliverySending;
    private LocalDateTime itemLastSold;

}
