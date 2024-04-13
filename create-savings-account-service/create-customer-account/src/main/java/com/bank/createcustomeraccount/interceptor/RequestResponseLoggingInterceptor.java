package com.bank.createcustomeraccount.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(request, response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        logger.info("Invoking ==> {} {}", request.getMethod(), request.getURI());
        logger.info("Request Headers :: {}", request.getHeaders());
        logger.info("Request Body :: {}", new String(body, StandardCharsets.UTF_8));
    }

    private void logResponse(HttpRequest request, ClientHttpResponse response) throws IOException {
        logger.info("Response Received <== {} {} {}", response.getStatusCode(), request.getMethod(), request.getURI());
        logger.info("Response Headers :: {}", response.getHeaders());
        logger.info("Response Body :: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
    }
}
