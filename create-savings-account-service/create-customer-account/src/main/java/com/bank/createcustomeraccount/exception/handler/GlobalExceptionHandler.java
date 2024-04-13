package com.bank.createcustomeraccount.exception.handler;

import com.bank.createcustomeraccount.constants.ErrorConstants;
import com.bank.createcustomeraccount.exception.*;
import com.bank.createcustomeraccount.model.ApiExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> handleBadRequestException(MethodArgumentNotValidException methodArgumentNotValidException) {
        logger.error("Bad Request. Required Fields are not present. Exception is:: {}", methodArgumentNotValidException.getMessage());

        StringBuffer errorDescription = new StringBuffer();

        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
            errorDescription.append("Request Field - ");
            errorDescription.append(((FieldError) error).getField());
            errorDescription.append(" ");
            errorDescription.append(error.getDefaultMessage());
            errorDescription.append(";");
        });

        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorCode(ErrorConstants.BAD_REQUEST_ERROR_CODE)
                .errorDescription(errorDescription.toString())
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {CreateCustomerAccountException.class, GoverningStateServiceException.class, CentralAuthServiceException.class, EurekaServiceException.class, CreateCustomerProfileServiceException.class, IntegrationException.class})
    public ResponseEntity<ApiExceptionResponse> handleCreateCustomerAccountException(GlobalException globalException) {
        logger.error("Handling Service Exception in Global Exception Handler");

        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorCode(globalException.getErrorCode())
                .errorDescription(globalException.getMessage())
                .errorCause(null != globalException.getCause() ? globalException.getCause().toString(): null)
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
