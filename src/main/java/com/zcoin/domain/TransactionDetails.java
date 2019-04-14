package com.zcoin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDetails implements Serializable {

    @JsonProperty("change")
    public BigDecimal change;

    @JsonProperty("time_utc")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    public Date time_utc;

}
