package com.bank.createcustomeraccount.exception;

public class GlobalException extends Exception {

    String errorCode;

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
