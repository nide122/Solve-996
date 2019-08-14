/**
 * 项目实体类
 * @author libiqi
 */
export class Project {
    constructor(obj) {
        // 模版名称
        this.project_name = obj.project_name
        // 模版id
        this.template_id = obj.template_id
        // 构建地址
        this.build_path = obj.build_path
        // 项目简介
        this.desc = obj.desc
        // 模版创建时间
        this.input_date = new Date().getTime()
        // 更新时间
        this.update_date = new Date().getTime()
        // 项目ID
        this._id = obj._id
    }
}
/**
 * 项目VO
 */
export class ProjectVO extends Project {
    constructor(props) {
        super(props);
    }
}