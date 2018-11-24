package com.xyhmo.dao;


import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.Bulletin;

import java.util.List;

@MyBatisRepository
public interface BulletinDao {


    /**
     * 获取快报列表
     *
     * */
    List<Bulletin> selectBulletinList();
}
