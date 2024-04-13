package com.bank.createcustomeraccount.service.helper.centralAuth;

import com.bank.createcustomeraccount.constants.ErrorConstants;
import com.bank.createcustomeraccount.eureka.EurekaService;
import com.bank.createcustomeraccount.exception.CentralAuthServiceException;
import com.bank.createcustomeraccount.exception.EurekaServiceException;
import com.bank.createcustomeraccount.exception.IntegrationException;
import com.bank.createcustomeraccount.model.centralAuth.AuthenticationRequest;
import com.bank.createcustomeraccount.model.centralAuth.AuthenticationResponse;
import com.bank.createcustomeraccount.service.helper.AppUtils;
import com.bank.createcustomeraccount.service.helper.restTemplate.RestTemplateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class CentralAuthServiceHelper {

    private static final Logger logger = LoggerFactory.getLogger(CentralAuthServiceHelper.class);

    @Value("${external.integrations.centralAuthService.serviceName}")
    private String centralAuthServiceName;

    @Value("${external.integrations.centralAuthService.generateJwt}")
    private String generateJwtServiceUri;

    @Value("${application.authentication.username}")
    private String userName;

    @Value("${application.authentication.password}")
    private String password;

    private final EurekaService eurekaService;
    private final RestTemplateHelper restTemplateHelper;
    private final RestTemplate restTemplate;

    public CentralAuthServiceHelper(EurekaService eurekaService, RestTemplateHelper restTemplateHelper, RestTemplate restTemplate) {
        this.eurekaService = eurekaService;
        this.restTemplateHelper = restTemplateHelper;
        this.restTemplate = restTemplate;
    }

    //TODO: Implement JWT Caching
    public String generateJwtFromCentralAuth(String privilege, String requestId) throws CentralAuthServiceException, EurekaServiceException, IntegrationException {
        HttpEntity<AuthenticationRequest> authenticationRequest
                = new HttpEntity<>(buildAuthenticationRequest(privilege), buildHttpHeaders(requestId));

        String domainServiceUrl = eurekaService.fetchDomainServiceUrl(centralAuthServiceName);
        URI generateJwtUri = restTemplateHelper.buildURI(domainServiceUrl, generateJwtServiceUri, null);
        ResponseEntity<String> authenticationResponseEntity = null;

        try {
            authenticationResponseEntity = restTemplate.exchange(generateJwtUri, HttpMethod.POST, authenticationRequest, String.class);
        } catch (Exception exception) {
            logger.error("Integration Error Occurred while calling {}. Exception is:: {}", centralAuthServiceName, exception);
            throw new IntegrationException(ErrorConstants.INTEGRATION_ERROR_CODE, ErrorConstants.INTEGRATION_ERROR_DESC, exception.getCause());
        }

        if (restTemplateHelper.isSuccessResponse(authenticationResponseEntity)) {
            AuthenticationResponse authenticationResponse = AppUtils.deserialize(authenticationResponseEntity.getBody(), AuthenticationResponse.class);

            return authenticationResponse.getJwt();
        } else {
            logger.error("Central Auth Service Error Response:: {}", AppUtils.convertToJson(authenticationResponseEntity.getBody()));
            throw new CentralAuthServiceException(ErrorConstants.CENTRAL_AUTH_SERVICE_ERROR_CODE, ErrorConstants.CENTRAL_AUTH_SERVICE_ERROR_DESC);
        }
    }

    private AuthenticationRequest buildAuthenticationRequest(String privilege) {
        return AuthenticationRequest.builder()
                .userName(userName)
                .password(password)
                .accessRequestedFor(privilege)
                .build();
    }

    private HttpHeaders buildHttpHeaders(String requestId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("requestId", requestId);

        return httpHeaders;
    }

}
