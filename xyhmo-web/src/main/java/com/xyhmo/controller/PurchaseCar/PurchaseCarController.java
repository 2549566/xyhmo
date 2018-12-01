package com.xyhmo.controller.PurchaseCar;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.ParamCheckUtil;
import com.xyhmo.domain.Banner;
import com.xyhmo.service.BannerService;
import com.xyhmo.service.PurchaseCarService;
import com.xyhmo.service.TokenService;
import com.xyhmo.vo.purchaseCar.PurchaseWareVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/purchaseCar")
public class PurchaseCarController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private PurchaseCarService purchaseCarService;
    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/getTotalNum")
    @ResponseBody
    private Result getTotalNum(String token){
        Result result = new Result();
        try{
            Integer totalNum = purchaseCarService.getTotalNum(token);
            result.success(totalNum,ReturnEnum.RETURN_SUCCESS.getDesc());
            return result;
        }catch (ParamException p){
            logger.error("PurchaseCarController:入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("PurchaseCarController：获取轮采购车总数量失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }

    }

    @RequestMapping(value = "/getSelectNum")
    @ResponseBody
    private Result getSelectNum(String token){
        Result result = new Result();
        try{
            Integer getSelectNum = purchaseCarService.getSelectNum(token);
            result.success(getSelectNum,ReturnEnum.RETURN_SUCCESS.getDesc());
            return result;
        }catch (ParamException p){
            logger.error("PurchaseCarController:入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("PurchaseCarController：获取采购车选中数量失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }

    }

    @RequestMapping(value = "/getPurchaseWareList")
    @ResponseBody
    private Result getPurchaseWareList(String token){
        Result result = new Result();
        try{
            tokenService.checkTokenExist(token);
            List<PurchaseWareVo> voList = purchaseCarService.getPurchaseWareList(token);
            return result.success(voList,ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("PurchaseCarController:入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("PurchaseCarController：获取采购车内商品列表失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }

    @RequestMapping(value = "/addWareToPurchaseCar", method = RequestMethod.POST)
    @ResponseBody
    private Result addWareToPurchaseCar(String token,Long skuId){
        Result result = new Result();
        try{
            tokenService.checkTokenExist(token);
            Boolean success = purchaseCarService.addWareToPurchaseCar(token,skuId);
            return result.success(success,ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("PurchaseCarController:入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("PurchaseCarController：向采购车中添加一个商品失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }

    @RequestMapping(value = "/updateWareNum", method = RequestMethod.POST)
    @ResponseBody
    private Result updateWareNum(String token,Long skuId,Integer num){
        Result result = new Result();
        try{
            tokenService.checkTokenExist(token);
            Boolean success = purchaseCarService.updateWareNum(token,skuId,num);
            return result.success(success,ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("PurchaseCarController:入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("PurchaseCarController：修改采购车内的商品数量失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }

    @RequestMapping(value = "/updateWareSelectStatus", method = RequestMethod.POST)
    @ResponseBody
    private Result updateWareSelectStatus(String token,Long skuId,Boolean selected){
        Result result = new Result();
        try{
            tokenService.checkTokenExist(token);
            Boolean success = purchaseCarService.updateWareSelectStatus(token,skuId,selected);
            return result.success(success,ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("PurchaseCarController:入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("PurchaseCarController：修改采购车内商品的选中状态失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }

    @RequestMapping(value = "/deletePurchaseCarWareList", method = RequestMethod.POST)
    @ResponseBody
    private Result deletePurchaseCarWareList(String token,String skuIds){
        Result result = new Result();
        try{
            ParamCheckUtil.checkSkuIds(skuIds);
            tokenService.checkTokenExist(token);
            List<PurchaseWareVo> voList = purchaseCarService.deletePurchaseCarWareList(token,skuIds);
            return result.success(voList,ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("PurchaseCarController:入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("PurchaseCarController：删除采购车内的商品列表失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }
}
