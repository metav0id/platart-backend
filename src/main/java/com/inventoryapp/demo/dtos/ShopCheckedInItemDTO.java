package com.inventoryapp.demo.dtos;

import com.inventoryapp.demo.entities.ShopsCheckedInProductsFromWarehouse;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class ShopCheckedInItemDTO {

    private String shop;
    private String category;
    private int quantity;
    private long priceSalesPerUnit;
    private long priceListPerUnit;
    private int discountPercent;
    private LocalDateTime deliverySending;

    // Extra fields
    private String warehouseInstruction;
    private String shopComment;

        public ShopCheckedInItemDTO(String shop, String category, int quantity, long priceSalesPerUnit, long priceListPerUnit, int discountPercent, LocalDateTime deliverySending, String warehouseInstruction, String shopComment) {
        this.shop = shop;
        this.category = category;
        this.quantity = quantity;
        this.priceSalesPerUnit = priceSalesPerUnit;
        this.priceListPerUnit = priceListPerUnit;
        this.discountPercent = discountPercent;
        this.deliverySending = deliverySending;
        this.warehouseInstruction = warehouseInstruction;
        this.shopComment = shopComment;
    }
}
