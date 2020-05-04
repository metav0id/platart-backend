package com.inventoryapp.demo.entities;

import lombok.Data;

import javax.persistence.*;
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
    private long priceListPerUnit;
    private int discountPercent;
    private LocalDateTime deliverySending;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="checkin_id", referencedColumnName = "id")
    private ShopsCheckedInProductsFromWarehouse shopsCheckedInProductsFromWarehouse;

}
