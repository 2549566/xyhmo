package com.xyhmo.service.orderWare.impl;

import com.xyhmo.commom.utils.TableNameUtil;
import com.xyhmo.dao.OrderDao;
import com.xyhmo.dao.OrderWareDao;
import com.xyhmo.domain.Order;
import com.xyhmo.domain.OrderWare;
import com.xyhmo.service.orderWare.OrderWareService;
import com.xyhmo.vo.order.OrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderWareServiceImpl implements OrderWareService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderWareDao orderWareDao;

    @Override
    public OrderVo getOrderWareDetail(String workerPin,String orderId) {
        if(StringUtils.isEmpty(workerPin)){
            logger.error("OrderWareServiceImpl:worderPin is empty");
            return null;
        }
        if(StringUtils.isEmpty(orderId)){
            logger.error("OrderWareServiceImpl:orderId is empty");
            return null;
        }
        String tableName = TableNameUtil.genOrderTabeleName(workerPin);
        String orderWareTableName=TableNameUtil.genOrderWareTabeleName(workerPin);
        Order paramOrder = new Order();
        paramOrder.setTableName(tableName);
        paramOrder.setOrderId("'"+orderId+"'");
        Order order=orderDao.selectOrderByOrderId(paramOrder);
        if(order==null){
            return null;
        }
        List<String> orderIdList = new ArrayList<>();
        orderIdList.add("'"+orderId+"'");
        List<OrderWare> orderWareList=orderWareDao.selectOrderWareListByOrderIdList(orderWareTableName,orderIdList);
        return translationToOrderVo(order,orderWareList);
    }

    private OrderVo translationToOrderVo(Order order, List<OrderWare> orderWareList) {
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
        vo.setRejectCase(order.getRejectCase());
        vo.setContext(order.getContext());
        if(CollectionUtils.isEmpty(orderWareList)){
            return vo;
        }
        vo.setOrderWareList(orderWareList);
        return vo;
    }
}
