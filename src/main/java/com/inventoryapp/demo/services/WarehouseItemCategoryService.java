package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.WarehouseItemCategoryDTO;
import com.inventoryapp.demo.entities.WarehouseItemCategory;
import com.inventoryapp.demo.repositories.WarehouseItemCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseItemCategoryService {

    @Autowired
    private WarehouseItemCategoryRepository warehouseItemCategoryRepository;

    public List<WarehouseItemCategoryDTO> getAllCategories() {
        List<WarehouseItemCategory> allCategoryEntities = this.warehouseItemCategoryRepository.findAll();
        return mapEntityToDtoList(allCategoryEntities);
    }

    public List<WarehouseItemCategoryDTO> getActivatedCategories(){
        List<WarehouseItemCategory> allActivatedCategories = this.warehouseItemCategoryRepository.findActivatedCategories();
        return mapEntityToDtoList(allActivatedCategories);
    }

    public List<WarehouseItemCategoryDTO> getDeactivatedCategories(){
        List<WarehouseItemCategory> allDeactivatedCategories = this.warehouseItemCategoryRepository.findDeactivatedCategories();
        return mapEntityToDtoList(allDeactivatedCategories);
    }

    private List<WarehouseItemCategoryDTO> mapEntityToDtoList(List<WarehouseItemCategory> listEntity){
        List<WarehouseItemCategoryDTO> listDTO = new ArrayList<>();
        for(WarehouseItemCategory entity : listEntity){
            listDTO.add(new WarehouseItemCategoryDTO(entity.getCategory(), entity.isActivated()));
        }
        return listDTO;
    }

    private WarehouseItemCategory mapDtoToEntity(WarehouseItemCategoryDTO categoryDto){
        return new WarehouseItemCategory(categoryDto.getCategory(), categoryDto.isActivated());
    }

    public void saveNewCategory(WarehouseItemCategoryDTO newCategory){
        if(!this.warehouseItemCategoryRepository.existsCategoryByName(newCategory.getCategory())){
            newCategory.setActivated(true);
            WarehouseItemCategory categoryEntity = mapDtoToEntity(newCategory);
            this.warehouseItemCategoryRepository.save(categoryEntity);
        } else {
            System.out.println("Category could not be saved: " + newCategory.getCategory());
        }
    }

    public void deactivateCategory(WarehouseItemCategoryDTO categoryToBeDeactivatedDTO) {
        this.warehouseItemCategoryRepository.deactivateCategory(categoryToBeDeactivatedDTO.getCategory());
    }

    public void activateCategory(WarehouseItemCategoryDTO categoryToBeActivatedDTO){
        this.warehouseItemCategoryRepository.activateCategory(categoryToBeActivatedDTO.getCategory());
    }
}
