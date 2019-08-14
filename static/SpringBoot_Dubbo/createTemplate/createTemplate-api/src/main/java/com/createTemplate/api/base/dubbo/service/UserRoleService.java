/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.api.base.dubbo.service;

import java.util.List;
import java.util.Map;

import com.createTemplate.model.exception.BusinessException;



/**
 * 
 * @Description:用户权限操作接口
 * @author yk
 * @Version: V1.00
 * 创建时间：2015-1-16 上午10:38:21
 * @param users
 */
public interface UserRoleService {
	
	/**
	 * 
	 * @Description:查询用户权限
	 * @author yk
	 * @Version: V1.00
	 * 创建时间：2015-1-16 上午10:38:21
	 * @param roleId 角色Id
	 * @return 拥有的菜单权限集合
	 */
	public List<Map> listUserRole(Long roleId,Long parentId) throws BusinessException;
}
