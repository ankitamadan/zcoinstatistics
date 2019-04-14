package com.zcoin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address implements Serializable {

    @JsonProperty("address")
    public String address;

    @JsonProperty("final_balance")
    public BigDecimal final_balance;

}
