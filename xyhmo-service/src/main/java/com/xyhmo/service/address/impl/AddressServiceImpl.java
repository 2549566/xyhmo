package com.xyhmo.service.address.impl;

import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.dao.AddressDao;
import com.xyhmo.domain.Address;
import com.xyhmo.service.address.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisService redisService;
    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Address> getAddressList() {
        if(null!=redisService.get(Contants.REDIS_ADDRESS)){
            return redisService.get(Contants.REDIS_ADDRESS);
        }
        List<Address> addressList=addressDao.selectAddress();
        redisService.set(Contants.REDIS_ADDRESS,addressList);
        return addressList;
    }
}
