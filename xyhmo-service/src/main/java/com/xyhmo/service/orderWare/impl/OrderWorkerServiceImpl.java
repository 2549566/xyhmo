package com.xyhmo.service.orderWare.impl;

import com.xyhmo.commom.enums.*;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.exception.SystemException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.HashCodeUtil;
import com.xyhmo.commom.utils.RedisUtil;
import com.xyhmo.commom.utils.TableNameUtil;
import com.xyhmo.dao.OrderDao;
import com.xyhmo.dao.OrderWareDao;
import com.xyhmo.domain.Order;
import com.xyhmo.domain.OrderWare;
import com.xyhmo.domain.WareInfo;
import com.xyhmo.service.orderWare.OrderWorkerService;
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

import static com.xyhmo.commom.utils.TableNameUtil.genOrderWareTabeleName;

@Service
public class OrderWorkerServiceImpl implements OrderWorkerService {

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
        //清除购物车Redis
        redisService.remove(Contants.REDIS_PURCHASE_CAR+token);
        return id;
    }

    @Override
    public List<OrderVo> getWorkerOrderList(String token, Integer orderStatus) {
        UserVo vo = userInfoService.getUserVoByToken(token);
        if(null==vo || StringUtils.isEmpty(vo.getPin())){
            return new ArrayList<>();
        }
        String redisBefore = RedisUtil.getBeforeWorkerRedisKey(orderStatus);
        if(StringUtils.isEmpty(redisBefore)){
            throw new ParamException(ParamEnum.PARAM_ORDER_STATUS.getCode(),ParamEnum.PARAM_ORDER_STATUS.getDesc());
        }
        List<OrderVo> orderVoList = redisService.get(redisBefore+vo.getPin());
        if(!CollectionUtils.isEmpty(orderVoList)){
            return orderVoList;
        }
        logger.error("OrderServiceImpl：getWorkerOrderList 缓存中获取状态为"+orderStatus+"的订单列表为空");
        orderVoList=new ArrayList<>();
        String tableName = TableNameUtil.genOrderTabeleName(vo.getPin());
        String orderWareTableName=TableNameUtil.genOrderWareTabeleName(vo.getPin());
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
            vo.setRejectCase(order.getRejectCase());
            vo.setContext(order.getContext());
            orderVos.add(vo);
        }
    }

    @Override
    public void sureOrderJiedan(String token, String orderId) {
        UserVo userVo = userInfoService.getUserVoByToken(token);
        if(null==userVo || StringUtils.isEmpty(userVo.getPin())){
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
        }
        List<OrderVo> orderVoList=getWorkerOrderList(token,OrderStatusEnum.ORDER_DLS_SURE.getCode());
        if(CollectionUtils.isEmpty(orderVoList)){
            logger.error("OrderWorkerServiceImpl:业务员订单列表的缓存出现问题。请排查");
        }
        OrderVo orderVo=null;
        for(OrderVo order:orderVoList){
            if(order.getOrderId().equals(orderId) && order.getPin().equals(userVo.getPin())){
                orderVo=order;
                break;
            }
        }
        if(orderVo==null){
            throw new SystemException(SystemEnum.SYSTEM_ERROR.getCode(),SystemEnum.SYSTEM_ERROR.getDesc());
        }
        String tableName = TableNameUtil.genOrderTabeleName(userVo.getPin());
        Order order=new Order();
        order.setTableName(tableName);
        order.setOrderId("'"+orderId+"'");
        order.setOrderStatus(OrderStatusEnum.ORDER_YWY_SURE_NOTPAY.getCode());
        if(orderVo.getIsPay().equals(1)){
            order.setOrderStatus(OrderStatusEnum.ORDER_YWY_SURE_PAY.getCode());
        }
        order.setModifier("'"+userVo.getPin()+"'");
        orderDao.updateOrderStatus(order);
        orderVo.setOrderStatus(order.getOrderStatus());
        orderVo.setModifier(userVo.getPin());
        updateRedisOrderInfo(orderVo,orderVoList);
    }

    @Override
    public void rejectOrder(String token, String orderId, String rejectCase) {
        UserVo userVo = userInfoService.getUserVoByToken(token);
        if(null==userVo || StringUtils.isEmpty(userVo.getPin())){
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
        }
        List<OrderVo> orderVoList=getWorkerOrderList(token,OrderStatusEnum.ORDER_DLS_SURE.getCode());
        if(CollectionUtils.isEmpty(orderVoList)){
            logger.error("OrderWorkerServiceImpl:业务员订单列表的缓存出现问题。请排查");
        }
        OrderVo orderVo=null;
        for(OrderVo order:orderVoList){
            if(order.getOrderId().equals(orderId) && order.getPin().equals(userVo.getPin())){
                orderVo=order;
                break;
            }
        }
        if(orderVo==null){
            throw new SystemException(SystemEnum.SYSTEM_ERROR.getCode(),SystemEnum.SYSTEM_ERROR.getDesc());
        }
        String tableName = TableNameUtil.genOrderTabeleName(userVo.getPin());
        Order order=new Order();
        order.setTableName(tableName);
        order.setModifier("'"+userVo.getPin()+"'");
        order.setOrderId("'"+orderId+"'");
        order.setOrderStatus(OrderStatusEnum.ORDER_YWY_SUBMIT.getCode());
        order.setRejectCase("'"+rejectCase+"'");
        orderDao.updateOrderStatus(order);
        orderVo.setOrderStatus(order.getOrderStatus());
        orderVo.setModifier(userVo.getPin());
        orderVo.setRejectCase(rejectCase);
        updateRedisOrderInfo(orderVo,orderVoList);
    }

    @Override
    public List<OrderVo> getWorkerOrderListByWorkerPin(String workerPin) {
        List<OrderVo> orderVoList=new ArrayList<>();
        if(StringUtils.isBlank(workerPin)){
            logger.error("OrderWorkerServiceImpl:workerPin is empty");
            return orderVoList;
        }
        String tableName = TableNameUtil.genOrderTabeleName(workerPin);
        String orderWareTableName=TableNameUtil.genOrderWareTabeleName(workerPin);
        Order order=new Order();
        order.setTableName(tableName);
        order.setPin("'"+workerPin+"'");
        List<Order> orderList=orderDao.selectOrderWorkerListByPin(order);
        List<String> orderIdList = new ArrayList<>();
        for(Order ord:orderList){
            orderIdList.add("'"+ord.getOrderId()+"'");
        }
        List<OrderWare> orderWareList=orderWareDao.selectOrderWareListByOrderIdList(orderWareTableName,orderIdList);
        translationToOrderVos(orderVoList,orderList,orderWareList);
        return orderVoList;
    }

    /**
     * 业务员的缓存只保留3天
     * 代理商的缓存一直存在
     *
     * */
    private void updateRedisOrderInfo(OrderVo orderVo,List<OrderVo> orderVoList){
        if(orderVo==null || CollectionUtils.isEmpty(orderVoList) || null==orderVo.getOrderStatus()){
            return;
        }
        //修改业务员的redis数据
        String redisWorkerBefore = RedisUtil.getBeforeWorkerRedisKey(orderVo.getOrderStatus());
        String redisProxyBefore = RedisUtil.getBeforeProxyRedisKey(orderVo.getOrderStatus());
        List<OrderVo> weijiedanProxyVoList = redisService.get(Contants.REDIS_ORDERPROXY_WEIJIEDAN_PIN+orderVo.getProxyPin());
        if(orderVo.getOrderStatus().equals(OrderStatusEnum.ORDER_YWY_SURE_NOTPAY.getCode()) || orderVo.getOrderStatus().equals(OrderStatusEnum.ORDER_YWY_SURE_PAY.getCode())){
            //删除未结单的
            for(OrderVo order:orderVoList){
                if(order.getOrderId().equals(orderVo.getOrderId())){
                    orderVoList.remove(order);
                    break;
                }
            }
            redisService.set(Contants.REDIS_ORDERWORKER_WEIJIEDAN_PIN+orderVo.getPin(),orderVoList,Contants.ORDERWORKER_CACHE_OVER_TIME);
            //获取已结单的订单列表
            List<OrderVo> yijiedanVoList = redisService.get(redisWorkerBefore+orderVo.getPin());
            if(CollectionUtils.isEmpty(yijiedanVoList)){
                yijiedanVoList=new ArrayList<>();
            }
            yijiedanVoList.add(orderVo);
            //设置业务员的已结单列表
            redisService.set(redisWorkerBefore+orderVo.getPin(),yijiedanVoList,Contants.ORDERWORKER_CACHE_OVER_TIME);
            //设置代理商的未结单列表
            for(OrderVo vo:weijiedanProxyVoList){
                if(vo.getOrderId().equals(orderVo.getOrderId())){
                    weijiedanProxyVoList.remove(vo);
                    break;
                }
            }
            redisService.set(Contants.REDIS_ORDERPROXY_WEIJIEDAN_PIN+orderVo.getProxyPin(),weijiedanProxyVoList);
            //设置代理商的已结单列表
            List<OrderVo> yijiedanProxyVoList=redisService.get(redisProxyBefore+orderVo.getProxyPin());
            if(CollectionUtils.isEmpty(yijiedanProxyVoList)){
                yijiedanProxyVoList=new ArrayList<>();
            }
            yijiedanProxyVoList.add(orderVo);
            redisService.set(redisProxyBefore+orderVo.getProxyPin(),yijiedanProxyVoList);
        }else if(orderVo.getOrderStatus().equals(OrderStatusEnum.ORDER_YWY_SUBMIT.getCode())){
            //业务员驳回，重新设置未结单列表
            for(OrderVo order:orderVoList){
                if(order.getOrderId().equals(orderVo.getOrderId())){
                    orderVoList.remove(order);
                    break;
                }
            }
            orderVoList.add(orderVo);
            redisService.set(Contants.REDIS_ORDERWORKER_WEIJIEDAN_PIN+orderVo.getPin(),orderVoList,Contants.ORDERWORKER_CACHE_OVER_TIME);
            //业务员驳回，重新设置代理商未结单列表
            for(OrderVo order:weijiedanProxyVoList){
                if(order.getOrderId().equals(orderVo.getOrderId())){
                    weijiedanProxyVoList.remove(order);
                    break;
                }
            }
            weijiedanProxyVoList.add(orderVo);
            redisService.set(Contants.REDIS_ORDERPROXY_WEIJIEDAN_PIN+orderVo.getProxyPin(),weijiedanProxyVoList);
        }
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
        order.setRejectCase(vo.getRejectCase());
        order.setContext(vo.getContext());
        return order;
    }
    private OrderVo translationToOrderVo(OrderParam orderParam, UserVo vo)throws Exception{
        String tableName = TableNameUtil.genOrderTabeleName(vo.getPin());
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
            String orderWareTableName = TableNameUtil.genOrderWareTabeleName(vo.getPin());
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
        String rejectCase="''";
        order.setRejectCase(rejectCase);
        String context="''";
        order.setContext(context);
        return order;
    }

}
