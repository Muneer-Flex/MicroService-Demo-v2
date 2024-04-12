package com.bank.createcustomerprofile.exception;

public class CreateCustomerProfileException extends GlobalException {

    private String errorCode;

    public CreateCustomerProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateCustomerProfileException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public CreateCustomerProfileException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
