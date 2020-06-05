package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.BarDataDTO;
import com.inventoryapp.demo.dtos.BasicReportingDTO;
import com.inventoryapp.demo.dtos.DailyReportingDTO;
import com.inventoryapp.demo.dtos.MonthToDateReportingDTO;
import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.repositories.DashboardRepositoryShop;
import com.inventoryapp.demo.repositories.DashboardRepositoryWarehouse;
import com.inventoryapp.demo.repositories.ShopsAllSoldItemsRepository;
import com.inventoryapp.demo.repositories.WarehouseDeliverySupplierRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class DashboardServiceTest {

    @InjectMocks
    private DashboardService dashboardService;

    @Mock
    private WarehouseDeliverySupplierRepository warehouseDeliverySupplierRepository;

    @Mock
    private ShopsAllSoldItemsRepository shopsAllSoldItemsRepository;

    @Mock
    private DashboardRepositoryWarehouse dashboardRepositoryWarehouse;

    @Mock
    private DashboardRepositoryShop dashboardRepositoryShop;

    public List<BarDataDTO> barDataDTOList = new ArrayList<>();
    public List<BasicReportingDTO> basicReportingDTOListTest = new ArrayList<>();
    public List<ShopsAllSoldItems> shopsAllSoldItemsListTest = new ArrayList<>();

    @Before
    public void setupData(){
        MockitoAnnotations.initMocks(this);

        // bardataDTO-List  for VBarData and HBarData-Testing

        for(int i=0; i<5; i++) {
            BarDataDTO barDataDTO = new BarDataDTO();
            barDataDTO.setName("Name " + i);
            barDataDTO.setValue(10.15 + i * 2);
            barDataDTO.setDate(LocalDateTime.now().minusDays(i));
            barDataDTOList.add(barDataDTO);
        }
        BarDataDTO barDataDTO = new BarDataDTO(); // outlier added to List
        barDataDTO.setName("dummy");
        barDataDTO.setValue(1.0);
        barDataDTO.setDate(LocalDateTime.now().minusDays(2));
        barDataDTOList.add(barDataDTO);

        // shopsAllSoldItems-List for VBarData, HBarData and mappingTestShopsAllServices

        for(int i=0;i<5;i++) {
            ShopsAllSoldItems shopsAllSoldItems = new ShopsAllSoldItems();
            shopsAllSoldItems.setCategory("bijoux" + i);
            shopsAllSoldItems.setQuantity((long) i + 1L);
            shopsAllSoldItems.setPriceListPerUnit((long) i + 400L);
            shopsAllSoldItems.setPriceSalesPerUnit((long) i + 300L);
            shopsAllSoldItems.setRevenuePerUnit((long) i + 100L);
            shopsAllSoldItems.setDiscountPercent(0);
            shopsAllSoldItems.setShop("shop" + i);
            shopsAllSoldItems.setDeliverySending(LocalDateTime.of(LocalDate.now().minusDays(2L), LocalTime.MIDNIGHT));
            shopsAllSoldItems.setItemLastSold(LocalDateTime.of(LocalDate.now().minusDays(1L), LocalTime.MIDNIGHT));
            shopsAllSoldItems.setComment("comment" + i);
            shopsAllSoldItemsListTest.add(shopsAllSoldItems);
        }

        // basicReportingDTO-List for getActuals and mappingTestBasicToBarchart

        for(int i=0;i<5;i++) {
            BasicReportingDTO basicReportingDTO = new BasicReportingDTO();
            basicReportingDTO.setCategory("bijoux");
            basicReportingDTO.setSalesQuantity((long) i + 1L);
            basicReportingDTO.setShop("Ye Olde Shoppe " + i);
            basicReportingDTO.setPurchasingCost((long) i + 200L);
            basicReportingDTO.setListPrice(1000L);
            basicReportingDTO.setSalesPrice((long) i + 300L);
            basicReportingDTOListTest.add(basicReportingDTO);
        }
    }

    // Tests for corresponding Services from Dashboard-Service

    @Test
    public void getVbarData() {
        java.sql.Date hBarStart = java.sql.Date.valueOf(LocalDateTime.now().minusDays(8).toLocalDate());
        java.sql.Date hBarEnd = java.sql.Date.valueOf(LocalDateTime.now().toLocalDate());

        Mockito.when(dashboardRepositoryShop.findByItemLastSoldDateMin(hBarStart, hBarEnd)).thenReturn(shopsAllSoldItemsListTest);

        List<BarDataDTO> barDataDTOList1 = dashboardService.getVbarData();
        Assert.assertEquals(barDataDTOList1.size(), 1, 0);
    }

    @Test
    public void getHbarData() {

        // prepare

        java.sql.Date hBarStart = java.sql.Date.valueOf(LocalDateTime.now().minusDays(1).toLocalDate());
        java.sql.Date hBarEnd = java.sql.Date.valueOf(LocalDateTime.now().toLocalDate());
        Mockito.when(dashboardRepositoryShop.findByItemLastSoldDateMin(hBarStart, hBarEnd)).thenReturn(shopsAllSoldItemsListTest);

        // execute

        List<BarDataDTO> barDataDTOList1 = dashboardService.getHbarData();
        Assert.assertEquals(barDataDTOList1.size(), 5, 0);
    }

    @Test
    public void getAggregatedData() {

        // prepare

        LocalDateTime start = LocalDateTime.of(LocalDate.now().minusMonths(1).withDayOfMonth(1), LocalTime.MIDNIGHT);
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        java.sql.Date startSQL = java.sql.Date.valueOf(LocalDate.now().minusMonths(1).withDayOfMonth(1));
        java.sql.Date endSQL  = java.sql.Date.valueOf(LocalDate.now());
        Mockito.when(dashboardRepositoryShop.findByItemLastSoldDateMin(startSQL, endSQL)).thenReturn(shopsAllSoldItemsListTest);

        // execute

        MonthToDateReportingDTO monthToDateReportingDTO = dashboardService.getAggregatedData(start, end);
        Assert.assertEquals(monthToDateReportingDTO.getSalesTo(), 226.5, 0);
    }

    @Test
    public void getActuals() {
        DailyReportingDTO dRD = dashboardService.mapBasicReportingDTOToDailyReportingDTO(basicReportingDTOListTest.get(0));
        Assert.assertEquals(dRD.getSalesMg(), 1.0, 0.0);
    }

    @Test
    public void mappingTestBasicToBarchart() {
        DailyReportingDTO dRD = dashboardService.mapBasicReportingDTOToDailyReportingDTO(basicReportingDTOListTest.get(0));
        Assert.assertEquals(dRD.getSalesMg(), 1.0, 0.0);
    }

    // mapper shopsallsolditems to basicreporting

    @Test
    public void mappingTestShopsAllServices(){
        basicReportingDTOListTest = shopsAllSoldItemsListTest.stream().map(x -> dashboardService.mapShopsAllSoldToBasicReporting(x))
                .collect(Collectors.toList());
        Assert.assertEquals(basicReportingDTOListTest.size(),5);
    }

}

