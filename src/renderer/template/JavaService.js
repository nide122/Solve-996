const path = require('path')
const fs = require('fs')
import FileOpt from '@/models/FileOpt'
export default class JavaService {
  constructor(obj) {
    /***********    非空判断      *********/
    if (obj.pojo_path.length == 0) {
      throw new Error("缺少pojo_path");
    }
    if (obj.vo_path.length == 0) {
      throw new Error("缺少vo_path");
    }
    if (obj.service_path.length == 0) {
      throw new Error("缺少service_path");
    }
    if (obj.service_impl_path.length == 0) {
      throw new Error("缺少service_impl_path");
    }
    if (obj.mapper_path.length == 0) {
      throw new Error("缺少mapper_path");
    }
    /***********    处理pojo_path 和 vo_path    *********/

    obj.pojo_path = path.parse(obj.pojo_path[1])
    obj.vo_path = path.parse(obj.vo_path[1])

    /***********    生成默认参数    *********/
    // 实体类生成参数
    this.formData = obj
    // 生成文件后缀
    this.file_suffix = '.java'
    // 生成xml后缀
    this.mapper_suffix = 'Mapper.xml'
    
    /***********    模版参数      *********/

    // 作者
    this.AUTHOR = FileOpt.AUTHOR
    // 创造者公司
    this.COMPANY = FileOpt.COMPANY
    // 版本
    this.VERSION = '1.0.0'
    // 当前日期
    this.NOWDATE = new Date().Format('yyyy年MM月dd日 hh:mm:ss')
    // 实体类
    this.POJO = this.formData.pojo_path.name
    // 实体类VO
    this.VO = this.formData.vo_path.name
    // serviceName
    this.SERVICE_NAME = this.POJO + 'Service'
    // serviceImplName
    this.SERVICE_IMPL_NAME = this.POJO + 'ServiceImpl'
    // controllerName
    this.CONTROLLER_NAME = this.POJO + 'Controller'
    // e2e name
    this.E2E_NAME = this.POJO + 'Test'

    // service 文件地址
    if (this.formData.service_path && this.formData.service_path.length > 0) {
      this.service_file_path = path.join(this.formData.service_path, this.SERVICE_NAME + this.file_suffix)
    }
    // service impl 文件地址
    if (this.formData.service_impl_path && this.formData.service_impl_path.length > 0) {
      this.service_impl_file_path = path.join(this.formData.service_impl_path, this.SERVICE_IMPL_NAME + this.file_suffix)
    }
    // mapper 文件地址
    if (this.formData.mapper_path && this.formData.mapper_path.length > 0) {
      this.mapper_file_path = path.join(this.formData.mapper_path, this.POJO + this.mapper_suffix)
    }
    // Controller 文件地址
    if (this.formData.controller_path && this.formData.controller_path.length > 0) {
      this.controller_path = path.join(this.formData.controller_path, this.CONTROLLER_NAME + this.file_suffix)
    }
    // Test 文件地址
    if (this.formData.e2e_path && this.formData.e2e_path.length > 0) {
      this.e2e_path = path.join(this.formData.e2e_path, this.E2E_NAME + this.file_suffix)
    }
  }
  /**
   * 创建service serviceImpl Mapper
   * @param {默认参数} formData 
   */
  create() {
    // 生成service
    if (this.service_file_path) {
      const serviceTemplate = this.createServiceTemplate()
      fs.writeFileSync(this.service_file_path, serviceTemplate)
    }
    // 生成serviceImpl
    if (this.service_impl_file_path) {
      const serviceImplTemplate = this.createServiceImplTemplate()
      fs.writeFileSync(this.service_impl_file_path, serviceImplTemplate)
    }

    // 生成xml
    if (this.mapper_file_path) {
      const mapperTemplate = this.createMapperTemplate()
      fs.writeFileSync(this.mapper_file_path, mapperTemplate)
    }

    // 生成Controller
    if (this.controller_path) {
      const controllerTemplate = this.createControllerTemplate()
      fs.writeFileSync(this.controller_path, controllerTemplate)
    }
    // 生成Test
    if (this.e2e_path) {
      const e2eTemplate = this.createE2eTemplate()
      fs.writeFileSync(this.e2e_path, e2eTemplate)
    }
  }
  /**
   * 生成Controller
   */
  createControllerTemplate() {
    const POJO = this.POJO
    const VO = this.VO
    const POJO_VARIABLE = this.getParamVariableFormat(POJO)
    const VO_VARIABLE = this.getParamVariableFormat(VO)
    const SERVICE_NAME_VARIABLE = this.getParamVariableFormat(this.SERVICE_NAME)
    let TABLE_COL = ""
    let TABLE_COL_CHECK = ""
    // 生成非空判断，用于保存接口
    if(this.formData.table_col_arr && this.formData.table_col_arr.length > 0){
      TABLE_COL = `@ApiImplicitParams({
        ${
          this.formData.table_col_arr.map( ele => {
            return '@ApiImplicitParam(name = "'+ ele.col +'", value = "'+ (ele.name || ele.col)  +'", required = true, dataType = "'+ ele.type +'", paramType = "query")'
          }).join(',\r\n\t\t')
        }})`
      TABLE_COL_CHECK = `CheckExistParamUtil.getInstance().
      ${
        this.formData.table_col_arr.map( ele => {
          return 'addCheckParam("'+ ele.col +'", '+ VO_VARIABLE +'.get'+ FileOpt.getSetFormat(ele.col) +'(), "'+ (ele.name || ele.col) +'")'
        }).join(".\r\n\t\t")
      }
      .check();`
    }

    const template =
      `
/**
 * Copyright (c) 2017-2020 ${this.COMPANY}. All rights reserved. 
 * 注意：本内容仅限于${this.COMPANY}内部传阅，禁止外泄以及用于其他的商业目的 
 */
package ${FileOpt.getPackageName(this.formData.controller_path, 'com')};

import ${this.getPackageNameByFileName(POJO + '.java')};
import ${this.getPackageNameByFileName(VO + '.java')};
import ${this.getPackageNameByFileName('BusinessException.java')};
import ${this.getPackageNameByFileName('Grid.java')};
import ${this.getPackageNameByFileName('CheckExistParamUtil.java')};
import ${FileOpt.getPackageName(this.service_file_path, 'com')};
import ${this.getPackageNameByFileName('CommonBaseController.java')};
import ${this.getPackageNameByFileName('ResultInfo.java')};
import ${this.getPackageNameByFileName('StringUtil.java')};

import java.util.HashSet;
import java.util.List;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ${this.AUTHOR}
 * @date: ${this.NOWDATE}
 * @version V${this.VERSION}
 */
@RestController
@RequestMapping(value = "/service/${POJO_VARIABLE}")
@Api(value = "${this.CONTROLLER_NAME}", tags = { "${POJO}操作接口" })
public class ${this.CONTROLLER_NAME} extends CommonBaseController{
  @Reference(version = "1.0.0", check = false)
  ${this.SERVICE_NAME} ${SERVICE_NAME_VARIABLE};

  /**
   * 保存
   * @param ${VO_VARIABLE}
   * @author: ${this.AUTHOR}
   */
  ${TABLE_COL}
  @ApiOperation(value = "保存信息", notes = "保存信息")
  @RequestMapping(value = "/save${POJO}", method = RequestMethod.POST)
  public ResultInfo<Object> save${POJO} (@RequestBody(required = false) ${VO} ${VO_VARIABLE}){
      ${TABLE_COL_CHECK}
      ${SERVICE_NAME_VARIABLE}.save${POJO}(${VO_VARIABLE});
      return new ResultInfo<Object>(ResultInfo.SUCCESS, "保存成功", null);
  };
  
  /**
   * 更新 
   * @param ${VO_VARIABLE}
   * @author: ${this.AUTHOR}
   */
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "query")
  })
  @ApiOperation(value = "更新信息", notes = "更新信息")
  @RequestMapping(value = "/update${POJO}", method = RequestMethod.POST)
  public ResultInfo<Object>  update${POJO} (@RequestBody(required = false) ${VO} ${VO_VARIABLE}){
      CheckExistParamUtil.getInstance()
        .addCheckParam("id", ${VO_VARIABLE}.getId(), "id")
        .check();
      ${SERVICE_NAME_VARIABLE}.update${POJO}(${VO_VARIABLE});
      return new ResultInfo<Object>(ResultInfo.SUCCESS, "更新成功", null);
  };

  /**
   * 根据ID查询详情
   * @param ${VO_VARIABLE}
   * @author: ${this.AUTHOR}
   */
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "query")
  })
  @ApiOperation(value = "根据ID查询详情", notes = "根据ID查询详情")
  @RequestMapping(value = "/findById", method = RequestMethod.POST)
  public ResultInfo<${POJO}> findById(@RequestBody(required = false) ${VO} ${VO_VARIABLE}) {
      CheckExistParamUtil.getInstance()
        .addCheckParam("id", ${VO_VARIABLE}.getId(), "id")
        .check();
      return new ResultInfo<${POJO}>(ResultInfo.SUCCESS, "成功", ${SERVICE_NAME_VARIABLE}.findById(${VO_VARIABLE}.getId()));
  }

  /**
   * 根据ID查询详情
   * @param ${VO_VARIABLE}
   * @author: ${this.AUTHOR}
   */
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "query")
  })
  @ApiOperation(value = "根据ID查询详情", notes = "根据ID查询详情")
  @RequestMapping(value = "/findVOById", method = RequestMethod.POST)
  public ResultInfo<${VO}> findVOById(@RequestBody(required = false) ${VO} ${VO_VARIABLE}){
      CheckExistParamUtil.getInstance()
          .addCheckParam("id", ${VO_VARIABLE}.getId(), "id")
          .check();
      return new ResultInfo<${VO}>(ResultInfo.SUCCESS, "成功", ${SERVICE_NAME_VARIABLE}.findVOById(${VO_VARIABLE}.getId()));
  }

  /**
   * 根据ids 查询
   * @param ${VO_VARIABLE} id集合
   * @author: ${this.AUTHOR}
   */
  @ApiImplicitParams({
    @ApiImplicitParam(name = "ids", value = "ID集合 ，分隔", required = true, dataType = "String", paramType = "query")
  })
  @ApiOperation(value = "根据ids 查询", notes = "根据ids 查询")
  @RequestMapping(value = "/findByIds", method = RequestMethod.POST)
  public ResultInfo<List<${POJO}>> findByIds(@RequestBody(required = false) ${VO} ${VO_VARIABLE}) {
      CheckExistParamUtil.getInstance()
          .addCheckParam("ids", ${VO_VARIABLE}.getIds(), "ids")
          .check();
      return new ResultInfo<List<${POJO}>>(ResultInfo.SUCCESS, "成功", ${SERVICE_NAME_VARIABLE}.findByIds(new HashSet<Long>(StringUtil.stringToLongList(${VO_VARIABLE}.getIds()))));
  }

  /**
   * 根据ID删除数据
   * @param ${VO_VARIABLE}
   * @author: ${this.AUTHOR}
   */
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "query")
  })
  @ApiOperation(value = "根据ID删除数据", notes = "根据ID删除数据")
  @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
  public ResultInfo<Object> deleteById(@RequestBody(required = false) ${VO} ${VO_VARIABLE}) {
      CheckExistParamUtil.getInstance()
          .addCheckParam("id", ${VO_VARIABLE}.getId(), "id")
          .check();
      ${SERVICE_NAME_VARIABLE}.deleteById(${VO_VARIABLE}.getId());
      return new ResultInfo<Object>(ResultInfo.SUCCESS, "成功", null);
  }

  /**
   * 分页查询
   * @param ${VO_VARIABLE}
   * @author: ${this.AUTHOR}
   */
  @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "当前页数", required = true, dataType = "Integer", paramType = "query"),
    @ApiImplicitParam(name = "rows", value = "条数", required = true, dataType = "Integer", paramType = "query")
  })
  @ApiOperation(value = "分页查询", notes = "分页查询")
  @RequestMapping(value = "/find${POJO}Page", method = RequestMethod.POST)
  public ResultInfo<Grid<${VO}>> find${POJO}Page (@RequestBody(required = false) ${VO} ${VO_VARIABLE}){
      CheckExistParamUtil.getInstance()
          .addCheckParam("page", ${VO_VARIABLE}.getPage(), "page")
          .addCheckParam("rows", ${VO_VARIABLE}.getRows(), "rows")
          .check();
      return new ResultInfo<Grid<${VO}>>(ResultInfo.SUCCESS, "查询成功", ${SERVICE_NAME_VARIABLE}.find${POJO}Page(${VO_VARIABLE}));
  };
}`
    return template
  }
  /**
   * 生成Test
   */
  createE2eTemplate() {
    let sort_name = this.formData.e2e_path.substring(0,this.formData.e2e_path.indexOf(/src/))
    let application = new FileOpt().findFileByKey(this.formData.project_path, 'Application.java').filter( ele => {
      return ele.value.indexOf(sort_name) >= 0
    })
    const POJO = this.POJO
    const VO = this.VO
    const POJO_VARIABLE = this.getParamVariableFormat(POJO)
    const VO_VARIABLE = this.getParamVariableFormat(VO)
    const SERVICE_NAME_VARIABLE = this.getParamVariableFormat(this.SERVICE_NAME)
    const template =
`
/**
 * Copyright (c) 2017-2020 ${this.COMPANY}. All rights reserved. 
 * 注意：本内容仅限于${this.COMPANY}内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.${this.formData.project_name};

import ${this.getPackageNameByFileName(POJO + '.java')};
import ${FileOpt.getPackageName(application[0].value, 'com')};
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import ${this.getPackageNameByFileName(POJO + '.java')};
import ${this.getPackageNameByFileName(VO + '.java')};
import ${FileOpt.getPackageName(this.service_file_path, 'com')};
import ${this.getPackageNameByFileName('Grid.java')};
import ${this.getPackageNameByFileName('StringUtil.java')};
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ${this.SERVICE_NAME} 测试
 * @author: ${this.AUTHOR}
 * @date: ${this.NOWDATE}
 * @version V${this.VERSION}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
//@Rollback
//@Transactional
public class ${this.E2E_NAME} {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  private ${this.SERVICE_NAME} ${SERVICE_NAME_VARIABLE};

  /**
   * 保存
   * @author: ${this.AUTHOR}
   */
  @Test
  public void save${POJO} (){
      ${VO} ${VO_VARIABLE} = new ${VO} ();
      ${VO_VARIABLE}.setId(1L);
      //TODO: 生成必填字段
      ${SERVICE_NAME_VARIABLE}.save${POJO}(${VO_VARIABLE});
      logger.error("save ------" + JSON.toJSONString(${VO_VARIABLE}));
  };
  
  /**
   * 更新 
   * @author: ${this.AUTHOR}
   */
  @Test
  public void update${POJO} (){
      ${VO} ${VO_VARIABLE} = new ${VO} ();
      ${VO_VARIABLE}.setId(1L);
      ${SERVICE_NAME_VARIABLE}.update${POJO}(${VO_VARIABLE});
    logger.error("update ------" + JSON.toJSONString(${VO_VARIABLE}));
  };

  /**
   * 根据ID查询详情
   * @author: ${this.AUTHOR}
   */
  @Test
  public void findById() {
      ${POJO} db = ${SERVICE_NAME_VARIABLE}.findVOById(1L);
      logger.error("findById ------" + JSON.toJSONString(db));
  }

  /**
   * 根据ID查询详情
   * @author: ${this.AUTHOR}
   */
  @Test
  public void findVOById(){
      ${POJO} db = ${SERVICE_NAME_VARIABLE}.findVOById(1L);
      logger.error("findVOById ------" + JSON.toJSONString(db));
  }

  /**
   * 根据ids 查询
   * @author: ${this.AUTHOR}
   */
  @Test
  public void findByIds() {
      List<${POJO}> list =${SERVICE_NAME_VARIABLE}.findByIds(new HashSet<Long>(StringUtil.stringToLongList("1,2,3")));
      logger.error("findByIds ------" + JSON.toJSONString(list));
  }

  /**
   * 根据ID删除数据
   * @author: ${this.AUTHOR}
   */
  @Test
  public void deleteById() {
      ${SERVICE_NAME_VARIABLE}.deleteById(1L);
      logger.error("deleteById ------ success");
  }

  /**
   * 分页查询
   * @author: ${this.AUTHOR}
   */
  @Test
  public void find${POJO}Page (){
      ${VO} ${VO_VARIABLE} = new ${VO}();
      ${VO_VARIABLE}.setPage(1);
      ${VO_VARIABLE}.setRows(10);
      Grid find${POJO}Page = ${SERVICE_NAME_VARIABLE}.find${POJO}Page(${VO_VARIABLE});
      logger.error("find${POJO}Page ------ " + JSON.toJSONString(find${POJO}Page));
  };

}
`
    return template
  }
  /**
   * 生成实体类
   */
  createMapperTemplate() {
    const POJO = this.POJO
    const VO = this.VO
    const POJO_VARIABLE = this.getParamVariableFormat(POJO)
    const VO_VARIABLE = this.getParamVariableFormat(VO)
    const template =
      `<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${POJO}">
	<resultMap type="${this.getPackageNameByFileName(POJO + '.java')}" id="${POJO_VARIABLE}"/>
  <resultMap type="${this.getPackageNameByFileName(VO + '.java')}" id="${VO_VARIABLE}"/>
  <resultMap type="java.util.HashMap" id="map"/>
	<parameterMap type="${this.getPackageNameByFileName(POJO + '.java')}" id="${POJO_VARIABLE}"/>
	<parameterMap type="${this.getPackageNameByFileName(VO + '.java')}" id="${VO_VARIABLE}"/>
	<parameterMap type="java.util.HashMap" id="map"/>
	
	<!-- 根据ID查询表信息 create by ${this.AUTHOR} -->
	<select id="findById" resultMap="${POJO_VARIABLE}" parameterMap="map">
		SELECT * FROM ${this.formData.table_name} WHERE ID = #{id}
  </select>
	
	<!-- 根据ID查询用户详情 create by ${this.AUTHOR} -->
	<select id="findVOById" resultMap="${VO_VARIABLE}" parameterMap="map">
		SELECT * FROM ${this.formData.table_name} WHERE ID = #{id}
  </select>
  
	<!-- 根据ID数组查询表信息 create by ${this.AUTHOR} -->
	<select id="findByIds" resultMap="${POJO_VARIABLE}" parameterMap="map">
		SELECT * FROM ${this.formData.table_name} WHERE 
		<foreach collection="ids" item="item" open="ID in  (" close=")" separator=",">
      #{item}
    </foreach>
	</select>
	
	<!-- 分页查询表 create by ${this.AUTHOR} -->
	<select id="find${POJO}Page" resultMap="${VO_VARIABLE}" parameterMap="${VO_VARIABLE}">
		SELECT * FROM ${this.formData.table_name} WHERE 1=1 ORDER BY ID ASC
	</select>

</mapper>
`
    return template
  }


