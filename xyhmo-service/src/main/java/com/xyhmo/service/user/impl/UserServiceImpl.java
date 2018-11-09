package com.xyhmo.service.user.impl;

import com.xyhmo.dao.XyhmoUserDao;
import com.xyhmo.domain.XyhmoUser;
import com.xyhmo.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private XyhmoUserDao xyhmoUserDao;
    @Override
    public XyhmoUser getUserByPin(String pin) {

        return xyhmoUserDao.selectUserByPin(pin);
    }
}
