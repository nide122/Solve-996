/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.base.mongo.pojo;

import java.io.Serializable;
import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import com.createTemplate.model.annotation.AntBean;
import com.createTemplate.model.annotation.AntField;

/**
 * 
 * 日志详情
 * @author:  
 * @date: 2018年5月28日 下午4:44:16 
 * @version V1.0
 */
@AntBean(name="日志详情",desc = "日志详情",author="xf")
@Entity(value = "systemLog", noClassnameStored = true)
@EnableMongoAuditing
public class SystemLog extends IdEntity implements Serializable{
    public SystemLog(String description, String method, String params) {
        super();
        this.method = method;
        this.description = description;
        this.params = params;
    }
    
    public SystemLog() {
       super();
    }

	@AntField(name="方法描述",desc="方法描述",required=true)
	private  String description;
	
	@AntField(name="方法",desc="方法",required=true)
	private String  method;
	
	/** 参数*/
	@AntField(name=" 参数",desc=" 参数",required=true)
	private String params;
	/**异常代码 */
	@AntField(name="异常代码",desc="异常代码",required=true)
	private String exceptionCode;
	/**异常详情 */
	@AntField(name="异常详情",desc="异常详情",required=true)
	private String exceptionDetail;
	/** 请求IP:*/
	@AntField(name="请求IP",desc="请求IP",required=true)
	private String requestIp;
	
	/**插入时间*/
	private Date inputDate;
	/**访问次数*/
	private Integer times;
	
	@AntField(name="日志类型",desc="日志类型 0  访问日志  1 异常日志",required=true)
	private String  type;

	
    /**
     * @return the times
     */
    public Integer getTimes() {
        return times;
    }

    
    /**
     * @param times the times to set
     */
    public void setTimes(Integer times) {
        this.times = times;
    }

    public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionDetail() {
		return exceptionDetail;
	}

	public void setExceptionDetail(String exceptionDetail) {
		this.exceptionDetail = exceptionDetail;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    /**  
     * @return 
     * @see java.lang.Object#toString() 
     */
    @Override
    public String toString() {
        return "Log [description=" + description + ", method=" + method + ", params=" + params + ", exceptionCode="
                + exceptionCode + ", exceptionDetail=" + exceptionDetail + ", requestIp=" + requestIp + ", inputDate="
                + inputDate + ", type=" + type + "]";
    }
}
