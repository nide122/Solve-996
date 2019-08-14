import axios from 'axios';
import Qs from 'qs'
axios.defaults.timeout = 5000

export const apiPost = (url, params) => {
    return axios.post(url, params).then(res => res.data);
};
export const apiGet = (url, params) => {
    return axios.get(url, params).then(res => res.data);
};

axios.interceptors.request.use(
    config => {
        if (typeof config.data === 'undefined') {
            config.data = {}
        }
        // config.headers['Content-Type'] = 'application/json; charset=UTF-8'
        if (config.data.token_key && config.data.token_key.length > 0) {
            config.url = config.url + `?${config.data.token_key}=${config.data[config.data.token_key]}`
            delete config.data.token_key
        }
        return config
    },
    error => {
        // Do something with request error
        console.log(error) // for debug
        Promise.reject(error)
    }
)

//http response 拦截器
axios.interceptors.response.use(
    response => {
        return response
    },
    error => {
        // vm.$loading.close()
        if (error && error.response) {
            switch (error.response.status) {
                case 400:
                    error.message = '请求错误'
                    break

                case 401:
                    error.message = '未授权，请登录'
                    // 只有在当前路由不是登录页面才跳转
                    router.currentRoute.path !== 'login' &&
                        router.replace({
                            path: 'login',
                            query: {
                                redirect: router.currentRoute.path
                            },
                        })
                    break

                case 403:
                    error.message = '拒绝访问'
                    break

                case 404:
                    error.message = `请求地址出错: ${error.response.config.url}`
                    break

                case 408:
                    error.message = '请求超时'
                    break

                case 500:
                    error.message = '服务器内部错误'
                    break

                case 501:
                    error.message = '服务未实现'
                    break

                case 502:
                    error.message = '网关错误'
                    break

                case 503:
                    error.message = '服务不可用'
                    break

                case 504:
                    error.message = '网关超时'
                    break

                case 505:
                    error.message = 'HTTP版本不受支持'
                    break

                default:
            }
        }
        // console.log(JSON.stringify(error)); //console : Error: Request failed with status code 402
        // return Promise.reject(error.response ? error.response.data : error.response)
        return Promise.resolve({
            data: {
                success: 1000,
                message: error.message,
                data: error.code
            }
        })
    }
)
