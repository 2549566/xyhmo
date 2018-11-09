package com.xyhmo.controller.user;

import com.xyhmo.commom.base.Result;
import com.xyhmo.domain.XyhmoUser;
import com.xyhmo.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger  logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUser", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    private Result getUser(String pin){
        Result result = new Result();
        XyhmoUser user = userService.getUserByPin(pin);
        result.setModelInfo(user);
        return result;
    }
}
