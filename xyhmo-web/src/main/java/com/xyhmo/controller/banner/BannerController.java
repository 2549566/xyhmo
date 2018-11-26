package com.xyhmo.controller.banner;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.domain.Banner;
import com.xyhmo.service.BannerService;
import com.xyhmo.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/banner")
public class BannerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BannerService bannerService;
    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/getBannerList")
    @ResponseBody
    private Result getBannerList(String token){
        Result result = new Result();
        try{
            tokenService.checkTokenExist(token);
            //TODO 需要上传轮播图
            List<Banner> bannerList = bannerService.getBannerList();
            result.success(bannerList, ReturnEnum.RETURN_SUCCESS.getDesc());
            return result;
        }catch (ParamException p){
            logger.error("BunnerController:token 不存在",p);
            return result.fail(ParamEnum.PARAM_TOKEN_NOT_EXIST.getCode(),ParamEnum.PARAM_TOKEN_NOT_EXIST.getDesc());
        }catch (Exception e){
            logger.error("BunnerController：获取轮播图列表失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }

    }


}
