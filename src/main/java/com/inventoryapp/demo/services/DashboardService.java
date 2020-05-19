package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.BarDataDTO;
import com.inventoryapp.demo.dtos.BasicReportingDTO;
import com.inventoryapp.demo.dtos.DailyReportingDTO;
import com.inventoryapp.demo.dtos.MonthToDateReportingDTO;
import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.DashboardRepositoryWarehouse;
import com.inventoryapp.demo.repositories.DashboardRepositoryShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private final DashboardRepositoryShop dashboardRepositoryShop;

    @Autowired
    private final DashboardRepositoryWarehouse dashboardRepositoryWarehouse;

    private DashboardService dashboardService;

    public DashboardService(DashboardRepositoryShop dashboardRepositoryShop, DashboardRepositoryWarehouse dashboardRepositoryWarehouse){
        this.dashboardRepositoryShop = dashboardRepositoryShop;
        this.dashboardRepositoryWarehouse = dashboardRepositoryWarehouse;
    }

    // Corresponding Service Methods for Controller-Methods

    /**
     *
     * @return
     */

    public List<BarDataDTO> getVbarData(){
        LocalDateTime start = LocalDateTime.now().minusDays(8);
        LocalDateTime end = LocalDateTime.now();
        List <BasicReportingDTO> basicReportingDTOS = dashboardService.extractDataByDate(start, end);
        Map <LocalDateTime, Long> turnoverMap = basicReportingDTOS.stream().
                collect(Collectors.groupingBy(p -> p.getDate(),
                        Collectors.summingLong(p -> p.getSalesQuantity()*p.getSalesPrice())));
        List<BarDataDTO> barDataDTOs = new ArrayList<>();
        for (long i = 0; i<turnoverMap.size(); i++) {
            BarDataDTO newEntry = new BarDataDTO();
            newEntry.setDate(LocalDateTime.now().minusDays(7-i));
            newEntry.setName(newEntry.getDate().getDayOfWeek().toString());
            newEntry.setValue(turnoverMap.get(newEntry.getDate()));
            barDataDTOs.add(newEntry);
        }
        return barDataDTOs;
    }

    public List <BarDataDTO> getHbarData() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        List <BasicReportingDTO> basicReportingDTOS = dashboardService.extractDataByDate(start, end);
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

    public MonthToDateReportingDTO getLastMonthData() {
        LocalDateTime start = LocalDateTime.now().minusMonths(1).withDayOfMonth(1);
        LocalDateTime end = LocalDateTime.now().minusMonths(1);
        List <BasicReportingDTO> basicReportingDTOS = dashboardService.extractDataByDate(start, end);
        BasicReportingDTO lastMonthAggregated = new BasicReportingDTO();
        lastMonthAggregated.setSalesPrice(basicReportingDTOS.stream().map(x -> x.getSalesPrice()).reduce(0L, (a,b) -> a+b));
        lastMonthAggregated.setSalesQuantity(basicReportingDTOS.stream().map(x -> x.getSalesQuantity()).reduce(0L, (a,b) -> a+b));
        lastMonthAggregated.setListPrice(basicReportingDTOS.stream().map(x -> x.getListPrice()).reduce(0L, (a,b) -> a+b));
        lastMonthAggregated.setPurchasingCost(basicReportingDTOS.stream().map(x -> x.getPurchasingCost()).reduce(0L, (a,b) -> a+b));
        lastMonthAggregated.setDate(LocalDateTime.now());
        lastMonthAggregated.setShop("");
        lastMonthAggregated.setCategory("");
        return dashboardService.mapBasicReportingToMonthToDateReporting(lastMonthAggregated);
    }

    public MonthToDateReportingDTO getCurrentMonthData() {
        LocalDateTime start = LocalDateTime.now().withDayOfMonth(1);
        LocalDateTime end = LocalDateTime.now();
        List <BasicReportingDTO> basicReportingDTOS = dashboardService.extractDataByDate(start, end);
        BasicReportingDTO lastMonthAggregated = new BasicReportingDTO();
        lastMonthAggregated.setSalesPrice(basicReportingDTOS.stream().map(x -> x.getSalesPrice()).reduce(0L, (a,b) -> a+b));
        lastMonthAggregated.setSalesQuantity(basicReportingDTOS.stream().map(x -> x.getSalesQuantity()).reduce(0L, (a,b) -> a+b));
        lastMonthAggregated.setListPrice(basicReportingDTOS.stream().map(x -> x.getListPrice()).reduce(0L, (a,b) -> a+b));
        lastMonthAggregated.setPurchasingCost(basicReportingDTOS.stream().map(x -> x.getPurchasingCost()).reduce(0L, (a,b) -> a+b));
        lastMonthAggregated.setDate(LocalDateTime.now());
        lastMonthAggregated.setShop("");
        lastMonthAggregated.setCategory("");
        return dashboardService.mapBasicReportingToMonthToDateReporting(lastMonthAggregated);
    }

    public DailyReportingDTO getYesterdaysData() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        List <BasicReportingDTO> basicReportingDTOS = dashboardService.extractDataByDate(start, end);
        BasicReportingDTO lastMonthAggregated = new BasicReportingDTO();
        lastMonthAggregated.setSalesPrice(basicReportingDTOS.stream().map(x -> x.getSalesPrice()).reduce(0L, (a,b) -> a+b));
        lastMonthAggregated.setSalesQuantity(basicReportingDTOS.stream().map(x -> x.getSalesQuantity()).reduce(0L, (a,b) -> a+b));
        lastMonthAggregated.setListPrice(basicReportingDTOS.stream().map(x -> x.getListPrice()).reduce(0L, (a,b) -> a+b));
        lastMonthAggregated.setPurchasingCost(basicReportingDTOS.stream().map(x -> x.getPurchasingCost()).reduce(0L, (a,b) -> a+b));
        lastMonthAggregated.setDate(LocalDateTime.now());
        lastMonthAggregated.setShop("");
        lastMonthAggregated.setCategory("");
        return dashboardService.mapBasicReportingDTOToDailyReportingDTO(lastMonthAggregated);
    }

    public List<DailyReportingDTO> getActualsData() {
        LocalDateTime start = LocalDateTime.now().withDayOfMonth(1);
        LocalDateTime end = LocalDateTime.now();
        List <BasicReportingDTO> basicReportingDTOS = dashboardService.extractDataByDate(start, end);
        List<DailyReportingDTO> list = new ArrayList<>();
        for (int i = 0; i<basicReportingDTOS.size(); i++){

        }
        return null;

    }

    // Mapper methods to transform data into Dashboard DTOs

    /**
     * Returns List of Shops with yesterday's turnover
     * @param basicReportingDTOS
     * @return BarDataDto
     */
    public List <BarDataDTO> mapBasicReportingToVBarData (List <BasicReportingDTO> basicReportingDTOS) {
        List <BarDataDTO> list = new ArrayList<>();
        for (BasicReportingDTO basicReportingDTO : basicReportingDTOS) {
            BarDataDTO barDataDTO = new BarDataDTO();
            barDataDTO.setName(basicReportingDTO.getShop());
            barDataDTO.setValue(basicReportingDTO.getSalesPrice() * basicReportingDTO.getSalesQuantity());
            barDataDTO.setDate(LocalDateTime.now().minusDays(1));
            list.add(barDataDTO);
        }
        return list;
    }

    public List<BarDataDTO> mapBasicReportingToHBarData (List <BasicReportingDTO> basicReportingDTOS) {
        List <BarDataDTO> list = new ArrayList<>();
        for (BasicReportingDTO basicReportingDTO : basicReportingDTOS) {
            BarDataDTO barDataDTO = new BarDataDTO();
            barDataDTO.setName(basicReportingDTO.getShop());
            barDataDTO.setValue(basicReportingDTO.getSalesPrice() * basicReportingDTO.getSalesQuantity());
            barDataDTO.setDate(LocalDateTime.now().minusDays(1));
            list.add(barDataDTO);
        }
        return list;
    }

    public MonthToDateReportingDTO mapBasicReportingToMonthToDateReporting(BasicReportingDTO basicReportingDTO) {
        MonthToDateReportingDTO monthToDateReportingDTO = new MonthToDateReportingDTO();
        monthToDateReportingDTO.setShop(basicReportingDTO.getShop());
        monthToDateReportingDTO.setSalesNo(basicReportingDTO.getSalesQuantity());
        monthToDateReportingDTO.setSalesTo((basicReportingDTO.getSalesPrice().doubleValue()/100)*monthToDateReportingDTO.getSalesNo());
        monthToDateReportingDTO.setSalesMg(monthToDateReportingDTO.getSalesTo()-(basicReportingDTO.getPurchasingCost().doubleValue()
                *basicReportingDTO.getSalesQuantity())/100);
        monthToDateReportingDTO.setSalesMgAvg(monthToDateReportingDTO.getSalesMg()/monthToDateReportingDTO.getSalesNo());
        monthToDateReportingDTO.setDiscountRateAvg(100-(basicReportingDTO.getPurchasingCost()/basicReportingDTO.getListPrice())*100);
        monthToDateReportingDTO.setDate(basicReportingDTO.getDate());
        return monthToDateReportingDTO;
    }

    /**
     * Provides yesterday's Data based on previous months' reporting
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

    public List<DailyReportingDTO> convertSoldItemsToActualsData(List <ShopsAllSoldItems> shopsAllSoldItemsList){
        return null;
    }

    // Internal methods to aggregate necessary data from different sources

    public List<ShopsAllSoldItems> aggregateReportingData(LocalDateTime localDateTimeStart, LocalDateTime localDateTimeEnd){
        return dashboardService.dashboardRepositoryShop
                .findByItemLastSoldDateMin(localDateTimeStart, localDateTimeEnd);
    }

    public BasicReportingDTO mapShopsAllSoldToBasicReporting (ShopsAllSoldItems shopsAllSoldItems) {
        BasicReportingDTO basicReportingDTO = new BasicReportingDTO();
        basicReportingDTO.setShop(shopsAllSoldItems.getShop());
        basicReportingDTO.setDate(shopsAllSoldItems.getItemLastSold());
        basicReportingDTO.setSalesQuantity(shopsAllSoldItems.getQuantity());
        basicReportingDTO.setListPrice(shopsAllSoldItems.getPriceListPerUnit());
        basicReportingDTO.setSalesPrice(shopsAllSoldItems.getPriceSalesPerUnit());
        basicReportingDTO.setCategory(shopsAllSoldItems.getCategory());
        basicReportingDTO.setPurchasingCost(dashboardService.getPurchasingPrice(basicReportingDTO));
        return basicReportingDTO;
    }

    /**
     * Method to extract purchasing price for specific item
     *
     * @param basicReportingDTO single Sale
     * @return estimated purchasing price (items are aggregated, so is return value)
     */

    public Long getPurchasingPrice(BasicReportingDTO basicReportingDTO){
        List<WarehouseSupplierItem> warehouseSupplierItems = dashboardRepositoryWarehouse
                .findByCategoryAndAndPriceListPerUnit(basicReportingDTO.getCategory(),
                        basicReportingDTO.getListPrice());
        OptionalDouble purchPrOptional = warehouseSupplierItems.stream().mapToLong(x -> x.getPriceSupplierPerUnit()).average();
        Double value = purchPrOptional.getAsDouble();
        return value.longValue();
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
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
//        return p -> LocalDate.parse(p.getDate(), formatter).isAfter(localdate);
        return null;
    }

    public List<BasicReportingDTO> extractDataByDate(LocalDateTime start, LocalDateTime end) {
        List <ShopsAllSoldItems> soldItemsList = dashboardService.aggregateReportingData(start, end);
        return soldItemsList.stream().map(x -> dashboardService.mapShopsAllSoldToBasicReporting(x))
                .collect(Collectors.toList());
    }

}

