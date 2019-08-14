/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.base.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.createTemplate.model.annotation.AntField;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据字典
 * @author:  
 * @date: 2018年5月26日 下午5:46:31 
 * @version V1.0
 */
@ApiModel(value ="数据字典信息")
@Entity(name = "T_ENUMOBJ")
public class EnumObj implements Serializable {

    /**id*/
	@Id
	@ApiModelProperty(value="id")
    private Long id;
	   
    /**上级字典id*/
	@ApiModelProperty(value="上级字典id")
    private Long upper_id;
	
    /**上级字典项*/
	@ApiModelProperty(value="上级字典项")
    private String upper_enum_key;
	
    /**字典类型主键id*/
	@ApiModelProperty(value="字典类型主键id")
    private Long enum_type_id;
    
    /**字典类型代码*/
	@ApiModelProperty(value="字典类型代码")
    private String enum_type_code;
    
    /**字典项*/
	@ApiModelProperty(value="字典项")
    private String enum_key;
    
    /**字典值*/
	@ApiModelProperty(value="字典值")
    private String enum_value;
    
    /**描述*/
	@ApiModelProperty(value="描述")
    private String remark;
    
    /**排序*/
	@ApiModelProperty(value="排序")
    private Integer sort_no;
 
    /**状态 0正常；1删除*/
	@ApiModelProperty(value="状态 0正常；1删除")
    private Integer status;
    
    /**数据字典前缀*/
	@ApiModelProperty(value="数据字典前缀")
    public static final String enumFix = "recruit_enum_";
    
    /**数据字典按code获取的前缀*/
	@ApiModelProperty(value="数据字典按code获取的前缀")
    public static final String enumByCodeFix = "recruit_enumByCode_";
    
    /**数据字典所有key，用逗号分隔*/
	@ApiModelProperty(value="数据字典所有key，用逗号分隔")
    public static final String enumAllKeyString = "recruit_enum_enumAllKeyString";
	
	
    
    public String getUpper_enum_key() {
		return upper_enum_key;
	}


	public void setUpper_enum_key(String upper_enum_key) {
		this.upper_enum_key = upper_enum_key;
	}


	public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }
  
    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }  

    
    
    public Long getUpper_id() {
		return upper_id;
	}


	public void setUpper_id(Long upper_id) {
		this.upper_id = upper_id;
	}


	public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }


	public Long getEnum_type_id() {
		return enum_type_id;
	}


	public void setEnum_type_id(Long enum_type_id) {
		this.enum_type_id = enum_type_id;
	}


	public String getEnum_type_code() {
		return enum_type_code;
	}


	public void setEnum_type_code(String enum_type_code) {
		this.enum_type_code = enum_type_code;
	}


	public String getEnum_key() {
		return enum_key;
	}


	public void setEnum_key(String enum_key) {
		this.enum_key = enum_key;
	}


	public String getEnum_value() {
		return enum_value;
	}


	public void setEnum_value(String enum_value) {
		this.enum_value = enum_value;
	}


	public Integer getSort_no() {
		return sort_no;
	}


	public void setSort_no(Integer sort_no) {
		this.sort_no = sort_no;
	}
    
    
    

}