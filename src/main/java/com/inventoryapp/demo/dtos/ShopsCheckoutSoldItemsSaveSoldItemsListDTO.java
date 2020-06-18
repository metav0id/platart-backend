package com.inventoryapp.demo.dtos;

import lombok.Data;
import java.util.List;

@Data
public class ShopsCheckoutSoldItemsSaveSoldItemsListDTO {
    private String shop;
    private List<ShopsCheckoutSoldItemsDTO> itemsDTOList;
}
