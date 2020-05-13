package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.BarDataDTO;
import com.inventoryapp.demo.dtos.DailyReportingDTO;
import com.inventoryapp.demo.dtos.MonthToDateReportingDTO;
import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.repositories.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    DashboardRepository dashboardRepository;

    private DashboardService dashboardService;

    public DashboardService(DashboardRepository dashboardRepository){
        this.dashboardRepository = dashboardRepository;
    }

    public List<BarDataDTO> getVbarData(){
        String currentMonth = LocalDate.now().toString();
        List <ShopsAllSoldItems> soldItemsList = dashboardRepository.findByItemLastSold(currentMonth);
        return this.dashboardService.convertSoldItemsToVBarData(soldItemsList);
    }

    public List <BarDataDTO> getHbarData() {
        String yesterday = LocalDate.now().minusDays(1).toString();
        List <ShopsAllSoldItems> soldItemsList = dashboardRepository.findByItemLastSold(yesterday);
        return this.dashboardService.convertSoldItemsToHBarData(soldItemsList);
    }

    public MonthToDateReportingDTO getLastMonthData() {
        String currentMonth = LocalDate.now().minusMonths(1).toString();
        List <ShopsAllSoldItems> soldItemsList = dashboardRepository.findByItemLastSold(currentMonth);
        return this.dashboardService.convertSoldItemsToLastMonthData(soldItemsList);
    }

    public MonthToDateReportingDTO getCurrentMonthData() {
        String currentMonth = LocalDate.now().toString();
        List <ShopsAllSoldItems> soldItemsList = dashboardRepository.findByItemLastSold(currentMonth);
        return this.dashboardService.convertSoldItemsToCurrentMonthData(soldItemsList);
    }

    public DailyReportingDTO getYesterdaysData() {
        String yesterday = LocalDate.now().minusDays(1).toString();
        List <ShopsAllSoldItems> soldItemsList = dashboardRepository.findByItemLastSold(yesterday);
        return this.dashboardService.convertSoldItemsToYesterdaysData(soldItemsList);
    }

    public List<DailyReportingDTO> getActualsData() {
        String currentMonth = LocalDate.now().toString();
        List <ShopsAllSoldItems> soldItemsList = dashboardRepository.findByItemLastSold(currentMonth);
        return this.dashboardService.convertSoldItemsToActualsData(soldItemsList);
    }

    public List<BarDataDTO> convertSoldItemsToVBarData (List <ShopsAllSoldItems> shopsAllSoldItemsList) {
        return null;
    }

    public List<BarDataDTO> convertSoldItemsToHBarData (List <ShopsAllSoldItems> shopsAllSoldItemsList) {
        return null;
    }

    public MonthToDateReportingDTO convertSoldItemsToCurrentMonthData(List <ShopsAllSoldItems> shopsAllSoldItemsList) {
        return null;
    }

    public MonthToDateReportingDTO convertSoldItemsToLastMonthData(List <ShopsAllSoldItems> shopsAllSoldItemsList) {
        return null;
    }

    public DailyReportingDTO convertSoldItemsToYesterdaysData(List <ShopsAllSoldItems> shopsAllSoldItemsList){
        return null;
    }

    public List<DailyReportingDTO> convertSoldItemsToActualsData(List <ShopsAllSoldItems> shopsAllSoldItemsList){
        return null;
    }

}
