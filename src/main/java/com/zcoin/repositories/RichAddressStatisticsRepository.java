package com.zcoin.repositories;

import com.zcoin.domain.RichAddressStatisticsModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RichAddressStatisticsRepository extends CrudRepository<RichAddressStatisticsModel, Integer> {
}
