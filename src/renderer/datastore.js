import Datastore from 'nedb'
import path from 'path'
import {
  remote
} from 'electron'

const db = {}
// 
// 模版表 /renderer/models/template
db.template = new Datastore({
  autoload: true,
  filename: path.join(remote.app.getPath('userData'), '/template.db')
})
// 项目表 /renderer/models/project
db.project = new Datastore({
  autoload: true,
  filename: path.join(remote.app.getPath('userData'), '/project.db')
})
// 测试表 /renderer/models/test
db.test = new Datastore({
  autoload: true,
  filename: path.join(remote.app.getPath('userData'), '/test.db')
})
// 前端项目表 /renderer/models/FrontEndProject
db.frontEndProject = new Datastore({
  autoload: true,
  filename: path.join(remote.app.getPath('userData'), '/frontEndProject.db')
})
export default db
