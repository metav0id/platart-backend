package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class DailyReportingDTO {
    private String shop;
    private int salesNumber;
    private double salesTurnover;
    private double purchasingCost;
    private double listPrice;
    private double salesMargin;
    private double salesMarginAverage;
    private double discountRateAverage;
    private String date;
}
