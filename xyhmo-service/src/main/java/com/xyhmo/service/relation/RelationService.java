package com.xyhmo.service.relation;


import com.xyhmo.vo.UserVo;

import java.util.List;

public interface RelationService {
    /**
     * 获取我的关系
     *
     * */
    List<UserVo> getMyRelationList(String token);
}
