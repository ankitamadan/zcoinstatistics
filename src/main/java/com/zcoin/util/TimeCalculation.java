package com.zcoin.util;

import java.math.BigDecimal;
import java.math.MathContext;

public class TimeCalculation {

    public static BigDecimal getTimeDifference(BigDecimal balanceInTenMinutes, BigDecimal amount) {
        BigDecimal balancePercentage = BigDecimal.ZERO;
        if(!(balanceInTenMinutes.compareTo(BigDecimal.ZERO) == 0)&& !(amount.subtract(balanceInTenMinutes).compareTo(BigDecimal.ZERO) == 0)){
            balancePercentage =
                    balanceInTenMinutes.divide(amount.subtract(balanceInTenMinutes), MathContext.DECIMAL32).multiply(new BigDecimal("100"));
        }
        return balancePercentage;
    }

}

