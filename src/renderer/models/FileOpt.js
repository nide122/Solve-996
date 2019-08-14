const path = require('path')
const fs = require('fs')
const dir_json_name = 'dir_structure.json'
const officegen = require('officegen')
import Model from '@/models/Model'
import Structure from '@/models/Structure'
import cheerio from "cheerio";

// 排除文件目录，文件名
const exclude_path = ['node_modules', 'target', 'bin', '.settings', 'logs', '.project',
    '.factorypath', '.classpath', '.apt_generated', '.git', '.idea', '.gitignore', 'yarn.lock', 'yarn-error.log', 'README.md']
/**
 * replaceAll方法
 */
String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, 'gm'), s2)
}

/**
 * 文件操作类
 * @author libiqi
 */
class FileOpt {
    // 公司
    static COMPANY = '深圳市科瑞特网络科技有限公司'
    // 作者
    static AUTHOR = 'CREATE-AI'

    constructor(obj) {
        // 返回数组
        this.keyPathArr = []
    }
    /**
     * 根据文件名、搜索目录获取唯一文件
     * @param {文件名称} fileName 
     * @param {搜索目录} project_path 
     */
    findOneFileByKey(fileName, project_path) {
        const filePathArr = this.findFileByKey(project_path, fileName)
        if (filePathArr.length > 1) {
            throw new Error('搜索出错，文件数量超出')
        }
        return filePathArr.length == 1 ? filePathArr[0] : { value: '' }
    }
    /**
     * 通过关键词搜索文件夹
     * @param {搜索目录} project_path 
     * @param {关键词} key 
     */
    findFolderByKey(project_path, key) {
        // 拿到项目里默认文件
        const dir_path = path.join(project_path, dir_json_name)
        const stats = fs.statSync(dir_path)
        if (stats.isFile()) {
            const jsonData = JSON.parse(fs.readFileSync(dir_path, 'utf-8'))
            this.get_models_structure(jsonData, key, project_path, 0)
        }
        return this.keyPathArr
    }
    /**
     * 通过关键词搜索文件
     * @param {搜索目录} project_path 
     * @param {关键词} key 
     */
    findFileByKey(project_path, key) {
        const dir_path = path.join(project_path, dir_json_name)
        const stats = fs.statSync(dir_path)
        if (stats.isFile()) {
            const jsonData = JSON.parse(fs.readFileSync(dir_path, 'utf-8'))
            this.get_models_structure(jsonData, key, project_path, 1)
        }
        return this.keyPathArr
    }

    /**
     * 根据文件获取JSON
     * @param  {[type]} dir_structure_json_path [项目JSON文件地址]
     * @return {[type]}                 [description]
     */
    getJsonFromPath(dir_structure_json_path) {
        const stats = fs.statSync(dir_structure_json_path)
        if (stats.isFile()) {
            // 处理项目文件目录
            if (dir_structure_json_path.indexOf(dir_json_name) >= 0) {
                // 重新生成fileList dirList
                const dir_structure = JSON.parse(fs.readFileSync(dir_structure_json_path, 'utf-8'))
                // build_path path
                dir_structure.path = path.parse(dir_structure_json_path).dir
                dir_structure.formData.build_path = path.parse(dir_structure.path).dir
                return this.showProjectStructure(dir_structure)
            } else {
                return JSON.parse(fs.readFileSync(dir_structure_json_path, 'utf-8'))
            }
        }
    }
    /**
     * 根据文档结构json 迭代出匹配关键字地址
     * @param  {[type]} dir_structure [文档json结构]
     * @param  {[type]} key           [关键字]
     * @param  {[Integer]} type           [搜索文件夹 还是 文件 默认0 :文件夹 1: 文件]
     * @return {[type]}               [如果是文件夹 返回 fileList 当前目录下fileList ]
     */
    get_models_structure(dir_structure, key, projectPath, type) {
        if (type == 1) {
            dir_structure.fileList.forEach((obj, index) => {
                if (obj.fileName == key) {
                    this.keyPathArr.push({
                        label: FileOpt.pathToLable(obj.path, projectPath),
                        value: obj.path
                    })
                }
            })
        } else {
            if (dir_structure.fileName == key) {
                this.keyPathArr.push({
                    label: FileOpt.pathToLable(dir_structure.path, projectPath),
                    value: dir_structure.path,
                    fileList: dir_structure.fileList
                })
            }
        }
        if (dir_structure.dirList.length > 0) {
            dir_structure.dirList.forEach((obj, index) => {
                this.get_models_structure(obj, key, projectPath, type)
            })
        }
    }
    /**
    * 显示目录结构
    * @return {[type]} [description]
    */
    static showProjectStructure(obj) {
        const dir_structure = new Structure({
            fileName: obj.fileName,
            path: obj.path,
            fromPath: obj.fromPath,
            formData: {},
        })
        FileOpt.fileDisplay(dir_structure)
        return dir_structure
    }

    /**
     * 获取文件路径简称
     * @param {*} file_path 
     * @param {*} projectPath 
     */
    static pathToLable(file_path, projectPath) {
        return file_path.replaceAll(projectPath.substr(0, projectPath.lastIndexOf('/') + 1), '')
    }