  /**
   * 创建service 模版
   * @param {*} obj 
   */
  createServiceTemplate() {
    const POJO = this.POJO
    const VO = this.VO
    const POJO_VARIABLE = this.getParamVariableFormat(POJO)
    const VO_VARIABLE = this.getParamVariableFormat(VO)
    const template =
      `
/**
 * Copyright (c) 2017-2020 ${this.COMPANY}. All rights reserved. 
 * 注意：本内容仅限于${this.COMPANY}内部传阅，禁止外泄以及用于其他的商业目的 
 */
package ${FileOpt.getPackageName(this.formData.service_path, 'com')};

import ${this.getPackageNameByFileName('MybatisDao.java')};
import ${this.getPackageNameByFileName(POJO + '.java')};
import ${this.getPackageNameByFileName(VO + '.java')};
import ${this.getPackageNameByFileName('BusinessException.java')};
import ${this.getPackageNameByFileName('Grid.java')};
import java.util.Set;
import java.util.List;

/**
 * ${this.SERVICE_NAME + '服务'}
 * @author: ${this.AUTHOR}
 * @date: ${this.NOWDATE}
 * @version V${this.VERSION}
 */
public interface ${this.SERVICE_NAME} extends MybatisDao {
    /**
     * 保存
     * @param ${VO_VARIABLE}
     * @author: ${this.AUTHOR}
     */
    public void save${POJO} (${VO} ${VO_VARIABLE})throws BusinessException;
    
    /**
     * 更新 
     * @param ${VO_VARIABLE}
     * @author: ${this.AUTHOR}
     */
    public void update${POJO} (${VO} ${VO_VARIABLE})throws BusinessException;

    /**
     * 根据ID查询pojo
     * @param id
     * @author: ${this.AUTHOR}
     */
    public ${POJO} findById(Long id) throws BusinessException;

    /**
     * 根据ID查询vo
     * @param id
     * @author: ${this.AUTHOR}
     */
    public ${VO} findVOById(Long id) throws BusinessException;

    /**
     * 根据ID集合查询
     * @param ids
     * @author: ${this.AUTHOR}
     */
    public List<${POJO}> findByIds(Set<Long> ids) throws BusinessException;

    /**
     * 根据ID删除数据
     * @param id
     * @author: ${this.AUTHOR}
     */
    public void deleteById(Long id) throws BusinessException;

    /**
     * 分页查询
     * @param ${VO_VARIABLE}
     * @author: ${this.AUTHOR}
     * @return
     */
    public Grid<${VO}> find${POJO}Page (${VO} ${VO_VARIABLE})throws BusinessException;
}`
    return template
  }
  /**
   * 创建serviceImpl 模版
   * @param {*} obj 
   */
  createServiceImplTemplate() {
    const POJO = this.POJO
    const VO = this.VO
    const POJO_VARIABLE = this.getParamVariableFormat(POJO)
    const VO_VARIABLE = this.getParamVariableFormat(VO)
    // serviceImpl模版
    const template =
      `
/**
 * Copyright (c) 2017-2020 ${this.COMPANY}. All rights reserved.
 * 注意：本内容仅限于${this.COMPANY}内部传阅，禁止外泄以及用于其他的商业目的 
 */
package ${FileOpt.getPackageName(this.formData.service_impl_path, 'com')};

import ${this.getPackageNameByFileName('MybatisDaoImpl.java')};
import ${this.getPackageNameByFileName(POJO + '.java')};
import ${this.getPackageNameByFileName(VO + '.java')};
import ${this.getPackageNameByFileName('BusinessException.java')};
import ${this.getPackageNameByFileName('Grid.java')};
import ${this.getPackageNameByFileName('DataUtils.java')};
import ${this.getPackageNameByFileName('PropertyUtils.java')};
import ${this.getPackageNameByFileName('PageParameter.java')};
import ${FileOpt.getPackageName(this.service_file_path, 'com')};

import com.alibaba.dubbo.config.annotation.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ${this.SERVICE_NAME + '服务实现类'}
 * @author: ${this.AUTHOR}
 * @date: ${this.NOWDATE}
 * @version V${this.VERSION}
 */
@Service(version = "1.0.0", retries = 0, timeout = 6000, parameters = { "methods.timeout", "12000" }, interfaceClass = ${this.SERVICE_NAME}.class)
public class ${this.SERVICE_IMPL_NAME} extends MybatisDaoImpl implements ${this.SERVICE_NAME} {

    public final String className = "${POJO}";
    
    /**
     * 保存
     * @param ${VO_VARIABLE}
     * @author: ${this.AUTHOR}
     */
    @Override
    public void save${POJO} (${VO} ${VO_VARIABLE}){
        ${POJO} ${POJO_VARIABLE} = new ${POJO}();
        DataUtils.copyPropertiesIgnoreNull(${VO_VARIABLE}, ${POJO_VARIABLE});
        super.save(${POJO_VARIABLE});
    };
    
    /**
     * 更新 
     * @param ${VO_VARIABLE}
     * @author: ${this.AUTHOR}
     */
    @Override
    public void update${POJO} (${VO} ${VO_VARIABLE}){
        ${POJO} dbObj = this.findById(${VO_VARIABLE}.getId());
        if (dbObj == null) {
            throw new BusinessException("数据不存在");
        }
        DataUtils.copyPropertiesIgnoreNull(${VO_VARIABLE}, dbObj);
        super.update(dbObj);
    };

    /**
     * 根据ID查询详情
     * @param id
     * @author: ${this.AUTHOR}
     */
    @Override
    public ${POJO} findById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("column", PropertyUtils.getPropertyNames(${POJO}.class));
        return this.baseDao.selectOne(className + ".findById", map);
    }

    /**
     * 根据ID查询详情
     * @param id
     * @author: ${this.AUTHOR}
     */
    @Override
    public ${VO} findVOById(Long id){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("column", PropertyUtils.getPropertyNames(${POJO}.class));
        return this.baseDao.selectOne(className + ".findVOById", map);
    }

    /**
     * 根据ids 查询
     * @param ids id集合
     * @author: ${this.AUTHOR}
     */
    @Override
    public List<${POJO}> findByIds(Set<Long> ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", ids);
        map.put("column", PropertyUtils.getPropertyNames(${POJO}.class));
        return this.baseDao.selectList(className + ".findByIds", map);
    }

    /**
     * 根据ID删除数据
     * @param id
     * @author: ${this.AUTHOR}
     */
    @Override
    public void deleteById(Long id) {
      ${POJO} dbObj = this.findById(id);
      if (dbObj == null) {
          throw new BusinessException("数据不存在");
      }
      this.delete(dbObj);
    }

    /**
     * 分页查询
     * @param ${VO_VARIABLE}
     * @author: ${this.AUTHOR}
     */
    @Override
    public Grid<${VO}> find${POJO}Page (${VO} ${VO_VARIABLE}){
      ${VO_VARIABLE}.setColumn(PropertyUtils.getPropertyNames(${POJO}.class));
      ${VO_VARIABLE}.setPageParameter(new PageParameter(${VO_VARIABLE}.getPage(), ${VO_VARIABLE}.getRows()));
      Grid<${VO}> grid = new Grid<>();
      List<${VO}> list = super.baseDao.selectList(className + ".find${POJO}Page", ${VO_VARIABLE});
      grid.setCount(Long.valueOf(${VO_VARIABLE}.getPageParameter().getTotalCount()));
      grid.setList(list);
      return grid;
    };
}`
    return template
  }

  /**
   * 根据文件名获取package
   * @param {文件名} file_name 
   */
  getPackageNameByFileName(file_name , folder_name) {
    let search_file_path = new FileOpt().findOneFileByKey(file_name, folder_name ? folder_name : this.formData.project_path).value
    return search_file_path.length > 0 ? FileOpt.getPackageName(search_file_path, 'com') : ''
  }

  /**
   * 根据传入实体类获取参数变量
   * @param {*} str 
   */
  getParamVariableFormat(str) {
    return str.charAt(0).toLowerCase() + str.substring(1)
  }
}