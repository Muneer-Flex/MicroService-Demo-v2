package com.bank.createcustomeraccount.exception;

public class IntegrationException extends GlobalException{
    public IntegrationException(String message) {
        super(message);
    }

    public IntegrationException(String message, String errorCode) {
        super(message, errorCode);
    }

    public IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IntegrationException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
