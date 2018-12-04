package com.xyhmo.service.impl;

import com.xyhmo.commom.enums.*;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.utils.HashCodeUtil;
import com.xyhmo.dao.OrderDao;
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
import java.math.BigDecimal;
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
        Order order = translationToOrder(orderVo);
        Long id = orderDao.insert(order);

        System.out.println("id================="+id);
        return id;
    }
    private Order translationToOrder(OrderVo vo){
        Order order = new Order();
        order.setTableName(vo.getTableName());
        order.setOrderId(vo.getOrderId());
        order.setPin(vo.getPin());
        order.setProxyPin(vo.getProxyPin());
        order.setIsDelivery(vo.getIsDelivery());
        if(vo.getIsDelivery()== DeliveryEnum.IS_DELIVERY.getCode()){
            order.setAddress(vo.getAddress());
        }
        order.setPayablePrice(vo.getPayablePrice());
        order.setSaveMonyPrice(vo.getSaveMonyPrice());
        order.setStatus(1);
        return order;
    }
    private OrderVo translationToOrderVo(OrderParam orderParam, UserVo vo)throws Exception{
        String tableName = genTabeleName(vo.getPin());
        if(StringUtils.isBlank(tableName)){
            throw new Exception(SystemEnum.SYSTEM_ERROR.getDesc());
        }
        Map<Long,Integer> skuMap = orderParam.getSkuMap();
        String orderId="'"+genIdService.genOrderId(CityEnum.SKU_JUANCAI.getCode())+"'";
        String pin="'"+vo.getPin()+"'";
        String proxyPin="'"+vo.getBindVenderProxy()+"'";
        Set<Long> skuIds = skuMap.keySet();
        List<WareInfo> wareInfoList = wareInfoService.getWareListBySkuIds(skuIds);
        Double payablePriceDouble = 0.0;
        Double saveMonyPrice=0.0;
        List<OrderWare> orderWareList = new ArrayList<>();
        for(Long key:skuMap.keySet()){
            OrderWare orderWare = new OrderWare();
            orderWare.setOrderId(orderId);
            orderWare.setPin(pin);
            orderWare.setSkuId(key);
            orderWare.setStatus(1);
            for(WareInfo wareInfo:wareInfoList){
                if(key.equals(wareInfo.getSkuId())){
                    orderWare.setWareNum(skuMap.get(key));
                    orderWare.setWarePrice(wareInfo.getSkuPrice());
                    orderWare.setReduImgPath(wareInfo.getImgPath());
                    orderWare.setReduSkuBeforePrice(wareInfo.getSkuBeforePrice());
                    orderWare.setReduSkuName(wareInfo.getSkuName());
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
        if(orderParam.getIsDelivery()== DeliveryEnum.IS_DELIVERY.getCode()){
            String address="'"+orderParam.getAddress()+"'";
            order.setAddress(address);
        }
        order.setPayablePrice(new BigDecimal(payablePriceDouble));
        order.setSaveMonyPrice(new BigDecimal(saveMonyPrice));
        order.setOrderWareList(orderWareList);
        order.setStatus(1);
        return order;
    }

    private String genTabeleName(String pin){
        if(StringUtils.isBlank(pin)){
            return "";
        }
        return "order_bj_"+ HashCodeUtil.toHash(pin)%4;
    }
}
