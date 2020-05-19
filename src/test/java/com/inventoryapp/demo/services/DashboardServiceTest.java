package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.BarDataDTO;
import com.inventoryapp.demo.dtos.BasicReportingDTO;
import com.inventoryapp.demo.dtos.DailyReportingDTO;
import com.inventoryapp.demo.dtos.MonthToDateReportingDTO;
import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.DashboardRepositoryShop;
import com.inventoryapp.demo.repositories.DashboardRepositoryWarehouse;
import com.inventoryapp.demo.repositories.ShopsAllSoldItemsRepository;
import com.inventoryapp.demo.repositories.WarehouseDeliverySupplierRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DashboardServiceTest {

    @Autowired
    private DashboardRepositoryShop dashboardRepositoryShop;

    @Autowired
    private DashboardRepositoryWarehouse dashboardRepositoryWarehouse;

    @Autowired
    private ShopsAllSoldItemsRepository shopsAllSoldItemsRepository;

    @Autowired
    private WarehouseDeliverySupplierRepository warehouseDeliverySupplierRepository;

    public List<BarDataDTO> barDataDTOListTest = new ArrayList<>();
    public List<DailyReportingDTO> dailyReportingDTOListTest = new ArrayList<>();
    public List<MonthToDateReportingDTO> monthToDateReportingDTOListTest = new ArrayList<>();
    public List<BasicReportingDTO> basicReportingDTOListTest = new ArrayList<>();
    public List<ShopsAllSoldItems> shopsAllSoldItemsListTest = new ArrayList<>();
    public List<WarehouseSupplierItem> warehouseSupplierItemListTest = new ArrayList<>();

    @Before
    public void setupData(){
        for(int i=0; i<5; i++) {
            BarDataDTO barDataDTO = new BarDataDTO();
            barDataDTO.setName("Name " + i);
            barDataDTO.setValue(10.15 + i * 2);
            barDataDTO.setDate(LocalDateTime.now().minusDays(i));
            barDataDTOListTest.add(barDataDTO);
        }
        for(int i=0;i<5;i++) {
            DailyReportingDTO dailyReportingDTO = new DailyReportingDTO();
            dailyReportingDTO.setShop("Shop " + i/2);
            dailyReportingDTO.setSalesNo(123 + i);
            dailyReportingDTO.setSalesTo((123 + i)*5);
            dailyReportingDTO.setPurchCo(103 +i);
            dailyReportingDTO.setListPr(123 + i*10);
            dailyReportingDTO.setSalesMg(15);
            dailyReportingDTO.setSalesMgAvg(5.5);
            dailyReportingDTO.setDiscountRateAvg(11.4);
            dailyReportingDTO.setDate(LocalDateTime.now().minusDays(i));
            dailyReportingDTOListTest.add(dailyReportingDTO);
        }
        for(int i=0;i<5;i++) {
            MonthToDateReportingDTO monthToDateReportingDTO = new MonthToDateReportingDTO();
            monthToDateReportingDTO.setShop("Shop " + i/2);
            monthToDateReportingDTO.setSalesNo(123L + i);
            monthToDateReportingDTO.setSalesTo((123 + i)*5);
            monthToDateReportingDTO.setSalesMg(15);
            monthToDateReportingDTO.setSalesMgAvg(5.5);
            monthToDateReportingDTO.setDiscountRateAvg(11.4);
            monthToDateReportingDTO.setDate(LocalDateTime.now().minusDays(i));
            monthToDateReportingDTOListTest.add(monthToDateReportingDTO);
        }
        for(int i=0;i<5;i++) {
            // shopsallsolditems
//            private String category;
//            private Long quantity;
//            private Long priceListPerUnit;
//            private Long priceSalesPerUnit;
//            private Long revenuePerUnit;
//            private int discountPercent;
//            private String shop;
//            private LocalDateTime deliverySending;
//            private LocalDateTime itemLastSold;
//            private String comment;
        }
        for(int i=0;i<5;i++) {
            // warehousesupplieritem
//            private String category;
//    private long quantity;
//    private String supplierName;
//    private long priceListPerUnit;
//    private long priceSupplierPerUnit;
        }
    }

    @Test
    public void checkServices(){

    }

}

