package com.xyhmo.service.relation.impl;

import com.xyhmo.commom.enums.UserTypeEnum;
import com.xyhmo.domain.UserAuthInfo;
import com.xyhmo.domain.UserInfo;
import com.xyhmo.service.UserAuthInfoService;
import com.xyhmo.service.UserInfoService;
import com.xyhmo.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProxyRelationServiceImpl extends RelationServiceImpl{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserAuthInfoService authInfoService;

    @Override
    public List<UserVo> getRelationList(UserVo userVo) {
        if(userVo==null){
            logger.error("ProxyRelationServiceImpl:userVo is null");
            return new ArrayList<>();
        }
        if(userVo.getUserType()!= UserTypeEnum.PROXY.getCode() && userVo.getUserType()!=UserTypeEnum.PROXY_STAFF.getCode()){
            logger.error("ProxyRelationServiceImpl:该角色不是代理商角色");
            return new ArrayList<>();
        }
        if(StringUtils.isEmpty(userVo.getPin())){
            logger.error("ProxyRelationServiceImpl:proxy pin is null");
            return new ArrayList<>();
        }
        List<UserInfo> userInfoList=userInfoService.getUserInfoByProxyPin(userVo.getPin());
        List<String> workerPinList=new ArrayList<>();
        for(UserInfo userInfo:userInfoList){
            if(StringUtils.isEmpty(userInfo.getPin())){
                logger.error("ProxyRelationServiceImpl:代理商的业务员的pin有空的"+userVo.getPin());
                continue;
            }
            workerPinList.add(userInfo.getPin());
        }
        List<UserAuthInfo> userAuthInfoList=authInfoService.getUserAuthInfoByPinList(workerPinList);
        return userInfoService.autowaredToVoList(userInfoList,userAuthInfoList);
    }
}
