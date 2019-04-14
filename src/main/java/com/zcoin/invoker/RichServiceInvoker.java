package com.zcoin.invoker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcoin.domain.*;
import com.zcoin.repositories.RichAddressRepository;
import com.zcoin.services.TimeDifference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.math.BigDecimal.*;

@Component
public class RichServiceInvoker extends AbstractInvoker{

    private static final String RICH_ADDRESS_URL = "https://chainz.cryptoid.info/xzc/api.dws?q=rich";

    private RichAddressRepository richAddressRepository;
    private TotalSupplyInvoker totalSupplyInvoker;

    @Autowired
    public RichServiceInvoker(RichAddressRepository richAddressRepository,
                              TotalSupplyInvoker totalSupplyInvoker){
        this.richAddressRepository = richAddressRepository;
        this.totalSupplyInvoker = totalSupplyInvoker;
    }

    public RichAddressList retrieveRichAddress() throws IOException{
        ResponseEntity<String> response = exchange(String.class, RICH_ADDRESS_URL);
        RichAddressList richAddressList = new ObjectMapper().readValue(response.getBody(), RichAddressList.class);
        return richAddressList;
    }

    public List<RichAddressStatistics> retrieveAddressDetailsList() throws IOException{
        List<RichAddressStatistics> addressDetailsList = new ArrayList<>();
        Iterable<RichAddressListModel> richAddressListModels = richAddressRepository.findAll();
        for(RichAddressListModel richAddressListModel : richAddressListModels){
            for(RichAddressDetailsModel richAddressDetailsModel : richAddressListModel.rich1000){
                RichAddressStatistics addressDetails = new RichAddressStatistics();
                String address = richAddressDetailsModel.addr;
                addressDetails = retrieveAddressDetails(address, richAddressDetailsModel.amount, richAddressListModel.totalSupply);
                addressDetailsList.add(addressDetails);
            }
        }
        return addressDetailsList;
    }

    public RichAddressStatistics retrieveAddressDetails(String address, BigDecimal amount, BigDecimal totalSupply) throws IOException{
        String url = createAddressDetailsUrl(address);
        ResponseEntity<String> response = exchange(String.class, url);
        AddressDetails addressDetails =
                new ObjectMapper().readValue(response.getBody(), AddressDetails.class);
        RichAddressStatistics richAddressStatistics = new RichAddressStatistics();
        if(!addressDetails.txs.isEmpty() && !addressDetails.addresses.isEmpty()){
            TimeDifference timeDifference = new TimeDifference(addressDetails).invoke();

            BigDecimal perCentageBalanceIncrementTenMinutes = timeDifference.getPerCentageBalanceIncrementTenMinutes();
            BigDecimal perCentageBalanceInHour = timeDifference.getPerCentageBalanceInHour();
            BigDecimal perCentageBalanceInOneDay = timeDifference.getPerCentageBalanceInOneDay();
            BigDecimal perCentageBalanceInOneMonth = timeDifference.getPerCentageBalanceInOneMonth();
            BigDecimal perCentageBalanceInOneWeek = timeDifference.getPerCentageBalanceInOneWeek();
            List<Date> dateList = timeDifference.getAddressDateList();
            List<BigDecimal> amountList = timeDifference.getAddressAmountList();

            richAddressStatistics
                    = populateRichAddressStatisticsModel(address, perCentageBalanceIncrementTenMinutes,
                    perCentageBalanceInHour, perCentageBalanceInOneDay,
                    perCentageBalanceInOneMonth, perCentageBalanceInOneWeek,
                    dateList, amountList, amount, totalSupply);
            return richAddressStatistics;
        }
        richAddressStatistics.amount = amount;
        richAddressStatistics.address = address.equalsIgnoreCase("Anonymous")
                ? richAddressStatistics.address = "Zeromint"
                : address;
        richAddressStatistics.supply = amount.divide(totalSupply, 5, 6).multiply(new BigDecimal("100"));
        return richAddressStatistics;
    }

    private RichAddressStatistics populateRichAddressStatisticsModel(String address, BigDecimal perCentageBalanceIncrementTenMinutes,
                                                                     BigDecimal perCentageBalanceInHour,
                                                                     BigDecimal perCentageBalanceInOneDay,
                                                                     BigDecimal perCentageBalanceInOneMonth,
                                                                     BigDecimal perCentageBalanceInOneWeek,
                                                                     List<Date> dateList, List<BigDecimal> amoutList,
                                                                     BigDecimal amount, BigDecimal totalSupply) {
        RichAddressStatistics richAddressStatistics = new RichAddressStatistics();
        richAddressStatistics.address = address;
        richAddressStatistics.balanceInTenMinutes = perCentageBalanceIncrementTenMinutes;
        richAddressStatistics.balanceInOneHour = perCentageBalanceInHour;
        richAddressStatistics.balanceInOneDay = perCentageBalanceInOneDay;
        richAddressStatistics.balanceInOneMonth = perCentageBalanceInOneMonth;
        richAddressStatistics.balanceInOneWeek = perCentageBalanceInOneWeek;
        richAddressStatistics.dateList = dateList;
        richAddressStatistics.amountList = amoutList;
        richAddressStatistics.amount = amount;
        richAddressStatistics.supply = getAddressSupply(amount, totalSupply, richAddressStatistics);
        return richAddressStatistics;
    }

    private BigDecimal getAddressSupply(BigDecimal amount, BigDecimal totalSupply, RichAddressStatistics richAddressStatistics) {
        BigDecimal supply = BigDecimal.ZERO;
        try {
            supply = amount.divide(totalSupply, 5, 6).multiply(new BigDecimal("100"));
            return supply;
        } catch (Exception e){
            return supply;
        }
    }


}
