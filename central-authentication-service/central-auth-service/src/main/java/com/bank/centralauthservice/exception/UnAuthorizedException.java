package com.bank.centralauthservice.exception;

public class UnAuthorizedException extends GlobalException {

    String errorCode;

    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorizedException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public UnAuthorizedException(String errorCode, String message, Throwable cause) {
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
