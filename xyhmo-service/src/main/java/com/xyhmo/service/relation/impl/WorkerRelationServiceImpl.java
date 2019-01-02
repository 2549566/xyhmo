package com.xyhmo.service.relation.impl;

import com.xyhmo.domain.UserAuthInfo;
import com.xyhmo.domain.UserInfo;
import com.xyhmo.service.UserAuthInfoService;
import com.xyhmo.service.UserInfoService;
import com.xyhmo.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkerRelationServiceImpl extends RelationServiceImpl{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserAuthInfoService userAuthInfoService;

    @Override
    public List<UserVo> getRelationList(UserVo userVo) {
        if(userVo==null){
            logger.error("WorkerRelationServiceImpl:userVo is null");
            return new ArrayList<>();
        }
        if(StringUtils.isEmpty(userVo.getBindVenderProxy())){
            logger.error("WorkerRelationServiceImpl：该工人无代理商");
            return new ArrayList<>();
        }
        UserInfo userInfo=userInfoService.getUserInfoByPin(userVo.getBindVenderProxy());
        UserAuthInfo userAuthInfo=userAuthInfoService.getUserAuthInfoByPin(userVo.getBindVenderProxy());
        UserVo vo=userInfoService.autowaredToVo(userInfo,userAuthInfo);
        List<UserVo> userVoList = new ArrayList<>();
        userVoList.add(vo);
        return userVoList;
    }
}
