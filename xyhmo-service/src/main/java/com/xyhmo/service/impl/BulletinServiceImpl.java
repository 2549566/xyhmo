package com.xyhmo.service.impl;

import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.dao.BulletinDao;
import com.xyhmo.domain.Bulletin;
import com.xyhmo.service.BulletinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BulletinServiceImpl implements BulletinService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BulletinDao bulletinDao;
    @Autowired
    private RedisService redisService;

    @Override
    public List<Bulletin> getBulletinList() {
        if(null!=redisService.get(Contants.REDIS_INDEX_BULLETIN)){
            return redisService.get(Contants.REDIS_INDEX_BULLETIN);
        }
        List<Bulletin> bulletinList = bulletinDao.selectBulletinList();
        redisService.set(Contants.REDIS_INDEX_BULLETIN,bulletinList);
        return bulletinList;
    }
}
