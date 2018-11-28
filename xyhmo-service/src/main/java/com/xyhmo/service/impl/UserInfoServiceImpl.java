package com.xyhmo.service.impl;

import com.xyhmo.commom.enums.ParamEnum;
import com.xyhmo.commom.service.Contants;
import com.xyhmo.commom.service.RedisService;
import com.xyhmo.commom.utils.ParamCheckUtil;
import com.xyhmo.commom.utils.UniqueUtil;
import com.xyhmo.dao.UserInfoDao;
import com.xyhmo.domain.UserAuthInfo;
import com.xyhmo.domain.UserInfo;
import com.xyhmo.service.TokenService;
import com.xyhmo.service.UserAuthInfoService;
import com.xyhmo.service.UserInfoService;
import com.xyhmo.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserInfoServiceImpl implements UserInfoService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserAuthInfoService  userAuthInfoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisService redisService;

    @Override
    public Long save(String mobile) {
        logger.info("UserInfoServiceImpl save:mobile="+mobile);
        ParamCheckUtil.checkMobileNumber(mobile);
        UserInfo userInfo = new UserInfo();
        userInfo.setMobileNumber(mobile);
        userInfo.setPin(UniqueUtil.genPin(mobile));
        userInfo.setStatus(ParamEnum.PARAM_DATA_USED.getCode());
        return userInfoDao.insert(userInfo);
    }

    @Override
    public UserInfo autuwaredUserInfo(String mobile) {
        return null;
    }

    @Override
    public UserInfo getUserInfoByMobile(String mobile) {
        return userInfoDao.selectUserInfoByMobile(mobile);
    }

    @Override
    public void EditUserInfo(UserVo vo) {
        if(vo==null || StringUtils.isEmpty(vo.getPin())){
            return ;
        }
        UserInfo userInfo = userInfoDao.selectUserInfoByPin(vo.getPin());
        if(userInfo==null){
            return ;
        }
        userInfo.setUserType(vo.getUserType());
        userInfoDao.updateUserInfo(userInfo);
    }

    @Override
    public UserVo autowaredToVo(UserInfo userInfo, UserAuthInfo userAuthInfo) {
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


    @Override
    public UserInfo getUserInfoByPin(String pin) {
        return userInfoDao.selectUserInfoByPin(pin);
    }

    @Override
    public UserVo getUserVoByToken(String token) {
        UserVo vo = tokenService.checkTokenExist(token);
        UserInfo userInfo = getUserInfoByPin(vo.getPin());
        UserAuthInfo userAuthInfo = userAuthInfoService.getUserAuthInfoByPin(vo.getPin());
        UserVo userVo = autowaredToVo(userInfo,userAuthInfo);
        redisService.set(Contants.REDIS_TOKEKN_BEFORE+token,userVo,Contants.TOKEN_OVER_TIME);
        return userVo;
    }
}
