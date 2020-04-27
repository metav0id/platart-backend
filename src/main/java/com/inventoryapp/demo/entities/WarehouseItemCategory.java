package com.inventoryapp.demo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class WarehouseItemCategory {

    @Id
    @GeneratedValue
    private Long id;

    private String category;
}
