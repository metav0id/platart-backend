package com.inventoryapp.demo.dtos;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class MarkerDTO {

    private Long id;

    public String lat;

    public String lng;
    public String titulo ;
    public String desc;
    public String link ;
}

