package com.xyhmo.service.impl;

import com.xyhmo.commom.enums.OrderStatusEnum;
import com.xyhmo.commom.enums.OrderTableEnum;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.dao.OrderDao;
import com.xyhmo.dao.OrderWareDao;
import com.xyhmo.domain.Order;
import com.xyhmo.domain.OrderWare;
import com.xyhmo.service.OrderProxyService;
import com.xyhmo.service.OrderService;
import com.xyhmo.service.TokenService;
import com.xyhmo.vo.UserVo;
import com.xyhmo.vo.order.OrderVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderProxyServiceImpl implements OrderProxyService{


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderWareDao orderWareDao;
    @Autowired
    private OrderService orderService;

    @Override
    @Transient
    public List<OrderVo> getOrderProxyList(String token,Integer orderStatus)throws ParamException,Exception {
        if(StringUtils.isEmpty(token) || (orderStatus!=null && orderStatus>20)){
            logger.error("OrderProxyServiceImpl:getOrderProxyList 入参出错");
            return new ArrayList<>();
        }
        //此处不做用户和绑定代理商的判断，因为有的用户脱离了代理商的话，还需要代理商能看见之前用户的订单数据
        UserVo userVo = tokenService.checkTokenExist(token);
        if(userVo==null){
            throw new Exception(SystemEnum.SYSTEM_ERROR.getDesc());
        }
        String redisBefore = getBeforeRedisKey(orderStatus);
        if(StringUtils.isEmpty(redisBefore)){
            throw new ParamException(ParamEnum.PARAM_ORDER_STATUS.getCode(),ParamEnum.PARAM_ORDER_STATUS.getDesc());
        }
        List<OrderVo> orderVoList = redisService.get(redisBefore+userVo.getPin());
        if(!CollectionUtils.isEmpty(orderVoList)){
            return orderVoList;
        }
        orderVoList=new ArrayList<>();
        if(OrderTableEnum.ORDER_TABLE_COUNT.getCode()!=OrderTableEnum.ORDER_WARE_TABLE_COUNT.getCode()){
            throw new Exception(SystemEnum.SYSTEM_ERROR.getDesc());
        }
        for(int i= 0;i<OrderTableEnum.ORDER_TABLE_COUNT.getCode();i++){
            OrderVo vo=new OrderVo();
            String orderTableName = "order_bj_"+i;
            String orderWareTableName = "order_ware_bj_"+i;
            Order order=new Order();
            order.setTableName(orderTableName);
            order.setStatus(orderStatus);
            String proxyPin="'"+userVo.getPin()+"'";
            order.setProxyPin(proxyPin);
            List<Order> orderList = orderDao.selectOrderProxyListByOrderStatus(order);
            if(CollectionUtils.isEmpty(orderList)){
                continue;
            }
            List<String> orderIdList = new ArrayList<>();
            for(Order ord:orderList){
                orderIdList.add("'"+ord.getOrderId()+"'");
            }
            List<OrderWare> orderWareList=orderWareDao.selectOrderWareListByOrderIdList(orderWareTableName,orderIdList);
            orderService.translationToOrderVos(orderVoList,orderList,orderWareList);
        }
        redisService.set(redisBefore+userVo.getPin(),orderVoList);
        return orderVoList;
    }

    private String getBeforeRedisKey(Integer orderStatus){
        if(OrderStatusEnum.ORDER_YWY_SUBMIT.getCode()==orderStatus || OrderStatusEnum.ORDER_DLS_SURE.getCode()==orderStatus){
            return Contants.REDIS_ORDERPROXY_WEIJIEDAN_PIN;
        }else if(OrderStatusEnum.ORDER_YWY_SURE_NOTPAY.getCode()==orderStatus){
            return Contants.REDIS_ORDERPROXY_WEIZHIFU_PIN;
        }else if(OrderStatusEnum.ORDER_YWY_SURE_PAY.getCode()==orderStatus){
            return Contants.REDIS_ORDERPROXY_YIZHIFU_PIN;
        }
        return "";
    }

}
