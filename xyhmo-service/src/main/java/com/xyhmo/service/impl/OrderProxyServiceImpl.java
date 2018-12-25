package com.xyhmo.service.impl;

import com.xyhmo.commom.enums.*;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.HashCodeUtil;
import com.xyhmo.commom.utils.RedisUtil;
import com.xyhmo.dao.OrderDao;
import com.xyhmo.dao.OrderWareDao;
import com.xyhmo.domain.Order;
import com.xyhmo.domain.OrderWare;
import com.xyhmo.service.OrderProxyService;
import com.xyhmo.service.OrderWorkerService;
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
    private OrderWorkerService orderWorkerService;

    @Override
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
        String redisBefore = RedisUtil.getBeforeProxyRedisKey(orderStatus);
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
            order.setOrderStatus(orderStatus);
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
            orderWorkerService.translationToOrderVos(orderVoList,orderList,orderWareList);
        }
        redisService.set(redisBefore+userVo.getPin(),orderVoList);
        return orderVoList;
    }

    @Override
    @Transient
    public Boolean sureWareAlreadyToWorker(String token, String orderId, Integer isPay, Double warePrice,Double deliveryPrice)throws Exception {
        List<OrderVo> orderVoList= getOrderProxyList(token,OrderStatusEnum.ORDER_YWY_SUBMIT.getCode());
        if(CollectionUtils.isEmpty(orderVoList)){
            return false;
        }
        OrderVo orderVo=null;
        for(OrderVo vo:orderVoList){
            if(orderId.equals(vo.getOrderId())){
                orderVo=vo;
                orderVoList.remove(vo);
                break;
            }
        }
        if(orderVo==null){
            return false;
        }
        if(orderVo.getOrderStatus()!=OrderStatusEnum.ORDER_YWY_SUBMIT.getCode()){
            throw new Exception("订单状态不是1，代理商无法操作");
        }
        if(orderVo.getIsDelivery()== DeliveryEnum.IS_DELIVERY.getCode() && deliveryPrice==null){
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),"此订单需配送，需填写配送费用，如免费，配送费用可填0");
        }
        orderVo.setIsPay(isPay);
        orderVo.setOrderStatus(OrderStatusEnum.ORDER_DLS_SURE.getCode());
        if(isPay==1){
            orderVo.setRealIncomePrice(warePrice);
        }
        if(orderVo.getIsDelivery().equals(DeliveryEnum.IS_DELIVERY.getCode())){
            orderVo.setDeliveryPrice(deliveryPrice);
        }
        //修改代理商缓存列表中的状态，状态为2的情况下不删除
        orderVoList.add(orderVo);
        Order order = new Order();
        String tableName = genOrderTabeleName(orderVo.getPin());
        order.setId(orderVo.getId());
        order.setTableName(tableName);
        order.setOrderId("'"+orderVo.getOrderId()+"'");
        order.setIsPay(orderVo.getIsPay());
        order.setOrderStatus(orderVo.getOrderStatus());
        order.setRealIncomePrice(orderVo.getRealIncomePrice());
        order.setDeliveryPrice(orderVo.getDeliveryPrice());
        order.setModifier("'"+orderVo.getProxyPin()+"'");
        orderDao.updateOrderStatus(order);
        //修改代理商和业务员Redis中的数据
        updateRedisOrderInfo(orderVo,orderVoList);
        return true;
    }

    private void updateRedisOrderInfo(OrderVo orderVo,List<OrderVo> orderVoList){
        if(null==orderVo || CollectionUtils.isEmpty(orderVoList)){
            return;
        }
        //修改代理商的Redis数据
        String redisBefore = RedisUtil.getBeforeProxyRedisKey(orderVo.getOrderStatus());
        redisService.set(redisBefore+orderVo.getProxyPin(),orderVoList);
        //修改业务员的Redis数据
        String redisWorkerBefore =RedisUtil.getBeforeWorkerRedisKey(orderVo.getOrderStatus());
        List<OrderVo> workerOrderList = redisService.get(redisWorkerBefore+orderVo.getPin());
        if(CollectionUtils.isEmpty(workerOrderList)){
            return;
        }
        for(OrderVo vo:workerOrderList){
            if(vo.getOrderId().equals(orderVo.getOrderId())){
                workerOrderList.remove(vo);
                break;
            }
        }
        workerOrderList.add(orderVo);
        redisService.set(redisWorkerBefore+orderVo.getPin(),workerOrderList,Contants.ORDERWORKER_CACHE_OVER_TIME);
    }
    private String genOrderTabeleName(String pin){
        if(StringUtils.isBlank(pin)){
            return "";
        }
        return "order_bj_"+ HashCodeUtil.toHash(pin)%4;
    }
}
