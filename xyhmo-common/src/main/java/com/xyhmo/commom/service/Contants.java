package com.xyhmo.commom.service;


public final class Contants {

    public static String MOBILE_GEN_CODE;
	public static int MOBILE_CHECK_CODE_OVER_TIME;
	public static String CHECK_IP;
	public static int CHECK_IP_TIMES;
	public static int CHECK_IP_OVER_TIME;
	public static int CHECK_MOBILE_TIMES_EVERYDAY;
	public static int CHECK_MOBILE_OVER_TIME;
	public static String CHECK_MOBILE;
	
	static{
		MOBILE_GEN_CODE = "mobile_check_code_";
		MOBILE_CHECK_CODE_OVER_TIME = 10*60;
		CHECK_IP="check_ip_";
		CHECK_IP_TIMES=5;
		CHECK_IP_OVER_TIME = 60*60*24;
		CHECK_MOBILE_TIMES_EVERYDAY=5;
		CHECK_MOBILE_OVER_TIME =60*60*24;
		CHECK_MOBILE="check_mobile_";
	}

	
	
	
}
