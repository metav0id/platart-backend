package com.inventoryapp.demo.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BasicReportingDTO {
    private String shop;
    private LocalDateTime date;
    private Long salesQuantity;
    private Double purchasingCost;
    private Double turnoverPerSale;
    private Double listPrice;
    private Double salesPrice;
    private String category;
}
