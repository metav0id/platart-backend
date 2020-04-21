package com.inventoryapp.demo.dtos;

import lombok.Data;

import java.util.List;

@Data
public class WarehouseNewDeliveryPersistanceResponseDTO {

    private boolean persistanceInitialized;
    private boolean persistanceSuccessful;
    private List<WarehouseItemPersistanceErrorDTO> itemPersistanceErrorDtoList;

}
