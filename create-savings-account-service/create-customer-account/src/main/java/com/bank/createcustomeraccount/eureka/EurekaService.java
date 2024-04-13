package com.bank.createcustomeraccount.eureka;

import com.bank.createcustomeraccount.constants.ErrorConstants;
import com.bank.createcustomeraccount.exception.EurekaServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.SecureRandom;
import java.util.List;

@Service
public class EurekaService {
    private static final Logger logger = LoggerFactory.getLogger(EurekaService.class);

    private final DiscoveryClient discoveryClient;

    public EurekaService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public String fetchDomainServiceUrl(String serviceName) throws EurekaServiceException {
        SecureRandom secureRandom = new SecureRandom();
        try {
            List<ServiceInstance> serviceInstances = this.discoveryClient.getInstances(serviceName);
            if (!CollectionUtils.isEmpty(serviceInstances)) {
                ServiceInstance serviceInstance = serviceInstances.get(secureRandom.nextInt(serviceInstances.size()));

                if (null != serviceInstance) {
                    return serviceInstance.getUri().toString();
                }
            } else {
                logger.error("No Service Instance available in Eureka Registry for Service:: {}", serviceName);
                throw new EurekaServiceException(ErrorConstants.EUREKA_SERVICE_ERROR_CODE, ErrorConstants.EUREKA_SERVICE_ERROR_DESC);
            }
        } catch (Exception exception) {
            logger.error("Exception occurred while fetching service instance from Eureka. Exception is:: {}", exception);
            throw new EurekaServiceException(ErrorConstants.EUREKA_SERVICE_ERROR_CODE, ErrorConstants.EUREKA_SERVICE_ERROR_DESC, exception.getCause());
        }
        return null;
    }
}
