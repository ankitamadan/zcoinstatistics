package com.zcoin.bootstrap;

import com.zcoin.domain.*;
import com.zcoin.repositories.RichAddressRepository;
import com.zcoin.repositories.RichAddressStatisticsRepository;
import com.zcoin.invoker.RichServiceInvoker;
import com.zcoin.services.RichAddressService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private RichServiceInvoker richServiceInvoker;
    private RichAddressRepository richAddressRepository;
    private RichAddressService richAddressService;
    private RichAddressStatisticsRepository richAddressStatisticsRepository;

    private Logger LOGGER = Logger.getLogger(SpringJpaBootstrap.class);

    @Autowired
    public void setProductRepository(RichServiceInvoker richServiceInvoker,
                                     RichAddressRepository richAddressRepository,
                                     RichAddressService richAddressService,
                                     RichAddressStatisticsRepository richAddressStatisticsRepository) {
        this.richServiceInvoker = richServiceInvoker;
        this.richAddressRepository = richAddressRepository;
        this.richAddressService = richAddressService;
        this.richAddressStatisticsRepository = richAddressStatisticsRepository;
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadRichAddress();
        loadRichAddressStatistics();
    }


    @Scheduled(cron = "0 0/10 * * * ?")
    private void loadRichAddress() {
        RichAddressList richAddressList = null;
        try {
            richAddressList = richServiceInvoker.retrieveRichAddress();
            richAddressRepository.deleteAll();
            RichAddressListModel richAddressListModel
                    = richAddressService.getRichAddressListModel(richAddressList);
            richAddressRepository.save(richAddressListModel);
            LOGGER.info("loadRichAddress RUNNING EVERY 10 MIN");
        } catch (IOException e) {
            LOGGER.info("Load rich address failed");
        }
    }

    @Scheduled(cron = "0 0/20 * * * ?")
    private void loadRichAddressStatistics(){
        List<RichAddressStatistics> richAddressStatisticsList = null;
        try {
            richAddressStatisticsList = richServiceInvoker.retrieveAddressDetailsList();
            richAddressStatisticsRepository.deleteAll();
            int count = 0;
            for(RichAddressStatistics richAddressStatistics : richAddressStatisticsList){
                count++;
                RichAddressStatisticsModel richAddressStatisticsModel = new RichAddressStatisticsModel();
                richAddressStatisticsModel.setRank(String.valueOf(count));
                richAddressStatisticsModel.setBalance(richAddressStatistics.amount);
                if(!(richAddressStatistics.supply.compareTo(BigDecimal.ZERO) == 0)) {
                    richAddressStatisticsModel.setSupply(richAddressStatistics.supply.toString()+"%");
                    String supply = richAddressStatistics.supply.toString();
                    richAddressStatisticsModel.setSupply(supply.substring(0,5)+"%");
                }else {
                    richAddressStatisticsModel.setSupply("0%");
                }

                richAddressStatisticsModel.setAddress(richAddressStatistics.address);
                if(richAddressStatistics.balanceInTenMinutes != null){
                    richAddressStatisticsModel.setBalanceInTenMinutes(richAddressStatistics.balanceInTenMinutes.toString()+"%");
                } else {
                    richAddressStatisticsModel.setBalanceInTenMinutes("-");
                }
                if(richAddressStatistics.balanceInOneHour != null){
                    richAddressStatisticsModel.setBalanceInOneHour(richAddressStatistics.balanceInOneHour.toString()+"%");
                } else {
                    richAddressStatisticsModel.setBalanceInOneHour("-");
                }
                if(richAddressStatistics.balanceInOneDay != null){
                    richAddressStatisticsModel.setBalanceInOneDay(richAddressStatistics.balanceInOneDay.toString()+"%");
                } else {
                    richAddressStatisticsModel.setBalanceInOneDay("-");
                }
                if(richAddressStatistics.balanceInOneMonth != null){
                    richAddressStatisticsModel.setBalanceInOneMonth(richAddressStatistics.balanceInOneMonth.toString()+"%");
                } else {
                    richAddressStatisticsModel.setBalanceInOneMonth("-");
                }
                if(richAddressStatistics.balanceInOneWeek != null){
                    richAddressStatisticsModel.setBalanceInOneWeek(richAddressStatistics.balanceInOneWeek.toString()+"%");
                } else {
                    richAddressStatisticsModel.setBalanceInOneWeek("-");
                }
                if(richAddressStatistics.dateList != null){
                    richAddressStatisticsModel.setDateList(richAddressStatistics.dateList);
                } else {
                    richAddressStatisticsModel.setDateList(new ArrayList<>());
                }
                if(richAddressStatistics.amountList != null){
                    richAddressStatisticsModel.setAmountList(richAddressStatistics.amountList);
                } else {
                    richAddressStatisticsModel.setAmountList(new ArrayList<>());
                }
                richAddressStatisticsRepository.save(richAddressStatisticsModel);
            }
        } catch (Exception e){
            LOGGER.info("Load rich adrress statistics failed");
        }
        System.out.print("************************ RUN AT************** "+ dateFormat.format(new Date()));
    }
}



