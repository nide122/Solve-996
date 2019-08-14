/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.resultvo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@ApiModel(value="返回响应数据")
public class ResultInfo <T>  implements Serializable {
    /**正常返回*/
	public static final Integer SUCCESS = 1;
	 /**业务异常*/
    public static final Integer BUSSINESSEXCEPTION = 0;
    /**系统异常*/
    public static final Integer ERROR = -1;
	
    /**返回码 1 正常  0 业务异常 -1 系统异常*/
    @ApiModelProperty(value = "返回码")
    private Integer success;
    /**返回消息*/
    @ApiModelProperty(value = "返回消息")
    private String message;
    /**url请求路径*/
    @ApiModelProperty(value = "url请求路径")
    private String url;
    /**返回消息体*/
    @ApiModelProperty(value = "返回数据对象")
    private T object;
    
	
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    public ResultInfo() {
        super();
    }
    public ResultInfo(Integer success, String message, T object) {
		super();
		this.success = success;
		this.message = message;
		this.object = object;
	}
	
    /**
     * @return the success
     */
    public Integer getSuccess() {
        return success;
    }

    
    /**
     * @param success the success to set
     */
    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
}
