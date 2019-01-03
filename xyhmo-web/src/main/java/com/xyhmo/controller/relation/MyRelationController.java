package com.xyhmo.controller.relation;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.enums.UserTypeEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.domain.Banner;
import com.xyhmo.service.BannerService;
import com.xyhmo.service.TokenService;
import com.xyhmo.service.relation.impl.ProxyRelationServiceImpl;
import com.xyhmo.service.relation.impl.VenderRelationServiceImpl;
import com.xyhmo.service.relation.impl.WorkerRelationServiceImpl;
import com.xyhmo.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/myRelation")
public class MyRelationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TokenService tokenService;
    @Autowired
    private WorkerRelationServiceImpl workerRelationService;
    @Autowired
    private ProxyRelationServiceImpl proxyRelationService;
    @Autowired
    private VenderRelationServiceImpl venderRelationService;

    /**
     * 我的模块-->我的关系（不同的角色点进去，显示不同的列表）
     *
     * */
    @RequestMapping(value = "/getMyRelation")
    @ResponseBody
    private Result getMyRelation(String token){
        Result result = new Result();
        try{
            UserVo userVo=tokenService.checkTokenExist(token);
            List<UserVo> userVoList=new ArrayList<>();
            if(userVo.getUserType().equals(UserTypeEnum.WORKER.getCode())){
                userVoList=workerRelationService.getRelationList(userVo);
            }else if(userVo.getUserType().equals(UserTypeEnum.PROXY.getCode()) || userVo.getUserType().equals(UserTypeEnum.PROXY_STAFF.getCode())) {
                userVoList = proxyRelationService.getRelationList(userVo);
            }else if(userVo.getUserType().equals(UserTypeEnum.VENDER.getCode()) || userVo.getUserType().equals(UserTypeEnum.VENDER_STAFF.getCode())){
                userVoList = venderRelationService.getRelationList(userVo);
            }
            result.success(userVoList, ReturnEnum.RETURN_SUCCESS.getDesc());
            return result;
        }catch (ParamException p){
            logger.error("MyRelationController:token 不存在",p);
            return result.fail(ParamEnum.PARAM_TOKEN_NOT_EXIST.getCode(),ParamEnum.PARAM_TOKEN_NOT_EXIST.getDesc());
        }catch (Exception e){
            logger.error("MyRelationController：获取我的关系失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }

    }


}
