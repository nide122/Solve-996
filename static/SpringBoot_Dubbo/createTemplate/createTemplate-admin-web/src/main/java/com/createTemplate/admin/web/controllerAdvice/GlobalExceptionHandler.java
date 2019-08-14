/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.admin.web.controllerAdvice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.createTemplate.model.exception.BusinessException;
import com.createTemplate.model.resultvo.ResultInfo;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = BusinessException.class)
	@ResponseBody
	public ResultInfo<String> jsonErrorHandler(HttpServletRequest req, BusinessException e) throws Exception {
	    ResultInfo<String> r = new ResultInfo<String>();
		r.setSuccess(e.getCode()==null?ResultInfo.BUSSINESSEXCEPTION:e.getCode());
		r.setMessage(e.getMessage());
		return r;
	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResultInfo<String> jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		e.printStackTrace();
		ResultInfo<String> r = new ResultInfo<>();
		r.setSuccess(ResultInfo.ERROR);
		r.setMessage("系统太忙，请稍后重试。");
		//r.setObject("Some Data");
		r.setUrl(req.getRequestURL().toString());
		return r;
	}
	
}