/**
 * 模版实体类
 * @author libiqi
 */
export class Template {
    constructor(obj){
        // 模版名称
        this.name = obj.name
        // 模版地址
        this.file_path = obj.file_path
        // 模版语言类型 
        // 0、java 1、小程序 2、admin 3、h5 4、web 5、安卓 6、iOS 7、其他
        this.type = obj.type
        // 模版创建时间
        this.input_date = new Date().getTime()
        // 模版更新时间
        this.update_date = new Date().getTime()
    }
}
