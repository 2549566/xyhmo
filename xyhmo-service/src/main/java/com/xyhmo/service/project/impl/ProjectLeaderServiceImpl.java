package com.xyhmo.service.project.impl;

import com.xyhmo.commom.enums.CityEnum;
import com.xyhmo.commom.enums.ProjectStatusEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.utils.TableNameUtil;
import com.xyhmo.dao.ProjectLeaderDao;
import com.xyhmo.domain.Address;
import com.xyhmo.domain.ProjectLeader;
import com.xyhmo.query.project.ProjectCreateReq;
import com.xyhmo.service.UserInfoService;
import com.xyhmo.service.address.AddressService;
import com.xyhmo.service.project.ProjectLeaderService;
import com.xyhmo.util.GenIdService;
import com.xyhmo.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

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
        ProjectLeader projectLeader=translationToProjectLeader(projectCreateReq,userVo);
        Long id=projectLeaderDao.insert(projectLeader);
        return id;
    }

    private ProjectLeader translationToProjectLeader(ProjectCreateReq projectCreateReq,UserVo userVo)throws Exception {
        String tableName = TableNameUtil.genProjectOrderTableName(userVo.getPin());
        if(StringUtils.isBlank(tableName)){
            throw new Exception(SystemEnum.SYSTEM_ERROR.getDesc());
        }
        String orderId="'"+genIdService.genProjectOrderId(CityEnum.PROJECT_CITY.getCode())+"'";
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
        Integer projectStatus= ProjectStatusEnum.PROJECT_NOT_START.getCode();
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
        projectLeader.setEveryDaySalary(everyDaySalary);
        projectLeader.setProjectStartTime(projecStartTime);
        projectLeader.setProjectEndTime(projectEndTime);
        projectLeader.setProjectStatus(projectStatus);
        projectLeader.setProjectTotalPay(projectTotalPay);
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
        return projectLeader;
    }
}
