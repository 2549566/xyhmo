package com.xyhmo.service.redis.impl;

import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.domain.ProjectLeader;
import com.xyhmo.domain.ProjectOrderVo;
import com.xyhmo.service.redis.RedisProjectOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisProjectOrderService implements RedisProjectOrder{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService redisService;

    @Override
    public void saveProjectOrder2Reids(ProjectLeader projectLeader) {
        if(projectLeader==null){
            logger.error("RedisProjectOrderService:saveProjectOrder2Reids projectLeader is null");
            return;
        }
        List<ProjectOrderVo> projectOrderVoList=redisService.get(Contants.REDIS_PROJECTORDERLIST_BJ);
        ProjectOrderVo projectOrderVo=translationToProjectOrderVo(projectLeader);
        if(projectOrderVo==null){
            return;
        }
        if(CollectionUtils.isEmpty(projectOrderVoList)){
            projectOrderVoList =new ArrayList<>();
            projectOrderVoList.add(projectOrderVo);
            redisService.set(Contants.REDIS_PROJECTORDERLIST_BJ,projectOrderVoList);
            return;
        }
        //todo 第一期先不做排序，排序放到招工模块上了以后做
        projectOrderVoList.add(projectOrderVo);
        redisService.set(Contants.REDIS_PROJECTORDERLIST_BJ,projectOrderVoList);
        return;
    }

    @Override
    public List<ProjectOrderVo> getProjectOrderListPage(Integer page, Integer pageSize) {
        //todo 分页查询缓存中的工程订单(第一期先不做)
        List<ProjectOrderVo> projectOrderVoList=redisService.get(Contants.REDIS_PROJECTORDERLIST_BJ);
        if(CollectionUtils.isEmpty(projectOrderVoList)){
            return new ArrayList<>();
        }
        return projectOrderVoList;
    }

    private ProjectOrderVo translationToProjectOrderVo(ProjectLeader projectLeader) {
        if(projectLeader==null){
            return null;
        }
        ProjectOrderVo projectOrderVo=new ProjectOrderVo();
        projectOrderVo.setTableName(projectLeader.getTableName());
        projectOrderVo.setId(projectLeader.getId());
        projectOrderVo.setOrderId(projectLeader.getOrderId());
        projectOrderVo.setPin(projectLeader.getPin());
        projectOrderVo.setCoordinate(projectLeader.getCoordinate());
        projectOrderVo.setUserName(projectLeader.getUserName());
        projectOrderVo.setProjectTitle(projectLeader.getProjectTitle());
        projectOrderVo.setProjectNeedDay(projectLeader.getProjectNeedDay());
        projectOrderVo.setProjectNeedWorker(projectLeader.getProjectNeedWorker());
        projectOrderVo.setEveryDaySalary(projectLeader.getEveryDaySalary());
        projectOrderVo.setProjectStartTime(projectLeader.getProjectStartTime());
        projectOrderVo.setProjectEndTime(projectLeader.getProjectEndTime());
        projectOrderVo.setProjectStatus(projectLeader.getProjectStatus());
        projectOrderVo.setProjectTotalPay(projectLeader.getProjectTotalPay());
        projectOrderVo.setMobileNumber(projectLeader.getMobileNumber());
        projectOrderVo.setDescription(projectLeader.getDescription());
        projectOrderVo.setProvinceId(projectLeader.getProvinceId());
        projectOrderVo.setCityId(projectLeader.getCityId());
        projectOrderVo.setCountyId(projectLeader.getCountyId());
        projectOrderVo.setProvinceName(projectLeader.getProvinceName());
        projectOrderVo.setCityName(projectLeader.getCityName());
        projectOrderVo.setCountyName(projectLeader.getCountyName());
        projectOrderVo.setAddressDetail(projectLeader.getAddressDetail());
        projectOrderVo.setCompleteAddress(projectLeader.getCompleteAddress());
        projectOrderVo.setProjectLeaderWithList(new ArrayList<>());
        return projectOrderVo;
    }

}
