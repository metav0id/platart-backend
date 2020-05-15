package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.BarDataDTO;
import com.inventoryapp.demo.dtos.BasicReportingDTO;
import com.inventoryapp.demo.dtos.DailyReportingDTO;
import com.inventoryapp.demo.dtos.MonthToDateReportingDTO;
import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.repositories.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private final DashboardRepository dashboardRepository;

    private DashboardService dashboardService;

    public DashboardService(DashboardRepository dashboardRepository){
        this.dashboardRepository = dashboardRepository;
    }

    // Corresponding Service Methods for Controller-Methods

    /**
     *
     * @return
     */

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
        this.dashboardService.convertSoldItemsToBasicReportingList(soldItemsList);
        return this.dashboardService.convertSoldItemsToYesterdaysData(soldItemsList);
    }

    public List<DailyReportingDTO> getActualsData() {
        String currentMonth = LocalDate.now().toString();
        List <ShopsAllSoldItems> soldItemsList = dashboardRepository.findByItemLastSold(currentMonth);
        return this.dashboardService.convertSoldItemsToActualsData(soldItemsList);
    }

    // Internal conversion methods transform data into Dashboard DTOs

    /**
     * Returns List of Shops with yesterday's turnover
     * @param basicReportingDTOList
     * @return BarDataDto
     */
    public List <BarDataDTO> convertSoldItemsToVBarData (List <BasicReportingDTO> basicReportingDTOList) {

    }

    public List<BarDataDTO> convertSoldItemsToHBarData (List <ShopsAllSoldItems> shopsAllSoldItemsList) {
        return null;
    }

    public MonthToDateReportingDTO convertSoldItemsToCurrentMonthData(List <ShopsAllSoldItems> shopsAllSoldItemsList) {
        return null;
    }

    public MonthToDateReportingDTO convertSoldItemsToLastMonthData(List <BasicReportingDTO> basicReportingDTOList) {
        return null;
    }

    /**
     * Provides yesterday's Data based on previous months' reporting
     * @param basicReportingDTOList Aggregated List of Sales
     * @return DailyReportingDTO
     */

    public DailyReportingDTO convertBasicReportingDTOToYesterdaysData(List <BasicReportingDTO> basicReportingDTOList){
        DailyReportingDTO dailyReportingDTO = new DailyReportingDTO();
        dailyReportingDTO.setShop("");
        dailyReportingDTO.setDate(LocalDate.now().minusDays(1).toString());
        dailyReportingDTO.setSalesNo(basicReportingDTOList.stream().
                map(x -> x.getSalesQuantity()).reduce(0L, (a, b) -> a + b));
        dailyReportingDTO.setSalesTo(basicReportingDTOList.stream().
                map(x -> x.getSalesPrice()).reduce(0.0, (a, b) -> a + b));
        dailyReportingDTO.setListPr(basicReportingDTOList.stream().
                map(x -> x.getListPrice()).reduce(0.0, (a, b) -> a + b));
        dailyReportingDTO.setDiscountRateAvg((1.0-(dailyReportingDTO.getSalesTo()/dailyReportingDTO.getListPr()))*100);
        dailyReportingDTO.setPurchCo(dailyReportingDTO.getListPr()); // todo: purchasing price required
        dailyReportingDTO.setSalesMg(dailyReportingDTO.getSalesTo()-dailyReportingDTO.getPurchCo());
        dailyReportingDTO.setSalesMgAvg(dailyReportingDTO.getSalesMg()/dailyReportingDTO.getSalesNo());
        return dailyReportingDTO;
    }

    public List<DailyReportingDTO> convertSoldItemsToActualsData(List <ShopsAllSoldItems> shopsAllSoldItemsList){
        return null;
    }

    // Internal conversion methods to aggregate necessary data from different sources

    public List<BasicReportingDTO> aggregateReportingDataCurrentMonth(){
        return null;
    }

    public List<BasicReportingDTO> aggregateReportingDataPreviousMonth(){
        return null;
    }

    // utility methods

    /**
     * Predicate function to use for filtering java-stream by date.
     * Put in separate method to improve readability of code
     *
     * @param localdate defines the reuired range (all dates after param)
     * @return predicate
     */

    public static Predicate<BasicReportingDTO> dateIsInRange(LocalDate localdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        return p -> LocalDate.parse(p.getDate(), formatter).isAfter(localdate);
    }

}

