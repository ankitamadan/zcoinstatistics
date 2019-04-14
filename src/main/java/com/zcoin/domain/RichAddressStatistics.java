package com.zcoin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RichAddressStatistics
{

    public String address;
    public BigDecimal amount;
    public BigDecimal supply;

    public BigDecimal balanceInTenMinutes;
    public BigDecimal balanceInOneHour;
    public BigDecimal balanceInOneDay;
    public BigDecimal balanceInOneMonth;
    public BigDecimal balanceInOneWeek;
    public List<Date> dateList;
    public List<BigDecimal> amountList;

}
