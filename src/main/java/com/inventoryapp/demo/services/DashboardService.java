package com.inventoryapp.demo.services;

import com.inventoryapp.demo.dtos.*;
import com.inventoryapp.demo.entities.ShopsAllSoldItems;
import com.inventoryapp.demo.entities.WarehouseSupplierItem;
import com.inventoryapp.demo.repositories.DashboardRepositoryShop;
import com.inventoryapp.demo.repositories.DashboardRepositoryWarehouse;
import com.inventoryapp.demo.repositories.ShopsAllSoldItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    DashboardRepositoryShop dashboardRepositoryShop;

    @Autowired
    DashboardRepositoryWarehouse dashboardRepositoryWarehouse;

    @Autowired
    ShopsAllSoldItemsRepository shopsAllSoldItemsRepository;

    // Corresponding Service Methods for Controller-Methods

    /**
     * returns aggregated turnover for previous 7 days in List to be utilized in vertical Barchart
     * @param dateRangeDTO timeframe for returned List
     * @return List<BarDataDTO>
     */

    public List<BarDataDTO> getTurnoverByDate(DateRangeDTO dateRangeDTO) {
        LocalDateTime start = typescriptDateConversion(dateRangeDTO.getStartDate());
        LocalDateTime end = typescriptDateConversion(dateRangeDTO.getEndDate());
        List<BasicReportingDTO> basicReportingDTOS = this.extractDataByDate(start, end);
        Map<LocalDateTime, Long> turnoverMap = basicReportingDTOS.stream().
                collect(Collectors.groupingBy(p -> p.getDate(),
                        Collectors.summingLong(p -> p.getTurnoverPerSale())));
        List<BarDataDTO> barDataDTOs = new ArrayList<>();
        for (Map.Entry<LocalDateTime, Long> entry : turnoverMap.entrySet()) {
            BarDataDTO newEntry = new BarDataDTO();
            newEntry.setDate(entry.getKey());
            newEntry.setValue(entry.getValue());
            newEntry.setName(newEntry.getDate().getDayOfWeek().toString());
            barDataDTOs.add(newEntry);
        }

        barDataDTOs.sort((b1, b2) -> {
            DayOfWeek d1 = DayOfWeek.valueOf(b1.getName());
            DayOfWeek d2 = DayOfWeek.valueOf(b2.getName());
            return d1.compareTo(d2);
        });

        return barDataDTOs;
        }


    /**
     * returns turnover ranked by shop for previous day to be utilized in horizontal Barchart
     * @param dateRangeDTO timeframe for returned List
     * @return List <BarDataDTO>
     */

    public List <BarDataDTO> getTurnoverByShop(DateRangeDTO dateRangeDTO) {
        LocalDateTime start = typescriptDateConversion(dateRangeDTO.getStartDate());
        LocalDateTime end = typescriptDateConversion(dateRangeDTO.getEndDate());
        List <BasicReportingDTO> basicReportingDTOS = this.extractDataByDate(start, end);
        Map <String, Long> turnoverMap = basicReportingDTOS.stream().
                collect(Collectors.groupingBy(p -> p.getShop(),
                        Collectors.summingLong(p -> p.getTurnoverPerSale())));
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
        LocalDateTime start = typescriptDateConversion(dateRangeDTO.getStartDate());
        LocalDateTime end = typescriptDateConversion(dateRangeDTO.getEndDate());
        List <BasicReportingDTO> basicReportingDTOS = this.extractDataByDate(start, end);
        BasicReportingDTO aggregatedData = new BasicReportingDTO();
        aggregatedData.setTurnoverPerSale(basicReportingDTOS.stream().map(x -> x.getSalesQuantity() * x.getSalesPrice()).reduce(0L, (a,b) -> a+b));
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
     * @param dateRangeDTO timeframe for returned List
     * @return DailyReportingDTO
     */

    public List<DailyReportingDTO> getActualsData(DateRangeDTO dateRangeDTO) {
        DashboardService db = new DashboardService();
        LocalDateTime start = typescriptDateConversion(dateRangeDTO.getStartDate());
        LocalDateTime end = typescriptDateConversion(dateRangeDTO.getEndDate());
        List <BasicReportingDTO> basicReportingDTOS = this.extractDataByDate(start, end);
        List<DailyReportingDTO> list = new ArrayList<>();
        list = basicReportingDTOS.stream().map(x -> this.mapBasicReportingDTOToDailyReportingDTO(x)).collect(Collectors.toList());

        // Function to map DailyreportingDTOs of same Date to one DailyReportingDTO

        Function<Map.Entry<LocalDate, List<DailyReportingDTO>>, DailyReportingDTO> dailyRepAggregator = p -> {
            DailyReportingDTO dailyReportingDTO = new DailyReportingDTO();
            for (DailyReportingDTO entry : p.getValue()) {
                dailyReportingDTO.setSalesNo(dailyReportingDTO.getSalesNo()+entry.getSalesNo());
                dailyReportingDTO.setSalesMg(roundUp(dailyReportingDTO.getSalesMg()+entry.getSalesMg()));
                dailyReportingDTO.setSalesTo(roundUp(dailyReportingDTO.getSalesTo()+entry.getSalesTo()));
                dailyReportingDTO.setListPr(roundUp(dailyReportingDTO.getListPr() + entry.getListPr()));
                dailyReportingDTO.setPurchCo(roundUp(dailyReportingDTO.getPurchCo() + entry.getPurchCo()));
                dailyReportingDTO.setDate(entry.getDate());
                dailyReportingDTO.setShop(entry.getShop());
            }
            dailyReportingDTO.setSalesMgAvg(roundUp(dailyReportingDTO.getSalesMg()/dailyReportingDTO.getSalesNo()));
            dailyReportingDTO.setDiscountRateAvg(roundUp(dailyReportingDTO.getDiscountRateAvg()));
            return dailyReportingDTO;
        };

        Map <LocalDate, List<DailyReportingDTO>> actualsMap = list.stream().collect(Collectors.groupingBy(p -> p.getDate()));
        List<DailyReportingDTO> dailyReportingDTOList = actualsMap.entrySet().stream().map(dailyRepAggregator).collect(Collectors.toList());

        dailyReportingDTOList.sort(Comparator.comparing(DailyReportingDTO::getDate));

        return dailyReportingDTOList;
    }

    /**
     * returns sales-numbers by category to be used in BarChart
     * @param dateRangeDTO timeframe for returned List
     * @return BarDataDTO
     */

    public List<BarDataDTO> getCategoryData(DateRangeDTO dateRangeDTO) {

        LocalDateTime start = typescriptDateConversion(dateRangeDTO.getStartDate());
        LocalDateTime end = typescriptDateConversion(dateRangeDTO.getEndDate());
        List <BasicReportingDTO> basicReportingDTOS = this.extractDataByDate(start, end);
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
        list.sort((x, y) -> Double.compare(y.getValue(),(x.getValue())));
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
        monthToDateReportingDTO.setSalesTo(roundUp((basicReportingDTO.getTurnoverPerSale().doubleValue()/100)));
        monthToDateReportingDTO.setSalesMg(roundUp(monthToDateReportingDTO.getSalesTo()-((basicReportingDTO.getPurchasingCost().doubleValue()/100)
                *basicReportingDTO.getSalesQuantity())/100));
        monthToDateReportingDTO.setSalesMgAvg(roundUp(monthToDateReportingDTO.getSalesMg()/monthToDateReportingDTO.getSalesNo()));
        try{
            monthToDateReportingDTO.setDiscountRateAvg(roundUp(100-(basicReportingDTO.getPurchasingCost()/basicReportingDTO.getListPrice())*100));
        } catch (ArithmeticException ae) {
            monthToDateReportingDTO.setDiscountRateAvg(0.0);
        }
        monthToDateReportingDTO.setDate(basicReportingDTO.getDate());
        return monthToDateReportingDTO;
    }

    /**
     * Mapper to convert BasicReportingDTO into DailyReportingDTO
     * @param basicReportingDTO Aggregated List of Sales
     * @return DailyReportingDTO
     */

    public DailyReportingDTO mapBasicReportingDTOToDailyReportingDTO(BasicReportingDTO basicReportingDTO){
        DailyReportingDTO dailyReportingDTO = new DailyReportingDTO();
        dailyReportingDTO.setShop(basicReportingDTO.getShop());
        dailyReportingDTO.setDate(basicReportingDTO.getDate().toLocalDate());
        dailyReportingDTO.setSalesNo(basicReportingDTO.getSalesQuantity());
        dailyReportingDTO.setSalesTo(roundUp((basicReportingDTO.getTurnoverPerSale().doubleValue())/100));
        dailyReportingDTO.setListPr(roundUp(basicReportingDTO.getListPrice().doubleValue()/100));
        dailyReportingDTO.setPurchCo(roundUp(basicReportingDTO.getPurchasingCost().doubleValue()/100));
        dailyReportingDTO.setDiscountRateAvg(roundUp(dailyReportingDTO.getSalesTo()/(dailyReportingDTO.getListPr()*100)));
        dailyReportingDTO.setSalesMg(roundUp(dailyReportingDTO.getSalesTo()-dailyReportingDTO.getPurchCo()));
        dailyReportingDTO.setSalesMgAvg(roundUp(dailyReportingDTO.getSalesMg()/dailyReportingDTO.getSalesNo()));
        return dailyReportingDTO;
    }

    // Internal methods to aggregate necessary data from different sources

    /**
     * Mapper to map shopsallsolditems to basic reporting
     * @param shopsAllSoldItems from corresponding database-table
     * @return BasicReportingDTO
     */

    public BasicReportingDTO mapShopsAllSoldToBasicReporting (ShopsAllSoldItems shopsAllSoldItems) {
        BasicReportingDTO basicReportingDTO = new BasicReportingDTO();
        basicReportingDTO.setShop(shopsAllSoldItems.getShop());
        basicReportingDTO.setDate(shopsAllSoldItems.getItemLastSold());
        basicReportingDTO.setTurnoverPerSale(shopsAllSoldItems.getPriceSalesPerUnit()*shopsAllSoldItems.getQuantity());
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
     * Utility-method to convert date-string created by Typescript to date to be handled in Java-code
     * @param dateToParse
     * @return LocalDateTime-Object of submitted iso-String-Date
     */

    public static LocalDateTime typescriptDateConversion(String dateToParse) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return LocalDateTime.parse(dateToParse, formatter);
    }

    /**
     * Utility-method to round values to 2 digits floating
     * @param input (not rounded)
     * @return double rounded to 2 digits floating
     */
    public static double roundUp(double input){
        return Math.round(input *100)/100.00;
    }
}

