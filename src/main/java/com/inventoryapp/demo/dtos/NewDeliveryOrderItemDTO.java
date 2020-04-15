package com.inventoryapp.demo.dtos;

import lombok.Data;

import javax.persistence.Id;

@Data
public class NewDeliveryOrderItemDTO {

    @Id
    private Long id;

    private String category;
    private int deliveryQuantity;
    private long deliveryDisplayPricePerUnit;
    private int deliveryDiscount;
    private long deliveryFinalPricePerUnit;
    private String deliveryShop;

    public NewDeliveryOrderItemDTO() {
    }

    public NewDeliveryOrderItemDTO(Long id, String category, int deliveryQuantity, long deliveryDisplayPricePerUnit, int deliveryDiscount, long deliveryFinalPricePerUnit) {
        this.id = id;
        this.category = category;
        this.deliveryQuantity = deliveryQuantity;
        this.deliveryDisplayPricePerUnit = deliveryDisplayPricePerUnit;
        this.deliveryDiscount = deliveryDiscount;
        this.deliveryFinalPricePerUnit = deliveryFinalPricePerUnit;
        this.deliveryShop = "ShopTest";
    }

    public NewDeliveryOrderItemDTO(Long id, String category, int deliveryQuantity, long deliveryDisplayPricePerUnit, int deliveryDiscount, long deliveryFinalPricePerUnit, String deliveryShop) {
        this.id = id;
        this.category = category;
        this.deliveryQuantity = deliveryQuantity;
        this.deliveryDisplayPricePerUnit = deliveryDisplayPricePerUnit;
        this.deliveryDiscount = deliveryDiscount;
        this.deliveryFinalPricePerUnit = deliveryFinalPricePerUnit;
        this.deliveryShop = deliveryShop;
    }
}
