/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.admin.system.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 菜单
 * 
 * @date 2015-1-8上午10:10:38
 * @version 1.0
 */
@Entity(name = "T_ROLE_MENU")
public class RoleMenu implements java.io.Serializable {

    private static final long serialVersionUID = -6626977647959072701L;

    @Id
    /** id */
    private Long id;

    /** 角色id */
    private Long role_id;

    /** 菜单id */
    private Long menu_id;

    /** 菜单英文名称 */
    private String menu_ename;

    /** 按钮英文名称 多个逗号分隔 */
    private String button_ename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public Long getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Long menu_id) {
        this.menu_id = menu_id;
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

}
