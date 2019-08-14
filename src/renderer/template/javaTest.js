import {
  path,
  fs,
  fileDisplay
} from './java'

// 初始化项目下所有接口
/**
 * 初始化API
 * @param  {[type]} build_path [description]
 * @return {[type]}            [description]
 */
function initControllerTestApi(build_path) {
  // 根据build_path 获取目录下所有Controller
  const controllerList = []
  for (const v of build_path) {
    const dir_structure = {
      fileName: v,
      path: v,
      fromPath: v,
      dirList: [],
      fileList: []
    }
    fileDisplay(dir_structure)
    for (const f of dir_structure.fileList) {
      controllerList.push(f)
    }
  }
  const apiJsonList = []
  for (const c of controllerList) {
    // 读取controller
    const stats = fs.statSync(c.path)
    if (stats.isFile()) {
      const data = fs.readFileSync(c.path, 'utf-8').trim()
      const className = c.fileName.split('.')[0]
      const apiArr = data.split('\n')
      const result = []
      let public_model = {}
      for (let api of apiArr) {
        // 去除代码中空格
        api = api.replaceAll(/[\s]/, '')
        if (api.indexOf('public') >= 0) {
          public_model['public'] = api
          result.push(public_model)
          public_model = {}
        } else if (api.indexOf('@ApiOperation') >= 0) {
          public_model['ApiOperation'] = api
        } else if (api.indexOf('@PostMapping') >= 0) {
          public_model['PostMapping'] = api
        } else if (api.indexOf('@RequestMapping') >= 0) {
          public_model['RequestMapping'] = api
        } else if (api.indexOf('@Api(') >= 0) {
          public_model['Api'] = api
        } else if (api.indexOf('@ApiImplicitParams') >= 0) {
          public_model['ApiImplicitParams'] = api
        } else if (api.indexOf('@ApiImplicitParam') >= 0) {
          public_model['ApiImplicitParams'] += api
        }
      }
      apiJsonList.push({
        className: className,
        result: result
      })
    }
  }
  // 处理apiJson
  for (const api of apiJsonList) {
    api.Api = api.result[0].Api
    api.ApiText = api.result[0].Api ? api.result[0].Api.split('"')[1] : ''
    api.RequestMapping = api.result[0].RequestMapping
    api.RequestMappingText = api.result[0].RequestMapping ? api.result[0].RequestMapping.split('"')[1] : ''
    if (api.RequestMappingText.match(/^\//) == null) {
      api.RequestMappingText = '/' + api.RequestMappingText
    }
    // 将类提取出来 到上一层
    api.result = api.result.filter((ele, index) => {
      return index != 0
    })

    for (const service of api.result) {
      if (service.ApiOperation) {
        service.ApiOperationText = service.ApiOperation.split('"')[1]
      }
      if (service.RequestMapping) {
        service.RequestMappingText = service.RequestMapping.split('"')[1]
        service.RequestMappingType = service.RequestMapping.split('.')[1] ? service.RequestMapping.split('.')[1].split(')')[0] : ''
      }
      if (service.PostMapping) {
        service.RequestMappingText = service.PostMapping.split('"')[1]
        service.RequestMappingType = 'POST'
      }
      // 处理不规范问题
      if (service.RequestMappingType) {
        service.RequestMappingType = service.RequestMappingType.replaceAll(/[({})"]/, '')
      }
      if (service.RequestMappingText && service.RequestMappingText.match(/^\//) == null) {
        service.RequestMappingText = '/' + service.RequestMappingText
      }
      service.ApiImplicitParamsText = []
      if (service.ApiImplicitParams) {
        const paramsArray = service.ApiImplicitParams.replaceAll(/[({})"]/, '').split('@ApiImplicitParam').filter((ele, index) => {
          return index > 1
        })
        for (const param of paramsArray) {
          const paramArr = param.split(',')
          const paramObj = {}
          for (const p of paramArr) {
            // 如果有 = 号
            if (p.indexOf('=') >= 0) {
              const splitArr = p.split('=')
              paramObj[splitArr[0]] = splitArr[1]
            }
          }
          service.ApiImplicitParamsText.push(paramObj)
        }
      }
    }
  }
  return apiJsonList
}

export {
  initControllerTestApi
}
