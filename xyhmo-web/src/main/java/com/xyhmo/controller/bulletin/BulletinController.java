package com.xyhmo.controller.bulletin;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.domain.Banner;
import com.xyhmo.domain.Bulletin;
import com.xyhmo.service.BannerService;
import com.xyhmo.service.BulletinService;
import com.xyhmo.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/bulletin")
public class BulletinController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BulletinService bulletinService;
    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/getBulletinList")
    @ResponseBody
    private Result getBulletinList(String token){
        Result result = new Result();
        try{
            tokenService.checkTokenExist(token);
            List<Bulletin> bulletinList = bulletinService.getBulletinList();
            result.success(bulletinList, ReturnEnum.RETURN_SUCCESS.getDesc());
            return result;
        }catch (ParamException p){
            logger.error("BunnerController:token 不存在",p);
            return result.fail(ParamEnum.PARAM_TOKEN_NOT_EXIST.getDesc());
        }catch (Exception e){
            logger.error("BulletinController：获取快报列表失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }

    }


}
