/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.api.base.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.InsertOptions;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.createTemplate.model.resultvo.Grid;

@SuppressWarnings("unchecked")
public class MongoBaseDaoImpl<T,K> extends BasicDAO<T,K> implements MongoBaseDao {
	
	/**
     * @param ds
     */
    protected MongoBaseDaoImpl(Datastore ds) {
        super(ds);
        this.ds = ds;
    }

    /**
	 * morphia 查询的数据存储
	 */
	protected Datastore ds = null;


	/**
	 * 根据class得到query
	 * 
	 * @param type
	 * @return
	 */
	@Override
	public <T> Query<T> getQuery(Class<T> type) {
		Query<T> query = ds.createQuery(type);
		return query;
	}

	/**
	 * 
	 * @author sjl
	 * @Date:2014-2-26下午08:35:06
	 * @param type
	 * @return
	 */
	public UpdateOperations<T> getUpdateOperations(Class<T> type) {
		UpdateOperations<T> updateOperations = ds.createUpdateOperations(type);
		return updateOperations;
	}

	/**
	 * 保存实体
	 * @param obj
	 */
	@Override
	public void save(Iterable  objs){
		ds.save(objs);
	}
	
	/**
	 * 保存实体
	 * 
	 * @param obj
	 */
	@Override
	public void save(Object... obj) {
	    List objList = new ArrayList();
	    this.save(objList);
	}
	
	

	/**
	 * 根据条件删除数据
	 * 
	 * @param query
	 */
	@Override
	public void delete(Query query) {
		ds.delete(query);
	}
	
	/**
	 * 根据条件删除数据
	 * 
	 * @param query
	 */
	@Override
	public void deleteObj(Object object) {
		ds.delete(object);
	}
	
	/**
	 * 删除所有数据
	 * 
	 * @param type
	 */
	@Override
	public void deleteAll(Class type) {
		ds.delete(ds.createQuery(type));
	}
	
	/**
	 * 查找某个数据库所有数据
	 * 
	 * @param type
	 * @return
	 */
	@Override
	public List findAll(Class type) {
		return ds.find(type).asList();
	}

	/**
	 * 根据id的查询对象
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	@Override
	public Object findById(Class type, ObjectId id) {
		List list = ds.createQuery(type).filter("id =", id).asList();
		return list == null || list.isEmpty() ? null : list.get(0);
	}

	/**
	 * 更改数据
	 * 
	 * @param entity
	 */
	@Override
	public void update(Object entity) {
		ds.save(entity);
	}
	
	/**
	 * 更改实体
	 * @param obj
	 */
	@Override
	public void update(Iterable  objs){
		ds.save(objs);
	}
	/**
	 * 根据query对象查询 实体
	 * 
	 * @param query
	 * @return
	 */
	@Override
	public List findByQuery(Query query) {
		return query.asList();
	}

	/**
	 * 查询实体
	 * 
	 * @author sjl
	 * @Date:2014-1-20下午12:05:13
	 * @param query
	 * @return
	 */
	@Override
	public<T> T findOneByQuery(Query<T> query) {
		List<T> list = query.asList();
		return list == null || list.isEmpty() ? null : list.get(0);
	}

	/**
	 * 分页查询记录
	 * 
	 * @author sjl
	 * @Date:2013-12-31上午10:51:17
	 * @param query
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	@Override
	public <T> List<T> findPageList(Query<T> query, int offset, int pagesize) {
		return query.offset(offset).limit(pagesize).asList();
	}

	/**
	 * 根据query查询总条数
	 * 
	 * @author sjl
	 * @Date:2013-12-31上午10:47:23
	 * @param query
	 * @return
	 */
	@Override
	public Long findCount(Query query) {
		return query.countAll();
	}

	/**
	 * 根据query对象查询 实体key
	 * 
	 * @param query
	 * @return
	 */
	@Override
	public List findKeyListByQuery(Query query) {
		return query.asKeyList();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public <T> Grid<T> getGrid(Query<T> paramQuery, Integer page, Integer rows) {
		List<T> list = findPageList(paramQuery, (page - 1) * rows, rows);
		Long count = findCount(paramQuery);
		Grid<T>grid = new Grid<T>();
		grid.setList(list);
		grid.setCount(count);
		return grid;
	}

}
