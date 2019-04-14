package com.zcoin.services;

import com.zcoin.domain.AddressDetails;
import com.zcoin.domain.TransactionDetails;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.zcoin.util.TimeCalculation.getTimeDifference;
import static java.util.Map.Entry.comparingByKey;

public class TimeDifference {
    private AddressDetails addressDetails;
    private BigDecimal perCentageBalanceIncrementTenMinutes;
    private BigDecimal perCentageBalanceInHour;
    private BigDecimal perCentageBalanceInOneDay;
    private BigDecimal perCentageBalanceInOneMonth;
    private BigDecimal perCentageBalanceInOneWeek;
    private List<Date> addressDateList;
    private List<BigDecimal> addressAmountList;
    private BigDecimal totalSupply;
    private BigDecimal amount;

    public TimeDifference(AddressDetails addressDetails) {
        this.addressDetails = addressDetails;
    }

    public BigDecimal getPerCentageBalanceIncrementTenMinutes() {
        return perCentageBalanceIncrementTenMinutes;
    }

    public BigDecimal getPerCentageBalanceInHour() {
        return perCentageBalanceInHour;
    }

    public BigDecimal getPerCentageBalanceInOneDay() {
        return perCentageBalanceInOneDay;
    }

    public BigDecimal getPerCentageBalanceInOneMonth() {
        return perCentageBalanceInOneMonth;
    }

    public BigDecimal getPerCentageBalanceInOneWeek() {
        return perCentageBalanceInOneWeek;
    }

    public List<Date> getAddressDateList() {
        return addressDateList;
    }

    public List<BigDecimal> getAddressAmountList() {
        return addressAmountList;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TimeDifference invoke() {
        Instant instant = Instant.now();

        List<BigDecimal> tenMinuteDifference = new ArrayList<>();
        List<BigDecimal> oneHourDifference = new ArrayList<>();
        List<BigDecimal> oneDayDifference = new ArrayList<>();
        List<BigDecimal> oneWeekDifference = new ArrayList<>();
        List<BigDecimal> oneMonthDifference = new ArrayList<>();
        Map<Date, BigDecimal> dateAmountMap = new HashMap<>();

        computeTimeDifference(addressDetails, instant, tenMinuteDifference, oneHourDifference,
                oneDayDifference, oneWeekDifference, oneMonthDifference, dateAmountMap);

        BigDecimal balanceInTenMinutes = tenMinuteDifference.stream().reduce(
                BigDecimal.ZERO, BigDecimal::add);
        BigDecimal balanceInHour = oneHourDifference.stream().reduce(
                BigDecimal.ZERO, BigDecimal::add);
        BigDecimal balanceInOneDay = oneDayDifference.stream().reduce(
               BigDecimal.ZERO, BigDecimal::add);
        BigDecimal balanceInOneMonth = oneMonthDifference.stream().reduce(
                BigDecimal.ZERO, BigDecimal::add);
        BigDecimal balanceInOneWeek = oneWeekDifference.stream().reduce(
                BigDecimal.ZERO, BigDecimal::add);

        amount = addressDetails.addresses.get(0).final_balance;

        perCentageBalanceIncrementTenMinutes = getTimeDifference(balanceInTenMinutes, amount);
        perCentageBalanceInHour = getTimeDifference(balanceInHour, amount);
        perCentageBalanceInOneDay = getTimeDifference(balanceInOneDay, amount);
        perCentageBalanceInOneMonth = getTimeDifference(balanceInOneMonth, amount);
        perCentageBalanceInOneWeek = getTimeDifference(balanceInOneWeek, amount);

        Map<Date, BigDecimal> sortedDateAmountMap = dateAmountMap.entrySet().stream()
                .sorted(comparingByKey())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

        addressDateList = sortedDateAmountMap.keySet().stream().collect(Collectors.toList());
        addressAmountList = getCumulativeSumOfAmount(sortedDateAmountMap.values().stream().collect(Collectors.toList()));

        return this;
    }

    private List<BigDecimal> getCumulativeSumOfAmount(List<BigDecimal> sortedAmountList) {
        List<BigDecimal> listCumulativeSum = new ArrayList<>();
        BigDecimal cumulativeSum = BigDecimal.ZERO;
        for(int i = 0; i < sortedAmountList.size(); i++){
            cumulativeSum = sortedAmountList.get(i)
                    .add(cumulativeSum);
            listCumulativeSum.add(cumulativeSum);
        }
        return listCumulativeSum;
    }

    private void computeTimeDifference(AddressDetails addressDetails, Instant instant, List<BigDecimal> tenMinuteDifference,
                                       List<BigDecimal> oneHourDifference, List<BigDecimal> oneDayDifference,
                                       List<BigDecimal> oneWeekDifference, List<BigDecimal> oneMonthDifference,
                                       Map<Date, BigDecimal> dateAmountMap) {
        for(TransactionDetails transactionDetails : addressDetails.txs){
            long difftenminutes = ChronoUnit.MINUTES.between(transactionDetails.time_utc.toInstant(), instant);
            long diffonehour = ChronoUnit.HOURS.between(transactionDetails.time_utc.toInstant(), instant);
            long diffoneday = ChronoUnit.DAYS.between(transactionDetails.time_utc.toInstant(), instant);

            if(difftenminutes <= 10){
                tenMinuteDifference.add(new BigDecimal(transactionDetails.change.intValue()));
            }
            if(diffonehour <= 1){
                oneHourDifference.add(new BigDecimal(transactionDetails.change.intValue()));
            }
            if(diffoneday <= 1){
                oneDayDifference.add(new BigDecimal(transactionDetails.change.intValue()));
            }
            if(diffoneday <= 7){
                oneWeekDifference.add(new BigDecimal(transactionDetails.change.intValue()));
            }
            if(diffoneday <= 30){
                oneMonthDifference.add(new BigDecimal(transactionDetails.change.intValue()));
            }

            dateAmountMap.put(transactionDetails.time_utc, transactionDetails.change);
        }
    }
}