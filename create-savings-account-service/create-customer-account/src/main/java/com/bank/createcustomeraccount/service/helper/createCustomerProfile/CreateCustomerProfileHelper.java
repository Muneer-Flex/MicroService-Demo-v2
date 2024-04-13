package com.bank.createcustomeraccount.service.helper.createCustomerProfile;

import com.bank.createcustomeraccount.constants.AppConstants;
import com.bank.createcustomeraccount.constants.ErrorConstants;
import com.bank.createcustomeraccount.eureka.EurekaService;
import com.bank.createcustomeraccount.exception.CreateCustomerProfileServiceException;
import com.bank.createcustomeraccount.exception.EurekaServiceException;
import com.bank.createcustomeraccount.exception.IntegrationException;
import com.bank.createcustomeraccount.model.CustomerDetails;
import com.bank.createcustomeraccount.model.createCustomerProfile.CreateCustomerProfileRequest;
import com.bank.createcustomeraccount.service.helper.AppUtils;
import com.bank.createcustomeraccount.service.helper.restTemplate.RestTemplateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class CreateCustomerProfileHelper {

    private static final Logger logger = LoggerFactory.getLogger(CreateCustomerProfileHelper.class);

    @Value("${external.integrations.createCustomerProfileService.serviceName}")
    private String createCustomerProfileServiceName;

    @Value("${external.integrations.createCustomerProfileService.createCustomerProfile}")
    private String createCustomerProfileServiceUri;

    private final EurekaService eurekaService;
    private final RestTemplateHelper restTemplateHelper;
    private final RestTemplate restTemplate;

    public CreateCustomerProfileHelper(EurekaService eurekaService, RestTemplateHelper restTemplateHelper, RestTemplate restTemplate) {
        this.eurekaService = eurekaService;
        this.restTemplateHelper = restTemplateHelper;
        this.restTemplate = restTemplate;
    }

    public boolean createCustomerProfile(CustomerDetails customerDetails, String governingState, String requestId, String jwt) throws CreateCustomerProfileServiceException, EurekaServiceException, IntegrationException {
        HttpEntity<CreateCustomerProfileRequest> createCustomerProfileRequest = new HttpEntity<>(buildCreateCustomerProfile(customerDetails, governingState), buildHttpHeaders(requestId, jwt));

        String domainServiceUrl = eurekaService.fetchDomainServiceUrl(createCustomerProfileServiceName);
        URI createCustomerProfileUri = restTemplateHelper.buildURI(domainServiceUrl, createCustomerProfileServiceUri, null);
        ResponseEntity<String> createCustomerProfileResponseEntity = null;
        try {
            createCustomerProfileResponseEntity = restTemplate.exchange(createCustomerProfileUri, HttpMethod.POST, createCustomerProfileRequest, String.class);
        } catch (Exception exception) {
            logger.error("Integration Error Occurred while calling {}. Exception is:: {}", createCustomerProfileServiceName, exception);
            throw new IntegrationException(ErrorConstants.INTEGRATION_ERROR_CODE, ErrorConstants.INTEGRATION_ERROR_DESC, exception.getCause());
        }

        if(restTemplateHelper.isSuccessResponse(createCustomerProfileResponseEntity)) {
            return true;
        } else {
            logger.error("Create Customer Profile Error Response:: {}", AppUtils.convertToJson(createCustomerProfileResponseEntity.getBody()));
            throw new CreateCustomerProfileServiceException(ErrorConstants.CREATE_CUSTOMER_PROFILE_SERVICE_ERROR_CODE, ErrorConstants.CREATE_CUSTOMER_PROFILE_SERVICE_ERROR_DESC);
        }
    }

    private CreateCustomerProfileRequest buildCreateCustomerProfile(CustomerDetails customerDetails, String governingState) {
        return CreateCustomerProfileRequest.builder()
                .customerName(customerDetails.getCustomerName())
                .customerEmailId(customerDetails.getCustomerEmailId())
                .customerGoverningState(governingState)
                .build();
    }

    private HttpHeaders buildHttpHeaders(String requestId, String jwt) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("requestId", requestId);
        httpHeaders.add("Authorization", AppConstants.BEARER.concat(jwt));

        return httpHeaders;
    }
}
