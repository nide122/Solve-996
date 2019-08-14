/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.base.mongo.pojo;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

import com.createTemplate.model.annotation.AntField;

public abstract class IdEntity
{

  @Id
  @AntField(name="id", desc="id", required=false)
  private ObjectId id;

  public String getId()
  {
    return ((this.id == null) ? null : this.id.toString()); }

  public void setId(String id) {
    if (id == null) {
        this.id = null;
    } else {
        this.id = new ObjectId(id);
    }
  }
}
