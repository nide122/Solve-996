/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.base.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.createTemplate.model.annotation.AntBean;
import com.createTemplate.model.annotation.AntField;

import io.swagger.annotations.ApiModelProperty;

/**
 * 省份
 * @author:  
 * @date: 2018年5月26日 下午5:47:30 
 * @version V1.0
 */
@AntBean(name="省份",desc = "省份",author="libiqi")
@Entity(name = "T_PROVINCE")
public class Province implements java.io.Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = -3701449654750475080L;
    @AntField(name="id",desc="唯一标识",required=false)
	@Id
	/**id*/
	private Long id;
	/**省代码*/
    @ApiModelProperty(value="省代码")
	private String province_code;

	/**省名称*/
    @ApiModelProperty(value="省名称")
	private String province_name;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    
    /**
     * @return the province_code
     */
    public String getProvince_code() {
        return province_code;
    }

    
    /**
     * @param province_code the province_code to set
     */
    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    
    /**
     * @return the province_name
     */
    public String getProvince_name() {
        return province_name;
    }

    
    /**
     * @param province_name the province_name to set
     */
    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
}
