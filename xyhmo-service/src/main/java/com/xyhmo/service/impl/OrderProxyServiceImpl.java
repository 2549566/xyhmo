package com.xyhmo.service.impl;

import com.xyhmo.commom.enums.OrderTableEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.dao.OrderDao;
import com.xyhmo.dao.OrderWareDao;
import com.xyhmo.domain.Order;
import com.xyhmo.domain.OrderWare;
import com.xyhmo.service.OrderProxyService;
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

    @Override
    @Transient
    public List<OrderVo> getOrderProxyList(String token,Integer orderStatus)throws Exception {
        if(StringUtils.isEmpty(token) || (orderStatus!=null && orderStatus>20)){
            logger.error("OrderProxyServiceImpl:getOrderProxyList 入参出错");
            return new ArrayList<>();
        }
        //此处不做用户和绑定代理商的判断，因为有的用户脱离了代理商的话，还需要代理商能看见之前用户的订单数据
        UserVo userVo = tokenService.checkTokenExist(token);
        if(userVo==null){
            throw new Exception(SystemEnum.SYSTEM_ERROR.getDesc());
        }
        List<OrderVo> orderVoList = redisService.get(Contants.REDIS_ORDER_PROXY_PIN+userVo.getPin());
        if(!CollectionUtils.isEmpty(orderVoList)){
            return getOrderProxyListForStatus(orderVoList,orderStatus);
        }
        if(OrderTableEnum.ORDER_TABLE_COUNT.getCode()!=OrderTableEnum.ORDER_WARE_TABLE_COUNT.getCode()){
            throw new Exception(SystemEnum.SYSTEM_ERROR.getDesc());
        }
        List<OrderVo> orderVos = new ArrayList<>();
        for(int i= 0;i<OrderTableEnum.ORDER_TABLE_COUNT.getCode();i++){
            OrderVo vo=new OrderVo();
            String orderTableName = "order_bj_"+i;
            String orderWareTableName = "order_ware_bj_"+i;
            Order order=new Order();
            order.setTableName(orderTableName);
            order.setStatus(orderStatus);
            String proxyPin="'"+userVo.getPin()+"'";
            order.setProxyPin(proxyPin);
            List<Order> orderList = orderDao.selectOrderListByOrderStatus(order);
            if(CollectionUtils.isEmpty(orderList)){
                continue;
            }
            List<String> orderIdList = new ArrayList<>();
            for(Order ord:orderList){
                orderIdList.add("'"+ord.getOrderId()+"'");
            }
            List<OrderWare> orderWareList=orderWareDao.selectOrderWareListByOrderIdList(orderWareTableName,orderIdList);
            translationToOrderVos(orderVos,orderList,orderWareList);
        }
        return orderVos;
    }

    private List<OrderVo> getOrderProxyListForStatus(List<OrderVo> orderVoList, Integer orderStatus) {
        if(null==orderStatus){
            return orderVoList;
        }
        List<OrderVo> voList = new ArrayList<>();
        for(OrderVo orderVo:orderVoList){
            if(orderStatus==orderVo.getStatus()){
                voList.add(orderVo);
            }
        }
        return voList;
    }

    private void translationToOrderVos(List<OrderVo> orderVos, List<Order> orderList, List<OrderWare> orderWareList) {
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

}
