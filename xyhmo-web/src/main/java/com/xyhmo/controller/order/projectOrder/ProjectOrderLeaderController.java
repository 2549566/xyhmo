package com.xyhmo.controller.order.projectOrder;

import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.DeliveryEnum;
import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.query.project.ProjectCreateReq;
import com.xyhmo.service.TokenService;
import com.xyhmo.service.project.ProjectLeaderService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/projectOrderLeader")
public class ProjectOrderLeaderController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private TokenService tokenService;
    @Autowired
    private ProjectLeaderService projectLeaderService;

    @RequestMapping(value = "/createProjectOrder", method = RequestMethod.POST)
    @ResponseBody
    public Result createProjectOrder(@RequestBody ProjectCreateReq projectCreateReq){
        Result result = new Result();
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
            logger.error("projectOrderLeader：保存工程订单失败",e);
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
}
