package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.VerifyAmountItemsOnStockDTO;
import com.inventoryapp.demo.entities.WarehouseStockItem;
import com.inventoryapp.demo.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseVerifyAmountItemsOnStockService {

    private WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseVerifyAmountItemsOnStockService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public VerifyAmountItemsOnStockDTO verifyAmountItemsOnStock(VerifyAmountItemsOnStockDTO verifyAmountItemsOnStockDTO){
        System.out.println("verifyAmountItemsOnStock-test"+ verifyAmountItemsOnStockDTO);

        WarehouseStockItem amountVerificationItem
                = this.warehouseRepository.findItemByCategoryAndPricePerUnit(
                        verifyAmountItemsOnStockDTO.getCategory(),
                        verifyAmountItemsOnStockDTO.getPricePerUnit()
                );

        if (amountVerificationItem != null) {
            verifyAmountItemsOnStockDTO.setQuantity(amountVerificationItem.getQuantity());
            return verifyAmountItemsOnStockDTO;
        } else {
            System.out.println("No elements of category found.");
            verifyAmountItemsOnStockDTO.setQuantity(0);
            return verifyAmountItemsOnStockDTO;
        }
    }

}
