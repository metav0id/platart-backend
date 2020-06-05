package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.BarDataDTO;
import com.inventoryapp.demo.dtos.DailyReportingDTO;
import com.inventoryapp.demo.dtos.MonthToDateReportingDTO;
import com.inventoryapp.demo.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    public DashboardController(){
        }

    /**
     * returns list containing previous days' turnover sorted by shop (for vertical barchart)
     * @return List<BarDataDTO>
     */

    @PostMapping("/getVbarData")
    public List<BarDataDTO> getVbarData(){
        return this.dashboardService.getVbarData();
    }

    /**
     * returns list containing last seven days' aggregated turnover (for horizontal barchart)
     * @return List<BarDataDTO>
     */

    @PostMapping("/getHbarData")
    public List<BarDataDTO> getHbarData(){
        return this.dashboardService.getHbarData();
    }

    /**
     * returns single json-object with aggregated data of last Month (central dashboard table)
     * corresponding to this months month-to-data data
     * @return MonthToDateReportingDTO
     */

    @PostMapping("/getLastMonthData")
    public MonthToDateReportingDTO getLastMonthData(){
        LocalDateTime start = LocalDateTime.of(LocalDate.now().minusMonths(1).withDayOfMonth(1), LocalTime.MIDNIGHT);
        LocalDateTime end = LocalDateTime.of(LocalDate.now().withDayOfMonth(1), LocalTime.MIDNIGHT);
        return this.dashboardService.getAggregatedData(start, end);
    }

    /**
     * returns single json-object containing aggregated month-to-date-data of current month (central dashboard table)
     * @return MonthToDateReportingDTO
     */

    @PostMapping("/getCurrentMonthData")
    public MonthToDateReportingDTO getCurrentMonthData(){
        LocalDateTime start = LocalDateTime.of(LocalDate.now().withDayOfMonth(1), LocalTime.MIDNIGHT);
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        return this.dashboardService.getAggregatedData(start, end);
    }

    /**
     * returns single json-object containing yesterdays' aggregated data (for use in Gauge-Charts)
     * @return MonthToDateReportingDTO
     */

    @PostMapping("/getYesterdaysData")
    public MonthToDateReportingDTO getYesterdaysData(){
        LocalDateTime start = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        return this.dashboardService.getAggregatedData(start, end);
    }

    /**
     * returns list containing detailed month-to-date performance aggregated on daily basis (for use in central table)
     * @return List of DailyReportingDTOs
     */

    @PostMapping("/getActualsData")
    public List <DailyReportingDTO> getActualsData(){
        return this.dashboardService.getActualsData();
    }
}
