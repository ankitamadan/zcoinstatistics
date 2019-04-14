package com.zcoin.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDetails {

    @JsonProperty("addresses")
    public List<Address> addresses;

    @JsonProperty("txs")
    public List<TransactionDetails> txs;

}
