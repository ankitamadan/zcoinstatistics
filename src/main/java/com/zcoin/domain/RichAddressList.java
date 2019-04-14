package com.zcoin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RichAddressList implements Serializable {

    @JsonProperty("total")
    public BigDecimal total;

    @JsonProperty("rich1000")
    public List<RichAddressDetails> rich1000;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }


}
