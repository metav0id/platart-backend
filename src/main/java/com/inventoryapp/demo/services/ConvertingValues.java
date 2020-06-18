package com.inventoryapp.demo.services;

import org.springframework.stereotype.Service;

@Service
public class ConvertingValues {
    public long convertDoubleToLongForDTOtoEntity(double input){
        input = input * 100;
        return Math.round(input);
    }

    public int convertDoubleToIntForDTOtoEntity(double input){
        input = input * 100;
        return (int)Math.round(input);
    }

    public double convertLongToDoubleForEntityToDTO(long input){
        double result = ((double)input) / 100;
        return result;
    }
}
