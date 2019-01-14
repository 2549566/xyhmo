package com.xyhmo.dao;


import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.Address;

import java.util.List;

@MyBatisRepository
public interface AddressDao {


    /**
     * 所有地址
     *
     * */
    List<Address> selectAddress();
}
