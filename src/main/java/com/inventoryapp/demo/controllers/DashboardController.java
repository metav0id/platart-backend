package com.inventoryapp.demo.controllers;

import com.inventoryapp.demo.dtos.BarDataDTO;
import com.inventoryapp.demo.dtos.DailyReportingDTO;
import com.inventoryapp.demo.dtos.DateRangeDTO;
import com.inventoryapp.demo.dtos.MonthToDateReportingDTO;
import com.inventoryapp.demo.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param dateRangeDTO containing required Date Range
     * @return List<BarDataDTO>
     */

    @PostMapping("/getTurnoverByShop")
    public List<BarDataDTO> getTurnoverByShop(@RequestBody DateRangeDTO dateRangeDTO){
        return this.dashboardService.getTurnoverByShop(dateRangeDTO);
    }

    /**
     * returns list containing last seven days' aggregated turnover (for horizontal barchart)
     * @param dateRangeDTO containing required Date Range
     * @return List<BarDataDTO>
     */

    @PostMapping("/getTurnoverByDate")
    public List<BarDataDTO> getTurnoverByDate(@RequestBody DateRangeDTO dateRangeDTO){
        return this.dashboardService.getTurnoverByDate(dateRangeDTO);
    }

    /**
     * returns list containing detailed month-to-date performance aggregated on daily basis (for use in central table)
     * @param dateRangeDTO containing required Date Range
     * @return List of DailyReportingDTOs
     */

    @PostMapping("/getDailyDataForPeriod")
    public List <DailyReportingDTO> getPeriodDetailData(@RequestBody DateRangeDTO dateRangeDTO) {
        return this.dashboardService.getActualsData(dateRangeDTO);
    }

    /**
     * returns single json-object with aggregated data of specified period (central dashboard table/gauge-charts)
     * corresponding to this months month-to-data data
     * @param dateRangeDTO containing required Date Range
     * @return MonthToDateReportingDTO
     */

    @PostMapping("/getAggregatedDataForPeriod")
    public MonthToDateReportingDTO getPeriodAggregatedData(@RequestBody DateRangeDTO dateRangeDTO) {
        return this.dashboardService.getAggregatedData(dateRangeDTO);
    }

    /**
     * returns number of sales by category for specified period
     * @param dateRangeDTO containing required Date Range
     * @return List of BarDataDTOs
     */

    @PostMapping("/getCategoryData")
    public List <BarDataDTO> getCategoryData(@RequestBody DateRangeDTO dateRangeDTO){
        return this.dashboardService.getCategoryData(dateRangeDTO);
    }
}
