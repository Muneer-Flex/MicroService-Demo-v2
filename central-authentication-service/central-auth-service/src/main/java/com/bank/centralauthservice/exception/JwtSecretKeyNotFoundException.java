package com.bank.centralauthservice.exception;

public class JwtSecretKeyNotFoundException extends GlobalException {

    String errorCode;

    public JwtSecretKeyNotFoundException(String message) {
        super(message);
    }

    public JwtSecretKeyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtSecretKeyNotFoundException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public JwtSecretKeyNotFoundException(String errorCode, String message, Throwable cause) {
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
