package com.xyhmo.dao;

import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.ProjectLeader;
import com.xyhmo.domain.ProjectLeaderWith;

import java.util.List;

@MyBatisRepository
public interface ProjectLeaderDao {
    /**
     * 保存一条数据
     *
     * */
    Long insert(ProjectLeader projectLeader);
    /**
     * 根据用户pin获取业务员创建的招工订单
     *
     * */
    List<ProjectLeader> selectMyProjectLeaderList(ProjectLeader projectLeader);
    /**
     * 查询一条招工信息
     * tableName
     * pin
     * orderId
     *
     * */
    ProjectLeader selectOneProjectLeader(ProjectLeader projectLeader);
    /**
     * 修改projectLeader
     *
     * */
    Long updateProjectLeader(ProjectLeader projectLeader);

    void insertBatch(List<ProjectLeaderWith> projectLeaderWithList);
}
