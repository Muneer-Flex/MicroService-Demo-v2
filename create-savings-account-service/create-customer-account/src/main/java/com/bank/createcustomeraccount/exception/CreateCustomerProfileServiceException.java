package com.bank.createcustomeraccount.exception;

public class CreateCustomerProfileServiceException extends GlobalException{
    public CreateCustomerProfileServiceException(String message) {
        super(message);
    }

    public CreateCustomerProfileServiceException(String message, String errorCode) {
        super(message, errorCode);
    }

    public CreateCustomerProfileServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateCustomerProfileServiceException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
