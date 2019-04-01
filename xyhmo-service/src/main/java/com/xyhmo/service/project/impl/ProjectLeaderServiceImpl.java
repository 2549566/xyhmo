package com.xyhmo.service.project.impl;

import com.xyhmo.commom.enums.*;
import com.xyhmo.commom.exception.BusinessException;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.exception.SystemException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.DateUtil;
import com.xyhmo.commom.utils.TableNameUtil;
import com.xyhmo.dao.ProjectLeaderDao;
import com.xyhmo.dao.ProjectLeaderWithDao;
import com.xyhmo.dao.ProjectWorkerDao;
import com.xyhmo.domain.*;
import com.xyhmo.query.project.ProjectCreateReq;
import com.xyhmo.query.project.WorkerInfoParam;
import com.xyhmo.service.UserInfoService;
import com.xyhmo.service.address.AddressService;
import com.xyhmo.service.project.ProjectLeaderService;
import com.xyhmo.service.project.ProjectWorkerService;
import com.xyhmo.service.redis.impl.RedisProjectOrderService;
import com.xyhmo.util.GenIdService;
import com.xyhmo.vo.UserVo;
import com.xyhmo.vo.project.ProjectLeaderVo;
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
import java.util.*;

@Service
public class ProjectLeaderServiceImpl implements ProjectLeaderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private GenIdService genIdService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ProjectLeaderDao projectLeaderDao;
    @Autowired
    private RedisProjectOrderService redisProjectOrderService;
    @Autowired
    private ProjectLeaderWithDao projectLeaderWithDao;
    @Autowired
    private ProjectWorkerService projectWorkerService;

    @Autowired
    private RedisService redisService;
    @Autowired
    private ProjectWorkerDao projectWorkerDao;

    @Override
    public Long createProjectOrder(ProjectCreateReq projectCreateReq)throws Exception {
        if(projectCreateReq==null){
            logger.error("ProjectLeaderServiceImpl:projectCreateReq is null");
            return null;
        }
        UserVo userVo=userInfoService.getUserVoByToken(projectCreateReq.getToken());
        if(userVo==null || StringUtils.isEmpty(userVo.getPin())){
            return null;
        }
        String orderId=genIdService.genProjectOrderId(CityEnum.PROJECT_CITY.getCode());
        ProjectLeader projectLeader=translationToProjectLeader(projectCreateReq,userVo,orderId);
        Long id=projectLeaderDao.insert(projectLeader);
        if(id==null || id<1){
            throw new Exception("ProjectLeaderServiceImpl：createProjectOrder业务员创建招工订单入库失败");
        }
        ProjectLeader projectLeaderRedis=translationToProjectLeaderRedis(projectCreateReq,userVo,orderId);
        redisProjectOrderService.saveProjectOrder2Reids(projectLeaderRedis);
        return id;
    }

    private ProjectLeader translationToProjectLeaderRedis(ProjectCreateReq projectCreateReq, UserVo userVo,String orderId)throws Exception  {
        String tableName = TableNameUtil.genProjectLeaderTableName(userVo.getPin());
        if(StringUtils.isBlank(tableName)){
            throw new Exception(SystemEnum.SYSTEM_ERROR.getDesc());
        }
        String pin=userVo.getPin();
        String coordinate="";
        String userName="";
        if(StringUtils.isNotBlank(userVo.getRealName())){
            userName=userVo.getRealName();
        }else if(StringUtils.isNotBlank(userVo.getUserName())){
            userName=userVo.getUserName();
        }
        String projectTitle=projectCreateReq.getProjectTitle();
        Integer projectNeedWorker=projectCreateReq.getProjectNeedWorker();
        Integer projectNeedDay=projectCreateReq.getProjectNeedDay();
        Double everyDaySalary=projectCreateReq.getEveryDaySalary();
        String projecStartTime=projectCreateReq.getProjectStartTime();
        String projectEndTime=projectCreateReq.getProjectEndTime();
        //创建工程必须是0（未开始）状态
        Integer projectStatus= ProjectStatusEnum.PROJECT_RECRUITMENT.getCode();
        Double projectTotalPay=projectCreateReq.getProjectTotalPay();
        String mobileNumber=projectCreateReq.getMobileNumber();
        String describe="";
        if(StringUtils.isNotBlank(projectCreateReq.getDescribe())){
            describe=projectCreateReq.getDescribe();
        }
        Integer provinceId=projectCreateReq.getProvinceId();
        Integer cityId=projectCreateReq.getCityId();
        Integer countyId=projectCreateReq.getCountyId();
        List<Address> addressList=addressService.getAddressList();
        String provinceName="";
        String cityName="";
        String countyName="";
        if(!CollectionUtils.isEmpty(addressList)){
            for(Address address:addressList){
                if(!provinceName.equals("") && !cityName.equals("") && !countyName.equals("")){
                    break;
                }
                if(address.getId().equals(provinceId)){
                    provinceName=address.getCityName();
                    projectCreateReq.setProvinceName(address.getCityName());
                    continue;
                }
                if(address.getId().equals(cityId)){
                    cityName=address.getCityName();
                    projectCreateReq.setCitName(address.getCityName());
                    continue;
                }
                if(address.getId().equals(countyId)){
                    countyName=address.getCityName();
                    projectCreateReq.setCountyName(address.getCityName());
                    continue;
                }
            }
        }
        String addressDetail="";
        if(StringUtils.isNotBlank(projectCreateReq.getAddressDetail())){
            addressDetail=projectCreateReq.getAddressDetail();
        }
        String completeAddress=projectCreateReq.getProvinceName()+projectCreateReq.getCitName()+projectCreateReq.getCountyName()+projectCreateReq.getAddressDetail();
        ProjectLeader projectLeader=new ProjectLeader();
        projectLeader.setTableName(tableName);
        projectLeader.setOrderId(orderId);
        projectLeader.setPin(pin);
        projectLeader.setCoordinate(coordinate);
        projectLeader.setUserName(userName);
        projectLeader.setProjectTitle(projectTitle);
        projectLeader.setProjectNeedWorker(projectNeedWorker);
        projectLeader.setProjectNeedDay(projectNeedDay);
        if(everyDaySalary!=null){
            projectLeader.setEveryDaySalary(new BigDecimal(everyDaySalary));
        }
        projectLeader.setProjectStartTime(projecStartTime);
        projectLeader.setProjectEndTime(projectEndTime);
        projectLeader.setProjectStatus(projectStatus);
        if(projectTotalPay!=null){
            projectLeader.setProjectTotalPay(new BigDecimal(projectTotalPay));
        }
        projectLeader.setMobileNumber(mobileNumber);
        projectLeader.setDescription(describe);
        projectLeader.setProvinceId(provinceId);
        projectLeader.setCityId(cityId);
        projectLeader.setCountyId(countyId);
        projectLeader.setProvinceName(provinceName);
        projectLeader.setCityName(cityName);
        projectLeader.setCountyName(countyName);
        projectLeader.setAddressDetail(addressDetail);
        projectLeader.setCompleteAddress(completeAddress);
        projectLeader.setCreator(pin);
        projectLeader.setModifier(pin);
        projectLeader.setStatus(1);
        projectLeader.setCurrentWorkerNumber(0);
        return projectLeader;
    }

    @Override
    public List<ProjectOrderVo> getProjectOrderListPage(UserVo userVo,Integer page,Integer pageSize) {
        if(userVo==null || StringUtils.isEmpty(userVo.getPin())){
            logger.error("ProjectLeaderServiceImpl:user is error");
            return new ArrayList<>();
        }
        Set<ProjectOrderVo> allSet = new HashSet<>();
        //获取自己的list，并转为userSet，allSet加入userSet
        if(page.equals("1")){
            Set<ProjectOrderVo> myProjectOrderListSet=getMyProjectOrderList(userVo);
            if(!CollectionUtils.isEmpty(myProjectOrderListSet)){
                allSet.addAll(myProjectOrderListSet);
            }
        }
        List<ProjectOrderVo> otherProjectOrderList= redisProjectOrderService.getProjectOrderListPage(page,pageSize);
        Set<ProjectOrderVo> otherProjectOrderSet=new HashSet<>(otherProjectOrderList);
        allSet.addAll(otherProjectOrderSet);
        return new ArrayList<>(allSet);
    }

    @Override
    public List<ProjectLeader> getMyProjectLeaderList(UserVo userVo, Integer projectStatus) {
        if(userVo==null || StringUtils.isEmpty(userVo.getPin())){
            return new ArrayList<>();
        }
        String tableName = TableNameUtil.genProjectLeaderTableName(userVo.getPin());
        if(StringUtils.isEmpty(tableName)){
            return new ArrayList<>();
        }
        ProjectLeader projectLeader=new ProjectLeader();
        projectLeader.setTableName(tableName);
        projectLeader.setPin("'"+userVo.getPin()+"'");
        projectLeader.setProjectStatus(projectStatus);
        List<ProjectLeader> projectLeaderList=projectLeaderDao.selectMyProjectLeaderList(projectLeader);
        return projectLeaderList;
    }

    @Override
    public ProjectOrderVo getProjectLeaderWithListByOrderId(UserVo userVo, String orderId,Integer projectStatus) {
        if(StringUtils.isBlank(orderId) || userVo==null || StringUtils.isBlank(userVo.getPin())){
            return null;
        }
        ProjectOrderVo projectOrderVo=redisService.get(Contants.REDIS_ONE_PROJECTORDER+orderId);
        if(projectOrderVo!=null){
            List<ProjectLeaderWith> projectLeaderWithList=projectOrderVo.getProjectLeaderWithList();
            if(!CollectionUtils.isEmpty(projectLeaderWithList)){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                List<String> errorWorkerPinList= new ArrayList<>();
                for(ProjectLeaderWith projectLeaderWith:projectLeaderWithList){
                    List<ProjectWorkerTimeVo> projectWorkerTimeVoList=redisService.get(Contants.REDIS_ONE_WORKERTIMELIST+projectLeaderWith.getWorkerPin());
                    if(CollectionUtils.isEmpty(projectWorkerTimeVoList)){
                        continue;
                    }
                    for(ProjectWorkerTimeVo projectWorkerTimeVo:projectWorkerTimeVoList){
                        Date startTime= null;
                        Date endTime=null;
                        try {
                            startTime = df.parse(projectOrderVo.getProjectStartTime());
                            endTime=df.parse(projectOrderVo.getProjectEndTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(projectWorkerTimeVo.getStartDate().compareTo(startTime)>0 && projectWorkerTimeVo.getStartDate().compareTo(endTime)<0){
                            errorWorkerPinList.add(projectLeaderWith.getWorkerPin());
                            break;
                        }
                        if(projectWorkerTimeVo.getEndDate().compareTo(startTime)>0 && projectWorkerTimeVo.getEndDate().compareTo(endTime)<0){
                            errorWorkerPinList.add(projectLeaderWith.getWorkerPin());
                            break;
                        }
                    }
                }
                if(!CollectionUtils.isEmpty(errorWorkerPinList)){
                    //不为空的话，删除缓存中的数据
                    redisProjectOrderService.removeProjectOrder(orderId,errorWorkerPinList);
                }

            }
            return projectOrderVo;
        }
        if(projectOrderVo==null && (ProjectStatusEnum.PROJECT_RECRUITMENT.getCode()==projectStatus || ProjectStatusEnum.PROJECT_RECRUIT_END_NOT_START.getCode()==projectStatus) ){
            throw new BusinessException();
        }
        /**
         * projectStatus 状态大于1，相当于工程正在进行中的时候，缓存中不一定有数据。所以就从数据库中获取
         *
         * */
        String projectLeaderTableName=TableNameUtil.genProjectLeaderTableName(userVo.getPin());
        String projectLeaderWithTableName=TableNameUtil.genProjectLeaderWithTableName(userVo.getPin());
        if(StringUtils.isBlank(projectLeaderTableName) || StringUtils.isBlank(projectLeaderWithTableName)){
            return null;
        }
        ProjectLeader projectLeaderParam=new ProjectLeader();
        projectLeaderParam.setPin("'"+userVo.getPin()+"'");
        projectLeaderParam.setTableName(projectLeaderTableName);
        projectLeaderParam.setOrderId("'"+orderId+"'");
        ProjectLeader projectLeader=projectLeaderDao.selectOneProjectLeader(projectLeaderParam);
        if(projectLeader==null){
            return null;
        }
        List<String> orderIdList=new ArrayList<>();
        orderIdList.add("'"+orderId+"'");
        List<ProjectLeaderWith> projectLeaderWithList=projectLeaderWithDao.batchProjectLeaderWithList(projectLeaderWithTableName,orderIdList);
        projectOrderVo=new ProjectOrderVo();
        projectOrderVo=translationToOneProjectVo(projectOrderVo,projectLeader,projectLeaderWithList);
        Integer initDay= DateEnum.DATE_PROJECTORDER_INITDAY.getCode();
        if(org.apache.commons.lang3.StringUtils.isNoneBlank(projectLeader.getProjectEndTime()) ){
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
        redisService.set(Contants.REDIS_ONE_PROJECTORDER+orderId,projectOrderVo,totalSecond);
        return projectOrderVo;
    }

    @Override
    public List<String> sureProjectWorkerList(UserVo userVo,String orderId, List<WorkerInfoParam> workerInfoList){
        if(userVo==null || CollectionUtils.isEmpty(workerInfoList)){
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),ParamEnum.PARAM_ERROR.getDesc());
        }
        ProjectOrderVo projectOrderVo=redisService.get(Contants.REDIS_ONE_PROJECTORDER+orderId);
        if(projectOrderVo==null || projectOrderVo.getProjectStartTime()==null || projectOrderVo.getProjectEndTime()==null){
            throw new BusinessException(BusiessExceptionEnum.PROJECT_ORDER_SUREWORKERLIST_ERROR.getCode(),BusiessExceptionEnum.PROJECT_ORDER_SUREWORKERLIST_ERROR.getDesc());
        }
        Set<String> errorWorkerNameList= new HashSet<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for(WorkerInfoParam workerInfo:workerInfoList){
            if(workerInfo==null){
                throw new BusinessException(BusiessExceptionEnum.PROJECT_ORDER_SUREWORKERLIST_ERROR.getCode(),BusiessExceptionEnum.PROJECT_ORDER_SUREWORKERLIST_ERROR.getDesc());
            }
            List<ProjectWorkerTimeVo> voList=redisService.get(Contants.REDIS_ONE_WORKERTIMELIST+workerInfo.getPin());
            if(CollectionUtils.isEmpty(voList)){
                continue;
            }
            for(ProjectWorkerTimeVo vo:voList){
                Date startTime= null;
                Date endTime=null;
                try {
                    startTime = df.parse(projectOrderVo.getProjectStartTime());
                    endTime=df.parse(projectOrderVo.getProjectEndTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(vo.getStartDate().compareTo(startTime)>0 && vo.getStartDate().compareTo(endTime)<0){
                    errorWorkerNameList.add(workerInfo.getRealName());
                    break;
                }
                if(vo.getEndDate().compareTo(startTime)>0 && vo.getEndDate().compareTo(endTime)<0){
                    errorWorkerNameList.add(workerInfo.getRealName());
                    break;
                }
            }
        }
        if(!CollectionUtils.isEmpty(errorWorkerNameList)){
            return new ArrayList<>(errorWorkerNameList);
        }
        ProjectLeader projectLeader=getOneProjectLeader(userVo.getPin(),orderId);
        if(projectLeader==null || projectLeader.getProjectNeedWorker()==null || projectLeader.getProjectNeedWorker()==0){
            throw new BusinessException(BusiessExceptionEnum.PROJECT_ORDER_SUREWORKERLIST_ERROR.getCode(),BusiessExceptionEnum.PROJECT_ORDER_SUREWORKERLIST_ERROR.getDesc());
        }
        if(projectLeader.getProjectNeedWorker()!=workerInfoList.size()){
            throw new BusinessException(BusiessExceptionEnum.PROJECT_ORDER_WORKER_IS_NOT_FULL.getCode(),BusiessExceptionEnum.PROJECT_ORDER_WORKER_IS_NOT_FULL.getDesc());
        }
        //获取工程报工人数列表
        List<ProjectLeaderWith> projectLeaderWithList=translationToProjectLeaderWithList(userVo,projectLeader,workerInfoList);
        if(projectLeader.getProjectNeedWorker()!=projectLeaderWithList.size()){
            throw new BusinessException(BusiessExceptionEnum.PROJECT_ORDER_WORKER_IS_NOT_FULL.getCode(),BusiessExceptionEnum.PROJECT_ORDER_WORKER_IS_NOT_FULL.getDesc());
        }

        List<ProjectWorker> projectWorkerList=translationToProjectWorkerList(userVo,projectLeader,projectLeaderWithList);
        //事务写数据库
        updateProjectOrder(userVo,projectLeader,projectLeaderWithList,projectWorkerList);
        //更新缓存
        List<ProjectLeaderWith> projectLeaderWithListRedis=translationToProjectLeaderWithListRedis(userVo,projectLeader,workerInfoList);
        redisProjectOrderService.updateProjectOrderRedis(userVo,projectLeader,projectLeaderWithListRedis);
        return new ArrayList<>();
    }

    private List<ProjectWorker> translationToProjectWorkerList(UserVo userVo, ProjectLeader projectLeader,List<ProjectLeaderWith> projectLeaderWithList) {
        if(userVo==null || projectLeader==null || CollectionUtils.isEmpty(projectLeaderWithList)){
            return null;
        }
        String orderId="''";
        if(StringUtils.isNotBlank(projectLeader.getOrderId())){
            orderId="'"+projectLeader.getOrderId()+"'";
        }
        String leadePin="''";
        if(StringUtils.isNotBlank(userVo.getPin())){
            leadePin="'"+userVo.getPin()+"'";
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
        Integer projectStatus= ProjectStatusEnum.PROJECT_RECRUIT_END_NOT_START.getCode();
        BigDecimal projectTotalPay=projectLeader.getProjectTotalPay();
        String leaderName="''";
        if(StringUtils.isNotBlank(projectLeader.getUserName())){
            leaderName="'"+projectLeader.getUserName()+"'";
        }
        String description="''";
        if(StringUtils.isNotBlank(projectLeader.getDescription())){
            description="'"+projectLeader.getDescription()+"'";
        }
        List<ProjectWorker> projectWorkerList=new ArrayList<>();
        for(ProjectLeaderWith projectLeaderWith:projectLeaderWithList){
            if(projectLeaderWith==null){
                continue;
            }
            String tableName=TableNameUtil.genProjectWorkerTableName(projectLeaderWith.getWorkerPin());

            String pin="''";
            if(StringUtils.isNotBlank(projectLeaderWith.getWorkerPin())){
                pin="'"+projectLeaderWith.getWorkerPin()+"'";
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
            projectWorkerList.add(projectWorker);
        }
        return projectWorkerList;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public Boolean updateProjectOrder(UserVo userVo, ProjectLeader projectLeader,List<ProjectLeaderWith> projectLeaderWithList,List<ProjectWorker> projectWorkerList) {
        //修改projectLeader的状态
        String leaderPin=userVo.getPin();
        String tableName=TableNameUtil.genProjectLeaderTableName(leaderPin);
        ProjectLeader projectLeaderParam=new ProjectLeader();
        projectLeader.setTableName(tableName);
        projectLeader.setOrderId("'"+projectLeader.getOrderId()+"'");
        projectLeader.setProjectStatus(ProjectStatusEnum.PROJECT_RECRUIT_END_NOT_START.getCode());
        projectLeaderDao.updateProjectLeader(projectLeaderParam);
        //批量插入报工列表
        projectLeaderDao.insertBatch(projectLeaderWithList);
        for(ProjectWorker projectWorker:projectWorkerList){
            projectWorkerDao.insert(projectWorker);
        }
        return true;
    }

    @Override
    public ProjectLeader getOneProjectLeader(String leaderPin, String projectOrderId) {
        ProjectLeader projectLeader=new ProjectLeader();
        String tableName = TableNameUtil.genProjectLeaderTableName(leaderPin);
        projectLeader.setPin("'"+leaderPin+"'");
        projectLeader.setTableName(tableName);
        projectLeader.setOrderId("'"+projectOrderId+"'");
        return projectLeaderDao.selectOneProjectLeader(projectLeader);
    }

    /**
     * 组装干活工人的报单类。
     * projectLeaderWith 按照leaderPin(发布招工信息的人)来分表
     *
     * */
    private List<ProjectLeaderWith> translationToProjectLeaderWithList(UserVo userVo, ProjectLeader projectLeader,List<WorkerInfoParam> workerInfoList){
        if(userVo==null || projectLeader== null || CollectionUtils.isEmpty(workerInfoList)){
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
        BigDecimal everyDaySalary=projectLeader.getEveryDaySalary();
        Integer totalDay=projectLeader.getProjectNeedDay();
        BigDecimal totaSalary=projectLeader.getProjectTotalPay();
        String workerMoblie="''";
        if(StringUtils.isNotBlank(userVo.getMobileNumber())){
            workerMoblie="'"+userVo.getMobileNumber()+"'";
        }
        List<ProjectLeaderWith> projectLeaderWithList=new ArrayList<>();
        for(WorkerInfoParam workerInfo:workerInfoList){
            if(workerInfo==null || StringUtils.isBlank(workerInfo.getPin())){
                continue;
            }
            String workerPin="''";
            if(StringUtils.isNotBlank(workerInfo.getPin())){
                workerPin="'"+workerInfo.getPin()+"'";
            }
            String workerName="''";
            if(StringUtils.isNotBlank(workerInfo.getRealName())){
                workerName="'"+workerInfo.getRealName()+"'";
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
            projectLeaderWithList.add(projectLeaderWith);
        }
        return projectLeaderWithList;
    }

    /**
     * 组装干活工人的报单类，缓存中。
     * projectLeaderWith 按照leaderPin(发布招工信息的人)来分表
     *
     * */
    private List<ProjectLeaderWith> translationToProjectLeaderWithListRedis(UserVo userVo, ProjectLeader projectLeader,List<WorkerInfoParam> workerInfoList){
        if(userVo==null || projectLeader== null || CollectionUtils.isEmpty(workerInfoList)){
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
        BigDecimal everyDaySalary=projectLeader.getEveryDaySalary();
        Integer totalDay=projectLeader.getProjectNeedDay();
        BigDecimal totaSalary=projectLeader.getProjectTotalPay();
        String workerMoblie="";
        if(StringUtils.isNotBlank(userVo.getMobileNumber())){
            workerMoblie=userVo.getMobileNumber();
        }
        List<ProjectLeaderWith> projectLeaderWithList=new ArrayList<>();
        for(WorkerInfoParam workerInfo:workerInfoList){
            if(workerInfo==null || StringUtils.isBlank(workerInfo.getPin())){
                continue;
            }
            String workerPin="";
            if(StringUtils.isNotBlank(workerInfo.getPin())){
                workerPin=workerInfo.getPin();
            }
            String workerName="";
            if(StringUtils.isNotBlank(workerInfo.getRealName())){
                workerName=workerInfo.getRealName();
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
            projectLeaderWithList.add(projectLeaderWith);
        }
        return projectLeaderWithList;
    }

    private ProjectOrderVo translationToOneProjectVo(ProjectOrderVo projectOrderVo, ProjectLeader projectLeader,List<ProjectLeaderWith> projectLeaderWithList) {
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
        projectOrderVo.setCurrentWorkerNumber(projectLeader.getCurrentWorkerNumber());
        projectOrderVo.setProjectLeaderWithList(projectLeaderWithList);
        return projectOrderVo;
    }

    private Set<ProjectOrderVo> getMyProjectOrderList(UserVo userVo){
        if(userVo==null || StringUtils.isEmpty(userVo.getPin())){
            return new HashSet<>();
        }
        String tableName = TableNameUtil.genProjectLeaderTableName(userVo.getPin());
        String projectOrderWithTableName=TableNameUtil.genProjectLeaderWithTableName(userVo.getPin());
        if(StringUtils.isEmpty(tableName)){
            return new HashSet<>();
        }
        ProjectLeader projectLeader=new ProjectLeader();
        projectLeader.setTableName(tableName);
        projectLeader.setPin("'"+userVo.getPin()+"'");
        projectLeader.setProjectStatus(ProjectStatusEnum.PROJECT_RECRUITMENT.getCode());
        List<ProjectLeader> projectLeaderList=projectLeaderDao.selectMyProjectLeaderList(projectLeader);
        if(CollectionUtils.isEmpty(projectLeaderList)){
            return new HashSet<>();
        }
        List<String> orderIdList = new ArrayList<>();
        for(ProjectLeader leader:projectLeaderList){
            if(leader==null){
                continue;
            }
            orderIdList.add("'"+leader.getOrderId()+"'");
        }
        List<ProjectLeaderWith> projectLeaderWithList=projectLeaderWithDao.batchProjectLeaderWithList(projectOrderWithTableName,orderIdList);
        List<ProjectOrderVo> orderVoList=translationToProjectOrderVo(projectLeaderList,projectLeaderWithList);
        return new HashSet<>(orderVoList);
    }

    private List<ProjectOrderVo> translationToProjectOrderVo(List<ProjectLeader> projectLeaderList, List<ProjectLeaderWith> projectLeaderWithList) {
        if(CollectionUtils.isEmpty(projectLeaderList)){
            return new ArrayList<>();
        }
        List<ProjectOrderVo> projectOrderVoList=new ArrayList<>();
        for(ProjectLeader projectLeader:projectLeaderList){
            if(projectLeader==null){
                continue;
            }
            List<ProjectLeaderWith> oneProjectLeaderWithList=new ArrayList<>();
            for(ProjectLeaderWith projectLeaderWith:projectLeaderWithList){
                if(projectLeader.getOrderId().equals(projectLeaderWith.getOrderId())){
                    oneProjectLeaderWithList.add(projectLeaderWith);
                }
            }
            ProjectOrderVo projectOrderVo=new ProjectOrderVo();
            projectOrderVo=translationToOneProjectVo(projectOrderVo,projectLeader,oneProjectLeaderWithList);
            projectOrderVoList.add(projectOrderVo);
        }
        return projectOrderVoList;
    }

    private ProjectLeader translationToProjectLeader(ProjectCreateReq projectCreateReq,UserVo userVo,String orderId)throws Exception {
        String tableName = TableNameUtil.genProjectLeaderTableName(userVo.getPin());
        if(StringUtils.isBlank(tableName)){
            throw new Exception(SystemEnum.SYSTEM_ERROR.getDesc());
        }
        orderId="'"+orderId+"'";
        String pin="'"+userVo.getPin()+"'";
        String coordinate="''";
        String userName="''";
        if(StringUtils.isNotBlank(userVo.getRealName())){
            userName="'"+userVo.getRealName()+"'";
        }else if(StringUtils.isNotBlank(userVo.getUserName())){
            userName="'"+userVo.getUserName()+"'";
        }
        String projectTitle="'"+projectCreateReq.getProjectTitle()+"'";
        Integer projectNeedWorker=projectCreateReq.getProjectNeedWorker();
        Integer projectNeedDay=projectCreateReq.getProjectNeedDay();
        Double everyDaySalary=projectCreateReq.getEveryDaySalary();
        String projecStartTime="'"+projectCreateReq.getProjectStartTime()+"'";
        String projectEndTime="'"+projectCreateReq.getProjectEndTime()+"'";
        //创建工程必须是0（未开始）状态
        Integer projectStatus= ProjectStatusEnum.PROJECT_RECRUITMENT.getCode();
        Double projectTotalPay=projectCreateReq.getProjectTotalPay();
        String mobileNumber="'"+projectCreateReq.getMobileNumber()+"'";
        String describe="''";
        if(StringUtils.isNotBlank(projectCreateReq.getDescribe())){
            describe="'"+projectCreateReq.getDescribe()+"'";
        }
        Integer provinceId=projectCreateReq.getProvinceId();
        Integer cityId=projectCreateReq.getCityId();
        Integer countyId=projectCreateReq.getCountyId();
        List<Address> addressList=addressService.getAddressList();
        String provinceName="''";
        String cityName="''";
        String countyName="''";
        if(!CollectionUtils.isEmpty(addressList)){
            for(Address address:addressList){
                if(!provinceName.equals("''") && !cityName.equals("''") && !countyName.equals("''")){
                    break;
                }
                if(address.getId().equals(provinceId)){
                    provinceName="'"+address.getCityName()+"'";
                    projectCreateReq.setProvinceName(address.getCityName());
                    continue;
                }
                if(address.getId().equals(cityId)){
                    cityName="'"+address.getCityName()+"'";
                    projectCreateReq.setCitName(address.getCityName());
                    continue;
                }
                if(address.getId().equals(countyId)){
                    countyName="'"+address.getCityName()+"'";
                    projectCreateReq.setCountyName(address.getCityName());
                    continue;
                }
            }
        }
        String addressDetail="''";
        if(StringUtils.isNotBlank(projectCreateReq.getAddressDetail())){
            addressDetail="'"+projectCreateReq.getAddressDetail()+"'";
        }
        String completeAddress="'"+projectCreateReq.getProvinceName()+projectCreateReq.getCitName()+projectCreateReq.getCountyName()+projectCreateReq.getAddressDetail()+"'";
        ProjectLeader projectLeader=new ProjectLeader();
        projectLeader.setTableName(tableName);
        projectLeader.setOrderId(orderId);
        projectLeader.setPin(pin);
        projectLeader.setCoordinate(coordinate);
        projectLeader.setUserName(userName);
        projectLeader.setProjectTitle(projectTitle);
        projectLeader.setProjectNeedWorker(projectNeedWorker);
        projectLeader.setProjectNeedDay(projectNeedDay);
        if(everyDaySalary!=null){
            projectLeader.setEveryDaySalary(new BigDecimal(everyDaySalary));
        }
        projectLeader.setProjectStartTime(projecStartTime);
        projectLeader.setProjectEndTime(projectEndTime);
        projectLeader.setProjectStatus(projectStatus);
        if(projectTotalPay!=null){
            projectLeader.setProjectTotalPay(new BigDecimal(projectTotalPay));
        }
        projectLeader.setMobileNumber(mobileNumber);
        projectLeader.setDescription(describe);
        projectLeader.setProvinceId(provinceId);
        projectLeader.setCityId(cityId);
        projectLeader.setCountyId(countyId);
        projectLeader.setProvinceName(provinceName);
        projectLeader.setCityName(cityName);
        projectLeader.setCountyName(countyName);
        projectLeader.setAddressDetail(addressDetail);
        projectLeader.setCompleteAddress(completeAddress);
        projectLeader.setCreator(pin);
        projectLeader.setModifier(pin);
        projectLeader.setStatus(1);
        projectLeader.setCurrentWorkerNumber(0);
        return projectLeader;
    }
}
