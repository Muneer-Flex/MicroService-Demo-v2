package com.bank.createcustomeraccount.service.helper.governingState;

import com.bank.createcustomeraccount.constants.ErrorConstants;
import com.bank.createcustomeraccount.eureka.EurekaService;
import com.bank.createcustomeraccount.exception.EurekaServiceException;
import com.bank.createcustomeraccount.exception.GoverningStateServiceException;
import com.bank.createcustomeraccount.exception.IntegrationException;
import com.bank.createcustomeraccount.model.governingState.GoverningStateExceptionResponse;
import com.bank.createcustomeraccount.model.governingState.GoverningStateResponse;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class GoverningStateHelper {
    private static final Logger logger = LoggerFactory.getLogger(GoverningStateHelper.class);

    @Value("${external.integrations.governingState.serviceName}")
    private String governingStateServiceName;

    @Value("${external.integrations.governingState.fetchGoverningStateByZipCode}")
    private String fetchGoverningStateByZipCodeUri;

    private final EurekaService eurekaService;
    private final RestTemplateHelper restTemplateHelper;
    private final RestTemplate restTemplate;

    public GoverningStateHelper(EurekaService eurekaService, RestTemplateHelper restTemplateHelper, RestTemplate restTemplate) {
        this.eurekaService = eurekaService;
        this.restTemplateHelper = restTemplateHelper;
        this.restTemplate = restTemplate;
    }

    public String fetchGoverningStateByZipCode(String zipCode, String requestId) throws GoverningStateServiceException, EurekaServiceException, IntegrationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(buildHttpHeaders(requestId));

        String domainServiceUrl = eurekaService.fetchDomainServiceUrl(governingStateServiceName);
        URI fetchGoverningStateUri = restTemplateHelper.buildURI(domainServiceUrl, fetchGoverningStateByZipCodeUri, buildQueryParams(zipCode));
        ResponseEntity<String> governingStateResponseEntity = null;
        try{
            governingStateResponseEntity = restTemplate.exchange(fetchGoverningStateUri, HttpMethod.GET, requestEntity, String.class);
        } catch (Exception exception) {
            logger.error("Integration Error Occurred while calling {}. Exception is:: {}", governingStateServiceName, exception);
            throw new IntegrationException(ErrorConstants.INTEGRATION_ERROR_CODE, ErrorConstants.INTEGRATION_ERROR_DESC, exception.getCause());
        }

        if (restTemplateHelper.isSuccessResponse(governingStateResponseEntity)) {
            GoverningStateResponse governingStateResponse = AppUtils.deserialize(governingStateResponseEntity.getBody(), GoverningStateResponse.class);

            return governingStateResponse.getZipCode();
        } else {
            logger.error("Governing State Error Response:: {}", AppUtils.convertToJson(governingStateResponseEntity.getBody()));
            throw new GoverningStateServiceException(ErrorConstants.GOVERNING_STATE_ERROR_CODE, ErrorConstants.GOVERNING_STATE_ERROR_DESC);
        }
    }

    private MultiValueMap<String, String> buildQueryParams(String zipCode) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("zipcode", zipCode);

        return queryParams;
    }

    private HttpHeaders buildHttpHeaders(String requestId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("requestId", requestId);

        return httpHeaders;
    }
}
