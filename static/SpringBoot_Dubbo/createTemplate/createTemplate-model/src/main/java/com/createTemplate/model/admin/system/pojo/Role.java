/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.admin.system.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.createTemplate.model.annotation.AntBean;

import io.swagger.annotations.ApiModelProperty;
/**
 * 角色
 * @date  2015-1-8上午10:10:38
 * @version 1.0
 */
@SuppressWarnings("serial")
@AntBean(name="角色表",desc = "角色表",author="xj")
@Entity(name = "T_ROLE")
public class Role implements Serializable{
//	private static final long serialVersionUID = -6626977647959072701L;
	@Id
	/**id*/
	private Long id;
	/**角色名称*/
	@ApiModelProperty(value="角色名称")
	private String role_name;

	/**描述*/
	@ApiModelProperty(value="描述")
	private String remark;
	
	@ApiModelProperty(value="状态 0 正常；1 禁用")
	private Integer status;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
