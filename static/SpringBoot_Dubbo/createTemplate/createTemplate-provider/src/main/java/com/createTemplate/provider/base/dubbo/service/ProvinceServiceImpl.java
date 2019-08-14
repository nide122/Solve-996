/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.provider.base.dubbo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.createTemplate.api.base.dubbo.service.CommonRedisDao;
import com.createTemplate.api.base.dubbo.service.ProvinceService;
import com.createTemplate.api.util.PropertyUtils;
import com.createTemplate.model.base.pojo.Areas;
import com.createTemplate.model.base.pojo.City;
import com.createTemplate.model.base.pojo.Province;
import com.createTemplate.model.base.vo.AreasVo;
import com.createTemplate.model.base.vo.CityVo;
import com.createTemplate.model.base.vo.ProvinceVo;
import com.createTemplate.model.constant.CommonConstant;
import com.createTemplate.model.constant.RedisConstant;
import com.createTemplate.model.exception.BusinessException;
import com.createTemplate.model.mybatis.page.PageParameter;
import com.createTemplate.model.resultvo.Grid;
import com.createTemplate.provider.base.dao.impl.MybatisDaoImpl;

/**
 * 
 * 省份业务实现类
 * 
 * @author: Administrator
 * @date: 2018年5月15日 下午2:02:21
 * @version V1.0
 */
@Service(version = "1.0.0", retries = 0, timeout = 6000, parameters = { "sendMessage.timeout",
		"12000" }, interfaceClass = ProvinceService.class)
public class ProvinceServiceImpl extends MybatisDaoImpl implements ProvinceService {

	@Resource
	private CommonRedisDao commonRedisDao;

	public static final String CLASSNAME = "province";

	/**
	 * 分页查询省份
	 * 
	 * @author libiqi
	 * @date 2018年4月9日
	 */
	@Override
	public Grid getProvincePage(ProvinceVo objVo) {
		objVo.setColumn(PropertyUtils.getPropertyNames(Province.class));
		objVo.setPageParameter(new PageParameter(objVo.getPage(), objVo.getRows()));
		List<ProvinceVo> list = this.baseDao.selectList(CLASSNAME + ".findProvincePage", objVo);
		Grid grid = new Grid();
		grid.setCount(Long.valueOf(objVo.getPageParameter().getTotalCount()));
		grid.setList(list);
		return grid;
	}

	/**
	 * 分页查询城市
	 * 
	 * @author libiqi
	 * @date 2018年4月9日
	 */
	@Override
	public Grid getCityPage(CityVo objVo) {
		objVo.setColumn(PropertyUtils.getPropertyNames(City.class));
		objVo.setPageParameter(new PageParameter(objVo.getPage(), objVo.getRows()));
		List<CityVo> list = this.baseDao.selectList(CLASSNAME + ".findCityPage", objVo);
		Grid grid = new Grid();
		grid.setCount(Long.valueOf(objVo.getPageParameter().getTotalCount()));
		grid.setList(list);
		return grid;
	}

	/**
	 * 分页查询区县
	 * 
	 * @author libiqi
	 * @date 2018年4月9日
	 */
	@Override
	public Grid getAreasPage(AreasVo objVo) {
		objVo.setColumn(PropertyUtils.getPropertyNames(Areas.class));
		objVo.setPageParameter(new PageParameter(objVo.getPage(), objVo.getRows()));
		List<AreasVo> list = this.baseDao.selectList(CLASSNAME + ".findAreasPage", objVo);
		Grid grid = new Grid();
		grid.setCount(Long.valueOf(objVo.getPageParameter().getTotalCount()));
		grid.setList(list);
		return grid;
	}

