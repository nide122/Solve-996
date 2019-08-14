/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.admin.system.vo;

import com.createTemplate.model.admin.system.pojo.Menu;
import com.createTemplate.model.annotation.AntBean;
import com.createTemplate.model.annotation.AntField;
import com.createTemplate.model.mybatis.page.PageParameter;

import io.swagger.annotations.ApiModelProperty;

/**
 * 菜单表vo
 */

@SuppressWarnings("serial")
@AntBean(name = "菜单表vo", desc = "菜单表vo", author = "qjh")
public class MenuVo extends Menu implements java.io.Serializable {

    @AntField(name = "列", desc = "列", required = false)
    private String column;

    /** 分页参数 */
    private PageParameter pageParameter;

    /** 角色id */
    @AntField(name = "角色id", desc = "角色id", required = false)
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @AntField(name = "ids", desc = "ids 多个逗号分隔", required = false)
    @ApiModelProperty(value = "ids 多个逗号分隔")
    private String ids;

    @AntField(name = "当前页", desc = "当前页", required = false)
    private Integer page;

    @AntField(name = "每页的条数", desc = "每页的条数", required = false)
    private Integer rows;

    /** 按钮英文名称 多个逗号分隔 */
    @ApiModelProperty(value = "按钮英文名称 多个逗号分隔")
    private String button_ename;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public PageParameter getPageParameter() {
        return pageParameter;
    }

    public void setPageParameter(PageParameter pageParameter) {
        this.pageParameter = pageParameter;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
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

    public String getButton_ename() {
        return button_ename;
    }

    public void setButton_ename(String button_ename) {
        this.button_ename = button_ename;
    }

}
