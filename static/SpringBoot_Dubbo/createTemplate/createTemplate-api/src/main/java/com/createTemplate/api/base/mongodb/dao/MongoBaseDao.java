/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.api.base.mongodb.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;

import com.createTemplate.model.resultvo.Grid;

@SuppressWarnings("rawtypes")
public interface  MongoBaseDao{
	/**
	 * 根据class得到query
	 * @param type
	 * @return
	 */
	public <T> Query<T> getQuery(Class<T> type);
	
	/**
	 * 保存实体
	 * @param obj
	 */
	public void save(Object ... obj);
	
	/**
	 * 保存实体
	 * @param obj
	 */
	public void save(Iterable  objs);
	
	
	/**
	 * 根据条件删除数据
	 * @param query
	 */
	public void delete(Query query);
	
	/**
	 * 根据条件删除数据
	 * @param query
	 */
	public void deleteObj(Object object);
	
	/**
	 * 删除所有数据
	 * @param type
	 */
	public void deleteAll(Class type);
	
	/**
	 * 查找某个数据库所有数据
	 * @param type
	 * @return
	 */
	public <T>List<T> findAll(Class<T> type);
	
	/**
	 * 根据id的查询对象
	 * @param type
	 * @param id
	 * @return
	 */
	public <T>T findById(Class<T> type,ObjectId id);
	
	/**
	 * 更改数据
	 * @param entity
	 */
	public void update(Object entity);
	
	/**
	 * 更改数据
	 * @param entity
	 */
	public void update(Iterable  objs);
	
	/**
	 * 根据query对象查询 实体
	 * @param query
	 * @return
	 */
	public <T> List<T> findByQuery(Query<T> query);
	
	/**
	 * 查询实体
	 * @author sjl
	 * @Date:2014-1-20下午12:05:13
	 * @param query
	 * @return
	 */
	public <T>T findOneByQuery(Query<T> query);
	
	
	/**
	 * 分页查询记录
	 * @author sjl
	 * @Date:2013-12-31上午10:51:17
	 * @param query
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public <T>List<T> findPageList(Query<T> query,int offset,int pagesize);
	/**
	 * 根据query查询总条数
	 * @author sjl
	 * @Date:2013-12-31上午10:47:23
	 * @param query
	 * @return
	 */
	public Long findCount(Query query);
	
	
	/**
	 * 根据query对象查询 实体key
	 * @param query
	 * @return
	 */
	public List findKeyListByQuery(Query query);
	
	
	public abstract <T> Grid<T> getGrid(Query<T> paramQuery, Integer page, Integer rows);

}
