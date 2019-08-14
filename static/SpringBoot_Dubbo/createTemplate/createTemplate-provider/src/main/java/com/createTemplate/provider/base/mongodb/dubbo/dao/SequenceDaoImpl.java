/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.provider.base.mongodb.dubbo.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.createTemplate.api.base.mongodb.dao.MongoBaseDaoImpl;
import com.createTemplate.api.base.mongodb.dao.SequenceDao;
import com.createTemplate.model.constant.CommonConstant;
import com.createTemplate.model.core.mongo.pojo.Sequence;

/**
 *  序列dao
 * @author:  
 * @date: 2018年10月15日 下午3:07:45 
 * @version V1.0
 */
@Service(version = "1.0.0",retries=0,timeout=6000,parameters={"sendMessage.timeout","12000"},interfaceClass=SequenceDao.class)
public class SequenceDaoImpl extends MongoBaseDaoImpl<Sequence, ObjectId> implements  SequenceDao {
    
    /**
     * @param ds
     */
    @Autowired
    public SequenceDaoImpl(Datastore ds) {
        super(ds);
    }
    
	/**
	 * 获取序列值
	 * @param name
	 * @return
	 */
	public Long getNextSequence(String name){
		Query<Sequence> query = super.getQuery(Sequence.class);
		query.filter("sequenceName", name);
		UpdateOperations<Sequence> uo = this.getUpdateOperations(Sequence.class);
		uo.inc("value");
		Sequence sq = ds.findAndModify(query, uo);
		return sq.getValue();
	}
	
	/**
	 * 获取下一个充值订单号
	 * @return
	 */
	public String getNextRechargeSerialNo(){
		return "re" + this.getNextSequence(CommonConstant.Sequence.rechargeSerialNo);
	}
	
	
	/**
	 * 获取下一个支付订单号
	 * @return
	 */
	public String getNextPayOrderNo(){	
		return "p"  + this.getNextSequence(CommonConstant.Sequence.payOrderNo);
	}

}
