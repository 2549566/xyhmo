package com.xyhmo.service.impl;

import com.xyhmo.commom.enums.SystemEnum;
import com.xyhmo.commom.exception.SystemException;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.ParamCheckUtil;
import com.xyhmo.domain.UserAuthInfo;
import com.xyhmo.domain.UserInfo;
import com.xyhmo.service.LoginService;
import com.xyhmo.service.TokenService;
import com.xyhmo.service.UserAuthInfoService;
import com.xyhmo.service.UserInfoService;
import com.xyhmo.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserAuthInfoService userAuthInfoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TokenService tokenService;


    @Override
    public Boolean register(String mobile,String token){
        Long id=userInfoService.save(mobile);
        if(id<=0){
            logger.error("LoginServiceImpl register error");
            return false;
        }
        redisService.set(Contants.REDIS_TOKEKN_BEFORE+token,mobile,Contants.TOKEN_OVER_TIME);
        return id>0;
    }

    @Override
    public UserVo login(String token) {
        return null;
    }

    @Override
    public String genCheckCode(String mobile) {
        checkMobileTimes(mobile);
        try{
            Integer code =(int)((Math.random()*9+1)*1000);
            redisService.set(Contants.MOBILE_GEN_CODE+mobile,code.toString(),Contants.MOBILE_CHECK_CODE_OVER_TIME);
            return code.toString();
        }catch (SystemException s){
            logger.error("LoginServiceImpl:生成手机验证码失败");
            throw new SystemException(SystemEnum.SYSTEM_GEN_CODE_ERROR.getCode(),SystemEnum.SYSTEM_GEN_CODE_ERROR.getDesc());
        }

    }

    @Override
    public boolean checkIp(String ip) {
        ParamCheckUtil.checkIp(ip);
        if(null==redisService.get(Contants.CHECK_IP+ip)){
            redisService.set(Contants.CHECK_IP+ip,1,Contants.CHECK_IP_OVER_TIME);
            return true;
        }
        if(Integer.parseInt(redisService.get(Contants.CHECK_IP+ip).toString())<Contants.CHECK_IP_TIMES){
            redisService.set(Contants.CHECK_IP+ip,Integer.parseInt(redisService.get(Contants.CHECK_IP+ip).toString())+1,Contants.CHECK_IP_OVER_TIME);
            return true;
        }
        throw new SystemException(SystemEnum.SYSTEM_IP_SAFE.getCode(),SystemEnum.SYSTEM_IP_SAFE.getDesc());
    }

    @Override
    public UserVo getUserInfo(String mobile) {
        ParamCheckUtil.checkMobileNumber(mobile);
        UserInfo userInfo = userInfoService.getUserInfoByMobile(mobile);
        if(null==userInfo){
            return null;
        }
        UserAuthInfo userAuthInfo = userAuthInfoService.getUserAuthInfoByPin(userInfo.getPin());
        return autowaredToVo(userInfo,userAuthInfo);
    }

    @Override
    public String saveUserVoToRedis(UserVo vo) {
        String token = tokenService.genToken(vo.getMobileNumber());
        redisService.set(Contants.REDIS_TOKEKN_BEFORE+token,vo,Contants.TOKEN_OVER_TIME);
        return token;
    }

    private UserVo autowaredToVo(UserInfo userInfo,UserAuthInfo userAuthInfo){
        if(userInfo==null){
            return null;
        }
        UserVo vo = new UserVo();
        vo.setId(userInfo.getId());
        vo.setPin(userInfo.getPin());
        vo.setToken(userInfo.getToken());
        vo.setMobileNumber(userInfo.getMobileNumber());
        vo.setUserName(userInfo.getUserName());
        vo.setUserType(userInfo.getUserType());
        vo.setPassword(userInfo.getPassword());
        vo.setBindVender(userInfo.getBindVender());
        vo.setBindVenderProxy(userInfo.getBindVenderProxy());
        vo.setIsAuth(userInfo.getIsAuth());
        vo.setImageHearder(userInfo.getImageHearder());
        vo.setCreated(userInfo.getCreated());
        vo.setModified(userInfo.getModified());
        vo.setScore(userInfo.getScore());
        vo.setIsAcceptOrder(userInfo.getIsAcceptOrder());
        if(null==userAuthInfo){
            return vo;
        }
        vo.setRealName(userAuthInfo.getRealName());
        vo.setImageCardFace(userAuthInfo.getImageCardFace());
        vo.setImageCardBack(userAuthInfo.getImageCardBack());
        vo.setImageCompanyQualiy(userAuthInfo.getImageCompanyQualiy());
        vo.setCompanyName(userAuthInfo.getCompanyName());
        vo.setAuthCreated(userAuthInfo.getCreated());
        vo.setAuthModified(userAuthInfo.getModified());
        return vo;
    }

    public boolean checkMobileTimes(String mobile){
        ParamCheckUtil.checkMobileNumber(mobile);
        if(null==redisService.get(Contants.CHECK_MOBILE+mobile)){
            redisService.set(Contants.CHECK_MOBILE+mobile,1,Contants.CHECK_MOBILE_OVER_TIME);
            return true;
        }
        if(Integer.parseInt(redisService.get(Contants.CHECK_MOBILE+mobile).toString())<Contants.CHECK_MOBILE_TIMES_EVERYDAY){
            redisService.set(Contants.CHECK_MOBILE+mobile,Integer.parseInt(redisService.get(Contants.CHECK_MOBILE+mobile).toString())+1,Contants.CHECK_IP_OVER_TIME);
            return true;
        }
        throw new SystemException(SystemEnum.SYSTEM_MOBILE_SAFE.getCode(),SystemEnum.SYSTEM_MOBILE_SAFE.getDesc());
    }
}
