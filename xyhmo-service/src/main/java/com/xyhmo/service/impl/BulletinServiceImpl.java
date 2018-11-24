package com.xyhmo.service.impl;

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

    @Override
    public List<Bulletin> getBulletinList() {
        return bulletinDao.selectBulletinList();
    }
}
