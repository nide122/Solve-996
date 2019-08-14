/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.provider.base.mongodb.dubbo.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.createTemplate.api.base.mongodb.dao.MongoBaseDaoImpl;
import com.createTemplate.api.base.mongodb.dao.SystemLogDao;
import com.createTemplate.model.base.mongo.pojo.SystemLog;


@Service(version = "1.0.0",retries=0,timeout=6000,parameters={"sendMessage.timeout","12000"},interfaceClass=SystemLogDao.class)
public class SystemLogDaoImpl extends MongoBaseDaoImpl<SystemLog, ObjectId> implements  SystemLogDao {
    
    /**
     * @param ds
     */
    @Autowired
    public SystemLogDaoImpl(Datastore ds) {
        super(ds);
    }
    
    
    
    
    
    
}
