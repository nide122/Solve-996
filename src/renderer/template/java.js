const path = require('path')
const fs = require('fs')
const dir_json_name = 'dir_structure.json'
const officegen = require('officegen')
/**
 * replaceAll方法
 */
String.prototype.replaceAll = function (s1, s2) {
  return this.replace(new RegExp(s1, 'gm'), s2)
}
// 关键字数组
let keyPathArr = []
/**
 * 文件遍历方法
 * @param fileObj 需要遍历的文件路径
 */
function fileDisplay(fileObj) {
  // 根据文件路径读取文件，返回文件列表
  const files = fs.readdirSync(fileObj.path)
  // 遍历读取到的文件列表
  files.forEach(function (fileName) {
    // 获取当前文件的绝对路径
    var filedir = path.join(fileObj.path, fileName)
    // 根据文件路径获取文件信息，返回一个fs.Stats对象
    const stats = fs.statSync(filedir)
    var isFile = stats.isFile() // 是文件
    var isDir = stats.isDirectory() // 是文件夹
    if (isFile && fileName != '.DS_Store') {
      // 根据 fileObj 判读缓存数据 是否存在父亲目录
      const fileArr = fileObj.dirList.filter(function (ele) {
        return ele.path == fileObj.path
      })
      const obj = {
        fileName: fileName,
        path: filedir,
        fromPath: filedir
      }
      // 如果有父级
      if (fileArr.length == 1) {
        fileArr[0].fileList.push(obj)
      } else {
        fileObj.fileList.push(obj)
      }
    }
    if (isDir) {
      const obj = {
        fileName: fileName,
        path: filedir,
        fromPath: filedir,
        dirList: [],
        fileList: []
      }
      // 根据 fileObj 判读缓存数据 是否存在父亲目录
      const dirArr = fileObj.dirList.filter(function (ele) {
        return ele.path == fileObj.path
      })
      // 如果有父级
      if (dirArr.length == 1) {
        dirArr[0].dirList.push(obj)
      } else {
        fileObj.dirList.push(obj)
      }
      // 排除无效文件夹
      if (filedir.indexOf('target') == -1 &&
        filedir.indexOf('bin') == -1 &&
        filedir.indexOf('.settings') == -1 &&
        filedir.indexOf('logs') == -1 &&
        filedir.indexOf('.project') == -1 &&
        filedir.indexOf('.factorypath') == -1 &&
        filedir.indexOf('.classpath') == -1 &&
        filedir.indexOf('.apt_generated') == -1) {
        fileDisplay(obj) // 递归，如果是文件夹，就继续遍历该文件夹下面的文件
      }
    }
  })
}

/**
 * 根据 _ 生成驼峰 , 如果没有 _ 分隔符 , 则取第一个大写
 * @param  {[type]} str [description]
 * @return {[type]}     [description]
 */
function tranformHumpStr(str) {
  if (str.length == 0) {
    return false
  }
  if (str.indexOf('_') >= 0) {
    var strArr = str.split('_')
    for (var i = 0; i < strArr.length; i++) {
      strArr[i] = strArr[i].charAt(0).toUpperCase() + strArr[i].substring(1).toLowerCase()
    }
    return strArr.join('')
  } else {
    return str.charAt(0).toUpperCase() + str.substring(1).toLowerCase()
  }
}

/**
 * 根据文档结构json 迭代出匹配关键字地址
 * @param  {[type]} dir_structure [文档json结构]
 * @param  {[type]} key           [关键字]
 * @param  {[Integer]} type           [搜索文件夹 还是 文件 默认0 :文件夹 1: 文件]
 * @return {[type]}               [如果是文件夹 返回 fileList 当前目录下fileList ]
 */
function get_models_structure(dir_structure, key, projectPath, type) {
  if (type == 1) {
    dir_structure.fileList.forEach(function (obj, index) {
      if (obj.fileName == key) {
        keyPathArr.push({
          label: obj.path.replaceAll(projectPath.substr(0, projectPath.lastIndexOf('/') + 1), ''),
          value: obj.path
        })
      }
    })
  } else {
    if (dir_structure.fileName == key) {
      keyPathArr.push({
        label: dir_structure.path.replaceAll(projectPath.substr(0, projectPath.lastIndexOf('/') + 1), ''),
        value: dir_structure.path,
        fileList: dir_structure.fileList
      })
    }
  }
  if (dir_structure.dirList.length > 0) {
    dir_structure.dirList.forEach(function (obj, index) {
      get_models_structure(obj, key, projectPath, type)
    })
  }
}

