/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.base.vo;

import com.createTemplate.model.annotation.AntBean;
import com.createTemplate.model.annotation.AntField;
import com.createTemplate.model.base.pojo.City;
import com.createTemplate.model.mybatis.page.PageParameter;

import io.swagger.annotations.ApiModelProperty;

/**
 * 城市vo
 * @author:  
 * @date: 2018年5月26日 下午5:55:01 
 * @version V1.0
 */
@AntBean(name = "城市vo", desc = "城市vo", author = "libiqi")
public class CityVo extends City implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@AntField(name = "列", desc = "列", required = false)
	private String column;

	/** 分页参数 */
	@ApiModelProperty(name="分页参数 ",hidden=true)
	private PageParameter pageParameter;

	@AntField(name = "当前页", desc = "当前页", required = false)
	private Integer page;

	@AntField(name = "每页的条数", desc = "每页的条数", required = false)
	private Integer rows;
	


    /**
     * @return the column
     */
    public String getColumn() {
        return column;
    }

    
    /**
     * @param column the column to set
     */
    public void setColumn(String column) {
        this.column = column;
    }

    
    /**
     * @return the pageParameter
     */
    public PageParameter getPageParameter() {
        return pageParameter;
    }

    
    /**
     * @param pageParameter the pageParameter to set
     */
    public void setPageParameter(PageParameter pageParameter) {
        this.pageParameter = pageParameter;
    }

    
    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    
    /**
     * @param page the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    
    /**
     * @return the rows
     */
    public Integer getRows() {
        return rows;
    }

    
    /**
     * @param rows the rows to set
     */
    public void setRows(Integer rows) {
        this.rows = rows;
    }

}
