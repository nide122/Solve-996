/*
* Copyright (c) 2017-2020 SHENZHEN ZHONGTUIKEJI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市科瑞特网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.createTemplate.admin.web.base.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.createTemplate.api.base.dubbo.service.CommonRedisDao;
import com.createTemplate.api.base.dubbo.service.EnumService;
import com.createTemplate.api.common.controller.CommonBaseController;
import com.createTemplate.api.util.CheckExistParamUtil;
import com.createTemplate.api.util.StringUtil;
import com.createTemplate.model.base.pojo.EnumObj;
import com.createTemplate.model.base.pojo.EnumType;
import com.createTemplate.model.base.vo.EnumObjVo;
import com.createTemplate.model.exception.BusinessException;
import com.createTemplate.model.resultvo.Grid;
import com.createTemplate.model.resultvo.ResultInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * 字典接口
 */
@RestController
@RequestMapping(value = "/service/enum")
@Api(value = "数据字典controller", tags = {"数据字典"})
public class EnumController extends CommonBaseController{

    private static final Logger logger = LoggerFactory.getLogger(EnumController.class);

    @Reference(version = "1.0.0",check = false)
    EnumService enumService;
    @Reference(version = "1.0.0",check = false)
    CommonRedisDao commonRedisDao;

    /**
     * 分页查询数据字典
     * 
     * @param
     * @version V1.0
     * @return 
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "行数", required = true, dataType = "Integer", paramType = "query") })
    @ApiOperation(value = "分页查询数据字典", notes = "分页查询数据字典")
    @RequestMapping(value = "/getPage", method = RequestMethod.POST)
    public ResultInfo<Grid<EnumObjVo>> getPage( @RequestBody(required=false) EnumObjVo enumObjVo) {
        if (enumObjVo.getPage() == null || enumObjVo.getRows() == null) {
            throw new BusinessException("page,rows不能为空");
        }
        return new ResultInfo<Grid<EnumObjVo>>(ResultInfo.SUCCESS, "成功", enumService.getPage(enumObjVo));
    }

    /**
     * 查询数据字典类型
     * @return 
     * 
     * @return
     */
    @ApiOperation(value = "查询数据字典类型", notes = "查询数据字典类型")
    @RequestMapping(value = "/listEnumType", method = RequestMethod.POST)
    public ResultInfo<List<EnumType>> listEnumType( @RequestBody(required=false) EnumType enumType) {
        return new ResultInfo<>(ResultInfo.SUCCESS, "成功", enumService.listEnumType(enumType));
    }

