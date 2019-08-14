/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.core.mongo.pojo;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import com.createTemplate.model.annotation.AntField;
import com.createTemplate.model.base.mongo.pojo.IdEntity;

/**
 * 序列号
 * @author:  
 * @date: 2018年5月29日 上午11:07:41 
 * @version V1.0
 */
public class Sequence extends IdEntity implements Serializable{
	private static final long serialVersionUID = 8859501880180355383L;
	
	/**序列名称*/
	private String sequenceName;
	
	/**值*/
	private Long value;

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

    

}
