/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.base.vo;

import com.createTemplate.model.annotation.AntBean;
import com.createTemplate.model.annotation.AntField;
import com.createTemplate.model.base.pojo.Province;
import com.createTemplate.model.mybatis.page.PageParameter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 省份vo
 *
 * @author:  
 * @date: 2018年5月26日 下午5:58:54 
 * @version V1.0
 */
@ApiModel(description="省份vo")
@AntBean(name = "省份vo", desc = "省份vo", author = "libiqi")
public class ProvinceVo extends Province implements java.io.Serializable{

	@AntField(name = "列", desc = "列", required = false)
	@ApiModelProperty(name="列",hidden=true)
	private String column;

	/** 分页参数 */
	@ApiModelProperty(name="分页参数 ",hidden=true)
	private PageParameter pageParameter;

	@ApiModelProperty(name="当前页",hidden=true)
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
