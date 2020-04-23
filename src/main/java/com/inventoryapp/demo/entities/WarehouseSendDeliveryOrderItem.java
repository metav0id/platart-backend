package com.inventoryapp.demo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class WarehouseSendDeliveryOrderItem {
    @Id
    @GeneratedValue
    private Long id;

    private String shop;
    private String category;
    private int quantity;
    private long priceSalesPerUnit;
    private int discountPercent;
    private long priceListPerUnit;
    private LocalDateTime deliverySending;
}
