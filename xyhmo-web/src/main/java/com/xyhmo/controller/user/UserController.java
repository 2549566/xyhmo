package com.xyhmo.controller.user;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.service.UserInfoService;
import com.xyhmo.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/getUser")
    @ResponseBody
    private Result getUser(String token){
        Result result = new Result();
        try{
            UserVo vo =userInfoService.getUserVoByToken(token);
            result.success(vo, ReturnEnum.RETURN_SUCCESS.getDesc());
            return result;
        }catch (ParamException p){
            logger.error("BunnerController:token 不存在",p);
            return result.fail(ParamEnum.PARAM_TOKEN_NOT_EXIST.getCode(),ParamEnum.PARAM_TOKEN_NOT_EXIST.getDesc());
        }catch (Exception e){
            logger.error("BulletinController：获取用户信息失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }


}
