package com.inventoryapp.demo.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SalesCheckedInProductsFromWarehouse {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @OneToOne(mappedBy = "salesCheckedInProductsFromWarehouse")
    private WarehouseSendDeliveryOrderItem warehouseSendDeliveryOrderItem;

    private Boolean isArrivedAtStore = false;
    private Boolean isAddedToStockOfShop = false;
}
