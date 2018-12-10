package com.xyhmo.controller.order;

import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.service.OrderProxyService;
import com.xyhmo.service.TokenService;
import com.xyhmo.vo.order.OrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/orderProxy")
public class OrderProxyController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private TokenService tokenService;
    @Autowired
    private OrderProxyService orderProxyService;

    /**
     * 获取代理商订单
     * token：代理商token
     * orderStatus：订单状态 如果为null 则获取所有的订单表
     *
     * */
    @RequestMapping(value = "/getProxyOrderList", method = RequestMethod.GET)
    @ResponseBody
    private Result getProxyOrderList(String token,Integer orderStatus){
        Result result = new Result();
        try{
            tokenService.checkTokenExist(token);
            if(orderStatus!=null && orderStatus>20){
                throw new ParamException(ParamEnum.PARAM_ORDER_STATUS.getCode(),ParamEnum.PARAM_ORDER_STATUS.getDesc());
            }
            List<OrderVo> orderVoList =orderProxyService.getOrderProxyList(token,orderStatus);
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

