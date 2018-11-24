package com.xyhmo.controller.banner;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.domain.Banner;
import com.xyhmo.service.BannerService;
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

    @RequestMapping(value = "/getBannerList")
    @ResponseBody
    private Result getBannerList(){
        Result result = new Result();
        try{
            List<Banner> bannerList = bannerService.getBannerList();
            result.success(bannerList, ReturnEnum.RETURN_SUCCESS.getDesc());
            return result;
        }catch (Exception e){
            logger.error("BunnerController：获取轮播图列表失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }

    }


}