/**
 * 根据关键字获取文件夹地址
 * @param  {[String]} project_path [项目地址]
 * @param  {[String]} key      [关键字 pojo , vo 等等]
 * @param  {[Integer]} type      [搜索文件夹 还是 文件 默认0 :文件夹 1: 文件]
 * @return {[Array]}           [返回数组]
 */
function getKeyPath(project_path, key, type) {
  // 重置数组
  keyPathArr = []
  const dir_path = path.join(project_path, dir_json_name)
  const stats = fs.statSync(dir_path)
  if (stats.isFile()) {
    const jsonData = JSON.parse(fs.readFileSync(dir_path, 'utf-8'))
    get_models_structure(jsonData, key, project_path, type)
  }
  return keyPathArr
}

/**
 * 显示目录结构
 * @return {[type]} [description]
 */
function showProjectStructure(obj) {
  const dir_structure = {
    fileName: obj.fileName,
    path: obj.path,
    fromPath: obj.path,
    formData: obj.formData,
    dirList: [],
    fileList: []
  }
  fileDisplay(dir_structure)
  return dir_structure
}

/**
 * 根据文件获取JSON
 * @param  {[type]} dir_structure_json_path [项目JSON文件地址]
 * @return {[type]}                 [description]
 */
function getJsonFromPath(dir_structure_json_path) {
  const stats = fs.statSync(dir_structure_json_path)
  if (stats.isFile()) {
    // 处理项目文件目录
    if (dir_structure_json_path.indexOf(dir_json_name) >= 0) {
      // 重新生成fileList dirList
      const dir_structure = JSON.parse(fs.readFileSync(dir_structure_json_path, 'utf-8'))
      // build_path path
      dir_structure.path = path.parse(dir_structure_json_path).dir
      dir_structure.formData.build_path = path.parse(dir_structure.path).dir
      console.log(path.parse(dir_structure.path).dir)
      return showProjectStructure(dir_structure)
    } else {
      return JSON.parse(fs.readFileSync(dir_structure_json_path, 'utf-8'))
    }
  }
}

/**
 * 根据文件获取JSON
 * @param  {[type]} project_path [description]
 * @return {[type]}              [description]
 */
function updateProjectDirJson(project) {
  const dir_structure_json_path = path.join(path.join(project.build_path, project.project_name), dir_json_name)
  const dir_structure = getJsonFromPath(dir_structure_json_path)
  fs.writeFileSync(dir_structure_json_path, JSON.stringify(dir_structure))
}

/**
 * 导出word 文档
 * @param  {[type]} project_file_path [description]
 * @param  {[type]} word_data         [word 内容 ]
 * @return {[type]}                   [description]
 */
function exportWord(project_file_path, word_data) {
  // officegen.setVerboseMode(true);
  const docx = officegen('docx')
  // 设置头部
  const header = docx.getHeader().createP()
  header.addText('深圳市科瑞特网络科技有限公司')
  const footer = docx.getFooter().createP()
  footer.addText('深圳市科瑞特网络科技有限公司')
  // 设置内容
  docx.createByJson(word_data)
  // 文件写入
  const out = fs.createWriteStream(project_file_path)
  out.on('error', function (err) {
    console.log(err)
  })
  // 服务器端生成
  const result = docx.generate(out, {
    'finalize': function (written) {
      console.log('Finish to create a Word file.\nTotal bytes created: ' + written + '\n')
    },
    'error': function (err) {
      console.log(err)
    }
  })
}

function exportPPT() {

}

function exportExecl() {

}

/**
 * [根据文件夹地址、文件名称 获取文件目录]
 * @param  {[type]} fileName     [文件名称]
 * @param  {[type]} project_path [文件夹地址]
 * @return {[type]}              [filePathObj]
 */
function getFilePath(fileName, project_path) {
  const filePathArr = getKeyPath(project_path, fileName, 1)
  return filePathArr.length == 1 ? filePathArr[0] : { value: '' }
}

/**
 * 根据filePath 、起始参数 截取获取包名
 * @param  {[type]} filePath [description]
 * @param  {[type]} startFix    [description]
 * @return {[type]}             [description]
 */
function subStartFixFilePath(filePath, startFix) {
  const filePathArr = filePath.split('/')
  return filePathArr.filter((ele, index) => {
    return index >= filePathArr.indexOf(startFix)
  }).join('.')
}
// 版权公司
export const COMPANY = '深圳市科瑞特网络科技有限公司'
// 作者
export const AUTHOR = 'CREATE-AI'
export {
  path,
  fs,
  dir_json_name,
  getJsonFromPath,
  updateProjectDirJson,
  getFilePath,
  subStartFixFilePath,
  fileDisplay,
  getKeyPath,
  exportExecl,
  exportPPT,
  exportWord,
  tranformHumpStr
}
