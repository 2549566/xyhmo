package com.xyhmo.service.project.impl;

import com.xyhmo.commom.enums.BusiessExceptionEnum;
import com.xyhmo.commom.enums.DateEnum;
import com.xyhmo.commom.enums.ProjectStatusEnum;
import com.xyhmo.commom.exception.BusinessException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.DateUtil;
import com.xyhmo.commom.utils.TableNameUtil;
import com.xyhmo.dao.ProjectLeaderDao;
import com.xyhmo.dao.ProjectLeaderWithDao;
import com.xyhmo.dao.ProjectWorkerDao;
import com.xyhmo.domain.ProjectLeader;
import com.xyhmo.domain.ProjectLeaderWith;
import com.xyhmo.domain.ProjectOrderVo;
import com.xyhmo.domain.ProjectWorker;
import com.xyhmo.service.project.ProjectLeaderService;
import com.xyhmo.service.project.ProjectWorkerService;
import com.xyhmo.vo.UserVo;
import com.xyhmo.vo.project.ProjectWorkerTimeVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



@Service
public class ProjectWorkerServiceImpl implements ProjectWorkerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectLeaderDao projectLeaderDao;
    @Autowired
    private ProjectWorkerDao projectWorkerDao;
    @Autowired
    private ProjectLeaderWithDao projectLeaderWithDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ProjectLeaderService projectLeaderService;

    @Override
    public Boolean applyProjectOrder(UserVo userVo,String leaderPin, String projectOrderId) {
        if(userVo==null || StringUtils.isEmpty(userVo.getPin())){
            throw new BusinessException(BusiessExceptionEnum.PROJECT_ORDER_WORKER_APPLYERROR.getCode(),BusiessExceptionEnum.PROJECT_ORDER_WORKER_APPLYERROR.getDesc());
        }
        if(StringUtils.isEmpty(leaderPin) || StringUtils.isEmpty(projectOrderId)){
            return false;
        }
        //根据leaderPin(分表，根据leaderPin找到是在哪个表) 和工程订单查询该工程
        ProjectLeader projectLeader=projectLeaderService.getOneProjectLeader(leaderPin,projectOrderId);
        if(projectLeader==null){
            return false;
        }
        //查询缓存中的单个订单，缓存中必有该订单，如果没有该订单，则报错
        ProjectOrderVo projectOrderVo=redisService.get(Contants.REDIS_ONE_PROJECTORDER+projectOrderId);
        if(projectOrderVo==null){
            logger.error("ProjectWorkerServiceImpl：applyProjectOrder-招工订单(单个订单)缓存出现问题，请及时排查");
            throw new BusinessException(BusiessExceptionEnum.PROJECT_ORDER_REDIS_GETBYPROJECTORDERID.getCode(),BusiessExceptionEnum.PROJECT_ORDER_REDIS_GETBYPROJECTORDERID.getDesc());
        }
        //校验工人是否申报工单
        List<ProjectLeaderWith> withList=projectOrderVo.getProjectLeaderWithList();
        if(!CollectionUtils.isEmpty(withList)){
            for(ProjectLeaderWith projectLeaderWith:withList){
                if(projectLeaderWith==null){
                    continue;
                }
                if(userVo.getPin().equals(projectLeaderWith.getWorkerPin()) && projectLeaderWith.getIsApply()){
                    logger.error("ProjectWorkerServiceImpl：applyProjectOrder-该招工信息您已申报");
                    throw new BusinessException(BusiessExceptionEnum.PROJECT_ORDER_IS_APPLY.getCode(),BusiessExceptionEnum.PROJECT_ORDER_IS_APPLY.getDesc());
                }
            }
        }
        if(!projectOrderVo.getProjectStatus().equals(ProjectStatusEnum.PROJECT_RECRUITMENT.getCode())){
            logger.error("ProjectWorkerServiceImpl：applyProjectOrder-招工已经结束");
            throw new BusinessException(BusiessExceptionEnum.PROJECT_ORDER_WORKER_APPLYERROR.getCode(),BusiessExceptionEnum.PROJECT_ORDER_WORKER_APPLYERROR.getDesc());
        }
        Integer currentWorker=0;
        if(projectOrderVo.getCurrentWorkerNumber()!=null){
            currentWorker=projectOrderVo.getCurrentWorkerNumber();
        }
        if((projectOrderVo.getProjectNeedWorker()+20)<currentWorker){
            logger.error("ProjectWorkerServiceImpl：applyProjectOrder-招工已经结束");
            throw new BusinessException(BusiessExceptionEnum.PROJECT_ORDER_WORKER_APPLYERROR.getCode(),BusiessExceptionEnum.PROJECT_ORDER_WORKER_APPLYERROR.getDesc());
        }

//        /**
//         * 校验申报的时间是否有冲突
//         *
//         * */
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        List<ProjectWorkerTimeVo> voList=redisService.get(Contants.REDIS_ONE_WORKERTIMELIST+userVo.getPin());
//        for(ProjectWorkerTimeVo vo:voList){
//            Date startTime= null;
//            Date endTime=null;
//            try {
//                startTime = df.parse(projectOrderVo.getProjectStartTime());
//                endTime=df.parse(projectOrderVo.getProjectEndTime());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            if(vo.getStartDate().compareTo(startTime)>0 && vo.getStartDate().compareTo(endTime)<0){
//                errorWorkerNameList.add(workerInfo.getRealName());
//                break;
//            }
//            if(vo.getEndDate().compareTo(startTime)>0 && vo.getEndDate().compareTo(endTime)<0){
//                errorWorkerNameList.add(workerInfo.getRealName());
//                break;
//            }
//        }

//        for(ProjectOrderVo vo:projectOrderVoList){
//            if(vo==null){
//                continue;
//            }
//            List<ProjectLeaderWith> projectLeaderWithList=vo.getProjectLeaderWithList();
//            if(projectLeaderWithList==null || CollectionUtils.isEmpty(projectLeaderWithList)){
//                continue;
//            }
//            for(ProjectLeaderWith projectLeaderWith:projectLeaderWithList){
//                if(projectLeaderWith.getWorkerPin().equals(userVo.getPin()) && projectLeaderWith.getIsApply()){
//                    logger.error("ProjectWorkerServiceImpl：applyProjectOrder-该招工信息您已申报");
//                    throw new BusinessException(BusiessExceptionEnum.PROJECT_ORDER_IS_APPLY.getCode(),BusiessExceptionEnum.PROJECT_ORDER_IS_APPLY.getDesc());
//                }
//            }
//        }
//        ProjectLeaderWith projectLeaderWith=translationToProjectLeaderWith(userVo,projectLeader);
        ProjectLeaderWith projectLeaderWithRedis=translationToProjectLeaderWithRedis(userVo,projectLeader);
//        ProjectWorker projectWorker=translationToProjectWorker(userVo,projectLeader);
        autowaredToProjectOrderVo(projectOrderVo,projectLeaderWithRedis,projectLeader);
//        if(!projectOrderVo.getProjectStatus().equals(projectWorker.getProjectStatus())){
//            projectWorker.setProjectStatus(projectOrderVo.getProjectStatus());
//        }
//        todo 没有必要事务存入 缓存中就有数据
        saveWorkerApply(projectLeader);
        List<ProjectOrderVo> projectOrderVoList=redisService.get(Contants.REDIS_PROJECTORDERLIST_BJ);
        //查询大缓存中的所有订单列表
        if(CollectionUtils.isEmpty(projectOrderVoList)){
            logger.error("ProjectWorkerServiceImpl：applyProjectOrder-招工订单(所有订单)大缓存出现问题，请及时排查");
            throw new BusinessException(BusiessExceptionEnum.PROJECT_ORDER_REDIS_GETBYPROJECTORDERID.getCode(),BusiessExceptionEnum.PROJECT_ORDER_REDIS_GETBYPROJECTORDERID.getDesc());
        }
        Iterator<ProjectOrderVo> it = projectOrderVoList.iterator();
        while(it.hasNext()){
            ProjectOrderVo vo= it.next();
            if(vo.getOrderId().equals(projectOrderVo.getOrderId())){
                it.remove();
            }
        }
        projectOrderVoList.add(projectOrderVo);
        redisService.set(Contants.REDIS_PROJECTORDERLIST_BJ,projectOrderVoList);
        //todo 优化：存入两张表有点慢。等量大以后，接入MQ，优化向projectLeaderWith 表和projectWorker表中插一条数据。
        return true;
    }

    private void autowaredToProjectOrderVo(ProjectOrderVo projectOrderVo,ProjectLeaderWith projectLeaderWith,ProjectLeader projectLeader) {
        Integer currtWorkerNum=0;
        if(projectOrderVo.getCurrentWorkerNumber()==null){
            currtWorkerNum=1;
        }else{
            currtWorkerNum=projectOrderVo.getCurrentWorkerNumber()+1;
        }
        projectOrderVo.setCurrentWorkerNumber(currtWorkerNum);
        projectLeader.setCurrentWorkerNumber(currtWorkerNum);
        //将新创建的订单放入缓存，在缓存中保存的时间为 工程订单结束日期-今天+15天
        Integer initDay= DateEnum.DATE_PROJECTORDER_INITDAY.getCode();
        if(org.apache.commons.lang3.StringUtils.isNoneBlank(projectOrderVo.getProjectEndTime()) ){
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
        List<ProjectLeaderWith> projectLeaderWithList=projectOrderVo.getProjectLeaderWithList();
        if(CollectionUtils.isEmpty(projectLeaderWithList)) {
            projectLeaderWithList = new ArrayList<>();
        }
        projectLeaderWithList.add(projectLeaderWith);
        projectOrderVo.setProjectLeaderWithList(projectLeaderWithList);
        redisService.set(Contants.REDIS_ONE_PROJECTORDER+projectOrderVo.getOrderId(),projectOrderVo,totalSecond);
    }


    @Override
//    @Transactional(propagation= Propagation.REQUIRED)
    public void saveWorkerApply(ProjectLeader projectLeader) {
//        projectLeaderWithDao.insert(projectLeaderWith);
//        projectWorkerDao.insert(projectWorker);
        ProjectLeader projectLeaderParam=new ProjectLeader();
        String leaderPin=projectLeader.getPin();
        String tableName=TableNameUtil.genProjectLeaderTableName(leaderPin);
        projectLeaderParam.setTableName(tableName);
        projectLeaderParam.setOrderId("'"+projectLeader.getOrderId()+"'");
        projectLeaderParam.setCurrentWorkerNumber(projectLeader.getCurrentWorkerNumber());
        projectLeaderDao.updateProjectLeader(projectLeaderParam);
    }

    @Override
    public List<ProjectWorker> getMyProjectWorkerListWroking(String pin) {
        if(StringUtils.isBlank(pin)){
            return new ArrayList<>();
        }
        String tableName=TableNameUtil.genProjectWorkerTableName(pin);
        ProjectWorker projectWorker= new ProjectWorker();
        projectWorker.setTableName(tableName);
        projectWorker.setPin("'"+pin+"'");
        return projectWorkerDao.selectMyProjectWorkerWorkingList(projectWorker);
    }

    /**
     * 组装干活工人的报单类。
     * projectLeaderWith 按照leaderPin(发布招工信息的人)来分表
     *
     * */
    private ProjectLeaderWith translationToProjectLeaderWith(UserVo userVo, ProjectLeader projectLeader) {
        if(userVo==null || projectLeader== null){
            return null;
        }
        String tableName=TableNameUtil.genProjectLeaderWithTableName(projectLeader.getPin());
        if(StringUtils.isBlank(tableName)){
            return null;
        }
        String orderId="''";
        if(StringUtils.isNotBlank(projectLeader.getOrderId())){
            orderId="'"+projectLeader.getOrderId()+"'";
        }
        String pin="''";
        if(StringUtils.isNotBlank(projectLeader.getPin())){
            pin="'"+projectLeader.getPin()+"'";
        }
        String workerPin="''";
        if(StringUtils.isNotBlank(userVo.getPin())){
             workerPin="'"+userVo.getPin()+"'";
        }
        BigDecimal everyDaySalary=projectLeader.getEveryDaySalary();
        Integer totalDay=projectLeader.getProjectNeedDay();
        BigDecimal totaSalary=projectLeader.getProjectTotalPay();
        String workerMoblie="''";
        if(StringUtils.isNotBlank(userVo.getMobileNumber())){
            workerMoblie="'"+userVo.getMobileNumber()+"'";
        }
        String workerName="''";
        if(StringUtils.isNotBlank(userVo.getRealName())){
            workerName="'"+userVo.getRealName()+"'";
        }
        ProjectLeaderWith projectLeaderWith=new ProjectLeaderWith();
        projectLeaderWith.setTableName(tableName);
        projectLeaderWith.setOrderId(orderId);
        projectLeaderWith.setPin(pin);
        projectLeaderWith.setWorkerPin(workerPin);
        projectLeaderWith.setEveryDaySalary(everyDaySalary);
        projectLeaderWith.setTotalDay(totalDay);
        projectLeaderWith.setTotaSalary(totaSalary);
        projectLeaderWith.setWorkerMoblie(workerMoblie);
        projectLeaderWith.setWorkerName(workerName);
        projectLeaderWith.setCreator(pin);
        projectLeaderWith.setModifier(pin);
        projectLeaderWith.setStatus(1);
        projectLeaderWith.setScore(0.00);
        projectLeaderWith.setNextCooperation(1);
        projectLeaderWith.setIsApply(true);
        return projectLeaderWith;
    }
    /**
     * 组装干活工人的报单类(缓存中)。
     * projectLeaderWith 按照leaderPin(发布招工信息的人)来分表
     *
     * */
    private ProjectLeaderWith translationToProjectLeaderWithRedis(UserVo userVo, ProjectLeader projectLeader) {
        if(userVo==null || projectLeader== null){
            return null;
        }
        String tableName=TableNameUtil.genProjectLeaderWithTableName(projectLeader.getPin());
        if(StringUtils.isBlank(tableName)){
            return null;
        }
        String orderId="";
        if(StringUtils.isNotBlank(projectLeader.getOrderId())){
            orderId=projectLeader.getOrderId();
        }
        String pin="";
        if(StringUtils.isNotBlank(projectLeader.getPin())){
            pin=projectLeader.getPin();
        }
        String workerPin="";
        if(StringUtils.isNotBlank(userVo.getPin())){
            workerPin=userVo.getPin();
        }
        BigDecimal everyDaySalary=projectLeader.getEveryDaySalary();
        Integer totalDay=projectLeader.getProjectNeedDay();
        BigDecimal totaSalary=projectLeader.getProjectTotalPay();
        String workerMoblie="";
        if(StringUtils.isNotBlank(userVo.getMobileNumber())){
            workerMoblie=userVo.getMobileNumber();
        }
        String workerName="";
        if(StringUtils.isNotBlank(userVo.getRealName())){
            workerName=userVo.getRealName();
        }
        ProjectLeaderWith projectLeaderWith=new ProjectLeaderWith();
        projectLeaderWith.setTableName(tableName);
        projectLeaderWith.setOrderId(orderId);
        projectLeaderWith.setPin(pin);
        projectLeaderWith.setWorkerPin(workerPin);
        projectLeaderWith.setEveryDaySalary(everyDaySalary);
        projectLeaderWith.setTotalDay(totalDay);
        projectLeaderWith.setTotaSalary(totaSalary);
        projectLeaderWith.setWorkerMoblie(workerMoblie);
        projectLeaderWith.setWorkerName(workerName);
        projectLeaderWith.setCreator(pin);
        projectLeaderWith.setModifier(pin);
        projectLeaderWith.setStatus(1);
        projectLeaderWith.setScore(0.00);
        projectLeaderWith.setNextCooperation(1);
        projectLeaderWith.setIsApply(true);
        return projectLeaderWith;
    }
    /**
     * 组装工人的订单类
     * 用于工人查询订单信息
     *
     * */
    private ProjectWorker translationToProjectWorker(UserVo userVo, ProjectLeader projectLeader) {
        if(userVo==null || projectLeader==null){
            return null;
        }
        String tableName=TableNameUtil.genProjectWorkerTableName(userVo.getPin());
        String orderId="''";
        if(StringUtils.isNotBlank(projectLeader.getOrderId())){
            orderId="'"+projectLeader.getOrderId()+"'";
        }
        String pin="''";
        if(StringUtils.isNotBlank(userVo.getPin())){
            pin="'"+userVo.getPin()+"'";
        }
        String leadePin="''";
        if(StringUtils.isNotBlank(projectLeader.getPin())){
            leadePin="'"+projectLeader.getPin()+"'";
        }
        String leaderMobile="''";
        if(StringUtils.isNotBlank(projectLeader.getMobileNumber())){
            leaderMobile="'"+projectLeader.getMobileNumber()+"'";
        }
        String coordinate="''";
        String completeAddress="''";
        if(StringUtils.isNotBlank(projectLeader.getCompleteAddress())){
            completeAddress="'"+projectLeader.getCompleteAddress()+"'";
        }
        String projectTitle="''";
        if(StringUtils.isNotBlank(projectLeader.getProjectTitle())){
            projectTitle="'"+projectLeader.getProjectTitle()+"'";
        }
        Integer projectNeedWorker=projectLeader.getProjectNeedWorker();
        Integer projectNeedDay=projectLeader.getProjectNeedDay();
        BigDecimal everyDaySalary=projectLeader.getEveryDaySalary();
        String projectStartTime="''";
        if(StringUtils.isNotBlank(projectLeader.getProjectStartTime())){
            projectStartTime="'"+projectLeader.getProjectStartTime()+"'";
        }
        String projectEndTime="''";
        if(StringUtils.isNotBlank(projectLeader.getProjectEndTime())){
            projectEndTime="'"+projectLeader.getProjectEndTime()+"'";
        }
        //todo  工程是否未开始
        Integer projectStatus= ProjectStatusEnum.PROJECT_RECRUITMENT.getCode();
        BigDecimal projectTotalPay=projectLeader.getProjectTotalPay();
        String leaderName="''";
        if(StringUtils.isNotBlank(projectLeader.getUserName())){
            leaderName="'"+projectLeader.getUserName()+"'";
        }
        String description="''";
        if(StringUtils.isNotBlank(projectLeader.getDescription())){
            description="'"+projectLeader.getDescription()+"'";
        }
        ProjectWorker projectWorker=new ProjectWorker();
        projectWorker.setCreator(pin);
        projectWorker.setModifier(pin);
        projectWorker.setStatus(1);
        projectWorker.setTableName(tableName);
        projectWorker.setOrderId(orderId);
        projectWorker.setLeadePin(leadePin);
        projectWorker.setLeaderMobile(leaderMobile);
        projectWorker.setCoordinate(coordinate);
        projectWorker.setCompleteAddress(completeAddress);
        projectWorker.setProjectTitle(projectTitle);
        projectWorker.setProjectNeedWorker(projectNeedWorker);
        projectWorker.setProjectNeedDay(projectNeedDay);
        projectWorker.setEveryDaySalary(everyDaySalary);
        projectWorker.setProjectStartTime(projectStartTime);
        projectWorker.setProjectEndTime(projectEndTime);
        projectWorker.setProjectStatus(projectStatus);
        projectWorker.setProjectTotalPay(projectTotalPay);
        projectWorker.setLeaderName(leaderName);
        projectWorker.setDescription(description);
        projectWorker.setScore(0.00);
        projectWorker.setNextCooperation(1);
        projectWorker.setPin(pin);
        return projectWorker;
    }

}
