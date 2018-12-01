package com.xyhmo.service;


import com.xyhmo.domain.Banner;
import java.util.List;

public interface BannerService {
    /**
     * 获取轮播图列表
     *
     * */
    List<Banner> getBannerList(String token);
}
