/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.admin.web.aop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.createTemplate.api.base.dubbo.service.UsersService;
import com.createTemplate.api.util.JSONParamUtils;
import com.createTemplate.model.admin.system.vo.UsersVo;
import com.createTemplate.model.auth.AuthToken;
import com.createTemplate.model.exception.BusinessException;

/**
 * 登录Aop
 * 
 * @author libiqi
 *
 */
@Aspect
@Component
public class AuthTokenAOPInterceptor {

	private Logger logger = Logger.getLogger(getClass());

	@Reference(version = "1.0.0",check=false)
	UsersService personService;

	private static final String authFieldName = "authToken";

	@Pointcut("@annotation( com.createTemplate.model.auth.AuthToken)")
	public void controllerAspect() {
	}

    @Before(value = "controllerAspect() && @annotation(authToken)", argNames = "authToken")
	public void before(final JoinPoint joinPoint, AuthToken authToken) throws Throwable {
		logger.info("=====前置通知开始=====");
		boolean isFound = false;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
		HttpServletResponse response =  attributes.getResponse();
		String authTokenStr = request.getParameter(authFieldName);
		if (StringUtils.isBlank(authTokenStr)) {
			printErrorMessage(resultMap, response);
		}
		UsersVo user = personService.getCurrentUser(authTokenStr);
		if (user == null) {
			printErrorMessage(resultMap, response);
		}
		if (StringUtils.isNotBlank(authTokenStr))
			isFound = true;
		if (!isFound) {
			printErrorMessage(resultMap, response);
		}
		logger.info("=====前置通知结束=====");
	}

	private void printErrorMessage(Map<String, Object> resultMap, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		// springboot 这里 只能用outputStream . 因为在前面已经使用了outputStrem
		ServletOutputStream outputStream = response.getOutputStream();
		String msg = "用户token令牌失效！";
		resultMap.put("success", 2);
		resultMap.put("message", msg);
		String jsonString = new JSONObject(resultMap).toJSONString();
		outputStream.write(jsonString.getBytes("UTF-8"));
		outputStream.close();
		throw new BusinessException(msg);
	}
}
