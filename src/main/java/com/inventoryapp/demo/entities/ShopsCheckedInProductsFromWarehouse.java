package com.inventoryapp.demo.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ShopsCheckedInProductsFromWarehouse {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @OneToOne(mappedBy = "shopsCheckedInProductsFromWarehouse")
    private WarehouseSendDeliveryOrderItem warehouseSendDeliveryOrderItem;

    private Boolean isArrivedAtStore = false;
    private Boolean isAddedToStockOfShop = false;
}
