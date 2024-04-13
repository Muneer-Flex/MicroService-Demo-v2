package com.bank.createcustomeraccount.service;

import com.bank.createcustomeraccount.constants.ErrorConstants;
import com.bank.createcustomeraccount.entity.CustomerAccount;
import com.bank.createcustomeraccount.exception.*;
import com.bank.createcustomeraccount.model.CreateCustomerAccountRequest;
import com.bank.createcustomeraccount.model.CreateCustomerAccountResponse;
import com.bank.createcustomeraccount.repository.CreateCustomerAccountRepository;
import com.bank.createcustomeraccount.service.helper.centralAuth.CentralAuthServiceHelper;
import com.bank.createcustomeraccount.service.helper.createCustomerProfile.CreateCustomerProfileHelper;
import com.bank.createcustomeraccount.service.helper.governingState.GoverningStateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateCustomerAccountService {
    private static final Logger logger = LoggerFactory.getLogger(CreateCustomerAccountService.class);

    @Value("${application.privileges.createProfile}")
    private String createProfilePrivilege;

    private final GoverningStateHelper governingStateHelper;
    private final CentralAuthServiceHelper centralAuthServiceHelper;
    private final CreateCustomerProfileHelper createCustomerProfileHelper;
    private final CreateCustomerAccountRepository createCustomerAccountRepository;

    public CreateCustomerAccountService(GoverningStateHelper governingStateHelper, CentralAuthServiceHelper centralAuthServiceHelper, CreateCustomerProfileHelper createCustomerProfileHelper, CreateCustomerAccountRepository createCustomerAccountRepository) {
        this.governingStateHelper = governingStateHelper;
        this.centralAuthServiceHelper = centralAuthServiceHelper;
        this.createCustomerProfileHelper = createCustomerProfileHelper;
        this.createCustomerAccountRepository = createCustomerAccountRepository;
    }

    public CreateCustomerAccountResponse createCustomerAccount(CreateCustomerAccountRequest createCustomerAccountRequest, String requestId) throws GoverningStateServiceException, CentralAuthServiceException, CreateCustomerProfileServiceException, EurekaServiceException, CreateCustomerAccountException, IntegrationException {
        String governingState = governingStateHelper.fetchGoverningStateByZipCode(createCustomerAccountRequest.getCustomerDetails().getZipCode(), requestId);
        logger.info("Fetched Governing State:: {}", governingState);

        if (null != governingState) {
            String jwt = centralAuthServiceHelper.generateJwtFromCentralAuth(createProfilePrivilege, requestId);
            logger.info("Generated JWT:: {}", jwt);

            if (null != jwt) {
                boolean isCustomerProfileCreated = createCustomerProfileHelper.createCustomerProfile(createCustomerAccountRequest.getCustomerDetails(), governingState, requestId, jwt);
                logger.info("Is Customer Profile Created:: {}", isCustomerProfileCreated);

                if(isCustomerProfileCreated) {
                    try {
                        CustomerAccount customerAccount = createCustomerAccountRepository.save(buildCustomerAccountEntity(createCustomerAccountRequest));
                        logger.info("Customer Account Created Successfully!");

                        return CreateCustomerAccountResponse.builder()
                                .customerId(customerAccount.getCustomerId())
                                .customerName(customerAccount.getCustomerName())
                                .customerAccountNumber(customerAccount.getAccountNumber())
                                .accountType(customerAccount.getAccountType())
                                .createdOn(LocalDateTime.now())
                                .build();
                    } catch (Exception exception) {
                        logger.error("Exception occurred while persisting customer account. Exception is:: {}", exception);
                        throw new CreateCustomerAccountException(ErrorConstants.PERSIST_CUSTOMER_ACCOUNT_ERROR_CODE, ErrorConstants.PERSIST_CUSTOMER_ACCOUNT_ERROR_DESC, exception.getCause());
                    }
                }
            }
        }
        logger.error("Exception occurred while creating customer account");
        throw new CreateCustomerAccountException(ErrorConstants.CREATE_CUSTOMER_ACCOUNT_ERROR_CODE, ErrorConstants.CREATE_CUSTOMER_ACCOUNT_ERROR_DESC);
    }

    private CustomerAccount buildCustomerAccountEntity(CreateCustomerAccountRequest createCustomerAccountRequest) {
        return CustomerAccount.builder()
                .customerName(createCustomerAccountRequest.getCustomerDetails().getCustomerName())
                .accountNumber(createCustomerAccountRequest.getAccountDetails().getAccountNumber())
                .accountType(createCustomerAccountRequest.getAccountDetails().getAccountType())
                .build();
    }

}
