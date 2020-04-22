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
    private long itemQuantity;
    private long itemFinalPricePerUnit;
    private long itemDisplayPricePerUnit;
    private int itemDiscount;
    private String itemInShop;
    private LocalDateTime itemLastDelivery;
    private LocalDateTime itemLastSold;

}
