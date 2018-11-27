package com.xyhmo.controller.ware;


import com.github.pagehelper.PageInfo;
import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.utils.ParamCheckUtil;
import com.xyhmo.domain.Bulletin;
import com.xyhmo.domain.WareInfo;
import com.xyhmo.service.BulletinService;
import com.xyhmo.service.TokenService;
import com.xyhmo.service.WareInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/ware")
public class WareController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenService tokenService;
    @Autowired
    private WareInfoService wareInfoService;

    @RequestMapping(value = "/getWareList")
    @ResponseBody
    private Result getWareList(String token,Integer skuType,Integer pageNum){
        Result result = new Result();
        try{
            tokenService.checkTokenExist(token);
            ParamCheckUtil.checkPageNum(pageNum);
            PageInfo pageInfo = wareInfoService.getWareInfoListByUserType(token,skuType,pageNum);
//            Map<Integer,List<WareInfo>> map = wareInfoService.getWareInfoList(token);
//            result.success(map,ReturnEnum.RETURN_SUCCESS.getDesc());
            return result.success(pageInfo,ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("BunnerController:token 不存在",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("BulletinController：获取商品列表失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }

    }


}
