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
 * 菜单
 * 
 * @author 
 * @date 2015-1-8上午10:10:38
 * @version 1.0
 */
@Entity(name = "T_MENU")
public class Menu implements Serializable{
	private static final long serialVersionUID = -6626977647959072701L;
	@Id
	/**id*/
	private Long id;
	/** 菜单中文名称 */
	@ApiModelProperty(value="菜单中文名称")
	private String menu_cname;

	/** 菜单英文名称 */
	@ApiModelProperty(value="菜单英文名称")
	private String menu_ename;

	/** 上级菜单id */
	@ApiModelProperty(value="上级菜单id")
	private Long parent_id;

	/** 级别 1 一级菜单；2 二级菜单 */
	@ApiModelProperty(value="级别 1 一级菜单；2 二级菜单")
	private String level;
	
	/** 图标 */
	@ApiModelProperty(value="图标")
	private String icon_cls;
	
	/** 链接地址 */
	@ApiModelProperty(value="链接地址")
	private String url;
	
	/** 排序 */
	@ApiModelProperty(value="排序")
	private Integer sort;
	
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

	public String getMenu_cname() {
		return menu_cname;
	}

	public void setMenu_cname(String menu_cname) {
		this.menu_cname = menu_cname;
	}

	public String getMenu_ename() {
		return menu_ename;
	}

	public void setMenu_ename(String menu_ename) {
		this.menu_ename = menu_ename;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getIcon_cls() {
		return icon_cls;
	}

	public void setIcon_cls(String icon_cls) {
		this.icon_cls = icon_cls;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	
	
	
}
