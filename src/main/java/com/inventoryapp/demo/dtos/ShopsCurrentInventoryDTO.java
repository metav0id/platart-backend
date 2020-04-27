package com.inventoryapp.demo.dtos;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class ShopsCurrentInventoryDTO {
    @Id
    private Long id;

    private String category;
    private long quantity;
    private long priceListPricePerUnit;
    private long priceSalesPricePerUnit;
    private int discountPercent;
    private String itemInShop;
    private LocalDateTime itemLastDelivery;
    private LocalDateTime itemLastSold;
}
