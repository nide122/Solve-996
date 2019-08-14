import {
  path,
  fs,
  dir_json_name,
  fileDisplay
} from './java'
// 缓存目录结构
const cache_dir_structure = {
  fileName: 'createTemplate',
  path: path.join(path.join(__static, 'SpringBoot_Dubbo'), 'createTemplate')
}
/**
 * 显示目录结构
 * @return {[type]} [description]
 */
function showProjectStructure(formData, obj) {
  const dir_structure = {
    fileName: obj ? obj.fileName : cache_dir_structure.fileName,
    path: obj ? obj.path : cache_dir_structure.path,
    fromPath: obj ? obj.path : cache_dir_structure.path,
    formData: formData,
    dirList: [],
    fileList: []
  }
  fileDisplay(dir_structure)
  return dir_structure
}
/**
 * 初始化文件夹
 * @return {[type]} [description]
 */
function initProject(formData) {
  // 1、获取目录结构
  const dir_structure = showProjectStructure(formData)
  // 2、生成结构目录文件
  get_dir_structure(dir_structure, dir_structure.fileName, formData.project_name, formData.build_path)
  // formData.project_name 目标名
  // formData.build_path	产出地址
  // 3、将模版修改后输出到产出目录
  copyCoding(dir_structure, cache_dir_structure.fileName, formData.project_name)
  // 4、将生成的目录文件copy到输出目录项目下
  fs.writeFileSync(path.join(path.join(formData.build_path, formData.project_name), dir_json_name), JSON.stringify(dir_structure))
}

/**
 * 处理项目结构文件 修改fileName \ path \ fileList里的path
 * @param  {[type]} dir_structure     [结构目录]
 * @param  {[type]} project_name      [目标项目名]
 * @param  {[type]} form_project_name [模版项目名]
 * @return {[type]}                   [description]
 */
function get_dir_structure(dir_structure, form_project_name, project_name, build_path) {
  dir_structure.fileName = dir_structure.fileName.replaceAll(form_project_name, project_name)
  // 处理window 系统 路径\\
  if (dir_structure.path.indexOf('\\') >= 0) {
    dir_structure.path = dir_structure.path
      .replaceAll(cache_dir_structure.path.substr(0, cache_dir_structure.path.lastIndexOf('\\')).replaceAll('\\\\', '\\\\'), build_path)
      .replaceAll(form_project_name, project_name)
  } else if (dir_structure.path.indexOf('/') >= 0) {
    dir_structure.path = dir_structure.path
      .replaceAll(cache_dir_structure.path.substr(0, cache_dir_structure.path.lastIndexOf('/')), build_path)
      .replaceAll(form_project_name, project_name)
  }
  if (dir_structure.fileList.length > 0) {
    dir_structure.fileList.forEach(function(obj, index) {
      if (obj.path.indexOf('\\') >= 0) {
        obj.path = obj.path
          .replaceAll(cache_dir_structure.path.substr(0, cache_dir_structure.path.lastIndexOf('\\')).replaceAll('\\\\', '\\\\'), build_path)
          .replaceAll(form_project_name, project_name)
      } else if (obj.path.indexOf('/') >= 0) {
        obj.path = obj.path
          .replaceAll(cache_dir_structure.path.substr(0, cache_dir_structure.path.lastIndexOf('/')), build_path)
          .replaceAll(form_project_name, project_name)
      }
    })
  }
  if (dir_structure.dirList.length > 0) {
    dir_structure.dirList.forEach(function(obj, index) {
      get_dir_structure(obj, form_project_name, project_name, build_path)
    })
  }
}
/**
 * 根据文件对象生成文件夹和文件
 * @param  {[type]} dir_structure [结构目录]
 * @param  {[type]} form_project_name [模版项目名]
 * @param  {[type]} project_name      [目标项目名]
 * @return {[type]}               [description]
 */
function copyCoding(dir_structure, form_project_name, project_name) {
  if (!fs.existsSync(dir_structure.path)) { fs.mkdirSync(dir_structure.path) }
  if (dir_structure.fileList.length > 0) {
    dir_structure.fileList.forEach(function(obj, index) {
      const data = fs.readFileSync(obj.fromPath, 'utf8')
      const reg = new RegExp(form_project_name, 'g')
      var result = data.replace(reg, project_name)
      fs.writeFileSync(obj.path, result, 'utf8')
    })
  }
  if (dir_structure.dirList.length > 0) {
    dir_structure.dirList.forEach(function(obj, index) {
      copyCoding(obj, form_project_name, project_name)
    })
  }
}
export {
  showProjectStructure,
  initProject
}
