import * as common from '@/template/java'
import FileOpt from '@/models/FileOpt'
// 创造者公司
const COMPANY = common.COMPANY
// 作者
const AUTHOR = common.AUTHOR

const table_split_str = 'CREATE TABLE IF NOT EXISTS'
const db_split_begin_str = 'EXISTS'
const table_split_name_str = '.'
const table_split_comment_str = 'COMMENT = '
const cloumArr_split_begin_str = '('
const cloumArr_split_end_str = ')'
const cloum_split_name_begin_str = '`'
const cloum_split_name_end_str = '`'
const cloum_split_comment_begin_str = "COMMENT '"
const cloum_split_comment_end_str = "'"
/**
 * 根据sql地址 获取JSON
 * @param {*} sql_path 
 */
function getSqlJSON(sql_path) {
  // 解析sql文件
  const stats = common.fs.statSync(sql_path)
  const tableArr = []
  if (stats.isFile()) {
    const data = common.fs.readFileSync(sql_path, 'utf-8')
    const dataArr = data.split(';')
    dataArr.forEach(function (ele, index) {
      if (ele.indexOf(table_split_str) >= 0) {
        const tableObj = {
          dbName: ele.slice(ele.indexOf(db_split_begin_str) + db_split_begin_str.length, ele.indexOf('.') - 1).replaceAll('`', '').trim(),
          tableName: ele.slice(ele.indexOf(table_split_name_str) + table_split_name_str.length, ele.indexOf('(')).replaceAll('`', '').trim(),
          sql: ele,
          cloums: []
        }
        // 处理字段注释
        if (ele.lastIndexOf(table_split_comment_str) >= 0) {
          tableObj.tableComment = ele.slice(ele.lastIndexOf(table_split_comment_str) + table_split_comment_str.length, ele.lastIndexOf("'")).replaceAll('\'', '').trim()
        }
        const cloumArr = ele.slice(ele.indexOf(cloumArr_split_begin_str) + cloumArr_split_begin_str.length, ele.lastIndexOf(cloumArr_split_end_str)).split('\n')
        cloumArr.forEach(function (cloum, index) {
          if (cloum.indexOf('PRIMARY KEY') == -1 && cloum.indexOf('INDEX') == -1 && cloum.length > 0) {
            var cloumObj = {
              name: cloum.slice(cloum.indexOf(cloum_split_name_begin_str) + cloum_split_name_begin_str.length, cloum.lastIndexOf(cloum_split_name_end_str))
            }
            // 处理字段注释
            if (cloum.indexOf(cloum_split_comment_begin_str) >= 0) {
              cloumObj.comment = cloum.slice(cloum.indexOf(cloum_split_comment_begin_str) + cloum_split_comment_begin_str.length, cloum.lastIndexOf(cloum_split_comment_end_str))
            }
            // 处理字段类型
            if (cloum.indexOf('BIGINT') >= 0) {
              cloumObj.type = 'Long'
            } else if (cloum.indexOf('INT') >= 0 || cloum.indexOf('TINYINT') >= 0) {
              cloumObj.type = 'Integer'
            } else if (cloum.indexOf('VARCHAR') >= 0 || cloum.indexOf('TEXT') >= 0) {
              cloumObj.type = 'String'
            } else if (cloum.indexOf('DATETIME') >= 0) {
              cloumObj.type = 'Date'
            } else if (cloum.indexOf('DOUBLE') >= 0) {
              cloumObj.type = 'Double'
            }
            tableObj.cloums.push(cloumObj)
          }
        })
        tableArr.push(tableObj)
      }
    })
  }
  return tableArr
}
/**
 * 格式化get set 方法
 * @param  {[type]} str [description]
 * @return {[type]}     [description]
 */
function getSetFormat(str) {
  return str.charAt(0).toUpperCase() + str.substring(1)
}
/**
 * 生成模型文件
 * @param  {[type]} formData [project_path, sql_path, build_path, build_path_vo , tableArr]
 * @return {[type]}          [description]
 */
