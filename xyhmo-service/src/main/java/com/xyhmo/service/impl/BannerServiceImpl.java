package com.xyhmo.service.impl;

import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.dao.BannerDao;
import com.xyhmo.domain.Banner;
import com.xyhmo.service.BannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private RedisService redisService;

    @Override
    public List<Banner> getBannerList(String token) {
        if(null!=redisService.get(Contants.REDIS_INDEX_BANNER)){
            return redisService.get(Contants.REDIS_INDEX_BANNER);
        }
        List<Banner> bannerList = bannerDao.selectBannerList();
        redisService.set(Contants.REDIS_INDEX_BANNER, bannerList);
        return bannerList;
    }
}
