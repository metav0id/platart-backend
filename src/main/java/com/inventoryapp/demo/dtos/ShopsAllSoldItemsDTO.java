package com.inventoryapp.demo.dtos;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class ShopsAllSoldItemsDTO {
    @Id
    private Long id;

    private String category;
    private Long quantity;
    private Long priceListPerUnit;
    private Long priceSalesPerUnit;
    private Long revenuePerUnit;
    private int discountPercent;
    private String shop;
    private LocalDateTime deliverySending;
    private LocalDateTime itemLastSold;
    private String comment;
}