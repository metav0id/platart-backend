package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class BasicReportingDTO {
    private String shop;
    private String date;
    private long salesQuantity;
    private double purchasingCost;
    private double listPrice;
    private double salesPrice;
}
