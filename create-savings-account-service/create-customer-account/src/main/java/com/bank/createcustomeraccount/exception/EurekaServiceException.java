package com.bank.createcustomeraccount.exception;

public class EurekaServiceException extends GlobalException {
    public EurekaServiceException(String message) {
        super(message);
    }

    public EurekaServiceException(String message, String errorCode) {
        super(message, errorCode);
    }

    public EurekaServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EurekaServiceException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
