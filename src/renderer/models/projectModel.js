/**
 * 项目实体模型类
 * @author libiqi
 */
export class ProjectModel {
    constructor(obj) {
        // 请选择项目地址
        this.project_path = obj.project_path
        // SQL地址
        this.sql_path = obj.sql_path
        // POJO地址
        this.build_path = obj.build_path
        // 导入的表数组
        this.tableArr = obj.tableArr
        // VO地址
        this.build_path_vo = obj.build_path_vo
        // 模版创建时间
        this.input_date = new Date().getTime()
        // 更新时间
        this.update_date = new Date().getTime()
        // 项目ID
        this._id = obj._id
    }
}