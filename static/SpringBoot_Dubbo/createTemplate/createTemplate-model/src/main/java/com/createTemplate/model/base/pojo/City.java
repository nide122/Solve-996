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

import io.swagger.annotations.ApiModelProperty;
/**
 * 城市
 * @author:  
 * @date: 2018年5月26日 下午5:40:32 
 * @version V1.0
 */
@SuppressWarnings("serial")
@AntBean(name="城市",desc = "城市",author="")
@Entity(name = "T_CITY")
public class City implements Serializable{
	@Id
	/**id*/
	@AntField(name = "id", desc = "id", required = false)
	private Long id;

	@ApiModelProperty(value="省份代码")
	private String province_code;

	@ApiModelProperty(value="城市代码")
	private String city_code;
	
	@ApiModelProperty(value="城市名称")
	private String city_name;
	
	@ApiModelProperty(value="市名称拼音首字母")
	private String first_pinyin;
	
	@ApiModelProperty(value="是否热门城市 0  不是 1 是")
	private Integer hot_flag;
	
	@ApiModelProperty(value="是否开通0 未开通  1开通")
	private Integer if_dredge;
	/**非热门城市*/
    public static final int hotFlag_no=0;
    /**热门城市*/
    public static final int hotFlag_yes=1;
    /**未开通*/
    public static final int ifDredge_no=0;
    /**已开通*/
    public static final int ifDredge_yes=1;
    
	@ApiModelProperty(value="状态 0 正常；1 禁用")
	private Integer status;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return the city_name
     */
    public String getCity_name() {
        return city_name;
    }

    
    /**
     * @param city_name the city_name to set
     */
    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    
    /**
     * @return the first_pinyin
     */
    public String getFirst_pinyin() {
        return first_pinyin;
    }

    
    /**
     * @param first_pinyin the first_pinyin to set
     */
    public void setFirst_pinyin(String first_pinyin) {
        this.first_pinyin = first_pinyin;
    }

    
    /**
     * @return the hot_flag
     */
    public Integer getHot_flag() {
        return hot_flag;
    }

    
    /**
     * @param hot_flag the hot_flag to set
     */
    public void setHot_flag(Integer hot_flag) {
        this.hot_flag = hot_flag;
    }

    
    /**
     * @return the if_dredge
     */
    public Integer getIf_dredge() {
        return if_dredge;
    }

    
    /**
     * @param if_dredge the if_dredge to set
     */
    public void setIf_dredge(Integer if_dredge) {
        this.if_dredge = if_dredge;
    }
	
}
