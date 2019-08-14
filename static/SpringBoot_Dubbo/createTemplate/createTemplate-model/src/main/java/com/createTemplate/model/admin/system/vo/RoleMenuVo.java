/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.admin.system.vo;

import com.createTemplate.model.admin.system.pojo.RoleMenu;
import com.createTemplate.model.annotation.AntBean;
import com.createTemplate.model.annotation.AntField;
import com.createTemplate.model.mybatis.page.PageParameter;

import io.swagger.annotations.ApiModelProperty;

/**
 * 角色菜单表vo
 */

@SuppressWarnings("serial")
@AntBean(name = "角色菜单表vo", desc = "角色菜单表vo", author = "")
public class RoleMenuVo extends RoleMenu {

    @AntField(name = "列", desc = "列", required = false)
    private String column;

    @AntField(name = "当前页", desc = "当前页", required = false)
    private Integer page;

    @AntField(name = "每页的条数", desc = "每页的条数", required = false)
    private Integer rows;

    /** 分页参数 */
    private PageParameter pageParameter;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
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

}
