package com.xyhmo.commom.base;

import java.io.Serializable;

public class CetusResult<T> implements Serializable {
    private static final long serialVersionUID = -5230176214305528119L;

    private boolean success=false;
    private Integer code;
    private String message;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public CetusResult fail(String message){
        this.success=false;
        this.message=message;
        this.code=-1;
        return this;
    }

    public CetusResult fail(Integer code, String message){
        this.success=false;
        this.code = code;
        this.message=message;
        return this;
    }

    public CetusResult fail(Integer code, String message, T data){
        this.success=false;
        this.code = code;
        this.message=message;
        this.data = data;
        return this;
    }

    public CetusResult success(T data, String message){
        this.success=true;
        this.code=0;
        this.message=message;
        this.data=data;
        return this;
    }

    public CetusResult success(T data){
        this.success=true;
        this.code=0;
        this.message="成功";
        this.data=data;
        return this;
    }
}
