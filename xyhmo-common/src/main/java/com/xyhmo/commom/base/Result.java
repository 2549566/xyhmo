package com.xyhmo.commom.base;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = -5230176214305528119L;

    private boolean success=false;
    private Integer code;
    private Integer businessCode;
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

    public Integer getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(Integer businessCode) {
        this.businessCode = businessCode;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result fail(String message){
        this.success=false;
        this.message=message;
        this.code=0;
        return this;
    }

    public Result fail(Integer businessCode, String message){
        this.success=false;
        this.code = 0;
        this.businessCode=businessCode;
        this.message=message;
        return this;
    }

    public Result fail(Integer code, String message, T data){
        this.success=false;
        this.code = code;
        this.message=message;
        this.data = data;
        return this;
    }

    public Result success(T data, String message){
        this.success=true;
        this.code=1;
        this.message=message;
        this.data=data;
        return this;
    }

    public Result success(T data){
        this.success=true;
        this.code=1;
        this.message="成功";
        this.data=data;
        return this;
    }
}
