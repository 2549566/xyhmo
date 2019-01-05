package com.xyhmo.controller.order.wareOrder;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.exception.SystemException;
import com.xyhmo.service.TokenService;
import com.xyhmo.service.orderWare.OrderWareService;
import com.xyhmo.vo.order.OrderVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/orderWare")
public class OrderWareController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenService tokenService;
    @Autowired
    private OrderWareService orderWareService;

    /**
     * 获取订单详情
     * 点击订单，进入我的订单详情页面
     * token:登录人员验证
     * orderId:订单ID
     * */
    @RequestMapping(value = "/getOrderWareDetail", method = RequestMethod.GET)
    @ResponseBody
    public Result getOrderWareDetail(String token,String workerPin,String orderId){
        Result result = new Result();
        try{
            tokenService.checkTokenExist(token);
            if(StringUtils.isEmpty(orderId) ||StringUtils.isEmpty(workerPin)){
                throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
            }
            OrderVo orderVo=orderWareService.getOrderWareDetail(workerPin,orderId);
            return result.success(orderVo, ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("OrderWareController:根据订单ID获取订单详情入参异常",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (SystemException s){
            logger.error("OrderWareController:根据订单ID获取订单详情失败",s);
            return result.fail(s.getCode(),s.getMessage());
        }catch (Exception e){
            logger.error("OrderWareController：根据订单ID获取订单详情失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }
}
