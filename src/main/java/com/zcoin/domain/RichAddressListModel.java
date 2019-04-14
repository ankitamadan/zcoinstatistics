package com.zcoin.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "richAddress")
public class RichAddressListModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated richAddress ID")
    @Column(name = "id")
    public Integer id;

    @ApiModelProperty
    public BigDecimal total;

    @ApiModelProperty
    public BigDecimal totalSupply;

    public List<RichAddressDetailsModel> getRich1000() {
          return Collections.unmodifiableList(this.rich1000);
    }

    public void setRich1000(List<RichAddressDetailsModel> rich1000) {
        this.rich1000 = rich1000;
    }

    public void addRich1000(RichAddressDetailsModel address) {
        address.setAddressDetailsModel(this);
        this.rich1000.add(address);
    }

    @ApiModelProperty
    @JsonManagedReference
    @OneToMany(mappedBy="addressDetailsModel",
            targetEntity = RichAddressDetailsModel.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<RichAddressDetailsModel> rich1000;

    @Column(name = "total")
    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getTotalSupply() {
        return totalSupply;
    }

}
