package com.xyhmo.controller.order;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.domain.Bulletin;
import com.xyhmo.service.BulletinService;
import com.xyhmo.service.OrderService;
import com.xyhmo.service.TokenService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private Result saveOrder(@RequestParam Map<String,String> map){
        Result result = new Result();
        if(CollectionUtils.isEmpty(map)){
            return result.fail(ParamEnum.PARAM_TOKEN_NOT_EXIST.getCode(),ParamEnum.PARAM_TOKEN_NOT_EXIST.getDesc());
        }
        Map<Long,Integer> orderMap = new HashMap<>();
        String req ="[0-9]*";
        try{
            String token = map.get("token");
            tokenService.checkTokenExist(token);
            for(String key:map.keySet()){
                if(key.equals(token)){
                    continue;
                }
                if(StringUtils.isEmpty(key) || StringUtils.isEmpty(map.get(key)) || !key.matches(req) || !map.get(key).matches(req)){
                    throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
                }
                orderMap.put(Long.parseLong(key),Integer.parseInt(map.get(key)));
            }
            if(CollectionUtils.isEmpty(orderMap)){
                throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
            }
            Long orderId= orderService.saveOrder(token,orderMap);
            if(orderId==null || orderId<1){
                return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
            }
            return result.success(orderId,ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("OrderController:订单入参错误",p);
            return result.fail(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
        }catch (Exception e){
            logger.error("OrderController：保存订单失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }

    }


}
