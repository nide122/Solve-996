/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.model.base.vo;

import com.createTemplate.model.annotation.AntField;
import com.createTemplate.model.base.pojo.EnumObj;
import com.createTemplate.model.mybatis.page.PageParameter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *数据字典vo
 * @author:  
 * @date: 2018年5月26日 下午5:55:43 
 * @version V1.0
 */
@ApiModel(value ="数据字典vo")
public class EnumObjVo extends EnumObj implements java.io.Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="列",hidden=true)
	@AntField(name="列",desc="列",required=false)
	private String column;
    @ApiModelProperty(value="当前页")
	@AntField(name="当前页",desc="当前页",required=false)
	private Integer page;
    @ApiModelProperty(value="每页的条数")
	@AntField(name="每页的条数",desc="每页的条数",required=false)
	private Integer rows;
    /**上级字典值*/
    private String upperEnumValue;
    @ApiModelProperty(value="类型名称")
    private String enum_type_name;
	/**分页参数*/
	@ApiModelProperty(name="分页参数 ",hidden=true)
	private PageParameter pageParameter;
	
    public String getEnum_type_name() {
        return enum_type_name;
    }

    
    public void setEnum_type_name(String enum_type_name) {
        this.enum_type_name = enum_type_name;
    }

    public String getUpperEnumValue() {
		return upperEnumValue;
	}

	public void setUpperEnumValue(String upperEnumValue) {
		this.upperEnumValue = upperEnumValue;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public PageParameter getPageParameter() {
		return pageParameter;
	}

	public void setPageParameter(PageParameter pageParameter) {
		this.pageParameter = pageParameter;
	}

}
