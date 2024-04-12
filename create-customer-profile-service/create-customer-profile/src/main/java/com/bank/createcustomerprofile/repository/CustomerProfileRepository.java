package com.bank.createcustomerprofile.repository;

import com.bank.createcustomerprofile.entity.CustomerProfileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerProfileRepository extends CrudRepository<CustomerProfileEntity, Long> {
}
