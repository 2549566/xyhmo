package com.xyhmo.service.impl;

import com.xyhmo.commom.enums.CityEnum;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.utils.HashCodeUtil;
import com.xyhmo.dao.OrderDao;
import com.xyhmo.domain.Order;
import com.xyhmo.service.OrderService;
import com.xyhmo.service.UserInfoService;
import com.xyhmo.util.GenIdService;
import com.xyhmo.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.beans.Transient;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private GenIdService genIdService;

    @Override
    @Transient
    public Long saveOrder(String token,Map<Long,Integer> orderMap) throws Exception {
        if(StringUtils.isEmpty(token) || CollectionUtils.isEmpty(orderMap)){
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
        }
        UserVo vo = userInfoService.getUserVoByToken(token);
        if(vo==null || StringUtils.isEmpty(vo.getPin())){
            return null;
        }
        String tableName = "order_bj_"+ HashCodeUtil.toHash(vo.getPin())%4;
        String orderId="'"+genIdService.genOrderId(CityEnum.SKU_JUANCAI.getCode())+"'";
        String pin="'"+vo.getPin()+"'";
        String proxyPin="'"+vo.getBindVenderProxy()+"'";
        Order order =  new Order();
        order.setTableName(tableName);
        order.setOrderId(orderId);
        order.setPin(pin);
        order.setProxyPin(proxyPin);

//        private String tableName;
//        private Long id;
//        private String orderId;
//        private String pin;
//        private String proxyPin;
//        private String coordinate;
//        private String address;
//        private Integer isDelivery;
//        private BigDecimal deliveryPrice;
//        private Integer orderType;
//        private BigDecimal payablePrice;
//        private BigDecimal realIncomePrice;
//        private BigDecimal saveMonyPrice;









//        String pin="'"+"ffffffffffffffffffffffffff"+"'";
//        order.setPin(pin);
//
//        String orderId="'"+"BJ10000001"+"'";
        order.setTableName(tableName);
        order.setOrderId(orderId);
        order.setOrderType(1);
        Long id = orderDao.insert(order);

        System.out.println("id================="+id);
        return id;
    }
}
