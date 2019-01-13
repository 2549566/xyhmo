package com.xyhmo.service.project;

import com.xyhmo.query.project.ProjectCreateReq;

public interface ProjectLeaderService {
    /**
     * 业务员创建一个工程订单
     *
     * */
    Long createProjectOrder(ProjectCreateReq projectCreateReq);
}
