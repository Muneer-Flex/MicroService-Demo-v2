package com.bank.centralauthservice.exception;

public class UserSignUpException extends GlobalException {

    String errorCode;

    public UserSignUpException(String message) {
        super(message);
    }

    public UserSignUpException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserSignUpException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public UserSignUpException(String errorCode, String message, Throwable cause) {
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
