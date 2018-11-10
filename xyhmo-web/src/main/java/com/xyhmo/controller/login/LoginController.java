package com.xyhmo.controller.login;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.exception.SystemException;
import com.xyhmo.commom.utils.IpUtil;
import com.xyhmo.commom.utils.ParamCheckUtil;
import com.xyhmo.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
            if(!ParamCheckUtil.checkMobileNumber(mobile) || !ParamCheckUtil.checkCode(code) || !checkCodeOver(code)){
                return result.fail(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
            }

        }catch (ParamException p){
            logger.error("LoginController：参数校验异常",p.getMessage());
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("LoginController:注册异常",e);
            return result.fail(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getCheckCode")
    @ResponseBody
    private Result getCheckCode(HttpServletRequest request, String mobile) {
        Result result = new Result();
        try {
            //校验手机号
            ParamCheckUtil.checkMobileNumber(mobile);
            //校验IP，防止网络攻击
            loginService.checkIp(IpUtil.getIpAddress(request));
            //获取验证码
            String code =loginService.genCheckCode(mobile);
            result.success(code,ReturnEnum.RETURN_MOBILE_CHECK_CODE.getDesc());
        } catch (ParamException p) {
            logger.error("LoginController：getCheckCode 手机号格式异常", p.getMessage());
            return result.fail(p.getCode(), p.getMessage());
        }catch (SystemException s){
            logger.error("LoginController：getCheckCode 遭受到短信攻击，或系统异常", s.getMessage());
            return result.fail(s.getCode(),s.getMessage());
        }catch (Exception e){
            logger.error("LoginController:获取验证码异常",e);
            return result.fail(e.getMessage());
        }
        return result;
    }

    private boolean checkCodeOver(String code){
        try{
            //todo 缓存里面找，找不见，返回false，找见，返回true
            //false
//            throw new ParamException(ParamEnum.PARAM_CODE_OVER.getCode(),ParamEnum.PARAM_CODE_OVER.getDesc());
            return true;
        }catch (Exception e){
            logger.error("redis中获取验证码失败",e);
            return false;
        }
    }
}
