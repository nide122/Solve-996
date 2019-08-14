/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.admin.web.base.controller;

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
import com.createTemplate.model.annotation.AntMethod;
import com.createTemplate.model.annotation.SystemControllerLog;
import com.createTemplate.model.base.pojo.Areas;
import com.createTemplate.model.base.pojo.City;
import com.createTemplate.model.base.pojo.Province;
import com.createTemplate.model.base.vo.AreasVo;
import com.createTemplate.model.base.vo.CityVo;
import com.createTemplate.model.base.vo.ProvinceVo;
import com.createTemplate.model.exception.BusinessException;
import com.createTemplate.model.resultvo.Grid;
import com.createTemplate.model.resultvo.ResultInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * 城市接口
 * @version V1.0
 */
@RequestMapping(value="/service/province")
@RestController
@Scope(value = "prototype")
@Api(value = "省份controller", tags = {"省份操作接口"})
public class ProvinceController  extends CommonBaseController{

	@Reference(version = "1.0.0",check = false)
	private ProvinceService provinceService;
	
	
    
    
    /**
     * @Description: 获取省份数据
     * @Author: xf
     * @Version: V1.00
     * @Create Date: 2015-1-8下午2:27:17
     * @Parameters:@return
     */
    @ApiOperation(value = "获取省份数据", notes = "获取省份数据")
    @RequestMapping(value = "/listProvince", method = { RequestMethod.POST })
    public ResultInfo<List<Province>> findProvince() {
        List<Province> provinceList = provinceService.listProvince();
        return new ResultInfo<List<Province>>(ResultInfo.SUCCESS, "成功", provinceList);
    }

    /**
     * @Description: 根据省份获取市数据
     * @Author: xf
     * @Version: V1.00
     * @Create Date: 2015-1-8下午2:27:17
     * @Parameters:@return
     */
    @ApiOperation(value = "根据省份获取市数据", notes = "根据省份获取市数据")
    @RequestMapping(value = "/listCity", method = { RequestMethod.POST })
    public ResultInfo<List<City>> listCity( @RequestBody(required=false) CityVo cityBase) {
        List<City> cityBaseList = provinceService.listCity(cityBase);
        return new ResultInfo<List<City>>(ResultInfo.SUCCESS, "成功", cityBaseList);
    }

    /**
     * @Description: 根据市获取区县数据
     * @Author: xf
     * @Version: V1.00
     * @Create Date: 2015-1-8下午2:27:17
     * @Parameters:@return
     */
    @ApiOperation(value = "根据市获取区县数据", notes = "根据市获取区县数据")
    @AntMethod(author = "", method = "post", name = "根据市获取区县数据", desc = "根据市获取区县数据")
    @RequestMapping(value = "/listAreas", method = { RequestMethod.POST })
    @SystemControllerLog(description = "根据市获取区县数据")
    public ResultInfo<List<Areas>> listAreas( @RequestBody(required=false) AreasVo areasBase) {
        List<Areas> areasBaseList = provinceService.listAreas(areasBase);
        return new ResultInfo<List<Areas>>(ResultInfo.SUCCESS, "成功", areasBaseList);
    }

