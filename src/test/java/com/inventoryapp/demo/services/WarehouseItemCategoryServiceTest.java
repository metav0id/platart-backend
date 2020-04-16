package com.inventoryapp.demo.services;

import com.inventoryapp.demo.repositories.WarehouseItemCategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WarehouseItemCategoryServiceTest {

    @Autowired
    private WarehouseItemCategoryRepository warehouseItemCategoryRepository;

    @Before
    public void setUp(){

    }

    /** Test CRUD persitance of items*/
    @Test
    public void getAllCategoriesTest(){

    }

}
