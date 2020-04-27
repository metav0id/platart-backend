package com.inventoryapp.demo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class MapMarker {
    @Id
    @GeneratedValue
    private Long id;

    public String lat;

    public String lng;
    public String category;
    public String name;
    public String address;
//    public String link ;
}
