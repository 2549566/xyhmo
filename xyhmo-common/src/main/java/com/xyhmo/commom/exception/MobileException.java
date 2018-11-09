package com.xyhmo.commom.exception;

/**
 * 统一异常
 */
public class MobileException extends RuntimeException {
    public MobileException() {
    }

    public MobileException(String message) {
        super(message);
    }

    public MobileException(String message, Throwable cause) {
        super(message, cause);
    }

    public MobileException(Throwable cause) {
        super(cause);
    }


}
