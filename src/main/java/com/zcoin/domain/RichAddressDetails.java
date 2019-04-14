package com.zcoin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RichAddressDetails {

    public BigDecimal amount;
    @JsonProperty("addr")
    public String addr;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
