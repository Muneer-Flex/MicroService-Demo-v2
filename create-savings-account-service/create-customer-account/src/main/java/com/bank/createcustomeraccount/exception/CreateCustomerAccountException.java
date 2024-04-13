package com.bank.createcustomeraccount.exception;

public class CreateCustomerAccountException extends GlobalException {
    public CreateCustomerAccountException(String message) {
        super(message);
    }

    public CreateCustomerAccountException(String message, String errorCode) {
        super(message, errorCode);
    }

    public CreateCustomerAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateCustomerAccountException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
