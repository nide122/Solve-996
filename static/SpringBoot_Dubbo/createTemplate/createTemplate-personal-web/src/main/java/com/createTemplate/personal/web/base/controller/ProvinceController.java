/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.personal.web.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.createTemplate.api.base.dubbo.service.ProvinceService;
import com.createTemplate.api.common.controller.CommonBaseController;
import com.createTemplate.model.annotation.SystemControllerLog;
import com.createTemplate.model.base.pojo.Areas;
import com.createTemplate.model.base.pojo.City;
import com.createTemplate.model.base.pojo.Province;
import com.createTemplate.model.base.vo.CityVo;
import com.createTemplate.model.resultvo.ResultInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * 城市接口
 * @author:  
 * @date: 2018年5月15日 下午5:08:30 
 * @version V1.0
 */
@RequestMapping(value="/service/province")
@RestController
@Scope(value = "prototype")
@Api(value = "省份controller", tags = {"省份操作接口"})
public class ProvinceController  extends CommonBaseController{

	@Reference(version = "1.0.0",check = false)
	private ProvinceService provinceBaseService;
	
	/**
	  * @Description: 获取省份数据
	  * @Author: xf
	  * @Version: V1.00 
	  * @Create Date: 2015-1-8下午2:27:17
	  * @Parameters:@return
	 */
	@ApiOperation(value = "获取省份数据", notes = "获取省份数据")
	@RequestMapping(value = "/findProvince", method = RequestMethod.POST)
	public ResultInfo<List<Province>>  findProvince() {
			List<Province> provinceList = provinceBaseService.listProvince();
			return new ResultInfo<List<Province>>(ResultInfo.SUCCESS, "查询成功", provinceList);
	}
	
	/**
	  * @Description: 根据省份获取市数据
	  * @Author: xf
	  * @Version: V1.00 
	  * @Create Date: 2015-1-8下午2:27:17
	  * @Parameters:@return
	 */
	@ApiImplicitParams({
        @ApiImplicitParam(name = "province_code", value = "省份代码", required = true, dataType = "String", paramType = "query")
    })
	@ApiOperation(value = "根据省份获取市数据", notes = "根据省份获取市数据")
	@RequestMapping(value = "/findCity", method = RequestMethod.POST)
	public ResultInfo<List<City>>  findCity( @RequestBody(required=false) City cityBase) {
		List<City> cityBaseList = provinceBaseService.listCity(cityBase);
		return new ResultInfo<List<City>>(ResultInfo.SUCCESS, "查询成功", cityBaseList);
	}
	
	/**
	  * @Description: 根据市获取区县数据
	  * @Author: xf
	  * @Version: V1.00 
	  * @Create Date: 2015-1-8下午2:27:17
	  * @Parameters:@return
	 */
	@ApiImplicitParams({
        @ApiImplicitParam(name = "city_code", value = "市代码", required = true, dataType = "String" , paramType = "query")
    })
    @ApiOperation(value = "根据市获取区县数据", notes = "根据市获取区县数据")
	@RequestMapping(value = "/findAreas", method = RequestMethod.POST)
	@SystemControllerLog(description = "根据市获取区县数据")
	public ResultInfo<List<Areas>>   findAreas( @RequestBody(required=false) Areas areasBase) {
			List<Areas> areasBaseList = provinceBaseService.listAreas(areasBase);
			return new ResultInfo<List<Areas>>(ResultInfo.SUCCESS, "查询成功", areasBaseList);
	}
	
	
	/**
	 * 查询开通的城市
	 * 
	 * @param
	 * @version V1.0
	 */
    @ApiOperation(value = "查询开通城市", notes = "查询开通城市")
	@RequestMapping(value = "/findIfDredgeCity", method = RequestMethod.POST)
	@SystemControllerLog(description = "查询开通城市")
	public ResultInfo<List<City>>   findIfDredgeCity( @RequestBody(required=false) CityVo cityVo) {
			List<City> list = provinceBaseService.listIfDredgeCity(cityVo);
			return new ResultInfo<List<City>>(ResultInfo.SUCCESS, "查询成功", list);
	}
	
	
	@ApiOperation(value = "查询热门城市列表", notes = "查询热门城市列表")
	@RequestMapping(value = "/findHotCityList", method = RequestMethod.POST)
	@SystemControllerLog(description = "查询热门城市列表")
	public ResultInfo<List<City>> findHotCityList( @RequestBody(required=false) CityVo cityVo){
		List<City> hotList  =provinceBaseService.listHotCity(cityVo);
		return new ResultInfo<List<City>>(ResultInfo.SUCCESS, "查询成功", hotList);
	}
	
	@ApiOperation(value = "查询开通的城市以及热门城市（按照拼音顺序排列）", notes = "查询开通的城市以及热门城市（按照拼音顺序排列）")
	@RequestMapping(value = "/findCityListMap", method = RequestMethod.POST)
	@SystemControllerLog(description = "查询开通的城市以及热门城市（按照拼音顺序排列）")
	public ResultInfo<Map> findCityListMap( @RequestBody(required=false) CityVo cityVo){
        List<City> list = provinceBaseService.listIfDredgeCity(cityVo);
        List<City> hotList = provinceBaseService.listHotCity(cityVo);
        Map result = new HashMap();
        result.put("hotList", hotList);
        List<City> listABCDEF = new ArrayList<City>();
        List<City> listGHIJ = new ArrayList<City>();
        List<City> listKLMN = new ArrayList<City>();
        List<City> listOPRQ = new ArrayList<City>();
        List<City> listSTUV = new ArrayList<City>();
        List<City> listWXYZ = new ArrayList<City>();
        for (City city : list) {
            if (city.getFirst_pinyin() == null) {
                continue;
            }
            // 根据城市的拼音放入对应的集合
            buildPingYinList(listABCDEF, listGHIJ, listKLMN, listOPRQ, listSTUV, listWXYZ, city);
        }
        result.put("listABCDEF", listABCDEF);
        result.put("listGHIJ", listGHIJ);
        result.put("listKLMN", listKLMN);
        result.put("listOPRQ", listOPRQ);
        result.put("listSTUV", listSTUV);
        result.put("listWXYZ", listWXYZ);
        return new ResultInfo<Map>(ResultInfo.SUCCESS, "查询成功", result);
	}

	private void buildPingYinList(List<City> listABCDEF, List<City> listGHIJ, List<City> listKLMN, List<City> listOPRQ, List<City> listSTUV,
			List<City> listWXYZ, City city) {
		switch (city.getFirst_pinyin().toUpperCase()) {
		case "A":
		case "B":
		case "C":
		case "D":
		case "E":
		case "F":
			listABCDEF.add(city);
			break;
		case "G":
		case "H":
		case "I":
		case "J":
			listGHIJ.add(city);
			break;	
		case "K":
		case "L":
		case "M":
		case "N":
			listKLMN.add(city);
			break;		
		case "O":
		case "P":
		case "R":
		case "Q":
			listOPRQ.add(city);
			break;		
		case "S":
		case "T":
		case "U":
		case "V":
			listSTUV.add(city);
			break;
		case "W":
		case "X":
		case "Y":
		case "Z":
			listWXYZ.add(city);
			break;				
		default:
			break;
		}
	}
	
	
}
