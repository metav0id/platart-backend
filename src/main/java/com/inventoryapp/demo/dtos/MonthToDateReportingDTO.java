package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class MonthToDateReportingDTO {
    private String shop;
    private int salesNo;
    private double salesTo;
    private double salesMg;
    private double salesMgAvg;
    private double discountRateAvg;
    private String date;
}
