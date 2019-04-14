package com.zcoin.services;

import com.zcoin.domain.RichAddressDetailsModel;
import com.zcoin.domain.RichAddressList;
import com.zcoin.domain.RichAddressListModel;
import com.zcoin.invoker.TotalSupplyInvoker;
import com.zcoin.repositories.RichAddressRepository;
import org.codehaus.groovy.tools.shell.IO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class RichAddressServiceImpl implements RichAddressService {

    @Autowired
    public RichAddressRepository richAddressRepository;

    @Autowired
    public TotalSupplyInvoker totalSupplyInvoker;

    @Override
    public Iterable<RichAddressListModel> listAllRichAddress() {
        return this.richAddressRepository.findAll();
    }

    @Override
    public BigDecimal getTotalSupply() {
        BigDecimal totalSupply = null;
        for(RichAddressListModel listModel : listAllRichAddress()){
            totalSupply = listModel.totalSupply;
        }
        return  totalSupply;
    }

    @Override
    public RichAddressListModel getRichAddressListModel(RichAddressList richAddressList) throws IOException{
        RichAddressListModel richAddressListModel = new RichAddressListModel();
        richAddressListModel.total = richAddressList.total;
        richAddressListModel.totalSupply = totalSupplyInvoker.getTotalSupply();
        getRichAddressesList(richAddressList, richAddressListModel);
        mapRichAddressModel(richAddressListModel);
        return richAddressListModel;
    }

    private void mapRichAddressModel(RichAddressListModel richAddressListModel) {
        CopyOnWriteArrayList<RichAddressDetailsModel> richAddressDetailsModels =
                new CopyOnWriteArrayList<>(richAddressListModel.rich1000);

        for(RichAddressDetailsModel listModel : richAddressDetailsModels){
            richAddressListModel.addRich1000(listModel);
        }
    }

    private void getRichAddressesList(RichAddressList richAddressList, RichAddressListModel richAddressListModel) {
        richAddressListModel.rich1000 = richAddressList.rich1000.stream().map(richAddressDetails -> {
                    RichAddressDetailsModel richAddressDetailsModel = new RichAddressDetailsModel();
                    richAddressDetailsModel.addr = richAddressDetails.addr;
                    richAddressDetailsModel.amount = richAddressDetails.amount;
                    return richAddressDetailsModel;
                }
        ).collect(Collectors.toList());
    }
}
