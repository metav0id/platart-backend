package com.inventoryapp.demo.dtos;

import lombok.Data;

@Data
public class ComerceDTO {

    private Long id;
// from origin shows only shops
    private String category;
    private String name;
    private String address;
}
