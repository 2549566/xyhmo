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
	//token系统无访问，token最大存货时间
	public static int TOKEN_OVER_TIME;
	//存入系统的token前缀
	public static String TOKEN_ADD_BEFORE_STRING;
	
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
		TOKEN_ADD_BEFORE_STRING="token_xyfs";
	}

	
	
	
}
