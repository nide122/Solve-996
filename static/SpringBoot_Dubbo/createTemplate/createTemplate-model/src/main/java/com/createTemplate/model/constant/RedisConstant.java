/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.constant;


public class RedisConstant {
	
	/** 缓存 personToken */
	public static final String personToken = "createTemplate_personToken:";
	/** 根据personId缓存 person信息 */
	public static final String personIdFix = "createTemplate_personFix:";
	/** 缓存 用户 token 关系 */
	public static final String personSeed = "createTemplate_personSeed:";
	
	/** 缓存 userToken */
    public static final String userToken = "createTemplate_userToken:";
    
    /** 根据id缓存 userToken信息 */
    public static final String userIdFix = "createTemplate_userFix:";
    
    /** 缓存 用户 token 关系 */
    public static final String userSeed = "createTemplate_userSeed:";
    
	/** 缓存 captchaToken 用于验证一分钟内频繁操作 */
	public static final String captchaToken = "createTemplate_captchaToken:";
	
    /** 缓存 captcha 用于验证短信验证码是否正确*/
    public static final String captcha = "createTemplate_captcha:";
	
	/** 省份数据前缀 */
	public static final String provinceFix = "createTemplate_province";

	/** 省代码到名称前缀 */
	public static final String provinceByCodeFix = "createTemplate_ProvinceByCode_";

	/** 市数据前缀 */
	public static final String cityFix = "createTemplate_city:";
	/** 省份下的市数据前缀 */
	public static final String provinceCityFix = "createTemplate_provinceCityFix:";

	/** 市数据前缀 */
	public static final String cityAllFix = "createTemplate_cityall_";

	/** 市代码到名称前缀 */
	public static final String cityByCodeFix = "createTemplate_cityByCode_";
	/** 热门城市列表前缀 */
	public static final String hotCityFix = "createTemplate_hotCity_";
	/** 已开通城市列表前缀 */
	public static final String dredgeCityFix = "createTemplate_dredgeCity_";
	/** 数据字典前缀 */
	public static final String enumFix = "createTemplate_enum_";

	/** 数据字典按code获取的前缀 */
	public static final String enumByCodeFix = "createTemplate_enumByCode_";

	/** 工作所在地前缀 */
	public static final String workPlaceConfigFix = "createTemplate_workPlaceConfig_";

	/** 根据地区代码获取名称前缀 */
	public static final String workPlaceConfigByCodeFix = "createTemplate_workPlaceConfigByCodeFix_";

	/** 职位类型配置前缀 */
	public static final String positionTypeConfigFix = "createTemplate_positionTypeConfig_";
	
	/** 职位类型配置根据id查询前缀 */
	public static final String positionTypeConfigByIdFix = "createTemplate_positionTypeConfigById_";

	/** 职位类型配置首先展示前缀 */
	public static final String positionTypeConfigShowFix = "createTemplate_positionTypeConfigShowFix_";
	

	/** 职位标签配置前缀 */
	public static final String positionLabelFix = "createTemplate_positionLabelFix_";
	/**热门职位标签前缀 */
	public static final String positionLabelHotFix = "createTemplate_positionLabelHotFix_";
	

	/** 数据字典所有key，用逗号分隔 */
	public static final String enumAllKeyString = "createTemplate_enum_enumAllKeyString";

	/** 系统配置前缀 */
	public static final String systemConfigFix = "createTemplate_systemConfigFix_";

	/** 系统配置按钮id查询前缀 */
	public static final String systemConfigByIdFix = "createTemplate_systemConfigByIdFix_";

	/** 数据字典按id获取的前缀 */
	public static final String enumByIdFix = "createTemplate_enumById:";
	/** 数据字典g前缀 */
	public static final String enumByUpperIdFix = "createTemplate_enumByUpperId:";
	
	/** 省数据列表前缀 */
	public static final String provinceAllFix = "createTemplate_provinceAll_";


	/** 区县数据前缀 */
	public static final String areasFix = "createTemplate_areas";

	/** 区县数据列表前缀 */
	public static final String areasAllFix = "createTemplate_areasAll_";

	/** 区县代码到名称前缀 */
	public static final String areasByCodeFix = "createTemplate_areasByCode:";
	/**热门公司列表*/
	public static final String hotCompanyListFix ="createTemplate_recruitDealResumeFix:";
	/**推荐公司列表*/
	public static final String recommendCompanyFix ="createTemplate_recommendCompany_";
	
	/**招聘人未处理简历数*/
	public static final String recruitUndealResumeCountFix = "createTemplate_recruitUndealResumeCountFix_";
	/**招聘人未查看简历数*/
	public static final String recruitRecruitUnseeResumeCountFix = "createTemplate_recruitRecruitUnseeResumeCountFix_";
	/**公司简历及时处理率、简历处理平均用时 更新*/
	public static final String recruitDealResumeFix = "createTemplate_recruitDealResumeFix_";
	/**应聘人未查看简历数*/
	public static final String recruitUnseeResumeCountFix = "createTemplate_recruitUnseeResumeCountFix_";
	/**任务信息英文*/
	public static final String TASKINFOBYENAME = "createTemplate_taskInfoByEName_";
	/**任务列表*/
	public static final String TASKINFOBLIST = "createTemplate_taskInfoList_";
	 /** 缓存  accessToken*/
    public static final String accessToken="createTemplate_accessToken_";
    /** 缓存  jsapi_Ticket*/
    public static final String jsapi_Ticket = "createTemplate_jsapi_Ticket_";
}
