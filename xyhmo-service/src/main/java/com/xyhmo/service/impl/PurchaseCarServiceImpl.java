package com.xyhmo.service.impl;

import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.domain.WareInfo;
import com.xyhmo.service.PurchaseCarService;
import com.xyhmo.service.WareInfoService;
import com.xyhmo.vo.purchaseCar.PurchaseWareVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class PurchaseCarServiceImpl implements PurchaseCarService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService redisService;
    @Autowired
    private WareInfoService wareInfoService;

    @Override
    public Integer getTotalNum(String token) {
        Integer totalNum = 0;
        List<PurchaseWareVo> voList = getPurchaseWareList(token);
        if(CollectionUtils.isEmpty(voList)){
            return totalNum;
        }
        for(PurchaseWareVo vo:voList){
            if(vo==null){
                continue;
            }
            totalNum+=vo.getSelectNum();
        }
        return totalNum;
    }

    @Override
    public Integer getSelectNum(String token) {
        Integer selectNum = 0;
        List<PurchaseWareVo> voList = getPurchaseWareList(token);
        if(CollectionUtils.isEmpty(voList)){
            return selectNum;
        }
        for(PurchaseWareVo vo:voList){
            if(vo==null || !vo.getSelect()){
                continue;
            }
            selectNum+=vo.getSelectNum();
        }
        return selectNum;
    }

    @Override
    public List<PurchaseWareVo> getPurchaseWareList(String token) {
        if(redisService.get(Contants.REDIS_PURCHASE_CAR+token)==null){
            return new ArrayList<>();
        }
        return redisService.get(Contants.REDIS_PURCHASE_CAR+token);
    }

    @Override
    public Boolean addWareToPurchaseCar(String token, Long skuId) {
        if(null==skuId){
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(), ParamEnum.PARAM_ERROR.getDesc());
        }
        try{
            List<PurchaseWareVo> voList = getPurchaseWareList(token);
            if(CollectionUtils.isEmpty(voList)){
                WareInfo wareInfo = wareInfoService.getWareInfoBySkuId(skuId);
                PurchaseWareVo vo = autowaredToVo(wareInfo);
                if(vo==null){
                    return false;
                }
                voList.add(vo);
                redisService.set(Contants.REDIS_PURCHASE_CAR+token,voList,Contants.PURCHASE_CAR_OVER_TIME);
                return true;
            }
            Boolean isExist=false;
            for(PurchaseWareVo vo:voList){
                if(null==vo){
                    continue;
                }
                if(vo.getWareInfo().getSkuId().equals(skuId)){
                    isExist=true;
                    if(vo.getSelect()){
                        vo.setSelectNum(vo.getSelectNum()+1);
                    }else{
                        vo.setSelect(true);
                        vo.setSelectNum(1);
                    }
                }
            }
            if(!isExist){
                WareInfo wareInfo = wareInfoService.getWareInfoBySkuId(skuId);
                PurchaseWareVo purchaseWareVo = autowaredToVo(wareInfo);
                voList.add(purchaseWareVo);
            }
            redisService.set(Contants.REDIS_PURCHASE_CAR+token,voList,Contants.PURCHASE_CAR_OVER_TIME);
            return true;
        }catch (Exception e){
            logger.error("向购物车中添加商品失败",e);
            return false;
        }
    }

    @Override
    public Boolean updateWareNum(String token, Long skuId, Integer num) {
        if(null==skuId || num==null || num<1){
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(), ParamEnum.PARAM_ERROR.getDesc());
        }
        List<PurchaseWareVo> voList = getPurchaseWareList(token);
        if(CollectionUtils.isEmpty(voList)){
            return false;
        }
        for(PurchaseWareVo vo:voList){
            if(vo==null){
                continue;
            }
            if(vo.getWareInfo().getSkuId().equals(skuId)){
                vo.setSelectNum(num);
                redisService.set(Contants.REDIS_PURCHASE_CAR+token,voList,Contants.PURCHASE_CAR_OVER_TIME);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean updateWareSelectStatus(String token, Long skuId, Boolean selected) {
        if(null==skuId){
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(), ParamEnum.PARAM_ERROR.getDesc());
        }
        List<PurchaseWareVo> voList = getPurchaseWareList(token);
        if(CollectionUtils.isEmpty(voList)){
            return false;
        }
        for(PurchaseWareVo vo:voList){
            if(vo==null){
                continue;
            }
            if(vo.getWareInfo().getSkuId().equals(skuId)){
                vo.setSelect(selected);
                redisService.set(Contants.REDIS_PURCHASE_CAR+token,voList,Contants.PURCHASE_CAR_OVER_TIME);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<PurchaseWareVo> deletePurchaseCarWareList(String token, String skuIds) {
        List<PurchaseWareVo> voList = getPurchaseWareList(token);
        if(StringUtils.isEmpty(skuIds)){
            return voList;
        }
        String[] skuArray = skuIds.split(",");
        Set<Long> skuIdList =new HashSet<>();
        for(String skuIdStr:skuArray){
            skuIdList.add(Long.parseLong(skuIdStr));
        }
        if(CollectionUtils.isEmpty(skuIdList)){
            return voList;
        }
        List<PurchaseWareVo> removeList = new ArrayList<>();
        for(PurchaseWareVo vo:voList){
            if(vo==null){
                continue;
            }
            for(Long skuId:skuIdList){
                if(vo.getWareInfo().getSkuId().equals(skuId)){
                   removeList.add(vo);
                   break;
                }
            }
        }
        voList.removeAll(removeList);
        redisService.set(Contants.REDIS_PURCHASE_CAR+token,voList,Contants.PURCHASE_CAR_OVER_TIME);
        return voList;
    }

    private PurchaseWareVo autowaredToVo(WareInfo wareInfo){
        if(wareInfo==null){
            return null;
        }
        PurchaseWareVo vo = new PurchaseWareVo();
        vo.setSelect(true);
        vo.setSelectNum(1);
        vo.setWareInfo(wareInfo);
        return vo;
    }
}
