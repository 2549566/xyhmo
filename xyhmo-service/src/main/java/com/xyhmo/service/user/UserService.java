package com.xyhmo.service.user;

import com.xyhmo.domain.XyhmoUser;

import java.util.List;

public interface UserService {
    /**
     * 获取所有的牧场参数
     *
     * */
     XyhmoUser getUserByPin(String pin);
}
