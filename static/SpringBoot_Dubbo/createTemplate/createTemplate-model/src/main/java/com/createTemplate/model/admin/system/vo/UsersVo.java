/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.admin.system.vo;

import java.util.Map;

import com.createTemplate.model.admin.system.pojo.Users;
import com.createTemplate.model.annotation.AntBean;
import com.createTemplate.model.annotation.AntField;
import com.createTemplate.model.mybatis.page.PageParameter;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户vo
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
@AntBean(name = "管理员用户vo", desc = "管理员用户vo", author = "xf")
public class UsersVo extends Users implements java.io.Serializable {

    /** 允许访问的菜单,key 为菜单的英文名称；value 菜单下的按钮权限 */
    private Map<String, Map<String, String>> menuMap;

    @ApiModelProperty(value="列")
    private String column;

    @ApiModelProperty(value="用户token")
    private String authToken;

    @ApiModelProperty(value="角色名称")
    private String roleName;

    @ApiModelProperty(value="关键字")
    private String key;

    @ApiModelProperty(value="角色id，多个逗号分隔")
    private String roleIds;

    @ApiModelProperty(value="当前页")
    private Integer page;

    @ApiModelProperty(value="每页的条数")
    private Integer rows;

    @ApiModelProperty(value="分页参数")
    private PageParameter pageParameter;

    @AntField(name = "失效时间", desc = "失效时间", required = false)
    private long expireTime;

    @AntField(name = "客户号", desc = "客户号", required = false)
    private String custId;

    /**
     * @return the expireTime
     */
    public long getExpireTime() {
        return expireTime;
    }

    /**
     * @param expireTime
     *            the expireTime to set
     */
    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * @return the custId
     */
    public String getCustId() {
        return custId;
    }

    /**
     * @param custId
     *            the custId to set
     */
    public void setCustId(String custId) {
        this.custId = custId;
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

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public PageParameter getPageParameter() {
        return pageParameter;
    }

    public void setPageParameter(PageParameter pageParameter) {
        this.pageParameter = pageParameter;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Map<String, Map<String, String>> getMenuMap() {
        return menuMap;
    }

    public void setMenuMap(Map<String, Map<String, String>> menuMap) {
        this.menuMap = menuMap;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * 判断是否有权限
     * 
     * @param roleId
     * @param menuEName
     * @return
     */
    public final static boolean checkRole(UsersVo sessionUsersVo, String menuEName, String buttonEName) {
        Map<String, Map<String, String>> menuMap = sessionUsersVo.getMenuMap();
        if (menuMap.containsKey(menuEName)) {
            /** 有权限 */
            Map<String, String> buttonMap = menuMap.get(menuEName);
            if (buttonMap.containsKey(buttonEName)) {
                return true;
            } else {
                return false;
            }
        } else {
            /** 无权限 */
            return false;
        }
    }

}
