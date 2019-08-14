/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.base.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.createTemplate.model.annotation.AntBean;
import com.createTemplate.model.annotation.AntField;

import io.swagger.annotations.ApiModel;
/**
 * 字典类型
 * @author:  
 * @date: 2018年5月26日 下午5:46:55 
 * @version V1.0
 */
@AntBean(name="数据字典类型",desc = "数据字典类型",author="libiqi")
@ApiModel(description="数据字典类型")
@Entity(name = "T_ENUMTYPE")
public class EnumType implements Serializable {

    private static final long serialVersionUID = 4948890200083274099L;

    @AntField(name="id",desc="唯一标识",required=false)
    @Id
	/**id*/
	private Long id;

	/**字典类型名称*/
	@AntField(name="字典类型名称",desc="字典类型名称",required=true)
	private String enum_type_name;
	
	/**字典类型描述*/
	@AntField(name="字典类型描述",desc="字典类型描述",required=true)
	private String remark;
	
	/**字典类型代码*/
	@AntField(name="字典类型代码",desc="字典类型代码",required=true)
	private String enum_type_code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnum_type_name() {
		return enum_type_name;
	}

	public void setEnum_type_name(String enum_type_name) {
		this.enum_type_name = enum_type_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEnum_type_code() {
		return enum_type_code;
	}

	public void setEnum_type_code(String enum_type_code) {
		this.enum_type_code = enum_type_code;
	}

    
    

}
