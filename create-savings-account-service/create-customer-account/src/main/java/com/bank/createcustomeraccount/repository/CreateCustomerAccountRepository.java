package com.bank.createcustomeraccount.repository;

import com.bank.createcustomeraccount.entity.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreateCustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {
}
