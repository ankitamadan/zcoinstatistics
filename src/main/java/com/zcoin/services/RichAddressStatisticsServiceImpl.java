package com.zcoin.services;

import com.zcoin.domain.RichAddressStatisticsModel;
import com.zcoin.repositories.RichAddressStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RichAddressStatisticsServiceImpl implements RichAddressStatisticsService {

    @Autowired
    private RichAddressStatisticsRepository richAddressStatisticsRepository;

    @Override
    public Iterable<RichAddressStatisticsModel> listAllRichAddressStatistics() {
        return richAddressStatisticsRepository.findAll();
    }
}
