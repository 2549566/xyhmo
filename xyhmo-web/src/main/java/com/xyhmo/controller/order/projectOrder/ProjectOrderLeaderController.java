package com.xyhmo.controller.order.projectOrder;

import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.DeliveryEnum;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.query.project.ProjectCreateReq;
import com.xyhmo.service.TokenService;
import com.xyhmo.service.project.ProjectLeaderService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/projectOrderLeader")
public class ProjectOrderLeaderController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private TokenService tokenService;
    @Autowired
    private ProjectLeaderService projectLeaderService;

    @RequestMapping(value = "/createProjectOrder", method = RequestMethod.POST)
    @ResponseBody
    public Result createProjectOrder(@RequestBody ProjectCreateReq projectCreateReq){
        Result result = new Result();
        try{
            if(projectCreateReq==null){
                throw new ParamException(ParamEnum.PARAM_DELIVERY_ERROR.getCode(),ParamEnum.PARAM_DELIVERY_ERROR.getDesc());
            }
            String token = projectCreateReq.getToken();
            tokenService.checkTokenExist(token);
            Long projectOrderId= projectLeaderService.createProjectOrder(projectCreateReq);
            if(projectOrderId==null || projectOrderId<1){
                return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
            }
            return result.success(projectOrderId, ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("projectOrderLeader:订单入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("projectOrderLeader：保存工程订单失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }
}