	@Override
	public List<Province> listProvince() {
	    /** 从redis中取配置 */
        String str = commonRedisDao.get(RedisConstant.provinceAllFix);
        List<Province>  list = new ArrayList<Province>();
        if (str != null && !"".equals(str)) {
            list = JSON.parseArray(str, Province.class);
        } else {
            /** 数据库中查询塞回redis */
            ProvinceVo objVo=new ProvinceVo();
            objVo.setColumn(PropertyUtils.getPropertyNames(Province.class));
           list = this.baseDao.selectList(CLASSNAME + ".findProvince", objVo);
            commonRedisDao.set(RedisConstant.provinceAllFix, JSON.toJSONString(list));
        }
        return list;
	}

	@Override
	public List<City> listCity(City cityBase) {
	    /** 从redis中取配置 */
        String str = commonRedisDao.get(RedisConstant.cityFix + cityBase.getProvince_code());
        List<City> cityList = new ArrayList<City>();
        if (str != null && !"".equals(str)) {
            cityList = JSON.parseArray(str, City.class);
        } else {
            /** 数据库中查询塞回redis */
            CityVo cityVo=new CityVo();
            cityVo.setColumn(PropertyUtils.getPropertyNames(City.class));
            cityList = this.baseDao.selectList(CLASSNAME + ".findCity", cityBase);
            commonRedisDao.set(RedisConstant.cityFix + cityVo.getProvince_code(), JSON.toJSONString(cityList));
        }
        return cityList;
	}

	@Override
	public List<Areas> listAreas(Areas areasBase) {
	    /** 从redis中取配置 */
        String str = commonRedisDao.get(RedisConstant.areasFix + areasBase.getCity_code());
        List<Areas> list = new ArrayList<Areas>();
        if (str != null && !"".equals(str)) {
            list = JSON.parseArray(str, Areas.class);
        } else {
            /** 数据库中查询塞回redis */
            AreasVo objVo=new AreasVo();
            objVo.setColumn(PropertyUtils.getPropertyNames(Areas.class));
            objVo.setCity_code(areasBase.getCity_code());
            list = this.baseDao.selectList(CLASSNAME + ".findAreas", objVo);
            commonRedisDao.set(RedisConstant.areasFix + areasBase.getCity_code(), JSON.toJSONString(list));
        }
        return list;
	}

	/**
	 * 查询已经开通的城市
	 * 
	 * @return
	 */
	@Override
	public List<City> listIfDredgeCity(CityVo cityVo) {
		String str = commonRedisDao.get(RedisConstant.dredgeCityFix);
		List<City> dredgeCityList = new ArrayList<City>();
		if (str != null && !"".equals(str)) {
			dredgeCityList = JSON.parseArray(str, City.class);
		} else {
			cityVo.setIf_dredge(City.ifDredge_yes);
			cityVo.setColumn(PropertyUtils.getPropertyNames(City.class));
			cityVo.setPageParameter(new PageParameter(cityVo.getPage(), cityVo.getRows()));
			dredgeCityList = this.baseDao.selectList(CLASSNAME + ".findCityPage", cityVo);
			if (dredgeCityList != null) {
				commonRedisDao.set(RedisConstant.dredgeCityFix, JSON.toJSONString(dredgeCityList));
			}
		}
		return dredgeCityList;
	}

	/**
	 * 查询热门城市
	 * 
	 * @return
	 */
	@Override
	public List<City> listHotCity(CityVo cityVo) {
		String str = commonRedisDao.get(RedisConstant.hotCityFix);
		List<City> hotCityList = new ArrayList<City>();
		if (str != null && !"".equals(str)) {
			hotCityList = JSON.parseArray(str, City.class);
		} else {
			cityVo.setHot_flag(City.hotFlag_yes);
			cityVo.setColumn(PropertyUtils.getPropertyNames(City.class));
			cityVo.setPageParameter(new PageParameter(cityVo.getPage(), cityVo.getRows()));
			hotCityList = this.baseDao.selectList(CLASSNAME + ".findCityPage", cityVo);
			if (hotCityList != null) {
				commonRedisDao.set(RedisConstant.hotCityFix, JSON.toJSONString(hotCityList));
			}
		}
		return hotCityList;
	}

