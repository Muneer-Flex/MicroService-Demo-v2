package com.bank.createcustomerprofile.service;

import com.bank.createcustomerprofile.constants.ErrorConstants;
import com.bank.createcustomerprofile.entity.CustomerProfileEntity;
import com.bank.createcustomerprofile.exception.CreateCustomerProfileException;
import com.bank.createcustomerprofile.mapper.CreateCustomerProfileMapper;
import com.bank.createcustomerprofile.model.CreateCustomerProfileRequest;
import com.bank.createcustomerprofile.model.CreateCustomerProfileResponse;
import com.bank.createcustomerprofile.model.Status;
import com.bank.createcustomerprofile.repository.CustomerProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateCustomerProfileService {
    private static final Logger logger = LoggerFactory.getLogger(CreateCustomerProfileService.class);

    private final CustomerProfileRepository customerProfileRepository;

    public CreateCustomerProfileService(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }

    public CreateCustomerProfileResponse createCustomerProfile(CreateCustomerProfileRequest createCustomerProfileRequest) throws CreateCustomerProfileException {
        try {
            CustomerProfileEntity customerProfileEntity = CreateCustomerProfileMapper.INSTANCE.asCustomerProfileEntity(createCustomerProfileRequest);
            logger.info("Customer Profile Entity :: {}", customerProfileEntity);

            customerProfileRepository.save(customerProfileEntity);
            return CreateCustomerProfileResponse.builder()
                    .status(Status.SUCCESS)
                    .createdOn(LocalDateTime.now())
                    .build();
        } catch (Exception exception) {
            logger.error("Exception occurred while saving customer profile. Exception is: {}", exception);
            throw new CreateCustomerProfileException(ErrorConstants.CUST_001, ErrorConstants.CUST_001_DESCRIPTION);
        }
    }
}
