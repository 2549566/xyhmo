package com.xyhmo.service.relation.impl;

import com.xyhmo.service.TokenService;
import com.xyhmo.service.relation.RelationService;
import com.xyhmo.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public abstract class RelationServiceImpl implements RelationService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenService tokenService;

    @Override
    public List<UserVo> getMyRelationList(String token) {
        //根据token获取UserVo
        UserVo userVo=tokenService.checkTokenExist(token);
        if(null==userVo || StringUtils.isEmpty(userVo.getPin())){
            logger.error("RelationServiceImpl:根据token获取用户信息失败");
            return new ArrayList<>();
        }
        //获取用户pin
        List<UserVo> userVoList=getRelationList(userVo);
        return userVoList;
    }

    public abstract List<UserVo> getRelationList(UserVo userVo);
}
