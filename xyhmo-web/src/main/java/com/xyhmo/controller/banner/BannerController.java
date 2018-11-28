package com.xyhmo.controller.banner;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.domain.Banner;
import com.xyhmo.service.BannerService;
import com.xyhmo.service.TokenService;
import com.xyhmo.vo.UserVo;
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
    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/getBannerList")
    @ResponseBody
    private Result getBannerList(String token){
        Result result = new Result();
        try{
            tokenService.checkTokenExist(token);
            //TODO 需要上传轮播图
            List<Banner> bannerList = bannerService.getBannerList();
            UserVo vo = redisService.get(Contants.REDIS_TOKEKN_BEFORE+token);
            if(vo==null){
                return result.fail(ParamEnum.PARAM_TOKEN_NOT_EXIST.getCode(),ParamEnum.PARAM_TOKEN_NOT_EXIST.getDesc());
            }
            redisService.set(Contants.REDIS_TOKEKN_BEFORE+token,vo,Contants.TOKEN_OVER_TIME);
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
