/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.api.base.dubbo.service;

import java.util.List;

import com.createTemplate.api.base.dao.MybatisDao;
import com.createTemplate.model.base.pojo.Areas;
import com.createTemplate.model.base.pojo.City;
import com.createTemplate.model.base.pojo.Province;
import com.createTemplate.model.base.vo.AreasVo;
import com.createTemplate.model.base.vo.CityVo;
import com.createTemplate.model.base.vo.ProvinceVo;
import com.createTemplate.model.exception.BusinessException;
import com.createTemplate.model.resultvo.Grid;

public interface ProvinceService extends MybatisDao {
	/**
	 * 分页查询省市区信息
	 * 
	 * @param objVo
	 * @return
	 * @author libiqi
	 * @date 2018年4月9日
	 */
	public Grid getProvincePage(ProvinceVo provinceVo);

	/**
	 * 分页查询城市信息
	 * 
	 * @param cityVo
	 * @return
	 * @author libiqi
	 * @date 2018年4月9日
	 */
	public Grid getCityPage(CityVo cityVo);

	/**
	 * 分页查询区县信息
	 * 
	 * @param areasVo
	 * @return
	 * @author libiqi
	 * @date 2018年4月9日
	 */
	public Grid getAreasPage(AreasVo areasVo);

	/**
	 * 查询所有的省份
	 * 
	 * @return
	 */
	public List<Province> listProvince();

	/**
	 * 根据省份代码查询省份下所有的城市
	 * 
	 * @param cityBase
	 * @return
	 */
	public List<City> listCity(City cityBase);

	/**
	 * 查询区域
	 * 
	 * @param areasBase
	 * @return
	 */
	public List<Areas> listAreas(Areas areasBase);

	/**
	 * 查询开通的城市
	 * 
	 * @return
	 */
	public List<City> listIfDredgeCity(CityVo cityVo);

	/**
	 * 查询热门城市列表
	 * 
	 * @return
	 */
	public List<City> listHotCity(CityVo cityVo);

	/**
	 * 是否开通城市
	 */
	public void updateIfDredge(String cityCode, Integer ifDredge);

	/**
	 * 后台是否开通城市
	 */
	public void updateIfDredgeTwo(Long id, Integer ifDredge);

	
	/**
     * 修改城市状态
     * 
     * @description:
     */
    public void updateCityStatus(City city) throws BusinessException;
	/**
	 * 是否设置热门
	 * 
	 * @param id
	 * @param hotFlag
	 */
	public void updateHotFlag(Long id, Integer hotFlag);

	/**
	 * 根据城市代码获取市名称
	 * 
	 * @param @param
	 *            cityCode
	 * @param @return
	 * @return
	 * @date 2015-4-11 下午08:36:50
	 * @version V1.0
	 */
	public String getCityNameByCode(String cityCode);

	/**
	 * 根据区县代码获取区县名称
	 * 
	 * @author xf
	 * @param @param
	 *            areasCode
	 * @param @return
	 * @return
	 * @date 2015-4-11 下午08:36:50
	 * @version V1.0
	 */
	public String getAreasNameByCode(String areasCode);

	/**
	 * 根据areasCode 获取 Areas
	 * 
	 * @param areasCode
	 * @return
	 */
	public Areas getAreasByCode(String areasCode);

	/**
	 * 根据省代码获取省对象
	 * 
	 * @param provinceCode
	 * @return
	 */
	public Province getProvinceByCode(String provinceCode);

	/**
	 * 根据市代码获取市对象
	 * 
	 * @param cityCode
	 * @return
	 */
	public City getCityByCode(String cityCode);

	/**
	 * 根据省份代码获取省名称
	 * 
	 * @param provinceCode
	 * @return
	 */
	public String getProvinceNameByCode(String provinceCode);

	/**
	 * 查询开通的城市
	 * 
	 * @description:
	 * @return
	 * @author: 
	 * @version: V1.00
	 */
	public List<City> listIfDredgeCity();

	/**
	 * 查询热门城市列表
	 * 
	 * @description:
	 * @return
	 * @author: 
	 * @version: V1.00
	 */
	public List<City> listHotCity();

	/**
	 * 保存城市
	 * 
	 * @description:
	 * @return
	 * @author: 
	 * @version: V1.00
	 */
	public void saveCity(City city);

	/**
	 * 修改城市状态
	 * 
	 * @description:
	 */
	public void updataCityStatus(City city);

}
