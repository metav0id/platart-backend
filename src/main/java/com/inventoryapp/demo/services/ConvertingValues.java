package com.inventoryapp.demo.services;

import org.springframework.stereotype.Service;

@Service
public class ConvertingValues {
    public long convertDoubleToLongForDTOtoEntity(Double input){
        input = input * 100;
        return input.longValue();
    }

    public int convertDoubleToIntForDTOtoEntity(Double input){
        input = input * 100;
        return input.intValue();
    }

    public double convertLongToDoubleForEntityToDTO(long input){
        return input/100;
    }
}
