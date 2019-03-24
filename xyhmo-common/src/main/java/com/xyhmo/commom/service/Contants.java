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
	//购物车过期时间
	public static int PURCHASE_CAR_OVER_TIME;
	//缓存：存入系统的token前缀
	public static String REDIS_TOKEKN_BEFORE;
	//缓存：首页轮播图key
	public static String REDIS_INDEX_BANNER;
	//缓存：首页快报key
	public static String REDIS_INDEX_BULLETIN;
	//缓存：所有商品
	public static String REDIS_ALL_WARE;
	//缓存：某个代理商商品
	public static String REDIS_WARE_PIN;
	//缓存：购物车
	public static String REDIS_PURCHASE_CAR;
	//缓存：单个商品缓存(会把所有的商品向缓存中存一份)
	public static String REDIS_WARE_SKUID;
	//缓存：缓存代理商的商品列表(未结单)一直存在
	public static String REDIS_ORDERPROXY_WEIJIEDAN_PIN;
	//缓存：缓存代理商的商品列表(未支付)一直存在
	public static String REDIS_ORDERPROXY_WEIZHIFU_PIN;
	//缓存：缓存代理商的商品列表(已支付)一直存在
	public static String REDIS_ORDERPROXY_YIZHIFU_PIN;
	//缓存：缓存业务员的商品列表(未结单)存3天
	public static String REDIS_ORDERWORKER_WEIJIEDAN_PIN;
	//缓存：缓存业务员的商品列表(未支付)存3天
	public static String REDIS_ORDERWORKER_WEIZHIFU_PIN;
	//缓存：缓存业务员的商品列表(已支付)存3天
	public static String REDIS_ORDERWORKER_YIZHIFU_PIN;
	//非缓存：业务员商品列表的缓存过期时间
	public static int ORDERWORKER_CACHE_OVER_TIME;
	//缓存：地址表的缓存
	public static String REDIS_ADDRESS;
	//缓存：(北京)招工订单（大缓存）
	public static String REDIS_PROJECTORDERLIST_BJ;
	//缓存：一个招工订单
	public static String REDIS_ONE_PROJECTORDER;
	static{
		MOBILE_GEN_CODE = "mobile_check_code_";
		MOBILE_CHECK_CODE_OVER_TIME = 10*60;
		CHECK_IP="check_ip_";
		CHECK_IP_TIMES=5;
		CHECK_IP_OVER_TIME = 60*60*24;
		PURCHASE_CAR_OVER_TIME=60*60*24;
		CHECK_MOBILE_TIMES_EVERYDAY=5;
		CHECK_MOBILE_OVER_TIME =60*60*24;
		CHECK_MOBILE="check_mobile_";
		TOKEN_OVER_TIME = 60*60*24*30;
		REDIS_TOKEKN_BEFORE="redis_token_xyfs";
		REDIS_INDEX_BANNER="redis_index_banner";
		REDIS_INDEX_BULLETIN="redis_index_bulletin";
		REDIS_ALL_WARE="redis_all_ware";
		REDIS_WARE_PIN="redis_ware_pin_";
		REDIS_PURCHASE_CAR="redis_purchase_car_";
		REDIS_WARE_SKUID="redis_ware_skuId_";
		REDIS_ORDERPROXY_WEIJIEDAN_PIN="redis_orderproxy_weijiedan_pin_";
		REDIS_ORDERPROXY_WEIZHIFU_PIN="redis_orderproxy_weizhifu_pin_";
		REDIS_ORDERPROXY_YIZHIFU_PIN="redis_orderproxy_yizhifu_pin_";
		REDIS_ORDERWORKER_WEIJIEDAN_PIN="redis_orderworker_weijiedan_pin_";
		REDIS_ORDERWORKER_WEIZHIFU_PIN="redis_orderworker_weizhifu_pin_";
		REDIS_ORDERWORKER_YIZHIFU_PIN="redis_orderworker_yizhifu_pin_";
		ORDERWORKER_CACHE_OVER_TIME=60*60*24*3;
		REDIS_ADDRESS="redis_address";
		REDIS_PROJECTORDERLIST_BJ="redis_projectOrderList_bj";
		REDIS_ONE_PROJECTORDER="redis_projectOrder_one_";
	}

	
	
	
}
