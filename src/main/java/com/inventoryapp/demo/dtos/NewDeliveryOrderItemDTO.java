package com.inventoryapp.demo.dtos;

import lombok.Data;

import javax.persistence.Id;

@Data

public class NewDeliveryOrderItemDTO {

    @Id
    private Long id;

    private String category;
    private int deliveryQuantity;
    private long deliveryPricePerUnit;
    private int deliveryDiscount;
    private long deliveryFinalPricePerUnit;

    public NewDeliveryOrderItemDTO() {
    }

    public NewDeliveryOrderItemDTO(Long id, String category, int deliveryQuantity, long deliveryPricePerUnit, int deliveryDiscount, long deliveryFinalPricePerUnit) {
        this.id = id;
        this.category = category;
        this.deliveryQuantity = deliveryQuantity;
        this.deliveryPricePerUnit = deliveryPricePerUnit;
        this.deliveryDiscount = deliveryDiscount;
        this.deliveryFinalPricePerUnit = deliveryFinalPricePerUnit;
    }
}
