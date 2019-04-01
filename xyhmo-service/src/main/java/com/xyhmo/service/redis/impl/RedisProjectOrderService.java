package com.xyhmo.service.redis.impl;

import com.xyhmo.commom.enums.DateEnum;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.DateUtil;
import com.xyhmo.domain.ProjectLeader;
import com.xyhmo.domain.ProjectLeaderWith;
import com.xyhmo.domain.ProjectOrderVo;
import com.xyhmo.domain.ProjectWorker;
import com.xyhmo.service.redis.RedisProjectOrder;
import com.xyhmo.vo.UserVo;
import com.xyhmo.vo.project.ProjectWorkerTimeVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
        if(StringUtils.isBlank(projectLeader.getOrderId())){
            logger.error("RedisProjectOrderService:saveProjectOrder2Reids projectLeader-orderId is empty");
            return;
        }
        List<ProjectOrderVo> projectOrderVoList=redisService.get(Contants.REDIS_PROJECTORDERLIST_BJ);
        ProjectOrderVo projectOrderVo=translationToProjectOrderVo(projectLeader);
        //将新创建的订单放入缓存，在缓存中保存的时间为 工程订单结束日期-今天+15天
        Integer initDay= DateEnum.DATE_PROJECTORDER_INITDAY.getCode();
        if(StringUtils.isNoneBlank(projectLeader.getProjectEndTime()) ){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date endDate=sdf.parse(projectLeader.getProjectEndTime());
                Integer timeDiff=Integer.parseInt(String.valueOf(DateUtil.getDatePoor(endDate,new Date())));
                if(timeDiff>=0){
                    initDay=timeDiff;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Integer totalDay=initDay+DateEnum.DATE_PROJECTORDER_ADDDAY.getCode();
        Integer totalSecond=totalDay*24*60*60;
        redisService.set(Contants.REDIS_ONE_PROJECTORDER+projectOrderVo.getOrderId(),projectOrderVo,totalSecond);

        if(projectOrderVo==null){
            return;
        }
        if(CollectionUtils.isEmpty(projectOrderVoList)){
            projectOrderVoList =new ArrayList<>();
            projectOrderVoList.add(projectOrderVo);
            redisService.set(Contants.REDIS_PROJECTORDERLIST_BJ,projectOrderVoList);
            return;
        }
        //todo 第一期先不做排序，排序放到招工模块上了以后做(第一期先不做)
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

    @Override
    public void updateProjectOrderRedis(UserVo userVo, ProjectLeader projectLeader, List<ProjectLeaderWith> projectLeaderWithListRedis){
        if(userVo==null || projectLeader==null){
            return;
        }
        ProjectOrderVo projectOrderVo=translationToProjectOrderVo(projectLeader);
        if(projectOrderVo==null){
            return;
        }
        projectOrderVo.setProjectLeaderWithList(projectLeaderWithListRedis);
        //将新创建的订单放入缓存，在缓存中保存的时间为 工程订单结束日期-今天+15天
        Integer initDay= DateEnum.DATE_PROJECTORDER_INITDAY.getCode();
        if(StringUtils.isNoneBlank(projectLeader.getProjectEndTime()) ){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date endDate=sdf.parse(projectLeader.getProjectEndTime());
                Integer timeDiff=Integer.parseInt(String.valueOf(DateUtil.getDatePoor(endDate,new Date())));
                if(timeDiff>=0){
                    initDay=timeDiff;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Integer totalDay=initDay+DateEnum.DATE_PROJECTORDER_ADDDAY.getCode();
        Integer totalSecond=totalDay*24*60*60;
        redisService.set(Contants.REDIS_ONE_PROJECTORDER+projectOrderVo.getOrderId(),projectOrderVo,totalSecond);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(ProjectLeaderWith projectLeaderWith:projectLeaderWithListRedis){
            List<ProjectWorkerTimeVo> voList=redisService.get(Contants.REDIS_ONE_WORKERTIMELIST+projectLeaderWith.getWorkerPin());
            if(CollectionUtils.isEmpty(voList)) {
                voList = new ArrayList<>();
            }
            ProjectWorkerTimeVo projectWorkerTimeVo=new ProjectWorkerTimeVo();
            projectWorkerTimeVo.setPin(projectLeaderWith.getWorkerPin());
            Date startTime= null;
            Date endTime =null;
            try {
                startTime = sdf.parse(projectLeader.getProjectStartTime());
                endTime = sdf.parse(projectLeader.getProjectEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            projectWorkerTimeVo.setStartDate(startTime);
            projectWorkerTimeVo.setEndDate(endTime);
            voList.add(projectWorkerTimeVo);
            redisService.set(Contants.REDIS_ONE_WORKERTIMELIST+projectLeaderWith.getWorkerPin(),voList);
        }
        updateProjectOrderVoList(projectOrderVo);
    }

    private void updateProjectOrderVoList(ProjectOrderVo projectOrderVo){
        List<ProjectOrderVo> projectOrderVoList=redisService.get(Contants.REDIS_PROJECTORDERLIST_BJ);
        if(CollectionUtils.isEmpty(projectOrderVoList)){
            projectOrderVoList=new ArrayList<>();
            projectOrderVoList.add(projectOrderVo);
        }else{
            Iterator<ProjectOrderVo> it = projectOrderVoList.iterator();
            while(it.hasNext()){
                ProjectOrderVo x = it.next();
                if(x.getOrderId().equals(projectOrderVo.getOrderId())){
                    it.remove();
                }
            }
            projectOrderVoList.add(projectOrderVo);
        }
        redisService.set(Contants.REDIS_PROJECTORDERLIST_BJ,projectOrderVoList);
    }

    @Override
    public void removeProjectOrder(String orderId,List<String> errorWorkerPinList) {
        ProjectOrderVo projectOrderVo=redisService.get(Contants.REDIS_ONE_PROJECTORDER+orderId);
        if(projectOrderVo!=null){
            List<ProjectLeaderWith> projectLeaderWithList=projectOrderVo.getProjectLeaderWithList();
            if(!CollectionUtils.isEmpty(projectLeaderWithList)){
                Iterator<ProjectLeaderWith> it = projectLeaderWithList.iterator();
                while(it.hasNext()){
                    ProjectLeaderWith x = it.next();
                    if(errorWorkerPinList.contains(x.getWorkerPin())){
                        it.remove();
                    }
                }
            }
            projectOrderVo.setProjectLeaderWithList(projectLeaderWithList);
            //将新创建的订单放入缓存，在缓存中保存的时间为 工程订单结束日期-今天+15天
            Integer initDay= DateEnum.DATE_PROJECTORDER_INITDAY.getCode();
            if(StringUtils.isNoneBlank(projectOrderVo.getProjectEndTime()) ){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date endDate=sdf.parse(projectOrderVo.getProjectEndTime());
                    Integer timeDiff=Integer.parseInt(String.valueOf(DateUtil.getDatePoor(endDate,new Date())));
                    if(timeDiff>=0){
                        initDay=timeDiff;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Integer totalDay=initDay+DateEnum.DATE_PROJECTORDER_ADDDAY.getCode();
            Integer totalSecond=totalDay*24*60*60;
            redisService.set(Contants.REDIS_ONE_PROJECTORDER+orderId,projectOrderVo,totalSecond);
        }
        updateProjectOrderVoList(projectOrderVo);
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
        projectOrderVo.setCurrentWorkerNumber(projectLeader.getCurrentWorkerNumber());
        return projectOrderVo;
    }

}
