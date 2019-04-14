package com.zcoin.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class RichAddressStatisticsModel
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated address ID")
    private Integer id;

    @ApiModelProperty
    private String rank;

    @ApiModelProperty
    private String address;

    @ApiModelProperty
    private BigDecimal balance;

    @ApiModelProperty
    public String supply;

    @ApiModelProperty
    private String balanceInTenMinutes;

    @ApiModelProperty
    private String balanceInOneHour;

    @ApiModelProperty
    private String balanceInOneDay;

    @ApiModelProperty
    public String balanceInOneWeek;

    @ApiModelProperty
    private String balanceInOneMonth;


    @ElementCollection
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public List<Date> dateList;

    @ElementCollection
    public List<BigDecimal> amountList;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getBalanceInOneWeek() {
        return balanceInOneWeek;
    }

    public void setBalanceInOneWeek(String balanceInOneWeek) {
        this.balanceInOneWeek =  balanceInOneWeek != null && !balanceInOneWeek.equals("")
                ? this.balanceInOneWeek = balanceInOneWeek : "";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBalanceInTenMinutes() {
        return balanceInTenMinutes;
    }

    public void setBalanceInTenMinutes(String balanceInTenMinutes) {
        this.balanceInTenMinutes =  balanceInTenMinutes != null && !balanceInTenMinutes.equals("")
                ? this.balanceInTenMinutes = balanceInTenMinutes : "";
    }

    public String getBalanceInOneHour() {
        return balanceInOneHour;
    }

    public void setBalanceInOneHour(String balanceInOneHour) {
        this.balanceInOneHour =  balanceInOneHour != null && !balanceInOneHour.equals("")
                ? this.balanceInOneHour = balanceInOneHour : "";
    }

    public String getBalanceInOneDay() {
        return balanceInOneDay;
    }

    public void setBalanceInOneDay(String balanceInOneDay) {
        this.balanceInOneDay =  balanceInOneDay != null && !balanceInOneDay.equals("")
                ? this.balanceInOneDay = balanceInOneDay : "";
    }

    public String getBalanceInOneMonth() {
        return balanceInOneMonth;
    }

    public void setBalanceInOneMonth(String balanceInOneMonth) {
        this.balanceInOneMonth =  balanceInOneMonth != null && !balanceInOneMonth.equals("")
                ? this.balanceInOneMonth = balanceInOneMonth : "";
    }

    public List<Date> getDateList() {
        return dateList;
    }

    public void setDateList(List<Date> dateList) {
        this.dateList =  dateList != null && !dateList.isEmpty()
                ? this.dateList = dateList : new ArrayList<>();
    }

    public List<BigDecimal> getAmountList() {
        return amountList;
    }

    public void setAmountList(List<BigDecimal> amountList) {
        this.amountList =  amountList != null && !amountList.isEmpty()
                ? this.amountList = amountList : new ArrayList<>();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }
}
