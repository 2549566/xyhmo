package com.xyhmo.controller.order.projectOrder;

import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.domain.ProjectLeader;
import com.xyhmo.domain.ProjectOrderVo;
import com.xyhmo.query.project.ProjectCreateReq;
import com.xyhmo.service.TokenService;
import com.xyhmo.service.project.ProjectLeaderService;
import com.xyhmo.vo.UserVo;
import com.xyhmo.vo.project.ProjectLeaderVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/projectLeader")
public class ProjectLeaderController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private TokenService tokenService;
    @Autowired
    private ProjectLeaderService projectLeaderService;


    /**
     * 业务员招工，创建招工订单
     * 1：不能创建小于等于今天的订单
     * 2：每日刷新（定时任务去做，此方法不做）缓存。并清理缓存数据
     * 3：数据库存一份，缓存中存一份
     * */
//    @RequestMapping(value = "/createProjectOrder", method = RequestMethod.POST)
    //todo 换成POST
    @RequestMapping(value = "/createProjectOrder", method = RequestMethod.GET)
    @ResponseBody
//    public Result createProjectOrder(@RequestBody ProjectCreateReq projectCreateReq){
     public Result createProjectOrder(){
        Result result = new Result();
        //参数
        ProjectCreateReq projectCreateReq=new ProjectCreateReq();
        projectCreateReq.setToken("f639681dfded0944eb486bbf08fe6891");
        projectCreateReq.setProjectTitle("工程标题");
        projectCreateReq.setProjectNeedWorker(5);
        projectCreateReq.setProjectNeedDay(7);
        String start  = "2019-03-26";
        String end="2019-03-28";
        projectCreateReq.setProjectStartTime(start);
        projectCreateReq.setProjectEndTime(end);
        projectCreateReq.setEveryDaySalary(300.00);
        projectCreateReq.setProjectTotalPay(10500.00);
        projectCreateReq.setProvinceId(2);
        projectCreateReq.setCityId(52);
        projectCreateReq.setCountyId(502);
        projectCreateReq.setCoordinate("12324,12221");
        projectCreateReq.setAddressDetail("新龙城小区1号楼2单元301室");
        projectCreateReq.setMobileNumber("13718699660");
        projectCreateReq.setDescribe("卫生间漏水，需要维修工人5个，7天内干完");
        try{
            if(projectCreateReq==null){
                throw new ParamException(ParamEnum.PARAM_DELIVERY_ERROR.getCode(),ParamEnum.PARAM_DELIVERY_ERROR.getDesc());
            }
            String token = projectCreateReq.getToken();
            tokenService.checkTokenExist(token);
            checkParam(projectCreateReq);
            Long projectOrderId= projectLeaderService.createProjectOrder(projectCreateReq);
            if(projectOrderId==null || projectOrderId<1){
                return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
            }
            return result.success(projectOrderId, ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("projectOrderLeader:订单入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("projectOrderLeader：创建工程订单失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }
    @RequestMapping(value = "/getProjectOrderListPage", method = RequestMethod.GET)
    @ResponseBody
    public Result getProjecOrderListPage(String token,Integer page,Integer pageSize){
        Result result = new Result();
        try{
            if(pageSize>50){
                throw new ParamException("每页最多查询不能超过50个");
            }
            UserVo userVo=tokenService.checkTokenExist(token);
            List<ProjectOrderVo> projectLeaderVoList= projectLeaderService.getProjectOrderListPage(userVo,page,pageSize);
            return result.success(projectLeaderVoList, ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("projectOrderLeader:每页最多查询不能超过50个",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("projectOrderLeader：获取所有的招工订单列表失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }

    /**
     * 获取不同状态的我的列表
     * 入参:0或1的情况下，取0和1的并集。并且按状态顺序排列
     * */
    @RequestMapping(value = "/getMyProjectOrderList", method = RequestMethod.GET)
    @ResponseBody
    public Result getMyProjectOrderList(String token,Integer projectStatus){
        Result result = new Result();
        try{
            UserVo userVo=tokenService.checkTokenExist(token);
            List<ProjectLeader> projectLeaderList=projectLeaderService.getMyProjectLeaderList(userVo,projectStatus);
            return result.success(projectLeaderList, ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("projectOrderLeader:获取我的招工订单入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("projectOrderLeader：获取我的订单列表失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }


    /**
     * 根据订单ID查询报工列表
     *
     * */
    @RequestMapping(value = "/getProjectLeaderWithListByOrderId", method = RequestMethod.GET)
    @ResponseBody
    public Result getProjectLeaderWithListByOrderId(String token,String orderId){
        Result result = new Result();
        try{
            if(StringUtils.isBlank(orderId)){
                throw new ParamException(ParamEnum.PARAM_ORDER_ID_ERROR.getCode(),ParamEnum.PARAM_ORDER_ID_ERROR.getDesc());
            }
            UserVo userVo=tokenService.checkTokenExist(token);
            ProjectOrderVo projectOrderVo=projectLeaderService.getProjectLeaderWithListByOrderId(userVo,orderId);
            return result.success(projectOrderVo, ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("projectOrderLeader:根据订单ID获取订单报工信息入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (Exception e){
            logger.error("projectOrderLeader：根据订单ID获取订单报工信息失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }



    private Boolean checkParam(ProjectCreateReq projectCreateReq){
        if(projectCreateReq==null){
            logger.error("projectOrderLeader:projectCreateReq is null");
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),"projectCreateReq is null");
        }
        if(StringUtils.isEmpty(projectCreateReq.getProjectTitle())){
            logger.error("projectOrderLeader:projectTitle is empty");
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),"projectTitle is empty");
        }
        if(projectCreateReq.getProjectNeedWorker()==null || projectCreateReq.getProjectNeedWorker()<=0){
            logger.error("projectOrderLeader:projecNeedWorker is error");
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),"projecNeedWorker is error");
        }
        if(projectCreateReq.getProjectNeedDay()==null || projectCreateReq.getProjectNeedDay()<=0){
            logger.error("projectOrderLeader:projectNeedDay is error");
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),"projectNeedDay is error");
        }
        if(projectCreateReq.getProjectStartTime()==null){
            logger.error("projectOrderLeader:projecStartTime is null");
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),"projecStartTime is null");
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date startTime=null;
        try {
            startTime=sdf.parse(projectCreateReq.getProjectStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date now=new Date();
        if(startTime.before(now)){
            logger.error("projectOrderLeader:projecStartTime is error");
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),"projecStartTime is error");
        }
        if(projectCreateReq.getProjectEndTime()==null){
            logger.error("projectOrderLeader:projectEnTime is null");
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),"projectEnTime is null");
        }
        if(projectCreateReq.getProvinceId()==null || projectCreateReq.getCityId()==null || projectCreateReq.getCountyId()==null){
            logger.error("projectOrderLeader:provinceId,cityId,countyId is null");
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),"provinceId,cityId,countyId is null");
        }
        if(StringUtils.isEmpty(projectCreateReq.getAddressDetail())){
            logger.error("projectOrderLeader:addressDetail is null");
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),"addressDetail is null");
        }
        if(projectCreateReq.getEveryDaySalary()==null){
            logger.error("projectOrderLeader:everyDaySalary is null");
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),"everyDaySalary is null");
        }
        if(projectCreateReq.getProjectTotalPay()==null){
            logger.error("projectOrderLeader:projectTotalPay is null");
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),"projectTotalPay is null");
        }
        if(StringUtils.isEmpty(projectCreateReq.getMobileNumber())){
            logger.error("projectOrderLeader:mobileNumber is null");
            throw new ParamException(ParamEnum.PARAM_ERROR.getCode(),"mobileNumber is null");
        }
        return true;
    }

    /**
     * 我的-招工-发布
     * 获取我的工单列表
     * 列表中每个工人必须校验工人在这期间是否有活
     * */
}
