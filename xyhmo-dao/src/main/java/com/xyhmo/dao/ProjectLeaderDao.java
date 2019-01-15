package com.xyhmo.dao;

import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.ProjectLeader;

@MyBatisRepository
public interface ProjectLeaderDao {
    /**
     * 保存一条数据
     *
     * */
    Long insert(ProjectLeader projectLeader);
}
