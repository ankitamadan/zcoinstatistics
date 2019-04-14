package com.zcoin.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class RichAddressDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated addr ID")
    private Integer id;

    @ApiModelProperty
    public BigDecimal amount;

    @ApiModelProperty
    public String addr;

    public RichAddressListModel getAddressDetailsModel() {
        return addressDetailsModel;
    }

    public void setAddressDetailsModel(RichAddressListModel addressDetailsModel) {
        this.addressDetailsModel = addressDetailsModel;
    }

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addres_details_id")
    RichAddressDetailsModel addressDetailsModel;*/

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "addres_details_id")
    @JsonBackReference
    RichAddressListModel addressDetailsModel;

}