	/**
	 * 更改开通城市
	 * 
	 * @param city
	 */
	@Override
	public void updateIfDredge(String cityCode, Integer ifDredge) {
		City dbCity =getCityByCode(cityCode);
		if (dbCity != null && (dbCity.getIf_dredge() == null || dbCity.getIf_dredge().intValue() != City.ifDredge_yes)) {
			// 城市不为空并且城市之前并不是开通城市。则更新城市为开通城市
			dbCity.setIf_dredge(ifDredge);
			super.update(dbCity);
			clearCityRedis(dbCity);
			commonRedisDao.delete(RedisConstant.dredgeCityFix);
		}
	}

	/**
	 * 清除redis
	 * 
	 * @param city
	 */
	public void clearCityRedis(City city) {
		commonRedisDao.delete(RedisConstant.cityAllFix);
		if (city != null) {
			commonRedisDao.delete(RedisConstant.cityByCodeFix + city.getCity_code());
			commonRedisDao.delete(RedisConstant.cityFix + city.getProvince_code());
		}
	}

	/**
	 * 根据区代码获取区对象
	 * 
	 * @param CityCode
	 * @return
	 */
	@Override
	public City getCityByCode(String cityCode) {
		City City = new City();
		/** 从redis中取配置 */
		String str = commonRedisDao.get(RedisConstant.cityByCodeFix + cityCode);
		if (str != null && !"".equals(str)) {
			City = JSON.parseObject(str, City.class);
		} else {
			City = this.baseDao.selectOne(CLASSNAME + ".findCityPage", cityCode);
			commonRedisDao.set(RedisConstant.cityByCodeFix + cityCode, JSON.toJSONString(City));
		}
		return City;
	}

	/**
	 * 是否设置热门
	 * 
	 * @param id
	 * @param hotFlag
	 */
	@Override
	public void updateHotFlag(Long id, Integer hotFlag) {
		City City =new City();
		City.setHot_flag(hotFlag);
		City.setId(id);
		super.baseDao.update(CLASSNAME+".updateHotFlag", City);
		commonRedisDao.delete(RedisConstant.hotCityFix);
	}

	/**
	 * 获取guzzRule对象
	 * 
	 * @author aj
	 * @param @param
	 *            province
	 * @param @param
	 *            page
	 * @param @param
	 *            rows
	 * @date 2015-3-9 下午05:09:35
	 * @version V1.0
	 */
	/*
	 * public GuzzRule generateGuzzRuleForProvince(Province Province, Integer
	 * page, Integer rows) { GuzzRule guzzRule = new GuzzRule();
	 * guzzRule.addTable(Province.class); guzzRule.addColumn(Province.class); if
	 * (Province != null && Province.getProvinceCode() != null &&
	 * !"".equals(Province.getProvinceCode())) {
	 * guzzRule.addLikeVlaue("ProvinceCode", null, Province.getProvinceCode());
	 * } if (Province != null && Province.getProvinceName() != null &&
	 * !"".equals(Province.getProvinceName())) {
	 * guzzRule.addLikeVlaue("ProvinceName", null, Province.getProvinceName());
	 * } if (page != null && rows != null) { guzzRule.setBeginIndex(page, rows);
	 * guzzRule.setIndexLength(rows); } return guzzRule;
	 * 
	 * }
	 */

