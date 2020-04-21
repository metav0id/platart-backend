package com.inventoryapp.demo.dtos;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class MarkerDTO {

    private Long id;

    public String lat;

    public String lng;
    public String category;
    public String name;
    public String address;
    public String link ;
}

