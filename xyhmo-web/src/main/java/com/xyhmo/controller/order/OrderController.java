package com.xyhmo.controller.order;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.DeliveryEnum;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.domain.Bulletin;
import com.xyhmo.service.BulletinService;
import com.xyhmo.service.OrderService;
import com.xyhmo.service.TokenService;
import com.xyhmo.vo.param.OrderParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenService tokenService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
    @ResponseBody
    private Result saveOrder(@RequestBody OrderParam orderParam){
        Result result = new Result();
        if(orderParam==null || CollectionUtils.isEmpty(orderParam.getSkuMap())){
            return result.fail(ParamEnum.PARAM_TOKEN_NOT_EXIST.getCode(),ParamEnum.PARAM_TOKEN_NOT_EXIST.getDesc());
        }
        try{
            String token = orderParam.getToken();
            tokenService.checkTokenExist(token);
            if(orderParam.getIsDelivery()== DeliveryEnum.IS_DELIVERY.getCode() && StringUtils.isBlank(orderParam.getAddress())){
                throw new ParamException(ParamEnum.PARAM_DELIVERY_ERROR.getCode(),ParamEnum.PARAM_DELIVERY_ERROR.getDesc());
            }
            Long orderId= orderService.saveOrder(orderParam);
            if(orderId==null || orderId<1){
                return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
            }
            return result.success(orderId,ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("OrderController:订单入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("OrderController：保存订单失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }

    }


}