	/**
	 * 获取guzzRule对象
	 * 
	 * @author aj
	 * @param @param
	 *            city 城市对象
	 * @param @param
	 *            page
	 * @param @param
	 *            rows
	 * @date 2015-3-9 下午05:09:35
	 * @version V1.0
	 */
	/*
	 * public GuzzRule generateGuzzRuleForCity(City City, Integer page, Integer
	 * rows) { GuzzRule guzzRule = new GuzzRule();
	 * guzzRule.addTable(City.class); guzzRule.addColumn(City.class); if (City
	 * != null && City.getProvinceCode() != null &&
	 * !"".equals(City.getProvinceCode())) { guzzRule.addEqual("ProvinceCode",
	 * null, City.getProvinceCode()); } if (City != null && City.getCityName()
	 * != null && !"".equals(City.getCityName())) {
	 * guzzRule.addLikeVlaue("CityName", null, City.getCityName()); } if (page
	 * != null && rows != null) { guzzRule.setBeginIndex(page, rows);
	 * guzzRule.setIndexLength(rows); } return guzzRule; }
	 */

	private City findCityById(Long id) {
		City city = this.baseDao.selectOne(CLASSNAME + ".findCityById", id);
		return city;
	}

	/**
	 * 根据城市代码获取市名称
	 * 
	 * @author aj
	 * @param @param
	 *            cityCode
	 * @param @return
	 * @return
	 * @date 2015-4-11 下午08:36:50
	 * @version V1.0
	 */
	@Override
	public String getCityNameByCode(String CityCode) {
		if (CityCode == null) {
			return "";
		}
		City City = this.getCityByCode(CityCode);
		if (City == null) {
			return "";
		}
		return City.getCity_name();
	}

	/**
	 * 根据省代码获取省对象
	 * 
	 * @param provinceCode
	 * @return
	 */
	@Override
	public Province getProvinceByCode(String province_code) {
		Province Province = new Province();
		/** 从redis中取配置 */
		String str = commonRedisDao.get(RedisConstant.provinceFix + province_code);
		if (str != null && !"".equals(str)) {
			Province = JSON.parseObject(str, Province.class);
		} else {
			Province province = new Province();
			province.setProvince_code(province_code);
			Province = (Province) super.findOne(province);
			commonRedisDao.set(RedisConstant.provinceFix + province_code, JSON.toJSONString(Province));
		}
		return Province;
	}

	/**
	 * 根据省份代码获取省名称
	 * 
	 * @param @param
	 *            provinceCode
	 * @param @return
	 * @return
	 * @date 2015-4-11 下午08:36:50
	 * @version V1.0
	 */
	@Override
	public String getProvinceNameByCode(String ProvinceCode) {
		if (ProvinceCode == null) {
			return "";
		}
		Province dbProvince = this.getProvinceByCode(ProvinceCode);
		if (dbProvince == null) {
			return "";
		}
		return dbProvince.getProvince_name();
	}

	/**
	 * 根据areasCode 获取 Areas
	 * 
	 * @param areasCode
	 * @return
	 */
	@Override
	public Areas getAreasByCode(String areas_code) {
		Areas Areas = new Areas();
		/** 从redis中取配置 */
		String str = commonRedisDao.get(RedisConstant.areasFix + areas_code);
		if (str != null && !"".equals(str)) {
			Areas = JSON.parseObject(str, Areas.class);
		} else {
//			Areas.setAreasCode(AreasCode);
			Areas = (Areas) super.findOne(Areas);
			commonRedisDao.set(RedisConstant.areasFix + areas_code, JSON.toJSONString(Areas));
		}
		return Areas;
	}

	/**
	 * 根据区代码获取区名称
	 * 
	 * @author aj
	 * @param @param
	 *            AreasCode
	 * @param @return
	 * @return
	 * @date 2015-4-11 下午08:36:50
	 * @version V1.0
	 */
	@Override
	public String getAreasNameByCode(String AreasCode) {
		if (AreasCode == null) {
			return "";
		}
		Areas dbAreas = this.getAreasByCode(AreasCode);
		if (dbAreas == null) {
			return "";
		}
		return dbAreas.getAreas_name();
	}

