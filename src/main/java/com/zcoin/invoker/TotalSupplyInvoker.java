package com.zcoin.invoker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;

@Component
public class TotalSupplyInvoker extends AbstractInvoker {

    private static final String TOTAL_SUPPLY_URL = "https://explorer.zcoin.io/ext/getmoneysupply";

    private Logger LOGGER = Logger.getLogger(TotalSupplyInvoker.class);

    public BigDecimal getTotalSupply() throws IOException {
        ResponseEntity<String> response = exchange(String.class, TOTAL_SUPPLY_URL);
        BigDecimal totalSupply = new ObjectMapper().readValue(response.getBody(), BigDecimal.class);
        return totalSupply;
    }

}