    /**
     * 文件遍历方法
     * @param fileObj 需要遍历的文件路径
     */
    static fileDisplay(fileObj) {
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
            let isExcludeFlag = exclude_path.filter(ele => {
                return filedir.indexOf(ele) >= 0
            })
            if (isExcludeFlag.length > 0) {
                return
            }
            if (isFile && fileName != '.DS_Store') {
                // 根据 fileObj 判读缓存数据 是否存在父亲目录
                const fileArr = fileObj.children.filter(function (ele) {
                    return ele.path == fileObj.path
                })
                const obj = {
                    fileName: fileName,
                    path: filedir,
                    fromPath: filedir
                }
                // 如果有父级
                if (fileArr.length == 1) {
                    fileArr[0].children.push(obj)
                } else {
                    fileObj.children.push(obj)
                }
            }
            if (isDir) {
                const obj = {
                    fileName: fileName,
                    path: filedir,
                    fromPath: filedir,
                    children: []
                }
                // 根据 fileObj 判读缓存数据 是否存在父亲目录
                const dirArr = fileObj.children.filter(function (ele) {
                    return ele.path == fileObj.path
                })
                // 如果有父级
                if (dirArr.length == 1) {
                    dirArr[0].children.push(obj)
                } else {
                    fileObj.children.push(obj)
                }
                FileOpt.fileDisplay(obj) // 递归，如果是文件夹，就继续遍历该文件夹下面的文件
            }
        })
    }

    /**
     * 获取包名
     * @param {*} filePath 
     * @param {*} startFix 
     */
    static getPackageName(filePath, startFix) {
        if (filePath.length == 0 || startFix.length == 0) {
            throw new Error('缺少参数!')
        }
        const fileObj = path.parse(filePath)
        const filePathArr = path.join(fileObj.dir, fileObj.name).split(path.sep)
        return filePathArr.filter((ele, index) => {
            return index >= filePathArr.indexOf(startFix)
        }).join('.')
    }

    /**
     * 根据pojo文件地址 逆向解析模型类 model -> json 格式
     * @param {*} filePath 
     */
    static getModelByPojoPath(filePath) {
        //读取 filePath 文件
        const fileText = this.readFile(filePath)

        //解析 fileText
        let typeArr = ['Long', 'Integer', 'String', 'Date', 'Double', 'List']
        return new Model({
            table_name: fileText
                .substring(fileText.search(/@+/), fileText.search(/public+/))
                .split("@")
                .find((ele) => {
                    return ele.indexOf("Entity") >= 0
                }).replace(/\s/g, '').split("=")[1].replace(/\"|\)/g, ''),
            table_col_arr: fileText
                .substring(fileText.search(/{+/) + 1, fileText.length - 1)
                .split(/public+(\S*)/g)[0].trim().replace(/\r\n/g, "").split(";")
                .filter((ele) => {
                    // 过滤空数据
                    if (ele.length >= 0) {
                        return ele
                    }
                }).map((ele) => {
                    let name = ''
                    let result = ele.trim().split(/\)/).filter((ele) => {
                        if (ele.indexOf('ApiModelProperty') >= 0) {
                            // 处理下第一行
                            ele = ele.replace(/\s/g, '')
                            name = ele && ele.length > 0 ? ele.split("=")[1].replace(/\"|\)/g, '') : ''
                            return false
                        } else {
                            return true
                        }
                    })
                    result = result[result.length - 1].split(/\s/g)
                    // 处理单条数据
                    let type = result.find(ele => {
                        return typeArr.indexOf(ele) >= 0
                    })
                    // 类型后面
                    let col = result[result.indexOf(type) + 1]
                    return {
                        name: name,
                        type: type,
                        col: col
                    }
                })
        })
    }

    /**
     * 根据文件名读取文件
     * @param {文件地址} filePath 
     */
    static readFile(filePath) {
        const stats = fs.statSync(filePath)
        if (stats.isFile()) {
            return fs.readFileSync(filePath, 'utf-8')
        } else {
            throw new Error('传入的参数必须为文件地址')
        }
    }

    /**
     * 根据 _ 生成驼峰 , 如果没有 _ 分隔符 , 则取第一个大写
     * @param {*} str 
     */
    static tranformHumpStr(str) {
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
     * 格式化get set 方法
     * @param  {[type]} str [description]
     * @return {[type]}     [description]
     */
    static getSetFormat(str) {
        return str.charAt(0).toUpperCase() + str.substring(1)
    }
    /**
     * 获取html parser对象
     * @param {*} htmlStr 
     */
    static getCheerio(htmlStr) {
        return cheerio.load(htmlStr, {
            withDomLvl1: false,
            ignoreWhitespace: true,
            xmlMode: true
        });
    }

    /**
     * 根据页面组件arr 深度遍历, 拼接html
     * @param {页面组件数组} arr 
     */
    static getHtmlStr(arr) {
        //如果只有一个参数
        if (typeof arr === 'object' && arr.length == undefined)
            arr = [arr]
        const result = [];
        arr.forEach(ele => {
            let $ = this.getCheerio(ele.h5_tag)
            ele.html = this.deepTraversal(ele, $);
            result.push(ele);
        });
        return result;
    }

    // 深度优先遍历的递归写法
    static deepTraversal(node, $) {
        if (node != null) {
            let childrens = node.children || [];
            childrens.forEach(element => {
                let $sub = cheerio.load(element.h5_tag, {
                    withDomLvl1: false,
                    ignoreWhitespace: true,
                    xmlMode: true
                });
                $.root()
                    .children()
                    .append($sub.root());
                this.deepTraversal(element, $sub);
            });
        }
        return $.html();
    }
    /**
     * 获取Java模版结构对象
     */
    static getJavaInitStructure() {
        return {
            fileName: 'createTemplate',
            path: path.join(path.join(__static, 'SpringBoot_Dubbo'), 'createTemplate')
        }
    }

}

export default FileOpt