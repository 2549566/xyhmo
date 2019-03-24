package com.xyhmo.dao;

import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.ProjectWorker;

import java.util.List;


@MyBatisRepository
public interface ProjectWorkerDao {
    /**
     * 保存一条数据
     *
     * */
    Long insert(ProjectWorker projectWorker);
    /**
     * 获取工人正在干的工程
     *
     * */
    List<ProjectWorker> selectMyProjectWorkerWorkingList(ProjectWorker projectWorker);
}
