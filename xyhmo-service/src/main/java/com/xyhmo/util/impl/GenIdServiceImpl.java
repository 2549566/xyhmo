package com.xyhmo.util.impl;

import com.xyhmo.dao.GenOrderDao;
import com.xyhmo.dao.GenProjectOrderDao;
import com.xyhmo.dao.GenSkuDao;
import com.xyhmo.domain.GenOrder;
import com.xyhmo.domain.GenProjectOrder;
import com.xyhmo.domain.GenSku;
import com.xyhmo.util.GenIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenIdServiceImpl implements GenIdService{

    @Autowired
    private GenSkuDao genSkuDao;
    @Autowired
    private GenOrderDao genOrderDao;
    @Autowired
    private GenProjectOrderDao genProjectOrderDao;

    @Override
    public Long genSkuId(Integer skuType) {
        GenSku genSku = new GenSku();
        genSku.setSkuName("1");
        Long id = genSkuDao.insert(genSku);
        String skuId = String.valueOf(skuType)+String.valueOf(id);
        return Long.parseLong(skuId);
    }

    @Override
    public String genOrderId(String city) {
        GenOrder genOrder = new GenOrder();
        genOrder.setOrderName("1");
        Long id = genOrderDao.insert(genOrder);
        String date = System.currentTimeMillis()+"";
        return city+date+String.valueOf(id);
    }

    @Override
    public String genProjectOrderId(String city) {
        GenProjectOrder genProjectOrder=new GenProjectOrder();
        genProjectOrder.setProjectName("1");
        Long id = genProjectOrderDao.insert(genProjectOrder);
        String date = System.currentTimeMillis()+"";
        return city+date+String.valueOf(id);
    }
}
