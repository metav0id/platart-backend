package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.*;
import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.DashboardRepositoryShop;
import com.inventoryapp.demo.repositories.DashboardRepositoryWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    DashboardRepositoryShop dashboardRepositoryShop;

    @Autowired
    DashboardRepositoryWarehouse dashboardRepositoryWarehouse;

//    DashboardService dashboardService;

    public DashboardService(DashboardRepositoryShop dashboardRepositoryShop, DashboardRepositoryWarehouse dashboardRepositoryWarehouse){
        this.dashboardRepositoryShop = dashboardRepositoryShop;
        this.dashboardRepositoryWarehouse = dashboardRepositoryWarehouse;
    }

    public DashboardService(){

    }

    // Corresponding Service Methods for Controller-Methods

    /**
     * returns aggregated turnover for previous 7 days in List to be utilized in vertical Barchart
     * @return List<BarDataDTO>
     */

    public List<BarDataDTO> getTurnoverByDate(DateRangeDTO dateRangeDTO){
        DashboardService db = new DashboardService();
        LocalDateTime start = db.typescriptDateConversion(dateRangeDTO.getStartDate());
        LocalDateTime end = db.typescriptDateConversion(dateRangeDTO.getEndDate());
        List <BasicReportingDTO> basicReportingDTOS = this.extractDataByDate(start, end);
        Map <LocalDateTime, Long> turnoverMap = basicReportingDTOS.stream().
                collect(Collectors.groupingBy(p -> p.getDate(),
                        Collectors.summingLong(p -> p.getSalesQuantity()*p.getSalesPrice())));
        List<BarDataDTO> barDataDTOs = new ArrayList<>();
        for (long i = 0; i<turnoverMap.size(); i++) {
            BarDataDTO newEntry = new BarDataDTO();
            newEntry.setDate(LocalDateTime.now().minusDays(7-i));
            newEntry.setName(newEntry.getDate().getDayOfWeek().toString());
            if (turnoverMap.get(newEntry.getDate()) != null) {
                newEntry.setValue(turnoverMap.get(newEntry.getDate()));
            } else {
                newEntry.setValue(0.0);
            }
            barDataDTOs.add(newEntry);
        }
        return barDataDTOs;
    }

    /**
     * returns turnover ranked by shop for previous day to be utilized in horizontal Barchart
     * @return List <BarDataDTO>
     */

    public List <BarDataDTO> getTurnoverByShop(DateRangeDTO dateRangeDTO) {
        DashboardService db = new DashboardService();
        LocalDateTime start = db.typescriptDateConversion(dateRangeDTO.getStartDate());
        LocalDateTime end = db.typescriptDateConversion(dateRangeDTO.getEndDate());
        List <BasicReportingDTO> basicReportingDTOS = this.extractDataByDate(start, end);
        Map <String, Long> turnoverMap = basicReportingDTOS.stream().
                collect(Collectors.groupingBy(p -> p.getShop(),
                        Collectors.summingLong(p -> p.getSalesQuantity()*p.getSalesPrice())));
        List<BarDataDTO> barDataDTOs = new ArrayList<>();
        List<String> turnoverKeyList= new ArrayList<>(turnoverMap.keySet());

        for (int i = 0; i<turnoverMap.size(); i++) {
            BarDataDTO newEntry = new BarDataDTO();
            newEntry.setDate(LocalDateTime.now());
            newEntry.setName(turnoverKeyList.get(i));
            newEntry.setValue(turnoverMap.get(newEntry.getName()));
            barDataDTOs.add(newEntry);
        }
        return barDataDTOs;
    }

    /**
     * returns numbers for required time-period aggregated to one object for use in various reportings
     * @param dateRangeDTO queried time-period
     * @return MonthToDateReportingDTO
     */

    public MonthToDateReportingDTO getAggregatedData(DateRangeDTO dateRangeDTO) {
        DashboardService db = new DashboardService();
        LocalDateTime start = db.typescriptDateConversion(dateRangeDTO.getStartDate());
        LocalDateTime end = db.typescriptDateConversion(dateRangeDTO.getEndDate());
        List <BasicReportingDTO> basicReportingDTOS = this.extractDataByDate(start, end);
        BasicReportingDTO aggregatedData = new BasicReportingDTO();
        aggregatedData.setSalesPrice(basicReportingDTOS.stream().map(x -> x.getSalesPrice()).reduce(0L, (a,b) -> a+b));
        aggregatedData.setSalesQuantity(basicReportingDTOS.stream().map(x -> x.getSalesQuantity()).reduce(0L, (a,b) -> a+b));
        aggregatedData.setListPrice(basicReportingDTOS.stream().map(x -> x.getListPrice()).reduce(0L, (a,b) -> a+b));
        aggregatedData.setPurchasingCost(basicReportingDTOS.stream().map(x -> x.getPurchasingCost()).reduce(0L, (a,b) -> a+b));
        aggregatedData.setDate(LocalDateTime.now());
        aggregatedData.setShop("");
        aggregatedData.setCategory("");
        return this.mapBasicReportingToMonthToDateReporting(aggregatedData);
    }

    /**
     * returns numbers for previous day to be utilized in gauge-charts
     * @return DailyReportingDTO
     */

    public List<DailyReportingDTO> getActualsData(DateRangeDTO dateRangeDTO) {
        DashboardService db = new DashboardService();
        LocalDateTime start = db.typescriptDateConversion(dateRangeDTO.getStartDate());
        LocalDateTime end = db.typescriptDateConversion(dateRangeDTO.getEndDate());
        List <BasicReportingDTO> basicReportingDTOS = db.extractDataByDate(start, end);
        List<DailyReportingDTO> list = new ArrayList<>();
        list = basicReportingDTOS.stream().map(x -> db.mapBasicReportingDTOToDailyReportingDTO(x)).collect(Collectors.toList());
        return list;
    }

    public List<BarDataDTO> getCategoryData(DateRangeDTO dateRangeDTO) {
        DashboardService db = new DashboardService();
        LocalDateTime start = db.typescriptDateConversion(dateRangeDTO.getStartDate());
        LocalDateTime end = db.typescriptDateConversion(dateRangeDTO.getEndDate());
        List <BasicReportingDTO> basicReportingDTOS = db.extractDataByDate(start, end);
        Map <String, Long> categoryMap = basicReportingDTOS.stream().
                collect(Collectors.groupingBy(p -> p.getCategory(),
                        Collectors.summingLong(p -> p.getSalesQuantity())));
        List <BarDataDTO> list = new ArrayList<>();
        List<String> categoryKeyList= new ArrayList<>(categoryMap.keySet());
        for (int i = 0; i<categoryMap.size(); i++) {
            BarDataDTO newEntry = new BarDataDTO();
            newEntry.setDate(LocalDateTime.now());
            newEntry.setName(categoryKeyList.get(i));
            newEntry.setValue(categoryMap.get(newEntry.getName()));
            list.add(newEntry);
        }
        return list;
    }

    // Mapper methods to transform data into Dashboard DTOs

    /**
     * mapper to convert Basic-Reporting to MonthToDateReportingDTO
     * @param basicReportingDTO - source
     * @return MonthToDateReportingDTO
     */


    public MonthToDateReportingDTO mapBasicReportingToMonthToDateReporting(BasicReportingDTO basicReportingDTO) {
        MonthToDateReportingDTO monthToDateReportingDTO = new MonthToDateReportingDTO();
        monthToDateReportingDTO.setShop(basicReportingDTO.getShop());
        monthToDateReportingDTO.setSalesNo(basicReportingDTO.getSalesQuantity());
        monthToDateReportingDTO.setSalesTo((basicReportingDTO.getSalesPrice().doubleValue()/100)*monthToDateReportingDTO.getSalesNo());
        monthToDateReportingDTO.setSalesMg(monthToDateReportingDTO.getSalesTo()-(basicReportingDTO.getPurchasingCost().doubleValue()
                *basicReportingDTO.getSalesQuantity())/100);
        monthToDateReportingDTO.setSalesMgAvg(monthToDateReportingDTO.getSalesMg()/monthToDateReportingDTO.getSalesNo());
        try{
            monthToDateReportingDTO.setDiscountRateAvg(100-(basicReportingDTO.getPurchasingCost()/basicReportingDTO.getListPrice())*100);
        } catch (ArithmeticException ae) {
            monthToDateReportingDTO.setDiscountRateAvg(0.0);
        }
        monthToDateReportingDTO.setDate(basicReportingDTO.getDate());
        return monthToDateReportingDTO;
    }

    /**
     * mapper to convert BasicReportingDTO into DailyReportingDTO
     * @param basicReportingDTO Aggregated List of Sales
     * @return DailyReportingDTO
     */

    public DailyReportingDTO mapBasicReportingDTOToDailyReportingDTO(BasicReportingDTO basicReportingDTO){
        DailyReportingDTO dailyReportingDTO = new DailyReportingDTO();
        dailyReportingDTO.setShop(""); // aggregated data
        dailyReportingDTO.setDate(LocalDateTime.now());
        dailyReportingDTO.setSalesNo(basicReportingDTO.getSalesQuantity());
        dailyReportingDTO.setSalesTo((basicReportingDTO.getSalesPrice().doubleValue()/100)*basicReportingDTO.getSalesQuantity());
        dailyReportingDTO.setListPr(basicReportingDTO.getListPrice().doubleValue()/100);
        dailyReportingDTO.setPurchCo(basicReportingDTO.getPurchasingCost().doubleValue()/100);
        dailyReportingDTO.setDiscountRateAvg(dailyReportingDTO.getSalesTo()/(dailyReportingDTO.getListPr()*100));
        dailyReportingDTO.setSalesMg(dailyReportingDTO.getSalesTo()-dailyReportingDTO.getPurchCo());
        dailyReportingDTO.setSalesMgAvg(dailyReportingDTO.getSalesMg()/dailyReportingDTO.getSalesNo());
        return dailyReportingDTO;
    }

    // Internal methods to aggregate necessary data from different sources

    /**
     * mapper to map shopsallsolditems to basic reporting
     * @param shopsAllSoldItems from corresponding database-table
     * @return BasicReportingDTO
     */

    public BasicReportingDTO mapShopsAllSoldToBasicReporting (ShopsAllSoldItems shopsAllSoldItems) {
        BasicReportingDTO basicReportingDTO = new BasicReportingDTO();
        basicReportingDTO.setShop(shopsAllSoldItems.getShop());
        basicReportingDTO.setDate(shopsAllSoldItems.getItemLastSold());
        basicReportingDTO.setSalesQuantity(shopsAllSoldItems.getQuantity());
        basicReportingDTO.setListPrice(shopsAllSoldItems.getPriceListPerUnit());
        basicReportingDTO.setSalesPrice(shopsAllSoldItems.getPriceSalesPerUnit());
        basicReportingDTO.setCategory(shopsAllSoldItems.getCategory());
        basicReportingDTO.setPurchasingCost(this.getPurchasingPrice(basicReportingDTO));
        return basicReportingDTO;
    }

    /**
     * Utility-Method to add purchasing price to ShopsAllSoldItems Data
     *
     * @param start, start of queried time-period
     * @param end, end of queried time-period
     * @return List<BasicReportingDTO>
     */

    public List<BasicReportingDTO> extractDataByDate(LocalDateTime start, LocalDateTime end) {

        List <ShopsAllSoldItems> soldItemsList = this.dashboardRepositoryShop.findByItemLastSoldDateMin(start, end);
        return soldItemsList.stream().map(x -> this.mapShopsAllSoldToBasicReporting(x))
                .collect(Collectors.toList());

    }

    /**
     * Utility-Method to enrich BasicReportingDTO-Data with purchasing-price from WarehouseSupplierItem-Data
     *
     * @param basicReportingDTO single Sale
     * @return estimated purchasing price (items are aggregated, so is return value)
     */

    public Long getPurchasingPrice(BasicReportingDTO basicReportingDTO){
       List<WarehouseSupplierItem> warehouseSupplierItems = dashboardRepositoryWarehouse
         .findByCategoryAndAndPriceListPerUnit(basicReportingDTO.getCategory(),
                 basicReportingDTO.getListPrice());
        OptionalDouble purchPrOptional = warehouseSupplierItems.stream().mapToLong(x -> x.getPriceSupplierPerUnit()).average();
        if (purchPrOptional.isPresent()) {
            Double value = purchPrOptional.getAsDouble();
            return value.longValue();
        }
        return 0L;
    }

    /**
     * Utility-Method to convert date delivered by Typescript to date to be handled in Java-code
     *
     * @param dateToParse
     * @return LocalDateTime-Object of submitted iso-String-Date
     */

    public LocalDateTime typescriptDateConversion(String dateToParse) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(dateToParse, formatter);
    }
}

