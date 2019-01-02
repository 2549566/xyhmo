package com.xyhmo.service.relation.impl;

import com.xyhmo.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenderRelationServiceImpl extends RelationServiceImpl{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<UserVo> getRelationList(UserVo userVo) {
        return null;
    }
}
