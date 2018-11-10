package com.xyhmo.controller.user;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/getUser")
    @ResponseBody
    private Result getUser(String pin){
        System.out.println("1111111111111111111111");
//        redisService.set("quyang","wangmengjiao+19880124");
        System.out.println(redisService.get("quyang").toString());
        String sss =redisService.get("quyang").toString();
        System.out.println(redisService.get(pin).toString());
        Result result = new Result();
                result.setData(redisService.get(pin).toString());
        return result;
    }


}
