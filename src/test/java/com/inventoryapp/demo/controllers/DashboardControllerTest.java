package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.*;
import com.inventoryapp.demo.services.DashboardService;
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

@RunWith(MockitoJUnitRunner.class)
public class DashboardControllerTest {

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private DashboardController dashboardController;

    private List<BarDataDTO> barDataDTOList = new ArrayList<>();
    private List<DailyReportingDTO> dailyReportingDTOList = new ArrayList<>();
    private MonthToDateReportingDTO monthToDateReportingDTO;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        for(int i=0; i<5; i++) {
            BarDataDTO barDataDTO = new BarDataDTO();
            barDataDTO.setName("Name " + i);
            barDataDTO.setValue(10.15 + i * 2);
            barDataDTO.setDate(LocalDateTime.now().minusDays(i));
            barDataDTOList.add(barDataDTO);
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
            dailyReportingDTOList.add(dailyReportingDTO);
        }

            MonthToDateReportingDTO monthToDateReportingDTO = new MonthToDateReportingDTO();
            monthToDateReportingDTO.setShop("Ye olde Shoppe" );
            monthToDateReportingDTO.setSalesNo(123L);
            monthToDateReportingDTO.setSalesTo(123*5);
            monthToDateReportingDTO.setSalesMg(15);
            monthToDateReportingDTO.setSalesMgAvg(5.5);
            monthToDateReportingDTO.setDiscountRateAvg(11.4);
            monthToDateReportingDTO.setDate(LocalDateTime.now().minusDays(1));
    }

    @Test
    public void getVBarReporting(){
        Mockito.when(dashboardService.getVbarData()).thenReturn(barDataDTOList);

        List<BarDataDTO> barDataDTOList1 = this.dashboardController.getVbarData();
        List<BarDataDTO> barDataDTOList2 = this.dashboardController.getVbarData();

        Mockito.verify(dashboardService, Mockito.times(2)).getVbarData();
    }

    @Test
    public void getHBarReporting(){
        Mockito.when(dashboardService.getHbarData()).thenReturn(barDataDTOList);

        List<BarDataDTO> barDataDTOList1 = this.dashboardController.getHbarData();
        List<BarDataDTO> barDataDTOList2 = this.dashboardController.getHbarData();

        Mockito.verify(dashboardService, Mockito.times(2)).getHbarData();
    }

    @Test
    public void getActualsDataReporting(){
        Mockito.when(dashboardService.getActualsData()).thenReturn(dailyReportingDTOList);

        List<DailyReportingDTO> dailyReportingDTODTOList1 = this.dashboardController.getActualsData();
        List<DailyReportingDTO> dailyReportingDTODTOList2 = this.dashboardController.getActualsData();

        Mockito.verify(dashboardService, Mockito.times(2)).getActualsData();
    }

    @Test
    public void getAggregatedData(){
        LocalDateTime start = LocalDateTime.of(LocalDate.now().minusMonths(1).withDayOfMonth(1), LocalTime.MIDNIGHT);
        LocalDateTime end = LocalDateTime.of(LocalDate.now().withDayOfMonth(1), LocalTime.MIDNIGHT);
        Mockito.when(dashboardService.getAggregatedData(start, end)).thenReturn(monthToDateReportingDTO);

        MonthToDateReportingDTO monthToDateReportingDTO1 = this.dashboardController.getLastMonthData();
        MonthToDateReportingDTO monthToDateReportingDTO2 = this.dashboardController.getLastMonthData();

        Mockito.verify(dashboardService, Mockito.times(2)).getAggregatedData(start, end);
    }
}
