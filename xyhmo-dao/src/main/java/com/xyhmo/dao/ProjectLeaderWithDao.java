package com.xyhmo.dao;

import com.xyhmo.commom.dao.MyBatisRepository;
import com.xyhmo.domain.ProjectLeaderWith;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@MyBatisRepository
public interface ProjectLeaderWithDao {
    /**
     * 保存一条数据
     *
     * */
    Long insert(ProjectLeaderWith projectLeaderWith);
    /**
     * 批量查询列表
     *
     * */
    List<ProjectLeaderWith> batchProjectLeaderWithList(@Param("tableName")String tableName, @Param("orderIdList")List<String> orderIdList);
    /**
     * 批量插入干活工人申报表（发布招工信息确认干活的时候，插入）
     *
     * */
    void insertBatch(@Param("tableName")String tableName,@Param("projectLeaderWithList")List<ProjectLeaderWith> projectLeaderWithList);
}
