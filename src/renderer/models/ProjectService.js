/**
 * 项目Service生成类
 * @author libiqi
 */
export class ProjectService {
    constructor(obj) {
        // 请选择项目地址
        this.project_path = obj.project_path
        // POJO地址
        this.pojo_path = obj.pojo_path
        // VO地址
        this.vo_path = obj.vo_path
        // service生成目录地址
        this.service_path = obj.service_path
        // VO地址
        this.service_impl_path = obj.service_impl_path
        // mapper 地址
        this.mapper_path = obj.mapper_path
        // test 单元测试地址
        this.e2e_path = obj.e2e_path
        // controller 地址
        this.controller_path = this.controller_path
        // 限制非空字段
        this.table_col = this.table_col
        // 非空字段集合
        this.table_col_arr = this.table_col_arr
        // 模版创建时间
        this.input_date = new Date().getTime()
        // 更新时间
        this.update_date = new Date().getTime()
        // 项目ID
        this._id = obj._id
    }
}