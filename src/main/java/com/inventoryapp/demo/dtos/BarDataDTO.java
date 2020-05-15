package com.inventoryapp.demo.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BarDataDTO {
    private String name;
    private double value;
    private LocalDateTime date;

}
