package com.xyhmo.controller.order.projectOrder;


import com.xyhmo.commom.base.Result;
import com.xyhmo.commom.enums.BusiessExceptionEnum;
import com.xyhmo.commom.enums.ReturnEnum;
import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.BusinessException;
import com.xyhmo.commom.exception.ParamException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.domain.ProjectLeader;
import com.xyhmo.domain.ProjectOrderVo;
import com.xyhmo.domain.ProjectWorker;
import com.xyhmo.service.TokenService;
import com.xyhmo.service.project.ProjectLeaderService;
import com.xyhmo.service.project.ProjectWorkerService;
import com.xyhmo.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 工程招工工人模块
 *
 * */
@Controller
@RequestMapping("/projectWorker")
public class ProjectWorkerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private ProjectWorkerService projectWorkerService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisService redisService;

    /**
     * 工人申报工单
     * 工人申报的时候，暂时提报的都是所有天数
     * 以后若有业务新需求，工作天数可选。
     * */
    @RequestMapping(value = "/applyProjectOrder", method = RequestMethod.GET)
    @ResponseBody
    //todo 业务需求：以后业务做起来后，优化，提报工单的时候，可以选择工作天数
    //todo 首先需要判断该干活工人有没有交叉中的订单
    public Result applyProjectOrder(String token,String leaderPin,String projectOrderId){
        Result result = new Result();
        try{
            if(StringUtils.isEmpty(token)){
                throw new ParamException("请先登录");
            }
            if(StringUtils.isEmpty(projectOrderId)){
                throw new ParamException("请选择工程订单");
            }
            if(StringUtils.isEmpty(leaderPin)){
                throw new ParamException("该工程已过期，请选择其他工程");
            }
            UserVo userVo=tokenService.checkTokenExist(token);
            //只获取状态1（招工结束），2（正在进行中的）时间状态，去校验
            List<ProjectWorker> projectWorkerList=projectWorkerService.getMyProjectWorkerListWroking(userVo.getPin());
            String redisOrderId=Contants.REDIS_ONE_PROJECTORDER+projectOrderId;
            ProjectOrderVo projectOrderVo =redisService.get(redisOrderId);
            if(projectOrderVo==null || StringUtils.isBlank(projectOrderVo.getProjectStartTime()) || StringUtils.isBlank(projectOrderVo.getProjectEndTime())){
                throw new BusinessException(BusiessExceptionEnum.PROJECT_ORDER_REDIS_GETBYPROJECTORDERID.getCode(),BusiessExceptionEnum.PROJECT_ORDER_REDIS_GETBYPROJECTORDERID.getDesc());
            }
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date projectStartDate=df.parse(projectOrderVo.getProjectStartTime());
            Date projectEndDate=df.parse(projectOrderVo.getProjectEndTime());

            if(!CollectionUtils.isEmpty(projectWorkerList)){
                for(ProjectWorker projectWorker:projectWorkerList){
                    if(projectWorker==null){
                        continue;
                    }

                    Date startDate=df.parse(projectWorker.getProjectStartTime());
                    Date endDate=df.parse(projectWorker.getProjectEndTime());
                    if(projectStartDate.compareTo(startDate)>0 && projectStartDate.compareTo(endDate)<0){
                        return result.fail("该工程的施工日期与"+projectWorker.getProjectTitle()+"工程冲突");
                    }
                    if(projectEndDate.compareTo(startDate)>0 && projectEndDate.compareTo(endDate)<0){
                        return result.fail("该工程的施工日期与"+projectWorker.getProjectTitle()+"工程冲突");
                    }
                }
            }

            Boolean flag=projectWorkerService.applyProjectOrder(userVo,leaderPin,projectOrderId);
            if(!flag){
                return result.fail(BusiessExceptionEnum.PROJECT_ORDER_WORKER_APPLYERROR.getCode(),BusiessExceptionEnum.PROJECT_ORDER_WORKER_APPLYERROR.getDesc());
            }
            return result.success(ReturnEnum.RETURN_SUCCESS.getDesc());
        }catch (ParamException p){
            logger.error("projectOrderWorker:订单入参错误",p);
            return result.fail(p.getCode(),p.getMessage());
        }catch (BusinessException b){
            logger.error("projectOrderWorker:申报工单失败",b);
            return result.fail(b.getCode(),b.getMessage());
        }catch (Exception e){
            logger.error("projectOrderWorker：申报工单失败",e);
            return result.fail(SystemEnum.SYSTEM_ERROR.getDesc());
        }
    }
}
