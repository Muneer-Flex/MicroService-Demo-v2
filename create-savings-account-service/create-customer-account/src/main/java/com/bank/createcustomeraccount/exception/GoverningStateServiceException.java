package com.bank.createcustomeraccount.exception;

public class GoverningStateServiceException extends GlobalException {
    public GoverningStateServiceException(String message) {
        super(message);
    }

    public GoverningStateServiceException(String message, String errorCode) {
        super(message, errorCode);
    }

    public GoverningStateServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoverningStateServiceException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
