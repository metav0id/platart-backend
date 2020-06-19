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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DashboardControllerTest {

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private DashboardController dashboardController;

    private final List<BarDataDTO> barDataDTOList = new ArrayList<>();
    private final List<DailyReportingDTO> dailyReportingDTOList = new ArrayList<>();
    private MonthToDateReportingDTO monthToDateReportingDTO;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIDNIGHT);
        DateRangeDTO dateRangeDTO = new DateRangeDTO();
        dateRangeDTO.setStartDate(startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
        dateRangeDTO.setEndDate(endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));

        for(int i=0; i<5; i++) {
            BarDataDTO barDataDTO = new BarDataDTO();
            barDataDTO.setName("Name " + i);
            barDataDTO.setValue(10.15 + i * 2);
            barDataDTO.setDate(LocalDateTime.of(LocalDate.now().minusDays(i), LocalTime.MIDNIGHT));
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
            dailyReportingDTO.setDate(LocalDate.now().minusDays(i));
            dailyReportingDTOList.add(dailyReportingDTO);
        }
    }

    public DateRangeDTO getDateRangeDTO (LocalDateTime start, LocalDateTime end) {
        DateRangeDTO dateRangeDTO = new DateRangeDTO();
        dateRangeDTO.setStartDate(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
        dateRangeDTO.setEndDate(end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
        return dateRangeDTO;
    }

    @Test
    public void getVBarReporting(){
//        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
//        LocalDateTime end = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIDNIGHT);
//        DateRangeDTO dateRangeDTO = this.getDateRangeDTO(start, end);
//
//        Mockito.when(dashboardService.getTurnoverByShop(dateRangeDTO)).thenReturn(barDataDTOList);
//
//        List<BarDataDTO> barDataDTOList1 = this.dashboardController.getTurnoverByShop(dateRangeDTO);
//        List<BarDataDTO> barDataDTOList2 = this.dashboardController.getTurnoverByShop(dateRangeDTO);
//
//        Mockito.verify(dashboardService, Mockito.times(2)).getTurnoverByShop(dateRangeDTO);
    }

    @Test
    public void getHBarReporting(){
//        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
//        LocalDateTime end = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIDNIGHT);
//        DateRangeDTO dateRangeDTO = this.getDateRangeDTO(start, end);
//
//        Mockito.when(dashboardService.getTurnoverByDate(dateRangeDTO)).thenReturn(barDataDTOList);
//
//        List<BarDataDTO> barDataDTOList1 = this.dashboardController.getTurnoverByDate(dateRangeDTO);
//        List<BarDataDTO> barDataDTOList2 = this.dashboardController.getTurnoverByDate(dateRangeDTO);
//
//        Mockito.verify(dashboardService, Mockito.times(2)).getTurnoverByDate(dateRangeDTO);
    }

    @Test
    public void getActualsDataReporting(){

//        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
//        LocalDateTime end = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIDNIGHT);
//        DateRangeDTO dateRangeDTO = this.getDateRangeDTO(start, end);
//
//        Mockito.when(dashboardService.getActualsData(dateRangeDTO)).thenReturn(dailyReportingDTOList);
//
//        List<DailyReportingDTO> dailyReportingDTODTOList1 = this.dashboardController.getPeriodDetailData(dateRangeDTO);
//        List<DailyReportingDTO> dailyReportingDTODTOList2 = this.dashboardController.getPeriodDetailData(dateRangeDTO);
//
//        Mockito.verify(dashboardService, Mockito.times(2)).getActualsData(dateRangeDTO);
    }

    @Test
    public void getAggregatedData(){
//       LocalDateTime start = LocalDateTime.of(LocalDate.now().minusMonths(1).withDayOfMonth(1), LocalTime.MIDNIGHT);
//       LocalDateTime end = LocalDateTime.of(LocalDate.now().withDayOfMonth(1), LocalTime.MIDNIGHT);
//        DateRangeDTO dateRangeDTO = this.getDateRangeDTO(start, end);
//        Mockito.when(dashboardService.getAggregatedData(dateRangeDTO)).thenReturn(monthToDateReportingDTO);
//
//        MonthToDateReportingDTO monthToDateReportingDTO1 = this.dashboardController.getPeriodAggregatedData(dateRangeDTO);
//        MonthToDateReportingDTO monthToDateReportingDTO2 = this.dashboardController.getPeriodAggregatedData(dateRangeDTO);
//
//        Mockito.verify(dashboardService, Mockito.times(2)).getAggregatedData(dateRangeDTO);
    }
}
