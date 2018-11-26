package com.xyhmo.controller.login;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.exception.SystemException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.IpUtil;
import com.xyhmo.commom.utils.ParamCheckUtil;
import com.xyhmo.domain.UserInfo;
import com.xyhmo.service.LoginService;
import com.xyhmo.service.TokenService;
import com.xyhmo.service.UserInfoService;
import com.xyhmo.vo.UserVo;
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
    private RedisService redisService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserInfoService userInfoService;
    /**
     * 注册，第一次登陆
     *
     * */
    @RequestMapping(value = "/login")
    @ResponseBody
    private Result login(String mobile, String code){
        Result result = new Result();
        try{
            if(!ParamCheckUtil.checkMobileNumber(mobile) || !ParamCheckUtil.checkCode(code) || !checkCodeOver(mobile,code)){
                return result.fail(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
            }
            UserVo vo = loginService.getUserInfo(mobile);
            String token = "";
            if(null==vo){
                token=tokenService.genToken(mobile);
                loginService.register(mobile,token);
            }else{
                token=loginService.saveUserVoToRedis(vo);
                result.setData(vo);
            }
            return result.success(token,vo,ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("LoginController：login 参数校验异常",p.getMessage());
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("LoginController:登录异常",e);
            return result.fail(e.getMessage());
        }
    }

    /**
     * 生成二维码
     *
     * */
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

    /**
     * 选择角色
     *
     * */
    @RequestMapping(value = "/choseRole")
    @ResponseBody
    private Result choseRole(String token,Integer userType){
        Result result = new Result();
        try{
            UserVo vo = tokenService.checkTokenExist(token);
            if(vo==null){
                return result.fail(SystemEnum.SYSTEM_GET_USERVO.getCode(),SystemEnum.SYSTEM_GET_USERVO.getDesc());
            }
            vo.setUserType(userType);
            //todo 可走异步，先插入缓存，再MQ入库
            userInfoService.EditUserInfo(vo);
            redisService.set(Contants.REDIS_TOKEKN_BEFORE+token,vo);
            return result.success(token,vo,ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("LoginController：choseRole token参数校验异常",p.getMessage());
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("LoginController:选择角色异常",e);
            return result.fail(e.getMessage());
        }
    }



    private boolean checkCodeOver(String mobile,String code){
        if(!code.equals(redisService.get(Contants.MOBILE_GEN_CODE+mobile))){
            throw new ParamException(ParamEnum.PARAM_CODE_OVER.getCode(),ParamEnum.PARAM_CODE_OVER.getDesc());
        }
        return true;
    }
}
