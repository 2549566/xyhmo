package com.xyhmo.commom.exception;

/**
 * Created by chenchunyu on 2018/10/19.
 */
public class SubmitOrderException extends RuntimeException{

    private String errorCode;
    private String skus;

    public SubmitOrderException(String message, String errorCode, String skus) {
        super(message);
        this.errorCode = errorCode;
        this.skus = skus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }
}
