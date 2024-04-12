package com.bank.centralauthservice.exception;

public class ForbiddenAccessException extends GlobalException {

    String errorCode;

    public ForbiddenAccessException(String message) {
        super(message);
    }

    public ForbiddenAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenAccessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ForbiddenAccessException(String errorCode, String message, Throwable cause) {
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
