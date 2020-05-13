package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class MonthToDateReportingDTO {
    private String shop;
    private int salesNumber;
    private double salesTurnover;
    private double salesMargin;
    private double salesMarginAverage;
    private double discountRateAverage;
    private int month;
    private int year;
}
