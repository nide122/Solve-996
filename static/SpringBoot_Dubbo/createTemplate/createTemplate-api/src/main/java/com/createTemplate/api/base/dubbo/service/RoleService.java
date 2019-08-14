/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.api.base.dubbo.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.createTemplate.model.admin.system.pojo.Menu;
import com.createTemplate.model.admin.system.pojo.Role;
import com.createTemplate.model.admin.system.pojo.RoleMenu;
import com.createTemplate.model.admin.system.vo.MenuVo;
import com.createTemplate.model.admin.system.vo.RoleVo;
import com.createTemplate.model.exception.BusinessException;
import com.createTemplate.model.resultvo.Grid;

/**
 * 角色service
 * 
 * @author Chenglf
 *
 */
public interface RoleService {

    /**
     * 角色分页查询
     * 
     * @author aj
     * @Version: V1.00
     * @param roleName
     *            角色名称
     * @param page
     * @param rows
     * @return
     */
    public Grid getRolePage(RoleVo roleVo) throws BusinessException;

    /**
     * 角色查询
     * 
     * @Version: V1.00
     * @param :
     *            roleName 角色名称
     * @return：返回查询对象
     */
    public List<Role> listRoleByRoleName(String roleName) throws BusinessException;

    /**
     * 修改角色状态值
     */
    public Role updateRoleStatus(Long id, Integer status) throws BusinessException;

    /**
     * 菜单查询
     * 
     * @author :aj
     * @Version: V1.00
     * @Create : 2014-11-14 下午15:39:50
     * @param :
     *            角色Id @return：
     */
    public List<Map> getMenu(Long roleId) throws BusinessException;

    /**
     * 根据roleIds删除角色
     * 
     * @Version: V1.00
     * @param roleIds
     */
    public void deleteRoleById(String roleIds) throws BusinessException;

    /**
     * 更新角色信息
     * @param role
     * @param menuIds
     */
    public void updateRole(Role role, String menuIds) throws BusinessException;

    /**
     * 添加角色
     * 
     * @auther xf
     * @date 2015-1-15 下午2:48:48
     * @param roleName
     * @param remark
     * @param menuIds
     * @return
     */
    public Long saveRole(String roleName, String remark, Integer status,String menuIds) throws BusinessException;

    /**
     * 校验角色名是否已存在
     * 
     * @Version: V1.00
     * @return true 通过校验 false 校验不通过
     */
    public boolean checkExistRoleName(Role role) throws BusinessException;

    /**
     * 根据角色id查询拥有的菜单权限
     * 
     * @param roleId
     * @return
     */
    public List<MenuVo> listByRoleId(Long roleId) throws BusinessException;

    /**
     * 角色查询
     * 
     * @Version: V1.00
     * @param :
     *            roleName 角色名称
     * @return：返回查询对象
     */
    public List<Role> listAllRole() throws BusinessException;

    /**
     * @param roleIds
     * @return
     */
    public List<RoleMenu> listByRoleIds(Set<Long> roleIds) throws BusinessException;

    /**
     * 根据角色id列表查询菜单列表
     * 
     * @param roleIds
     * @return
     */
    public List<Menu> listMenuByRoleIds(Set<Long> roleIds) throws BusinessException;

}
