/**
 * 模型类逆向表
 */
export default class Model {
    constructor(obj) {
        // 表名称
        this.table_name = obj.table_name
        // 属性集合 [{col:'input_date',type:'Date',name:'生成时间'}]
        this.table_col_arr = obj.table_col_arr
    }
}
