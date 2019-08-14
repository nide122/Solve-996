/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.admin.system.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
/**
 * 按钮
 */
@Entity(name = "T_USERS_ROLE")
public class UserRole implements java.io.Serializable{
	private static final long serialVersionUID = -6626977647959072701L;
	@Id
	/**id*/
	@ApiModelProperty(value="唯一标识")
	private Long id;
	
	/**用户id*/
	@ApiModelProperty(value="用户id")
	private Long cust_id;
	
	/**用户名*/
	@ApiModelProperty(value="用户名")
	private String user_name;
	
	/**角色id*/
	@ApiModelProperty(value="角色id")
	private Long role_id;
	
	/**状态*/
	@ApiModelProperty(value="状态")
	private Integer status;
	
	/**创建时间*/
	@ApiModelProperty(value="创建时间")
	private Date input_date;
	
	/**修改时间*/
	@ApiModelProperty(value="修改时间")
	private Date update_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCust_id() {
		return cust_id;
	}

	public void setCust_id(Long cust_id) {
		this.cust_id = cust_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getInput_date() {
		return input_date;
	}

	public void setInput_date(Date input_date) {
		this.input_date = input_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	
	
  
	
}
