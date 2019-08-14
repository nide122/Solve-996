/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.api.base.dubbo.service;

import java.util.List;

import com.createTemplate.api.base.dao.MybatisDao;
import com.createTemplate.model.base.pojo.EnumObj;
import com.createTemplate.model.base.pojo.EnumType;
import com.createTemplate.model.base.vo.EnumObjVo;
import com.createTemplate.model.exception.BusinessException;
import com.createTemplate.model.resultvo.Grid;
public interface EnumService extends MybatisDao {
	/**
	 * 数据字典查询
	 * 
	 * @param @param enumObj
	 * @return
	 * @version V1.0
	 */
	public List<EnumObj> listEnum(String enumTypeCode,Long upperId)throws BusinessException ;

	/**
	 * 
	 * @Description:根据id查询 
	 * @date 2016-6-21 下午08:50:31
	 * @version V1.0
	 */
	public EnumObj findById(Long enumId)throws BusinessException;

	/**
	 * 获取数据字典值
	 * 
	 * @param enumTypeCode
	 * @param upperEnumKey
	 * @param enumKey
	 * @return
	 */
	public String getEnumValue(Long enumId)throws BusinessException ;
	
	/**
	 * 获取数据字典对象
	 * @param enumId
	 * @return
	 */
	public EnumObj getEnumObj(Long enumId)throws BusinessException;
	
	/**
	 * 数据字典保存
	 * 
	 * @param @param enumObj
	 * @return
	 * @version V1.0
	 */
	public void save(EnumObj enumObj)throws BusinessException ;
	
	/**
	 * 清空redis
	 * @return
	 * @version V1.0
	 */
	public void clearEnumRedis(String enumTypeCode, Long upperId,Long enumId)throws BusinessException ;
	

    /**
     * 查询所有字典类型
     * @return
     */
    public List<EnumType> findAll()throws BusinessException;
    /**
     * 数据字典查询
     * @author aj
     * @param  @param enumObj
     * @return 
     * @date 2015-3-28 下午01:53:37
     * @version V1.0
     */
    public List<EnumObj> findEnum(EnumObj enumObj)throws BusinessException;
    
    /**
     * 分页查询
     * @author aj
     * @param  @param enumObjVo
     * @param  @return
     * @return 
     * @date 2015-4-12 上午11:13:26
     * @version V1.0
     */
    public Grid getPage(EnumObjVo enumObjVo)throws BusinessException;
    
    /**
     * 查询数据字典类型
     * @auther aijian
     * @date 2015-1-15 下午7:53:42
     * @return
     */
    public List<EnumType> listEnumType(EnumType enumType)throws BusinessException;
    
    /**
     * 激活/禁用
     * @author aj
     * @param  
     * @return 
     * @date 2015-4-12 下午12:24:53
     * @version V1.0
     */
    public void updateStatus(Long id,Integer status)throws BusinessException;
    
    
    /**
     * 数据字典修改
     * @author aj
     * @param  @param enumObj
     * @return 
     * @date 2015-3-28 下午01:51:46
     * @version V1.0
     */
    public void update(EnumObj enumObj)throws BusinessException;
    
    /**
     * 获取数据字典值
     * @param enumTypeCode
     * @param upperEnumKey
     * @param enumKey
     * @return
     */
    public String getEnumValue(String enumTypeCode,String upperEnumKey,String enumKey)throws BusinessException;
    
    /**
     * 根据数据字典类型获取所有key，用逗号分隔
     * @param enumTypeCode
     * @return
     */
    public String getAllEnumKeyString(String enumTypeCode)throws BusinessException;
    
    /**
     * 根据多个行业类型代码转换为名称
     * @param businessTypeCodes
     */
    public String getBusinessTypeNamesByCodes(String businessTypeCodes)throws BusinessException;
    
    /**
     * 根据多个行业类型代码转换为名称
     * @param businessTypeCodes
     */
    public String getProductTypeNamesByCodes(String productTypeCodes)throws BusinessException;
    
    /**
     * 根据多个学校特殊属性代码转换名称
     * @description:
     *@param specialProperties
     *@return
     *@author: 
     *@version: V1.00
     */
    public String getSpecialPropertiesNamesByCodes(String specialProperties)throws BusinessException;
}
