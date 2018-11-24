package com.xyhmo.service;


import com.xyhmo.domain.Bulletin;

import java.util.List;

public interface BulletinService {
    /**
     * 获取轮播图列表
     *
     * */
    List<Bulletin> getBulletinList();
}