    /**
     * 更新状态
     * @return 
     * 
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "0正常；1删除", required = true, dataType = "Integer", paramType = "query") })
    @ApiOperation(value = "更新状态", notes = "更新状态")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public ResultInfo<Object> updateStatus( @RequestBody(required=false) EnumObjVo enumObjVo) {
        
        CheckExistParamUtil.getInstance()
            .addCheckParam("id", enumObjVo.getId(), "id不能为空")
            .addCheckParam("status", enumObjVo.getStatus(), "状态值不能为空").check();

        enumService.updateStatus(enumObjVo.getId(), enumObjVo.getStatus());
        return new ResultInfo<>(ResultInfo.SUCCESS, "成功", null);

    }

    /**
     * 数据字典查询
     * 
     * @version V1.0
     * @return 
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enum_type_code", value = "字典类型代码", required = true, dataType = "String", paramType = "query") })
    @ApiOperation(value = "数据字典查询", notes = "数据字典查询")
    @RequestMapping(value = "/listEnum", method = RequestMethod.POST)
    public ResultInfo<List<EnumObj>> listEnum( @RequestBody(required=false) EnumObj enumObj) {
        enumObj.setStatus(0); // 只查询正常
        if (StringUtil.checkNull(enumObj.getEnum_type_code())) {
            throw new BusinessException("数据字典类型不能为空");
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, "成功", enumService.listEnum(enumObj.getEnum_type_code(), enumObj.getUpper_id()));
    }

    /**
     * 保存字典
     * 
     * @version V1.0
     * @return 
     */
    @ApiImplicitParams({
        @ApiImplicitParam(name = "enum_type_code", value = "字典类型代码", required = true, dataType = "String", paramType = "query") ,
        @ApiImplicitParam(name = "sort_no", value = "排序", required = true, dataType = "Integer", paramType = "query"),
        @ApiImplicitParam(name = "enum_value", value = "值", required = true, dataType = "String", paramType = "query")})
    @ApiOperation(value = "保存数据字典", notes = "保存数据字典")
    @RequestMapping(value = "/saveEnumObj", method = RequestMethod.POST)
    public ResultInfo<Object> saveEnumObj( @RequestBody(required=false) EnumObj enumObj) {
        // UsersVo sessionUsersVo = super.getCurrentUser();
        // if (!UsersVo.checkRole(sessionUsersVo,
        // CommonConstant.menuEName.sjzdgl,
        // CommonConstant.buttonEName.addButton)) {
        // throw new BusinessException("非法操作");
        // }
        CheckExistParamUtil.getInstance()
        .addCheckParam("enum_type_code", enumObj.getEnum_type_code(), "数据字典类型不能为空")
        .addCheckParam("sort_no", enumObj.getSort_no(), "序号不能为空")
        .addCheckParam("enum_value", enumObj.getEnum_value(), "值不能为空").check();
        //sensitiveWordService.filtrationSensitiveWord("0,1",enumObj.getEnum_value(), SensitivewordFilter.MAXMATCHTYPE);
        enumService.save(enumObj);
        return new ResultInfo<>(ResultInfo.SUCCESS, "成功", null);
    }
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "enum_type_name", value = "字典类型名称", required = true, dataType = "String", paramType = "query") ,
        @ApiImplicitParam(name = "enum_type_code", value = "字典类型代码", required = true, dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "remark", value = "字典类型描述", required = false, dataType = "String", paramType = "query")})
    @ApiOperation(value = "保存数据字典类型", notes = "保存数据字典类型")
    @RequestMapping(value = "/saveEnumType", method = RequestMethod.POST)
    public ResultInfo<Object> saveEnumType(@RequestBody(required=false)  EnumType enumType) {
        // UsersVo sessionUsersVo = super.getCurrentUser();
        // if (!UsersVo.checkRole(sessionUsersVo,
        // CommonConstant.menuEName.sjzdgl,
        // CommonConstant.buttonEName.addButton)) {
        // throw new BusinessException("非法操作");
        // }
        CheckExistParamUtil.getInstance()
        .addCheckParam("enum_type_code", enumType.getEnum_type_code(), "字典类型代码不能为空")
        .addCheckParam("enum_type_name", enumType.getEnum_type_name(), "字典类型名称不能为空").check();
        
        //sensitiveWordService.filtrationSensitiveWord("0,1",enumObj.getEnum_value(), SensitivewordFilter.MAXMATCHTYPE);
        enumService.save(enumType);
        return new ResultInfo<>(ResultInfo.SUCCESS, "成功", null);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "字典类型Id", required = true, dataType = "Long", paramType = "query") ,
        @ApiImplicitParam(name = "enum_type_name", value = "字典类型名称", required = true, dataType = "String", paramType = "query") ,
        @ApiImplicitParam(name = "enum_type_code", value = "字典类型代码", required = true, dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "remark", value = "字典类型描述", required = false, dataType = "String", paramType = "query")})
    @ApiOperation(value = "修改字典类型", notes = "修改字典类型")
    @RequestMapping(value = "/updateEnumType", method = RequestMethod.POST)
    public ResultInfo<Object> updateEnumType(@RequestBody(required=false)  EnumType enumType) {
        // UsersVo sessionUsersVo = super.getCurrentUser();
        // if (!UsersVo.checkRole(sessionUsersVo,
        // CommonConstant.menuEName.sjzdgl,
        // CommonConstant.buttonEName.editButton)) {
        // throw new BusinessException("非法操作");
        // }
        CheckExistParamUtil.getInstance()
        .addCheckParam("id", enumType.getId(), "字典类型id不能为空")
        .addCheckParam("enum_type_code", enumType.getEnum_type_code(), "字典类型代码不能为空")
        .addCheckParam("enum_type_name", enumType.getEnum_type_name(), "字典类型名称不能为空").check();
        
        //sensitiveWordService.filtrationSensitiveWord("0,1",enumObj.getEnum_value(), SensitivewordFilter.MAXMATCHTYPE); 
        
        enumService.update(enumType);
        return new ResultInfo<>(ResultInfo.SUCCESS, "成功", null);

    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "query") ,
        @ApiImplicitParam(name = "enum_type_code", value = "字典类型代码", required = true, dataType = "String", paramType = "query") ,
        @ApiImplicitParam(name = "sort_no", value = "排序", required = true, dataType = "Integer", paramType = "query"),
        @ApiImplicitParam(name = "enum_value", value = "值", required = true, dataType = "String", paramType = "query")})
    @ApiOperation(value = "修改数据字典", notes = "修改数据字典")
    @RequestMapping(value = "/updateEnumObj", method = RequestMethod.POST)
    public ResultInfo<Object> updateEnumObj( @RequestBody(required=false) EnumObj enumObj) {
        // UsersVo sessionUsersVo = super.getCurrentUser();
        // if (!UsersVo.checkRole(sessionUsersVo,
        // CommonConstant.menuEName.sjzdgl,
        // CommonConstant.buttonEName.editButton)) {
        // throw new BusinessException("非法操作");
        // }
        
        CheckExistParamUtil.getInstance()
        .addCheckParam("id", enumObj.getId(), "id不能为空")
        .addCheckParam("enum_type_code", enumObj.getEnum_type_code(), "字典类型不能为空")
        .addCheckParam("enum_value", enumObj.getEnum_value(), "字典值不能为空")
        .addCheckParam("enum_type_name", enumObj.getSort_no(), "序号不能为空").check();
        
        enumService.update(enumObj);
        return new ResultInfo<>(ResultInfo.SUCCESS, "成功", null);

    }

}
