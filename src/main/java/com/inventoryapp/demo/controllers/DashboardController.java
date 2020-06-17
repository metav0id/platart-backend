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
     * @return List<BarDataDTO>
     */

    @PostMapping("/getTurnoverByShop")
    public List<BarDataDTO> getTurnoverByShop(@RequestBody DateRangeDTO dateRangeDTO){
        return this.dashboardService.getTurnoverByShop(dateRangeDTO);
    }

    /**
     * returns list containing last seven days' aggregated turnover (for horizontal barchart)
     * @return List<BarDataDTO>
     */

    @PostMapping("/getTurnoverByDate")
    public List<BarDataDTO> getTurnoverByDate(@RequestBody DateRangeDTO dateRangeDTO){
        return this.dashboardService.getTurnoverByDate(dateRangeDTO);
    }

    /**
     * returns single json-object with aggregated data of last Month (central dashboard table)
     * corresponding to this months month-to-data data
     * @return MonthToDateReportingDTO
     */

    @PostMapping("/getLastMonthData")
    public MonthToDateReportingDTO getLastMonthData(@RequestBody DateRangeDTO dateRangeDTO){
        return this.dashboardService.getAggregatedData(dateRangeDTO);
    }

    /**
     * returns single json-object containing aggregated month-to-date-data of current month (central dashboard table)
     * @return MonthToDateReportingDTO
     */

    @PostMapping("/getCurrentMonthData")
    public MonthToDateReportingDTO getCurrentMonthData(@RequestBody DateRangeDTO dateRangeDTO){
        return this.dashboardService.getAggregatedData(dateRangeDTO);
    }

    /**
     * returns single json-object containing yesterdays' aggregated data (for use in Gauge-Charts)
     * @return MonthToDateReportingDTO
     */

    @PostMapping("/getYesterdaysData")
    public MonthToDateReportingDTO getYesterdaysData(@RequestBody DateRangeDTO dateRangeDTO){
         return this.dashboardService.getAggregatedData(dateRangeDTO);
    }

    /**
     * returns list containing detailed month-to-date performance aggregated on daily basis (for use in central table)
     * @return List of DailyReportingDTOs
     */

    @PostMapping("/getActualsData")
    public List <DailyReportingDTO> getActualsData(@RequestBody DateRangeDTO dateRangeDTO){
        return this.dashboardService.getActualsData(dateRangeDTO);
    }

    @PostMapping("/getDailyDataForPeriod")
    public List <DailyReportingDTO> getPeriodDetailData(@RequestBody DateRangeDTO dateRangeDTO) {
        return this.dashboardService.getActualsData(dateRangeDTO);
    }

    @PostMapping("/getAggregatedDataForPeriod")
    public MonthToDateReportingDTO getPeriodAggregatedData(@RequestBody DateRangeDTO dateRangeDTO) {
        return this.dashboardService.getAggregatedData(dateRangeDTO);
    }

    @PostMapping("/getCategoryData")
    public List <BarDataDTO> getCategoryData(@RequestBody DateRangeDTO dateRangeDTO){
        return this.dashboardService.getCategoryData(dateRangeDTO);
    }
}
