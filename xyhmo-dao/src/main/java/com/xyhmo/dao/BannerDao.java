package com.xyhmo.dao;


import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.Banner;

import java.util.List;

@MyBatisRepository
public interface BannerDao {


    /**
     * 获取轮播图列表
     *
     * */
    List<Banner> selectBannerList();
}
