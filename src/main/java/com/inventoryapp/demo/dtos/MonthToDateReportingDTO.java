package com.inventoryapp.demo.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MonthToDateReportingDTO {
    private String shop;
    private Long salesNo;
    private double salesTo;
    private double salesMg;
    private double salesMgAvg;
    private double discountRateAvg;
    private LocalDateTime date;
}
