package com.xyhmo.commom.exception;

import com.jd.cetus.mobile.enums.BizErrorCodeEnum;

/**
 * 服务层异常，检查异常，定位不影响可用率
 * @author <a href="mailto:cdguke@jd.com">guke</a>
 * @version 1.0. 2018/09/18
 * @since <分支名>
 */
public class BizServiceException extends Exception {
    private static final long serialVersionUID = -5592959581375527921L;
    private Integer code;
    private String msg;

    public BizServiceException(BizErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum.getCode(), errorCodeEnum.getMsg());
    }

    public BizServiceException(Integer code, String msg) {
        this(code.toString().concat(":").concat(msg), code, msg);
    }

    public BizServiceException(String message, Integer code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public BizServiceException(String message, Throwable cause, Integer code, String msg) {
        super(message, cause);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
