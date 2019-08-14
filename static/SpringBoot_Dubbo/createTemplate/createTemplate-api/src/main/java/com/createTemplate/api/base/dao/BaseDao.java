/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.api.base.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author:  
 * @date: 2018年6月13日 上午9:31:54 
 * @version V1.0
 */
public interface BaseDao extends SqlSession{
    /**
     * 获取jdbc模板对象
     * @return
     */
	public JdbcTemplate getJdbcTemplate();
	
	/**
	 * 获取sqlSession模板对象
	 * @return
	 */
	public SqlSessionTemplate getSqlSessionTemplate();
	/**
	 * 批量插入列表数据
	 * @param stateName  mybatis  命令key
	 * @param list  待插入的数据
	 */
	public void   batchInsertList(String stateName,List list);
}
