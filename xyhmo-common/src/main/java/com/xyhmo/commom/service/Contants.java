package com.xyhmo.commom.service;


public final class Contants {

	//生成手机验证码
    public static String MOBILE_GEN_CODE;
    //手机验证码过期时间
	public static int MOBILE_CHECK_CODE_OVER_TIME;
	//IP校验
	public static String CHECK_IP;
	//ip每日可调用的次数
	public static int CHECK_IP_TIMES;
	//ip限制时间
	public static int CHECK_IP_OVER_TIME;
	//手机号每日限制次数
	public static int CHECK_MOBILE_TIMES_EVERYDAY;
	//手机号限制时间
	public static int CHECK_MOBILE_OVER_TIME;
	//限制手机号的key
	public static String CHECK_MOBILE;
	//token系统无访问，token最大存活时间
	public static int TOKEN_OVER_TIME;
	//存入系统的token前缀
	public static String REDIS_TOKEKN_BEFORE;
	//缓存：首页轮播图key
	public static String REDIS_INDEX_BANNER;
	//缓存：首页快报key
	public static String REDIS_INDEX_BULLETIN;
	//缓存：所有商品
	public static String REDIS_ALL_WARE;
	//缓存：某个代理商商品
	public static String REDIS_WARE_PIN;
	
	static{
		MOBILE_GEN_CODE = "mobile_check_code_";
		MOBILE_CHECK_CODE_OVER_TIME = 10*60;
		CHECK_IP="check_ip_";
		CHECK_IP_TIMES=5;
		CHECK_IP_OVER_TIME = 60*60*24;
		CHECK_MOBILE_TIMES_EVERYDAY=5;
		CHECK_MOBILE_OVER_TIME =60*60*24;
		CHECK_MOBILE="check_mobile_";
		TOKEN_OVER_TIME = 60*60*24*30;
		REDIS_TOKEKN_BEFORE="redis_token_xyfs";
		REDIS_INDEX_BANNER="redis_index_banner";
		REDIS_INDEX_BULLETIN="redis_index_bulletin";
		REDIS_ALL_WARE="redis_all_ware";
		REDIS_WARE_PIN="redis_ware_pin_";
	}

	
	
	
}