    /**
     * 省份查询
     * 
     * @param cityVo
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province_name", value = "省名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "province_code", value = "省代码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "行数", required = true, dataType = "int", paramType = "query") })
    @ApiOperation(value = "省份查询", notes = "省份查询")
    @RequestMapping(value = "/getProvincePage", method = { RequestMethod.POST })
    public ResultInfo<Grid<ProvinceVo>> getProvincePage( ProvinceVo provinceVo) {
        if (provinceVo.getPage() == null || provinceVo.getRows() == null) {
            throw new BusinessException("page,rows不能为空");
        }
        return new ResultInfo<Grid<ProvinceVo>>(ResultInfo.SUCCESS, "成功", provinceService.getProvincePage(provinceVo));
    }

    /**
     * 城市查询
     * 
     * @param cityVo
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "行数", required = true, dataType = "Integer", paramType = "query") })
    @ApiOperation(value = "城市查询", notes = "城市查询")
    @RequestMapping(value = "/getCityPage", method = { RequestMethod.POST })
    public ResultInfo<Grid<CityVo>> getCityPage( @RequestBody(required=false) CityVo cityVo) {
        if (cityVo.getPage() == null || cityVo.getRows() == null) {
            throw new BusinessException("page,rows不能为空");
        }
        return new ResultInfo<Grid<CityVo>>(ResultInfo.SUCCESS, "成功", provinceService.getCityPage(cityVo));
    }

    /**
     * 区域查询
     * 
     * @param areasVo
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "行数", required = true, dataType = "Integer", paramType = "query") 
            })
    @ApiOperation(value = "区域查询", notes = "区域查询")
    @RequestMapping(value = "/getAreasPage", method = { RequestMethod.POST })
    public ResultInfo<Grid<AreasVo>> getAreasPage( @RequestBody(required=false) AreasVo areasVo) {
        if (areasVo.getPage() == null || areasVo.getRows() == null) {
            throw new BusinessException("page,rows不能为空");
        }
        return new ResultInfo<Grid<AreasVo>>(ResultInfo.SUCCESS, "成功", provinceService.getAreasPage(areasVo));
    }

    /**
     * 查询开通的城市
     * 
     * @param
     * @version V1.0
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "if_dredge", value = "是否开通0 未开通  1开通数", required = true, dataType = "Integer", paramType = "query") })
    @ApiOperation(value = "查询开通城市", notes = "查询开通城市")
    @RequestMapping(value = "/listIfDredgeCity", method = { RequestMethod.POST })
    @SystemControllerLog(description = "查询开通城市")
    public ResultInfo<List<City>> listIfDredgeCity( @RequestBody(required=false) CityVo cityVo) {
        if (cityVo.getIf_dredge() == null) {
            throw new BusinessException("ifDredge不能为空");
        }
        List<City> list = provinceService.listIfDredgeCity(cityVo);
        return new ResultInfo<List<City>>(ResultInfo.SUCCESS, "成功", list);
    }

    /**
     * 查询热门的城市
     * 
     * @param
     * @version V1.0
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hot_flag", value = "是否热门城市", required = true, dataType = "Integer", paramType = "query") })
    @ApiOperation(value = "查询热门城市列表", notes = "查询热门城市列表")
    @RequestMapping(value = "/listHotCity", method = { RequestMethod.POST })
    @SystemControllerLog(description = "查询热门城市列表")
    public ResultInfo<List<City>> listHotCity( @RequestBody(required=false) CityVo cityVo) {
        if (cityVo.getHot_flag() == null) {
            throw new BusinessException("hotFlag不能为空");
        }
        List<City> hotList = provinceService.listHotCity(cityVo);
        return new ResultInfo<List<City>>(ResultInfo.SUCCESS, "成功", hotList);
    }

    /**
     * 是否设置热门
     * 
     * @auther hhf
     * @param city
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hot_flag", value = "是否热门城市", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query") })
    @ApiOperation(value = "是否设置热门", notes = "是否设置热门")
    @RequestMapping(value = "/updateHotFlag", method = { RequestMethod.POST })
    public ResultInfo<Object> updateHotFlag( @RequestBody(required=false) City city) {
        if (city.getHot_flag() == null) {
            throw new BusinessException("hotFlag不能为空");
        }
        provinceService.updateHotFlag(city.getId(), city.getHot_flag());
        return new ResultInfo<>(ResultInfo.SUCCESS, "设置成功", null);
    }

    /**
     * 是否开通城市
     * 
     * @auther hhf
     * @param city
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "if_dredge", value = "是否开通0 未开通  1开通数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query") })
    @ApiOperation(value = "是否开通城市", notes = "是否开通城市")
    @RequestMapping(value = "/updateIfDredge", method = { RequestMethod.POST })
    public ResultInfo<Object> updateIfDredge( @RequestBody(required=false) City city) {
        if (city.getIf_dredge() == null) {
            throw new BusinessException("ifDredge不能为空");
        }
        provinceService.updateIfDredgeTwo(city.getId(), city.getIf_dredge());
        return new ResultInfo<>(ResultInfo.SUCCESS, "成功", null);
    }
    
    /**
     * 修改城市状态
     * 
     * @param city
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态 0 正常；1 禁用", required = true, dataType = "int", paramType = "query") })
    @ApiOperation(value = "修改城市状态", notes = "修改城市状态")
    @RequestMapping(value = "/updateCityStatus", method = { RequestMethod.POST })
    public ResultInfo<Object> updateCityStatus( @RequestBody(required=false) City city) {
        if (city.getId() == null || city.getStatus()==null) {
            throw new BusinessException("id、状态值不能为空");
        }
        provinceService.updateCityStatus(city);
        return new ResultInfo<>(ResultInfo.SUCCESS, "设置成功", null);
    }
    
    /**
     * 保存城市
     * 
     * @return
     */
    @ApiOperation(value = "保存城市", notes = "保存城市")
    @RequestMapping(value = "/saveCity", method = { RequestMethod.POST })
    public ResultInfo<Object> saveCity( @RequestBody(required=false) City city) {
        if (city.getIf_dredge() == null) {
            throw new BusinessException("ifDredge不能为空");
        }
        provinceService.save(city);
        return new ResultInfo<>(ResultInfo.SUCCESS, "成功", null);
    }

    /**
     * 查询开通的城市以及热门城市（按照拼音顺序排列）
     * 
     * @param
     * @version V1.0
     */
    @ApiOperation(value = "查询开通的城市以及热门城市（按照拼音顺序排列）", notes = "查询开通的城市以及热门城市（按照拼音顺序排列）")
    @AntMethod(author = "", method = "post", name = "查询开通的城市以及热门城市（按照拼音顺序排列）", desc = "查询开通的城市以及热门城市（按照拼音顺序排列）")
    @RequestMapping(value = "/getCityListMap", method = { RequestMethod.POST })
    @SystemControllerLog(description = "查询开通的城市以及热门城市（按照拼音顺序排列）")
    public ResultInfo<Map> getCityListMap( @RequestBody(required=false) CityVo cityVo) {
        List<City> list = provinceService.listIfDredgeCity(cityVo);
        List<City> hotList = provinceService.listHotCity(cityVo);
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
        return new ResultInfo<Map>(ResultInfo.SUCCESS, "成功", result);
    }

    private void buildPingYinList(List<City> listABCDEF, List<City> listGHIJ, List<City> listKLMN, List<City> listOPRQ,
            List<City> listSTUV, List<City> listWXYZ, City city) {
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
