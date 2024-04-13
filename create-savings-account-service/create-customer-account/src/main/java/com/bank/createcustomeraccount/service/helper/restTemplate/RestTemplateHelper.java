package com.bank.createcustomeraccount.service.helper.restTemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class RestTemplateHelper {

    public URI buildURI(String domainServiceUrl, String serviceUri, MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriComponentsBuilder;
        if(null != queryParams) {
            uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(domainServiceUrl.concat(serviceUri))
                    .queryParams(queryParams);
        } else {
            uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(domainServiceUrl.concat(serviceUri));
        }
        return uriComponentsBuilder.build().encode().toUri();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public boolean isSuccessResponse(ResponseEntity<?> responseEntity) {
        return null != responseEntity && null != responseEntity.getBody() && responseEntity.getStatusCode().is2xxSuccessful();
    }

    public boolean is4xxResponse(ResponseEntity<?> responseEntity) {
        return null != responseEntity && null != responseEntity.getBody() && responseEntity.getStatusCode().is4xxClientError();
    }

    public boolean is5xxResponse(ResponseEntity<?> responseEntity) {
        return null != responseEntity && null != responseEntity.getBody() && responseEntity.getStatusCode().is5xxServerError();
    }
}
