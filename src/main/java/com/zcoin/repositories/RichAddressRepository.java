package com.zcoin.repositories;

import com.zcoin.domain.RichAddressListModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigDecimal;

@RepositoryRestResource
public interface RichAddressRepository extends CrudRepository<RichAddressListModel, Integer> {
}
