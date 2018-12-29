package com.xyhmo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xyhmo.commom.enums.BusiessEnum;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.ParamCheckUtil;
import com.xyhmo.dao.WareInfoDao;
import com.xyhmo.domain.WareInfo;
import com.xyhmo.service.TokenService;
import com.xyhmo.service.WareInfoService;
import com.xyhmo.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class WareInfoServiceImpl implements WareInfoService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenService tokenService;
    @Autowired
    private WareInfoDao wareInfoDao;
    @Autowired
    private RedisService redisService;

    @Override
    public Map<Integer, List<WareInfo>> getWareInfoList(String token) {
        UserVo vo = tokenService.checkTokenExist(token);
        String proxyPin = vo.getBindVenderProxy();
        if(StringUtils.isEmpty(proxyPin)){
            return getAllWareInfoList();
        }
        return getWareInfoListByPin(proxyPin);
    }

    @Override
    public Map<Integer,List<WareInfo>> getAllWareInfoList() {
        if(redisService.get(Contants.REDIS_ALL_WARE)!=null){
            return redisService.get(Contants.REDIS_ALL_WARE);
        }
        List<WareInfo> list = wareInfoDao.selectAllWareInfoList();
        if(CollectionUtils.isEmpty(list)){
            return new HashMap<>();
        }
        Map<Integer,List<WareInfo>> map = autowaredToMap(list);
        redisService.set(Contants.REDIS_ALL_WARE,map);
        return map;
    }


    @Override
    public Map<Integer,List<WareInfo>> getWareInfoListByPin(String pin) {
        if(redisService.get(Contants.REDIS_WARE_PIN+pin)!=null){
            return redisService.get(Contants.REDIS_WARE_PIN+pin);
        }
        List<WareInfo> list = wareInfoDao.selectWareInfoList(pin);
        if(CollectionUtils.isEmpty(list)){
            return new HashMap<>();
        }
        Map<Integer,List<WareInfo>> map = autowaredToMap(list);
        //TODO 修改业务员绑定代理商，需要刷新缓存
        redisService.set(Contants.REDIS_WARE_PIN+pin,map);
        return map;
    }

    @Override
    public PageInfo getWareInfoListByUserType(String token, Integer skuType, Integer pageNum) {
        UserVo vo = tokenService.checkTokenExist(token);
        ParamCheckUtil.checkPageNum(pageNum);
        PageHelper.startPage(pageNum,ParamEnum.PARAM_DEFAULT_PAGESIZE.getCode());
        String pin = vo.getBindVenderProxy();
        WareInfo wareInfo = new WareInfo();
        if(!StringUtils.isEmpty(pin)){
            wareInfo.setPin(pin);
        }
        wareInfo.setSkuType(skuType);
        wareInfo.setSkuStatus(1);
        wareInfo.setStatus(1);
        List<WareInfo> wareInfoList = wareInfoDao.selectWareInfoListByUserType(wareInfo);
        PageInfo<WareInfo> pageInfo = new PageInfo<>(wareInfoList);
        return pageInfo;
    }

    @Override
    public WareInfo getWareInfoBySkuId(Long skuId) {
        if(null==skuId){
            return null;
        }
        WareInfo wareInfo = null;
        try{
            wareInfo=redisService.get(Contants.REDIS_WARE_SKUID+skuId);
        }catch (Exception e){
            logger.error("redis 中 skuId="+skuId+"不存在");
        }
        if(wareInfo!=null){
            return wareInfo;
        }
        wareInfo = wareInfoDao.selectWareInfoBySkuId(skuId);
        if(wareInfo!=null){
            redisService.set(Contants.REDIS_WARE_SKUID+skuId,wareInfo);
        }
        return wareInfo;
    }

    @Override
    public List<WareInfo> getWareListBySkuIds(Set<Long> skuIds) {
        if(CollectionUtils.isEmpty(skuIds)){
            logger.error("skuIds is empty");
            return new ArrayList<>();
        }
        return wareInfoDao.selectWareListBySkuIds(new ArrayList<>(skuIds));
    }


    private Map<Integer,List<WareInfo>> autowaredToMap(List<WareInfo> list) {
        if(CollectionUtils.isEmpty(list)){
            logger.info("WareInfoServiceImpl:从MySQL中查询到的商品列表为空");
            return new HashMap<>();
        }
        Map<Integer,List<WareInfo>> map = new HashMap<>();
        List<WareInfo> juancai = new ArrayList<>();
        List<WareInfo> meiqi = new ArrayList<>();
        List<WareInfo> tuliao = new ArrayList<>();
        List<WareInfo> other = new ArrayList<>();
        for(WareInfo wareInfo:list){
            if(BusiessEnum.WARE_TYPE_JUANCAI.getCode()==wareInfo.getSkuType()){
               juancai.add(wareInfo);
            }
            if(BusiessEnum.WARE_TYPE_MEIQI.getCode()==wareInfo.getSkuType()){
                meiqi.add(wareInfo);
            }
            if(BusiessEnum.WARE_TYPE_TULIAO.getCode()==wareInfo.getSkuType()){
                tuliao.add(wareInfo);
            }
            if(BusiessEnum.WARE_TYPE_OTHER.getCode()==wareInfo.getSkuType()){
                other.add(wareInfo);
            }
        }
        map.put(BusiessEnum.WARE_TYPE_JUANCAI.getCode(),juancai);
        map.put(BusiessEnum.WARE_TYPE_MEIQI.getCode(),meiqi);
        map.put(BusiessEnum.WARE_TYPE_TULIAO.getCode(),tuliao);
        map.put(BusiessEnum.WARE_TYPE_OTHER.getCode(),other);
        return map;
    }
}
