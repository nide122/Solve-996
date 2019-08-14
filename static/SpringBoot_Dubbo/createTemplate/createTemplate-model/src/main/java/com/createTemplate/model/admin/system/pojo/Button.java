/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.admin.system.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
/**
 * 按钮
 */
@Entity(name = "T_BUTTON")
public class Button implements Serializable{
	private static final long serialVersionUID = -6626977647959072701L;
	@Id
	/**id*/
	@ApiModelProperty(value="唯一标识")
	private Long id;
	
	/**菜单英文名称*/
	@ApiModelProperty(value="菜单英文名称")
	private String menu_ename;
	
	/**按钮英文名称*/
	@ApiModelProperty(value="按钮英文名称")
	private String button_ename;
	
	/**按钮中文名称*/
	@ApiModelProperty(value="按钮中文名称")
	private String button_cname;

	/**菜单id*/
	@ApiModelProperty(value="菜单id")
	private Long menu_id;

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

	public String getMenu_ename() {
		return menu_ename;
	}

	public void setMenu_ename(String menu_ename) {
		this.menu_ename = menu_ename;
	}

	public String getButton_ename() {
		return button_ename;
	}

	public void setButton_ename(String button_ename) {
		this.button_ename = button_ename;
	}

	public String getButton_cname() {
		return button_cname;
	}

	public void setButton_cname(String button_cname) {
		this.button_cname = button_cname;
	}

	public Long getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(Long menu_id) {
		this.menu_id = menu_id;
	}
	
  
	
}
