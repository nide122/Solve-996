# Solve-996
为了解决程序员996之痛，解决开发过程中大量重复性代码。实现编程自动化，目前已实现Java编程自动化。做软件开发行业的RPA。
# 操作流程
### 初始化项目
![初始化项目](https://github.com/CREATE-AI/Solve-996/blob/master/docs/initJava.gif)

# 技术实现思路介绍
将开发过程中大量重复性、规则性的代码，生成模版以及规则，开发人员只要输入模型，即可自动生成这部分代码，目前Java开发已实现40%的开发效率提升，已实现POJO、VO、Mapper、e2e 文件自动生成，实现部分API流程测试功能。
# 运行环境依赖
node v8+

# 目录结构
```
.
├── LICENSE
├── README.md
├── appveyor.yml
├── build
├── dist
├── package.json
├── src
│   ├── index.ejs
│   ├── main
│   │   ├── index.dev.js
│   │   └── index.js
│   └── renderer        核心代码编辑
│       ├── App.vue
│       ├── assets
│       ├── components      
│       ├── css
│       ├── datastore.js
│       ├── icons
│       ├── main.js
│       ├── mixins
│       ├── models
│       ├── permission.js
│       ├── props
│       ├── router
│       ├── store
│       ├── styles
│       ├── template        代码生成器，可以更改生成规则
│       ├── utils
│       └── views
├── static                  模版不满足，可以更新模版
│   └── SpringBoot_Dubbo    Java模版1
│       └── createTemplate 
└── test
    ├── e2e
    │   ├── index.js
    │   ├── specs
    │   └── utils.js
    └── unit
        ├── index.js
        ├── karma.conf.js
        └── specs
```
# 涉及到的技术
Nodejs、Vue、electron、Java、SpringBoot、Dubbo、Redis等等。
### 目前Java用的模版
Springboot + Dubbo + Redis + Mysql
### 模型建立工具
MysqlWorkbench
### 如果对模版不满意
可以static目录下增加模版或者clone分支更换模版

# 安装
## 第一步：
`
yarn 或者 npm install
`
## 第二步：
`
yarn dev
`
# 微信讨论群
![微信群图片](http://www.createsz.cn/download/5401565800545_.pic.jpg)
## 招募管理要求
同时具备：Java开发 2年以上，Js 3年以上 ，熟悉Nodejs
# 开发进度安排

## V1版本
完成Java完整自动化，加入CI、CD
## V2版本
完成Admin管理系统自动化
## V3版本
完成小程序自动化
完成H5自动化
# 注意事项
windows build 暂时编译不过去
请用Mac系统 build

# 开源协议
本项目基于MIT协议，请自由地享受和参与开源。