function createModelsFile(formData) {
  // 解析sql文件
  // let tableArr = getSqlJSON(formData.sql_path);
  const tableArr = formData.tableArr
  common.fs.writeFileSync(common.path.join(formData.project_path, 'sql.json'), JSON.stringify({
    sqlObj: tableArr
  }))
  const java_suffix = '.java'
  // 生成pojo文件
  if (formData.build_path.length > 0) {
    tableArr.forEach(function (ele, index) {
      // 包名
      const pojo_package_str = 'package ' + FileOpt.getPackageName(formData.build_path, 'com')+ ';\r\n'
      // 类名
      const pojoClassName = common.tranformHumpStr(ele.tableName)
      // 引入包
      let pojo_import_str = 'import java.io.Serializable;\r\n' + 'import javax.persistence.Entity;\r\n' +
        'import javax.persistence.Id;\r\n' +
        'import java.util.Date;\r\n' +
        'import io.swagger.annotations.ApiModelProperty;\r\n' +
        'import io.swagger.annotations.ApiModel;\r\n' +
        '@Entity(name = "' + ele.tableName + '")\r\n'
      // 注解
      let pojo_annotation_str =
`/**
 * Copyright (c) 2015-${new Date().getFullYear()} ${COMPANY}. All rights reserved.
 * @author: ${AUTHOR}
 * 注意：本内容仅限于${COMPANY}内部传阅，禁止外泄以及用于其他的商业目的 
 */\r\n`;
      // 如果有注释
      if (ele.tableComment && ele.tableComment.length > 0) {
        pojo_annotation_str += '/*** ' + ele.tableComment + ' ***/\r\n'
        pojo_import_str += '@ApiModel(value = "' + ele.tableComment + '")\r\n'
      }
      // TODO: 判断类型 , 缺少Date\List\Map\Set导入包
      pojo_import_str += '@SuppressWarnings("serial")\r\n'
      // 类
      let pojo_class_begin_str = 'public class ' + pojoClassName + ' implements Serializable{\r\n'

      // 如果有列
      if (ele.cloums && ele.cloums.length > 0) {
        ele.cloums.forEach(function (cloum, index) {
          if (cloum.comment && cloum.comment.length > 0) {
            pojo_class_begin_str += '\t@ApiModelProperty(value="' + cloum.comment + '")\r\n'
          }
          // 处理ID
          if (cloum.name == 'id') {
            pojo_class_begin_str += '\t@Id\r\n'
          }
          pojo_class_begin_str += '\tprivate ' + cloum.type + ' ' + cloum.name + ';\r\n'
        })
        // 生成get set
        ele.cloums.forEach(function (cloum, index) {
          // set
          pojo_class_begin_str += '\tpublic void set' + getSetFormat(cloum.name) + '(' + cloum.type + ' ' + cloum.name + ')' + '{\r\n' +
            '\t\t' + 'this.' + cloum.name + ' = ' + cloum.name + ';\r\n\t}\r\n'
          // get
          pojo_class_begin_str += '\tpublic ' + cloum.type + ' get' + getSetFormat(cloum.name) + '()' + '{\r\n' +
            '\t\t' + 'return ' + cloum.name + ';\r\n\t}\r\n'
        })
      }
      // TODO: 处理下状态类型
      const pojo_class_end_str = '}'
      const result_str = pojo_package_str + pojo_import_str + pojo_annotation_str + pojo_class_begin_str + pojo_class_end_str
      // console.log(result_str)
      common.fs.writeFileSync(common.path.join(formData.build_path, pojoClassName + java_suffix), result_str)
    })
  }
  if (formData.build_path_vo.length > 0) {
    // 生成VO文件
    tableArr.forEach(function (ele, index) {
      // 类名
      const pojoClassName = common.tranformHumpStr(ele.tableName)
      // 包名
      const pojo_package_str = 'import ' + FileOpt.getPackageName(formData.build_path, 'com') + '.' + pojoClassName + ';\r\n'

      // 包名
      const vo_package_str = 'package ' + FileOpt.getPackageName(formData.build_path_vo, 'com') + ';\r\n'

      const voClassName = pojoClassName + 'VO'
      // 引入包
      const pageParameterArr = common.getKeyPath(formData.project_path, 'PageParameter.java', 1)
      let vo_import_str = pojo_package_str + 'import java.io.Serializable;\r\n' +
        'import javax.persistence.Entity;\r\n' +
        'import javax.persistence.Id;\r\n' +
        'import io.swagger.annotations.ApiModel;\r\n' +
        'import ' + FileOpt.getPackageName(pageParameterArr[0].value, 'com').replaceAll('.java', '') + ';\r\n' +
        'import io.swagger.annotations.ApiModelProperty;\r\n' +
        '@Entity(name = "' + ele.tableName + '")\r\n'
      // 注解
      let vo_annotation_str =
`/**
  * Copyright (c) 2015-${new Date().getFullYear()} ${COMPANY}. All rights reserved.
  * @author: ${AUTHOR}
  * 注意：本内容仅限于${COMPANY}内部传阅，禁止外泄以及用于其他的商业目的 
  */\r\n`;
      // 如果有注释
      if (ele.tableComment && ele.tableComment.length > 0) {
        vo_annotation_str += '/*** ' + ele.tableComment + ' ***/\r\n'
        vo_import_str += '@ApiModel(value = "' + ele.tableComment + 'VO")\r\n'
      }
      vo_import_str += '@SuppressWarnings("serial")\r\n'
      // 类
      let vo_class_begin_str = 'public class ' + voClassName + ' extends ' + pojoClassName + ' implements Serializable{\r\n'

      // 设置vo colums
      ele.vo_colums = [{
        name: 'ids',
        comment: 'ID集合，逗号分隔',
        type: 'String'
      }, {
        name: 'page',
        comment: '当前页',
        type: 'Integer'
      }, {
        name: 'rows',
        comment: '每页的条数',
        type: 'Integer'
      }, {
        name: 'pageParameter',
        comment: '分页参数',
        type: 'PageParameter'
      }, {
        name: 'column',
        comment: '列',
        type: 'String'
      }]
      // 如果有列
      if (ele.vo_colums && ele.vo_colums.length > 0) {
        ele.vo_colums.forEach(function (cloum, index) {
          if (cloum.comment && cloum.comment.length > 0) {
            vo_class_begin_str += '\t@ApiModelProperty(value="' + cloum.comment + '")\r\n'
          }
          vo_class_begin_str += '\tprivate ' + cloum.type + ' ' + cloum.name + ';\r\n'
        })
        // 生成get set
        ele.vo_colums.forEach(function (cloum, index) {
          // set
          vo_class_begin_str += '\tpublic void set' + getSetFormat(cloum.name) + '(' + cloum.type + ' ' + cloum.name + ')' + '{\r\n' +
            '\t\t' + 'this.' + cloum.name + ' = ' + cloum.name + ';\r\n\t}\r\n'
          // get 判断字段类型
          vo_class_begin_str += '\tpublic ' + cloum.type + ' get' + getSetFormat(cloum.name) + '()' + '{\r\n' +
            '\t\t' + 'return ' + cloum.name + ';\r\n\t}\r\n'
        })
      }
      // TODO: 处理下状态类型

      const vo_class_end_str = '}'
      const result_str = vo_package_str + vo_import_str + vo_annotation_str + vo_class_begin_str + vo_class_end_str
      // console.log(result_str)
      common.fs.writeFileSync(common.path.join(formData.build_path_vo, voClassName + java_suffix), result_str)
    })
  }
}
export {
  getSqlJSON,
  createModelsFile
}
