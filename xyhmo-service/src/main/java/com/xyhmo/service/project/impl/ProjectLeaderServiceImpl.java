package com.xyhmo.service.project.impl;

import com.xyhmo.commom.enums.CityEnum;
import com.xyhmo.commom.enums.DateEnum;
import com.xyhmo.commom.enums.ProjectStatusEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.SystemException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.TableNameUtil;
import com.xyhmo.dao.ProjectLeaderDao;
import com.xyhmo.dao.ProjectLeaderWithDao;
import com.xyhmo.domain.Address;
import com.xyhmo.domain.ProjectLeader;
import com.xyhmo.domain.ProjectLeaderWith;
import com.xyhmo.domain.ProjectOrderVo;
import com.xyhmo.query.project.ProjectCreateReq;
import com.xyhmo.service.UserInfoService;
import com.xyhmo.service.address.AddressService;
import com.xyhmo.service.project.ProjectLeaderService;
import com.xyhmo.service.redis.impl.RedisProjectOrderService;
import com.xyhmo.util.GenIdService;
import com.xyhmo.vo.UserVo;
import com.xyhmo.vo.project.ProjectLeaderVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
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
    private RedisService redisService;

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
    public ProjectOrderVo getProjectLeaderWithListByOrderId(UserVo userVo, String orderId) {
        if(StringUtils.isBlank(orderId) || userVo==null || StringUtils.isBlank(userVo.getPin())){
            return null;
        }
        ProjectOrderVo projectOrderVo=redisService.get(Contants.REDIS_ONE_PROJECTORDER+orderId);
        if(projectOrderVo!=null){
            return projectOrderVo;
        }
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
        redisService.set(Contants.REDIS_ONE_PROJECTORDER+orderId,projectOrderVo, DateEnum.DATE_PROJECTORDER_ADDDAY.getCode());
        return projectOrderVo;
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
