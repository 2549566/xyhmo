package com.xyhmo.controller.address;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.domain.Address;
import com.xyhmo.service.address.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/address")
public class AddressController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService redisService;
    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/getAddress")
    @ResponseBody
    private Result getAddress(){
        Result result = new Result();
        try{
            List<Address> addressList=addressService.getAddressList();
            result.success(addressList, ReturnEnum.RETURN_SUCCESS.getDesc());
            return result;
        }catch (Exception e){
            logger.error("AddressController：获取地址列表失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }

    }


}
