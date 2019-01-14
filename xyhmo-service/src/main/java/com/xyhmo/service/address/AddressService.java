package com.xyhmo.service.address;


import com.xyhmo.domain.Address;

import java.util.List;

public interface AddressService {
    /**
     * 获取地址列表
     *
     * */
    List<Address> getAddressList();
}
