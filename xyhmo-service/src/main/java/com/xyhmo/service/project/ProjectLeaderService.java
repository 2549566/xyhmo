package com.xyhmo.service.project;

import com.xyhmo.domain.ProjectOrderVo;
import com.xyhmo.query.project.ProjectCreateReq;
import com.xyhmo.vo.UserVo;

import java.util.List;

public interface ProjectLeaderService {
    /**
     * 业务员创建一个工程订单
     *
     * */
    Long createProjectOrder(ProjectCreateReq projectCreateReq)throws Exception;
    /**
     * 获取所有的工程订单
     *
     * */
    List<ProjectOrderVo> getProjectOrderListPage(UserVo userVo,Integer page,Integer pageSize);
}
