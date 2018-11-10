package com.xyhmo.controller.login;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/register")
    @ResponseBody
    private Result register(String mobile, String code){
        Result result = new Result();
        try{
            if(!checkParam(mobile,code)){
                return result.fail(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
            }

        }catch (ParamException p){
            logger.error("LoginController：参数校验异常",p.getMessage());
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("LoginController:注册异常",e);
        }
        return null;
    }

    private boolean checkParam(String mobile,String code){
        if(StringUtils.isEmpty(mobile) ||  !mobile.matches( "[0-9]{1,}" )){
            throw new ParamException(ParamEnum.PARAM_MOBILE_RULE.getCode(),ParamEnum.PARAM_MOBILE_RULE.getDesc());
        }
        if(StringUtils.isEmpty(code) ||  !code.matches( "[0-9]{1,}" )){
            throw new ParamException(ParamEnum.PARAM_CODE_RULE.getCode(),ParamEnum.PARAM_CODE_RULE.getDesc());
        }
        if(!checkCodeOver(code)){
            throw new ParamException(ParamEnum.PARAM_CODE_OVER.getCode(),ParamEnum.PARAM_CODE_OVER.getDesc());
        }
        return true;
    }

    private boolean checkCodeOver(String code){
        try{
            //todo 缓存里面找，找不见，返回false，找见，返回true
            return true;
        }catch (Exception e){
            logger.error("redis中获取验证码失败",e);
            return false;
        }
    }
}
