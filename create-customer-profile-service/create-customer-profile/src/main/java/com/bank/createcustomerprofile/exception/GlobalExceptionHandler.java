package com.bank.createcustomerprofile.exception;

import com.bank.createcustomerprofile.constants.ErrorConstants;
import com.bank.createcustomerprofile.model.ApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> handleBadRequestException(MethodArgumentNotValidException methodArgumentNotValidException) {
        StringBuffer errorDescription = new StringBuffer();

        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
            errorDescription.append("Request Field - ");
            errorDescription.append(((FieldError)error).getField());
            errorDescription.append(" ");
            errorDescription.append(error.getDefaultMessage());
            errorDescription.append(";");
        });

        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorCode(ErrorConstants.CUST_002)
                .errorDescription(errorDescription.toString())
                .createdOn(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreateCustomerProfileException.class)
    public ResponseEntity<ApiExceptionResponse> handleCreateCustomerProfileException(CreateCustomerProfileException createCustomerProfileException) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .errorCode(createCustomerProfileException.getErrorCode())
                .errorDescription(createCustomerProfileException.getMessage())
                .createdOn(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
