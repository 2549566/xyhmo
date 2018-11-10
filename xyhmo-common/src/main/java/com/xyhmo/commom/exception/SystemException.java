package com.xyhmo.commom.exception;

/**
 * 统一异常
 */
public class SystemException extends RuntimeException {
    private Integer code;
    public SystemException() {
    }

    public SystemException(String message) {
        super(message);
    }
    public SystemException(Integer code, String message) {
        super(message);
        this.code=code;
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
