package com.zcoin.services;

import com.zcoin.domain.RichAddressList;
import com.zcoin.domain.RichAddressListModel;

import java.io.IOException;
import java.math.BigDecimal;

public interface RichAddressService {

    Iterable<RichAddressListModel> listAllRichAddress();

    BigDecimal getTotalSupply();

    RichAddressListModel getRichAddressListModel(RichAddressList richAddressList) throws IOException;
}
