/**
 * 目录结构实体
 */
export default class Structure {
    constructor(obj) {
        this.fileName = obj.fileName
        this.path = obj.path
        this.fromPath = obj.path
        this.formData = obj.formData
        // 子类
        this.children = []
    }
}