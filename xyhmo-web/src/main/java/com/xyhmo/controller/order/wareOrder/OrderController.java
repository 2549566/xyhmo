package com.xyhmo.controller.order.wareOrder;


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
import com.xyhmo.vo.order.OrderVo;
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

    @RequestMapping(value = "/saveOrder", method = RequestMethod.GET)
    //TODO 换成get
//    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
    @ResponseBody
    //TODO 添加参数
//    private Result saveOrder(@RequestBody OrderParam orderParam){
        private Result saveOrder(){
        OrderParam orderParam = new OrderParam();
        //TODO 去掉默认值
        orderParam.setToken("1f2e40a670db6c40b37e2cb577566b65");
        orderParam.setIsDelivery(DeliveryEnum.IS_NOT_DELIVERY.getCode());
        Map<Long,Integer> map = new HashMap<>();
        map.put(100000001l,1);
        map.put(100000002l,2);
        map.put(100000003l,3);
        map.put(100000004l,4);
        orderParam.setSkuMap(map);
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

    /**
     * 获取业务员订单
     * token：代理商token
     * orderStatus：订单状态 如果为null 则获取所有的订单表
     * list中的数据倒叙排列
     * */
    @RequestMapping(value = "/getWorkerOrderList", method = RequestMethod.GET)
    @ResponseBody
    private Result getWorkerOrderList(String token,Integer orderStatus){
        Result result = new Result();
        try{
            tokenService.checkTokenExist(token);
            if(orderStatus==null || orderStatus>20){
                throw new ParamException(ParamEnum.PARAM_ORDER_STATUS.getCode(),ParamEnum.PARAM_ORDER_STATUS.getDesc());
            }
            List<OrderVo> orderVoList =orderService.getWorkerOrderList(token,orderStatus);
            return result.success(orderVoList, ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("OrderProxyController:代理商订单表入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("OrderProxyController：代理商获取订单列表失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }


}