	/**
	 * 获取guzzRule对象
	 * 
	 * @author aj
	 * @param @param
	 *            areas 区域对象
	 * @param @param
	 *            page
	 * @param @param
	 *            rows
	 * @date 2015-3-9 下午05:09:35
	 * @version V1.0
	 */
	/*
	 * public GuzzRule generateGuzzRuleForAreas(Areas areas, Integer page,
	 * Integer rows) { GuzzRule guzzRule = new GuzzRule();
	 * guzzRule.addTable(Areas.class); guzzRule.addColumn(Areas.class); if(areas
	 * != null &&areas.getCityCode()!=null && !areas.getCityCode().equals("")){
	 * guzzRule.addEqual("CityCode", null, areas.getCityCode()); } if (areas !=
	 * null && areas.getAreasName() != null && !"".equals(areas.getAreasName()))
	 * { guzzRule.addLikeVlaue("AreasName", null, areas.getAreasName()); } if
	 * (page != null && rows != null) { guzzRule.setBeginIndex(page, rows);
	 * guzzRule.setIndexLength(rows); } return guzzRule; }
	 */

	@Override
	public List<City> listIfDredgeCity() {
		String str = commonRedisDao.get(RedisConstant.dredgeCityFix);
		List<City> dredgeCityList = new ArrayList<City>();
		if (str != null && !"".equals(str)) {
			dredgeCityList = JSON.parseArray(str, City.class);
		} else {
			City city = new City();
			city.setIf_dredge(City.ifDredge_yes);
			dredgeCityList = this.baseDao.selectList(CLASSNAME + ".generateGuzzRule", city);
			if (dredgeCityList != null) {
				commonRedisDao.set(RedisConstant.dredgeCityFix, JSON.toJSONString(dredgeCityList));
			}
		}
		return dredgeCityList;
	}

	/*
	 * private GuzzRule generateGuzzRule(City city) { GuzzRule guzzRule = new
	 * GuzzRule(); guzzRule.addTable(City.class).addColumn(City.class); if
	 * (city.getIfDredge() != null) { guzzRule.addEqual("ifDredge", null,
	 * city.getIfDredge()); } if (city.getHotFlag() != null) {
	 * guzzRule.addEqual("hotFlag", null, city.getHotFlag()); }
	 * if(StringUtil.checkNotNull(city.getCityCode())){
	 * guzzRule.addLikeVlaue("cityCode", null,city.getCityCode()); } return
	 * guzzRule; }
	 */

	@Override
	public List<City> listHotCity() {
		String str = commonRedisDao.get(RedisConstant.hotCityFix);
		List<City> hotCityList = new ArrayList<City>();
		if (str != null && !"".equals(str)) {
			hotCityList = JSON.parseArray(str, City.class);
		} else {
			City city = new City();
			city.setHot_flag(City.hotFlag_yes);
			hotCityList = this.baseDao.selectList(CLASSNAME + ".generateGuzzRule", city);
			if (hotCityList != null) {
				commonRedisDao.set(RedisConstant.hotCityFix, JSON.toJSONString(hotCityList));
			}
		}
		return hotCityList;
	}

	/*
	 * 保存城市
	 */
	@Override
	public void saveCity(City city) {
		City cityInfo = (City) super.findOne(city);
		if (cityInfo != null) {
			throw new BusinessException("城市已经存在");
		}
		city.setStatus(CommonConstant.STATUS_USED);
		super.save(city);
		clearCityRedis(city);
	}
		
	@Override
	public void updateIfDredgeTwo(Long id, Integer ifDredge) {
		City City =new City();
		City.setIf_dredge(ifDredge);
		City.setId(id);
		super.baseDao.update(CLASSNAME+".updateIfDredge", City);
		commonRedisDao.delete(RedisConstant.hotCityFix);

	}

	@Override
	public void updataCityStatus(City city) {
		super.update(city);
	}
	
	 @Override
    public void updateCityStatus(City city) {
        City dbCity = this.findCityById(city.getId());
        dbCity.setStatus(city.getStatus());
        super.update(dbCity);
        this.clearCityRedis(dbCity);
    }
}
