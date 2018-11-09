package com.xyhmo.commom.base;

/**
 * 错误码
 * @author <a href="mailto:cdguke@jd.com">guke</a>
 * @version 1.0. 2018/09/18
 * @since <分支名>
 */
public enum BizErrorCodeEnum {
    //0*** 成功
    SUCCESS(0, "操作成功"),

    //1*** 失败：参数类错误
    PARAM_NULL(1001, "参数不能为空"),
    PARAM_FORMAT_ERROR(1002, "参数格式不正确"),
    PARAM_VALUE_ERROR(1003, "参数值不正确"),

    //2*** 失败：权限类错误
    NO_VIEW_SKU(2001, "当前用户不能浏览该商品"),
    NO_BUY(2002, "不能购买该商品"),
    NO_PERMISSION_TO_BUY(2003, "价格接口返回无权限购买"),
    NOT_LOGIN(2004,"未登陆"),

    //3*** 失败：业务错误
    RETURN_NULL(3001, "返回数据为空"),
    WARE_NOT_SET_PRICE(3002, "商品没有配置价格"),
    SUBMIT_ORDER_WARE_ERROR(3003,"提交采购单时跟商品相关的异常"),

    //5*** 失败：系统错误
    SYSTEM_ERROR(5001, "服务异常，请稍后重试"),
    ;

    /**
     * 前置补齐
     * startWith("商品编码") --> 商品编码,参数不能为空
     * @param original 字符串
     * @return
     */
    public String startWith(String original) {
        return original.concat(",").concat(this.msg);
    }

    /**
     * 后置补齐
     * endWith("商品编码") --> 参数不能为空,商品编码
     * @param original 字符串
     * @return
     */
    public String endWith(String original) {
        return this.msg.concat(",").concat(original);
    }

    BizErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
