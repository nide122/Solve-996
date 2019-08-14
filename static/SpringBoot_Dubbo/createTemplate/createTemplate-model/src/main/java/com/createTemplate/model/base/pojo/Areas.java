/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.base.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.createTemplate.model.annotation.AntBean;

import io.swagger.annotations.ApiModelProperty;
/**
 * 区县
 *
 * @author:  
 * @date: 2018年5月26日 下午5:46:44 
 * @version V1.0
 */
@SuppressWarnings("serial")
@AntBean(name="区县",desc = "区县",author="")
@Entity(name = "T_AREAS")
public class Areas implements java.io.Serializable{
	@Id
	/**id*/
	@ApiModelProperty(name="id")
	private Long id;
	/**区县代码*/
	@ApiModelProperty(name="区县代码")
	private String areas_code;

	/**市代码*/
	@ApiModelProperty(name="市代码")
	private String city_code;
	
	/**区县名称*/
	@ApiModelProperty(name="区县名称")
	private String areas_name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    
    /**
     * @return the areas_code
     */
    public String getAreas_code() {
        return areas_code;
    }

    
    /**
     * @param areas_code the areas_code to set
     */
    public void setAreas_code(String areas_code) {
        this.areas_code = areas_code;
    }

    
    /**
     * @return the city_code
     */
    public String getCity_code() {
        return city_code;
    }

    
    /**
     * @param city_code the city_code to set
     */
    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    
    /**
     * @return the areas_name
     */
    public String getAreas_name() {
        return areas_name;
    }

    
    /**
     * @param areas_name the areas_name to set
     */
    public void setAreas_name(String areas_name) {
        this.areas_name = areas_name;
    }


}
