package com.inventoryapp.demo.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DailyReportingDTO {
    private String shop;
    private long salesNo;
    private double salesTo;
    private double purchCo;
    private double listPr;
    private double salesMg;
    private double salesMgAvg;
    private double discountRateAvg;
    private LocalDate date;
}
