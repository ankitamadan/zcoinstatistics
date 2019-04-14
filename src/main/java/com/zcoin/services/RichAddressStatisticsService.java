package com.zcoin.services;

import com.zcoin.domain.RichAddressStatisticsModel;

public interface RichAddressStatisticsService {

    Iterable<RichAddressStatisticsModel> listAllRichAddressStatistics();
}
