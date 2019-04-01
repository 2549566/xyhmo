package com.xyhmo.service.project;

import com.xyhmo.domain.ProjectLeader;
import com.xyhmo.domain.ProjectLeaderWith;
import com.xyhmo.domain.ProjectWorker;
import com.xyhmo.vo.UserVo;

import java.util.List;

public interface ProjectWorkerService {
    /**
     * 工人申报工单
     *
     * */
    Boolean applyProjectOrder(UserVo userVo,String leaderPin, String projectOrderId);
    /**
     * 事务处理
     *
     * */
    void saveWorkerApply(ProjectLeader projectLeader);
    /**
     * 获取干活工人在干的工程列表
     *
     * */
    List<ProjectWorker> getMyProjectWorkerListWroking(String pin);
}
