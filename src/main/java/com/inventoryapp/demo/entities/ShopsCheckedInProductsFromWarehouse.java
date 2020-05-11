package com.inventoryapp.demo.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class ShopsCheckedInProductsFromWarehouse {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @OneToOne(mappedBy = "shopsCheckedInProductsFromWarehouse")
    private WarehouseSendDeliveryOrderItem warehouseSendDeliveryOrderItem;

    private Boolean isArrivedAtShop = false;
    private Boolean isAddedToStockOfShop = false;
    private LocalDateTime timestampIsArrivedAtShop;
    private LocalDateTime timestampIsAddedToStockOfShop;
    private String comment;
    private Long quantityCheckedIn;
}
