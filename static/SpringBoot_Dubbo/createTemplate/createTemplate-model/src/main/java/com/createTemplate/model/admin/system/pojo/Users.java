/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.admin.system.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 用户
 * @version 1.0
 */
@Entity(name = "T_USER")
public class Users implements java.io.Serializable{
	@Id
	/**id*/
	private Long id;
	/**用户名*/
	private String userName;
	
	/**姓名*/
	private String realName;
	
	/**用户密码*/
	private String userPassword;
	
	/**创建时间*/
	private Date inputDate;
	
	/**修改时间*/
	private Date updateDate;
	
	/**0 正常 ； 1 禁用*/
	private Integer status;

	/**角色id*/
	private Long roleId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
