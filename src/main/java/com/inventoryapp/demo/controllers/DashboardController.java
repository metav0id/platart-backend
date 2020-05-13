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

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController("")
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService){
        this.dashboardService = dashboardService;
    }

    @PostMapping("/getVbarData")
    private List<BarDataDTO> getVbarData(){
        return this.dashboardService.getVbarData();
    }

    @PostMapping("/getHbarData")
    private List<BarDataDTO> getHbarData(){
        return this.dashboardService.getHbarData();
    }

    @PostMapping("/getLastMonthData")
    private MonthToDateReportingDTO getLastMonthData(){
        return this.dashboardService.getLastMonthData();
    }

    @PostMapping("/getCurrentMonthData")
    private MonthToDateReportingDTO getCurrentMonthData(){
        return this.dashboardService.getCurrentMonthData();
    }

    @PostMapping("/getYesterdaysData")
    private DailyReportingDTO getYesterdaysData(){
        return this.dashboardService.getYesterdaysData();
    }

    @PostMapping("/getYesterdaysData")
    private List <DailyReportingDTO> getActualsData(){
        return this.dashboardService.getActualsData();
    }
}
