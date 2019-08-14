/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.personal.web.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.createTemplate.api.base.dubbo.service.CommonRedisDao;
import com.createTemplate.api.base.mongodb.dao.SystemLogDao;
import com.createTemplate.api.core.doubbo.service.PersonService;
import com.createTemplate.api.util.IPUtil;
import com.createTemplate.api.util.JSONParamUtils;
import com.createTemplate.api.util.StringUtil;
import com.createTemplate.model.annotation.SystemControllerLog;
import com.createTemplate.model.annotation.SystemServiceLog;
import com.createTemplate.model.base.mongo.pojo.SystemLog;
import com.createTemplate.model.core.vo.TPersonVO;
import com.createTemplate.model.exception.BusinessException;

// @Aspect
// @Component
public class SystemLogAspect {

	private static final String ipCountpriFix = "ipCountpriFix:";
	private static final String saveOrderIpCountpriFix = "saveOrderIpCountpriFix:";
	
	@Reference(version="1.0.0",check=false)
    private SystemLogDao systemLogDao;

	@Reference(version="1.0.0",check=false)
	private CommonRedisDao redisDao;
	@Reference(version="1.0.0",check=false)
	private PersonService personService;

	// Service层切点
	@Pointcut("@annotation(com.createTemplate.model.annotation.SystemServiceLog)")
	public void serviceAspect() {
	}

	// Controller层切点
	@Pointcut("@annotation(com.createTemplate.model.annotation.SystemControllerLog)")
	public void controllerAspect() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) throws Throwable{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		JSONObject param =  JSONParamUtils.getJSONParam(request);
        String authToken = param.getString("authToken");
		TPersonVO person = null;
		if (StringUtils.isNotBlank(authToken)) {// TODO 没有登录，不统计访问次数
			//读取session中的用户
			person = personService.getCurrentUser(authToken);
			if(null != person){
			    System.out.println("请求人:" + person.getPhone_num());
			}
		 }
		// 请求的IP
		String ip = IPUtil.getIpAddr(request);
		System.out.println("请求IP:" + ip);
		// *========控制台输出=========*//
		System.out.println("请求方法:"
				+ (joinPoint.getTarget().getClass().getName() + "."
						+ joinPoint.getSignature().getName() + "()"));
		try {
			System.out.println("方法描述:"
					+ getControllerMethodDescription(joinPoint));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String redisKey = ipCountpriFix +joinPoint.getTarget().getClass().getName() +":"+joinPoint.getSignature().getName()+":"+ ip;
		String timesStr = redisDao.get(redisKey);
		if (StringUtil.checkNotNull(timesStr)) {
			int times = Integer.valueOf(timesStr).intValue();
			String maxTimes = "100";
			if (StringUtil.checkNull(timesStr)) {
				maxTimes = "3000";// 默认3000次
			}
			
			if (times == Integer.valueOf(maxTimes).intValue()) {
				//  保存或更新到mongodb中
				SystemLog log = new SystemLog();
				try {
					log.setDescription(getControllerMethodDescription(joinPoint));
				} catch (Exception e) {
					e.printStackTrace();
				}
				log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
				log.setType("0");
				log.setRequestIp(ip);
				log.setExceptionCode(null);
				log.setExceptionDetail(null);
				log.setParams(null);
				log.setInputDate(new Date());
//				log.setTimes(times);
				// 保存到mongodb数据库
				systemLogDao.save(log);
			} else {
				// 24小时内访问次数超过100次，
				redisDao.set(redisKey, (times + 1) + "", 60 * 24);
			}
			

		} else {
			// 初始化1次
			redisDao.set(redisKey, "1", 60 * 24);
		}
		
	}

	/**
	 * 异常通知 用于拦截service层记录异常日志
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		if( e  instanceof BusinessException ){
			return;//业务异常不报错
		}
		
		// 读取session中的用户
		JSONObject param =  JSONParamUtils.getJSONParam(request);
        String authToken = param.getString("authToken");
//		PersonVo person = null;
//		if (StringUtils.isNotBlank(authToken)) {// TODO 没有登录，不统计访问次数
//			// 读取session中的用户
//			person = personService.getCurrentUser(authToken);
//		 }
		// 获取请求ip
		String ip = request.getRemoteAddr();
		// 获取用户请求方法的参数并序列化为JSON格式字符串
		String params = "";
		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
			for (int i = 0; i < joinPoint.getArgs().length; i++) {
				params += JSON.toJSONString(joinPoint.getArgs()[i]) + ";";
			}
		}
		
		SystemLog log = new SystemLog();
		try {
			log.setDescription(getServiceMthodDescription(joinPoint));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		log.setExceptionCode(e.getClass().getName());
		log.setType("1");
		log.setExceptionDetail(e.getMessage());
		StackTraceElement stackTraceElement =  e.getStackTrace()[0];
		log.setMethod((joinPoint.getTarget().getClass().getName() + "."+ joinPoint.getSignature().getName() + "("+stackTraceElement.getFileName()+":"+stackTraceElement.getLineNumber()+")"));
		log.setParams(params);
//		log.setCreateBy(person);
		log.setInputDate(new Date());
		log.setRequestIp(ip);
		// 保存数据库Impl logDaoImpl = new LogDaoImpl();

	}

	/**
	 * 获取注解中对方法的描述信息 用于service层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getServiceMthodDescription(JoinPoint joinPoint)
			throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemServiceLog.class)
							.description();
					break;
				}
			}
		}
		return description;
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getControllerMethodDescription(JoinPoint joinPoint)
			throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(
							SystemControllerLog.class).description();
					break;
				}
			}
		}
		return description;
	}
}
