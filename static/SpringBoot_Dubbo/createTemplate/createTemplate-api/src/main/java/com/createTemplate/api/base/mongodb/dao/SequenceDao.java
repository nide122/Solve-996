/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.api.base.mongodb.dao;

/** 
 * 
 * 系统序列号
 * @author:  
 * @date: 2018年10月15日 下午2:42:24 
 * @version V1.0
 */
public interface SequenceDao extends MongoBaseDao {
    /**
    * 获取序列值
    * @param name
    * @return
    */
   public Long getNextSequence(String name);
   
   
   /**
    * 获取下一个充值订单号
    * @return
    */
   public String getNextRechargeSerialNo();
   
   /**
    * 获取下一个支付订单号
    * @return
    */
   public String getNextPayOrderNo();
}
