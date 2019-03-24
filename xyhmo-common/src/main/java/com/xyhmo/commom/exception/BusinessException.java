package com.xyhmo.commom.exception;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {
    private Integer code;
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(Integer code, String message) {
        super(message);
        this.code=code;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
