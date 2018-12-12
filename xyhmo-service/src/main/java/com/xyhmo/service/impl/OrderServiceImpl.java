package com.xyhmo.service.impl;

import com.xyhmo.commom.enums.*;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.HashCodeUtil;
import com.xyhmo.dao.OrderDao;
import com.xyhmo.dao.OrderWareDao;
import com.xyhmo.domain.Order;
import com.xyhmo.domain.OrderWare;
import com.xyhmo.domain.WareInfo;
import com.xyhmo.service.OrderService;
import com.xyhmo.service.UserInfoService;
import com.xyhmo.service.WareInfoService;
import com.xyhmo.util.GenIdService;
import com.xyhmo.vo.UserVo;
import com.xyhmo.vo.order.OrderVo;
import com.xyhmo.vo.param.OrderParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private GenIdService genIdService;
    @Autowired
    private WareInfoService wareInfoService;
    @Autowired
    private OrderWareDao orderWareDao;
    @Autowired
    private RedisService redisService;

    @Override
    @Transient
    public Long saveOrder(OrderParam orderParam) throws Exception {
        if(null==orderParam || StringUtils.isEmpty(orderParam.getToken()) || CollectionUtils.isEmpty(orderParam.getSkuMap())){
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
        }
        String token = orderParam.getToken();
        UserVo vo = userInfoService.getUserVoByToken(token);
        if(vo==null || StringUtils.isEmpty(vo.getPin())){
            return null;
        }
        OrderVo orderVo = translationToOrderVo(orderParam,vo);
        if(null==orderVo){
            throw new Exception(SystemEnum.SYSTEM_ERROR.getDesc());
        }
        saveOrderToRedis(orderVo,vo);
        Order order = translationToOrder(orderVo);
        Long id = orderDao.insert(order);
        List<OrderWare> orderWareList = orderVo.getOrderWareList();
        for(OrderWare orderWare:orderWareList){
            orderWareDao.insert(orderWare);
        }
        return id;
    }

    @Override
    public List<OrderVo> getWorkerOrderList(String token, Integer orderStatus) {
        UserVo vo = userInfoService.getUserVoByToken(token);
        if(null==vo || StringUtils.isEmpty(vo.getPin())){
            return new ArrayList<>();
        }
        String redisBefore = getBeforeRedisKey(orderStatus);
        if(StringUtils.isEmpty(redisBefore)){
            throw new ParamException(ParamEnum.PARAM_ORDER_STATUS.getCode(),ParamEnum.PARAM_ORDER_STATUS.getDesc());
        }
        List<OrderVo> orderVoList = redisService.get(redisBefore+vo.getPin());
        if(!CollectionUtils.isEmpty(orderVoList)){
            return orderVoList;
        }
        logger.error("OrderServiceImpl：getWorkerOrderList 缓存中获取状态为"+orderStatus+"的订单列表为空");
        orderVoList=new ArrayList<>();
        String tableName = genOrderTabeleName(vo.getPin());
        String orderWareTableName=genOrderWareTabeleName(vo.getPin());
        Order order = new Order();
        order.setTableName(tableName);
        order.setOrderStatus(orderStatus);
        order.setPin("'"+vo.getPin()+"'");
        List<Order> orderList = orderDao.selectOrderWorkerListByOrderStatus(order);
        if(CollectionUtils.isEmpty(orderList)){
            return new ArrayList<>();
        }
        List<String> orderIdList = new ArrayList<>();
        for(Order ord:orderList){
            orderIdList.add("'"+ord.getOrderId()+"'");
        }
        List<OrderWare> orderWareList=orderWareDao.selectOrderWareListByOrderIdList(orderWareTableName,orderIdList);
        translationToOrderVos(orderVoList,orderList,orderWareList);
        redisService.set(redisBefore+vo.getPin(),orderVoList,Contants.ORDERWORKER_CACHE_OVER_TIME);
        return orderVoList;
    }

    public void translationToOrderVos(List<OrderVo> orderVos, List<Order> orderList, List<OrderWare> orderWareList) {
        if(CollectionUtils.isEmpty(orderList)){
            return;
        }
        for(Order order:orderList) {
            OrderVo vo = new OrderVo();
            vo.setId(order.getId());
            vo.setOrderId(order.getOrderId());
            vo.setPin(order.getPin());
            vo.setProxyPin(order.getProxyPin());
            vo.setCoordinate(order.getCoordinate());
            vo.setAddress(order.getAddress());
            vo.setIsDelivery(order.getIsDelivery());
            vo.setDeliveryPrice(order.getDeliveryPrice());
            vo.setOrderStatus(order.getOrderStatus());
            vo.setPayablePrice(order.getPayablePrice());
            vo.setRealIncomePrice(order.getRealIncomePrice());
            vo.setSaveMonyPrice(order.getSaveMonyPrice());
            vo.setIsTotalPay(order.getIsTotalPay());
            vo.setTotalPayPrice(order.getTotalPayPrice());
            vo.setTotalPayOrderId(order.getTotalPayOrderId());
            List<OrderWare> orderWares = new ArrayList<>();
            for (OrderWare orderWare : orderWareList) {
                if (order.getOrderId().equals(orderWare.getOrderId())) {
                    orderWares.add(orderWare);
                }
            }
            vo.setOrderWareList(orderWares);
            orderVos.add(vo);
        }
    }

    private String getBeforeRedisKey(Integer orderStatus){
        if(OrderStatusEnum.ORDER_YWY_SUBMIT.getCode()==orderStatus || OrderStatusEnum.ORDER_DLS_SURE.getCode()==orderStatus){
            return Contants.REDIS_ORDERWORKER_WEIJIEDAN_PIN;
        }else if(OrderStatusEnum.ORDER_YWY_SURE_NOTPAY.getCode()==orderStatus){
            return Contants.REDIS_ORDERWORKER_WEIZHIFU_PIN;
        }else if(OrderStatusEnum.ORDER_YWY_SURE_PAY.getCode()==orderStatus){
            return Contants.REDIS_ORDERWORKER_YIZHIFU_PIN;
        }
        return "";
    }

    private void saveOrderToRedis(OrderVo orderVo,UserVo vo){
        //添加进业务员的订单列表缓存中
        List<OrderVo> orderWorkerList = redisService.get(Contants.REDIS_ORDERWORKER_WEIJIEDAN_PIN+vo.getPin());
        if(CollectionUtils.isEmpty(orderWorkerList)){
            orderWorkerList=new ArrayList<>();
        }
        orderWorkerList.add(orderVo);
        //添加进业务员的订单列表缓存中，时间只有3天。
        redisService.set(Contants.REDIS_ORDERWORKER_WEIJIEDAN_PIN+vo.getPin(),orderWorkerList,Contants.ORDERWORKER_CACHE_OVER_TIME);
        //添加进代理商的订单列表缓存中,因为是下单，所以只添加进未结单的代理商订单列表中
        List<OrderVo> orderVoList = redisService.get(Contants.REDIS_ORDERPROXY_WEIJIEDAN_PIN+vo.getBindVenderProxy());
        if(CollectionUtils.isEmpty(orderVoList)){
            orderVoList = new ArrayList<>();
        }
        orderVoList.add(orderVo);
        redisService.set(Contants.REDIS_ORDERPROXY_WEIJIEDAN_PIN+vo.getBindVenderProxy(),orderVoList);
    }

    private Order translationToOrder(OrderVo vo){
        Order order = new Order();
        order.setTableName(vo.getTableName());
        order.setOrderId(vo.getOrderId());
        order.setPin(vo.getPin());
        order.setProxyPin(vo.getProxyPin());
        order.setIsDelivery(vo.getIsDelivery());
        order.setAddress(vo.getAddress());
        order.setDeliveryPrice(vo.getDeliveryPrice());
        order.setCoordinate(vo.getCoordinate());
        order.setPayablePrice(vo.getPayablePrice());
        order.setSaveMonyPrice(vo.getSaveMonyPrice());
        order.setTotalPayOrderId(vo.getTotalPayOrderId());
        order.setStatus(1);
        order.setTotalPayOrderId(vo.getTotalPayOrderId());
        order.setOrderStatus(vo.getOrderStatus());
        order.setRealIncomePrice(vo.getRealIncomePrice());
        order.setCreator(vo.getCreator());
        order.setModifier(vo.getModifier());
        order.setIsTotalPay(vo.getIsTotalPay());
        order.setTotalPayPrice(vo.getTotalPayPrice());
        return order;
    }
    private OrderVo translationToOrderVo(OrderParam orderParam, UserVo vo)throws Exception{
        String tableName = genOrderTabeleName(vo.getPin());
        if(StringUtils.isBlank(tableName)){
            throw new Exception(SystemEnum.SYSTEM_ERROR.getDesc());
        }
        Map<Long,Integer> skuMap = orderParam.getSkuMap();
        String orderId="'"+genIdService.genOrderId(CityEnum.SKU_JUANCAI.getCode())+"'";
        String pin="'"+vo.getPin()+"'";
        String proxyPin="'"+vo.getBindVenderProxy()+"'";
        String modifier="''";
        Set<Long> skuIds = skuMap.keySet();
        List<WareInfo> wareInfoList = wareInfoService.getWareListBySkuIds(skuIds);
        Double payablePriceDouble = 0.0;
        Double saveMonyPrice=0.0;
        List<OrderWare> orderWareList = new ArrayList<>();
        for(Long key:skuMap.keySet()){
            String orderWareTableName = genOrderWareTabeleName(vo.getPin());
            OrderWare orderWare = new OrderWare();
            orderWare.setOrderId(orderId);
            orderWare.setPin(pin);
            orderWare.setSkuId(key);
            orderWare.setStatus(1);
            orderWare.setTableName(orderWareTableName);
            for(WareInfo wareInfo:wareInfoList){
                if(key.equals(wareInfo.getSkuId())){
                    String reduSkuName=StringUtils.isBlank(wareInfo.getSkuName())?"''":"'"+wareInfo.getSkuName()+"'";
                    String reduImgPath=StringUtils.isBlank(wareInfo.getImgPath())?"''":"'"+wareInfo.getImgPath()+"'";
                    orderWare.setWareNum(skuMap.get(key));
                    orderWare.setWarePrice(wareInfo.getSkuPrice());
                    orderWare.setReduImgPath(reduImgPath);
                    orderWare.setReduSkuBeforePrice(wareInfo.getSkuBeforePrice());
                    orderWare.setReduSkuName(reduSkuName);
                    orderWare.setCreator(pin);
                    orderWare.setModifier("''");
                    payablePriceDouble+=wareInfo.getSkuPrice().doubleValue()*(skuMap.get(key));
                    saveMonyPrice+=(wareInfo.getSkuBeforePrice().doubleValue()-wareInfo.getSkuPrice().doubleValue())*(skuMap.get(key));
                    break;
                }
            }
            orderWareList.add(orderWare);
        }
        OrderVo order =  new OrderVo();
        order.setTableName(tableName);
        order.setOrderId(orderId);
        order.setPin(pin);
        order.setProxyPin(proxyPin);
        order.setIsDelivery(orderParam.getIsDelivery());
        String coordinate="''";
        order.setCoordinate(coordinate);
        String address="''";
        if(orderParam.getIsDelivery()== DeliveryEnum.IS_DELIVERY.getCode()){
            address="'"+orderParam.getAddress()+"'";
        }
        order.setAddress(address);
        order.setPayablePrice(payablePriceDouble);
        order.setSaveMonyPrice(saveMonyPrice);
        order.setOrderWareList(orderWareList);
        order.setStatus(1);
        String totalPayOrderId="''";
        order.setTotalPayOrderId(totalPayOrderId);
        order.setOrderStatus(OrderStatusEnum.ORDER_YWY_SUBMIT.getCode());
        order.setCreator(pin);
        order.setModifier(modifier);
        order.setIsTotalPay(0);
        return order;
    }

    private String genOrderTabeleName(String pin){
        if(StringUtils.isBlank(pin)){
            return "";
        }
        return "order_bj_"+ HashCodeUtil.toHash(pin)%4;
    }

    private String genOrderWareTabeleName(String pin){
        if(StringUtils.isBlank(pin)){
            return "";
        }
        return "order_ware_bj_"+ HashCodeUtil.toHash(pin)%4;
    }

}
