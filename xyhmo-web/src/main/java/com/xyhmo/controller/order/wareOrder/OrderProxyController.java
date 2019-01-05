package com.xyhmo.controller.order.wareOrder;

import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.exception.SystemException;
import com.xyhmo.service.orderWare.OrderProxyService;
import com.xyhmo.service.orderWare.OrderWorkerService;
import com.xyhmo.service.TokenService;
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
@RequestMapping("/orderProxy")
public class OrderProxyController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private TokenService tokenService;
    @Autowired
    private OrderProxyService orderProxyService;
    @Autowired
    private OrderWorkerService orderWorkerService;

    /**
     * 获取代理商订单
     * token：代理商token
     * orderStatus：订单状态 如果为null 则获取所有的订单表
     *
     * */
    @RequestMapping(value = "/getProxyOrderList", method = RequestMethod.GET)
    @ResponseBody
    public Result getProxyOrderList(String token,Integer orderStatus){
        Result result = new Result();
        try{
            tokenService.checkTokenExist(token);
            if(orderStatus==null || orderStatus>20){
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

    /**
     * （代理商界面）根据订单ID修改订单状态 当前的订单状态为1
     *  代理商点击结单
     *  token:代理商token
     *  orderId:订单ID
     *  isPay:是否已付款
     *  price:付款金额
     *  确定货物已经送达到业务员手中
     *
     *  页面展示：1：此订单不配送，那么不显示配送费用填写栏
     *           2：如果此单需要配送，那么必须填写配送费用
     *           3：默认显示已经支付，需要填写实际支付费用
     *           4：如果未支付，就点击未支付，隐藏填写支付费用模块
     */
    //todo 修改为POST方法
    @RequestMapping(value = "/sureWareAlreadyToWorker", method = RequestMethod.POST)
    @ResponseBody
    public Result sureWareAlreadyToWorker(String token,String orderId,Integer isPay,Double warePrice,Double deliveryPrice){
        Result result = new Result();
        try{
            if(StringUtils.isEmpty(token) || StringUtils.isEmpty(orderId)){
                throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
            }
            if(isPay==1 && (warePrice==null|| warePrice<=0.00)){
                throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
            }
            tokenService.checkTokenExist(token);
            Boolean flag = orderProxyService.sureWareAlreadyToWorker(token,orderId,isPay,warePrice,deliveryPrice);
            return result.success(ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("OrderProxyController:代理商根据订单ID修改订单状态入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("OrderProxyController：代理商根据订单ID修改订单状态失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }

    /**
     * 根据业务员pin获取业务员的订单（代理商我的关系，点击业务员列表，显示业务员的订单列表）
     * token：代理商token
     * workerPin：订单ID
     * 无Redis
     * */
    @RequestMapping(value = "/getWorkerOrderListByWorkerPin", method = RequestMethod.GET)
    @ResponseBody
    public Result getWorkerOrderListByWorkerPin(String token,String workerPin){
        Result result = new Result();
        try{
            tokenService.checkTokenExist(token);
            if(StringUtils.isEmpty(workerPin)){
                throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
            }
            List<OrderVo> orderVoList=orderWorkerService.getWorkerOrderListByWorkerPin(workerPin);
            return result.success(orderVoList,ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("OrderWorkerController:根据业务员pin获取业务员订单入参异常",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (SystemException s){
            logger.error("OrderWorkerController:根据业务员pin获取业务员订单列表失败",s);
            return result.fail(s.getCode(),s.getMessage());
        }catch (Exception e){
            logger.error("OrderWorkerController：根据业务员pin获取业务员订单列表失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }
}

