package com.xyhmo.service.project;

import com.xyhmo.domain.ProjectLeader;
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
    /**
     * 获取我的订单列表
     *
     * */
    List<ProjectLeader> getMyProjectLeaderList(UserVo userVo,Integer projectStatus);
    /**
     * 根据订单ID获取订单报工信息
     *
     * */
    ProjectOrderVo getProjectLeaderWithListByOrderId(UserVo userVo, String orderId);
}
