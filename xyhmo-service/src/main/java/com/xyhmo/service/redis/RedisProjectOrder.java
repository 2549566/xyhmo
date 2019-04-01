package com.xyhmo.service.redis;

import com.xyhmo.domain.ProjectLeader;
import com.xyhmo.domain.ProjectLeaderWith;
import com.xyhmo.domain.ProjectOrderVo;
import com.xyhmo.domain.ProjectWorker;
import com.xyhmo.vo.UserVo;

import java.text.ParseException;
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
    /**
     * 业务员确认干活工人更新缓存
     *
     *
     * */
    void updateProjectOrderRedis(UserVo userVo, ProjectLeader projectLeader, List<ProjectLeaderWith> projectLeaderWithListRedis);
    /**
     * 删除缓存中的冲突的干活工人信息
     *
     * */
    void removeProjectOrder(String orderId,List<String> errorWorkerPinList);
}
