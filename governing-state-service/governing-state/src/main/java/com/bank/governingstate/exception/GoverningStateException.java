package com.bank.governingstate.exception;

public class GoverningStateException extends GlobalException {

    String errorCode;

    public GoverningStateException(String message) {
        super(message);
    }

    public GoverningStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoverningStateException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public GoverningStateException(String message, String errorCode, Throwable cause) {
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
