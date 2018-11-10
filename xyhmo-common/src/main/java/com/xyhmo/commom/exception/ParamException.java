package com.xyhmo.commom.exception;

/**
 * 统一异常
 */
public class ParamException extends RuntimeException {
    private Integer code;
    public ParamException() {
    }

    public ParamException(String message) {
        super(message);
    }
    public ParamException(Integer code,String message) {
        super(message);
        this.code=code;
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
