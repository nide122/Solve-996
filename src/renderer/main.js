import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'
import '@/icons' // icon
import '@/permission' // permission control

import db from './datastore'
require('@/utils/directive')
var infiniteScroll = require('vue-infinite-scroll');
Vue.use(infiniteScroll)
// UI 插件
Vue.use(ElementUI, {
  locale
})
if (!process.env.IS_WEB) Vue.use(require('vue-electron'))

Vue.config.productionTip = false
Vue.prototype.$db = db
Date.prototype.Format = function (a) {
  const b = {
    'M+': this.getMonth() + 1,
    'd+': this.getDate(),
    'h+': this.getHours(),
    'm+': this.getMinutes(),
    's+': this.getSeconds(),
    'q+': Math.floor((this.getMonth() + 3) / 3),
    S: this.getMilliseconds()
  };
  /(y+)/.test(a) && (a = a.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length)))
  for (const c in b) RegExp('(' + c + ')').test(a) && (a = a.replace(RegExp.$1, RegExp.$1.length === 1 ? b[c] : ('00' + b[c]).substr(('' + b[c]).length)))
  return a
}

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})