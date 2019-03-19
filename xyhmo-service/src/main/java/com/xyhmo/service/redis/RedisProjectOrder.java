package com.xyhmo.service.redis;

import com.xyhmo.domain.ProjectLeader;
import com.xyhmo.domain.ProjectOrderVo;

import java.util.List;

/**
 * 缓存：业务员招工订单聚合层
 * author:quy
 * date:2019-03-91
 * */
public interface RedisProjectOrder {
    /**
     * 保存招工订单到Redis中
     *
     * */
    void saveProjectOrder2Reids(ProjectLeader projectLeader);
    /**
     * 分页获取的订单
     * 每页20个
     * */
    List<ProjectOrderVo> getProjectOrderListPage(Integer page,Integer pageSize);
}
