package com.inventoryapp.demo.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BasicReportingDTO {
    private String shop;
    private LocalDateTime date;
    private Long salesQuantity;
    private Long purchasingCost;
    private Long listPrice;
    private Long salesPrice;
    private String category;
}
