/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.admin.system.vo;

import java.util.Set;

import com.createTemplate.model.admin.system.pojo.Role;
import com.createTemplate.model.annotation.AntBean;
import com.createTemplate.model.annotation.AntField;
import com.createTemplate.model.mybatis.page.PageParameter;

import io.swagger.annotations.ApiModelProperty;



/**
 * 角色vo
 * @version 1.0
 */

@AntBean(name="角色vo",desc = "角色vo",author="xf")
public class RoleVo extends Role implements java.io.Serializable{


	@AntField(name="列",desc="列",required=false)
	private String column;
	
	@AntField(name="当前页",desc="当前页",required=false)
	private Integer page;
	
	@AntField(name="每页的条数",desc="每页的条数",required=false)
	private Integer rows;
	
	/**排序字段*/
	private String order;

	@AntField(name="ids",desc="ids 多个逗号分隔",required=false)
	private String ids;
	
	/** 分页参数 */
	@ApiModelProperty(name="分页参数 ",hidden=true)
	private PageParameter pageParameter;
	
	/**菜单id*/
	@ApiModelProperty(value="菜单id")
	private String menuIds;

	@AntField(name="角色ids",desc="角色ids，多个逗号分隔",required=false)
    private Set<Long> roleIds;
	
    /**
     * @return the roleIds
     */
    public Set<Long> getRoleIds() {
        return roleIds;
    }

    
    /**
     * @param roleIds the roleIds to set
     */
    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	
    public PageParameter getPageParameter() {
        return pageParameter;
    }

    public void setPageParameter(PageParameter pageParameter) {
        this.pageParameter = pageParameter;
    }
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}
	
	
}
