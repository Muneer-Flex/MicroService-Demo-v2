package com.bank.createcustomeraccount.exception;

public class CentralAuthServiceException extends GlobalException {
    public CentralAuthServiceException(String message) {
        super(message);
    }

    public CentralAuthServiceException(String message, String errorCode) {
        super(message, errorCode);
    }

    public CentralAuthServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CentralAuthServiceException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
