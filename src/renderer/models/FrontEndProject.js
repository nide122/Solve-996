/**
 * 前端项目实例
 */
export default class FrontEndProject {
    constructor(obj) {
        // 模版名称
        this.project_name = obj.project_name
        // 项目地址
        this.build_path = obj.build_path
        // 选定哪个UI库
        this.ui_library = obj.ui_library 
        // 代码库
        this.produce_language_library = obj.produce_language_library
        // 当前页面使用的组件库，以及坐标值
        this.page_components = obj.page_components
        // 当前页面的事件库
        this.page_events = obj.page_events
        // 页面编辑宽度
        this.width = 0
        // 页面编辑高度
        this.height = 0
        // 模版创建时间
        this.input_date = new Date().getTime()
        // 更新时间
        this.update_date = new Date().getTime()
        // 项目ID
        this._id = obj._id
    }
}