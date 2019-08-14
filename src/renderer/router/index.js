import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

// export default router

/* Layout */
import Layout from '@/views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },
  //开发者
  {
    path: '/developer',
    component: Layout,
    name: 'Developer',
    redirect: '/developer/InitJavaPage',
    meta: { title: '开发者', icon: 'example' },
    children: [
      {
        path: 'InitJavaPage',
        name: 'InitJavaPage',
        component: () => import('@/components/Java/InitJavaPage'),
        meta: { title: '初始化项目', icon: 'nested' }
      },
      {
        path: 'InitJavaListPage',
        name: 'InitJavaListPage',
        component: () => import('@/components/Java/InitJavaListPage'),
        meta: { title: '项目列表', icon: 'nested' }
      },
      {
        path: 'JavaModels',
        name: 'JavaModels',
        component: () => import('@/components/Java/JavaModelsPage'),
        meta: { title: 'POJO', icon: 'nested' }
      },
      {
        path: 'JavaService',
        name: 'JavaService',
        component: () => import('@/components/Java/JavaServicePage'),
        meta: { title: 'Service', icon: 'nested' }
      },
      {
        path: 'JavaDeploy',
        name: 'JavaDeploy',
        component: () => import('@/components/Java/JavaDeployPage'),
        meta: { title: 'Deploy', icon: 'nested' }
      },
      {
        path: 'JavaTest',
        name: 'JavaTest',
        component: () => import('@/components/Java/JavaTestPage'),
        meta: { title: 'JavaTest', icon: 'nested' }
      },
      {
        path: 'JavaTestApi',
        name: 'JavaTestApi',
        hidden: true,
        component: () => import('@/components/Java/JavaTestApiPage'),
        meta: { title: 'JavaTestApi', icon: 'nested' }
      },
      {
        path: 'JavaTestCustom',
        name: 'JavaTestCustom',
        hidden: true,
        component: () => import('@/components/Java/JavaTestCustomPage'),
        meta: { title: 'JavaTestCustom', icon: 'nested' }
      },
      {
        path: 'JavaTestProject',
        name: 'JavaTestProject',
        component: () => import('@/components/Java/JavaTestProjectPage'),
        meta: { title: 'JavaTestProject', icon: 'nested' }
      },
      {
        path: 'InitFrontEnd',
        name: 'InitFrontEnd',
        component: () => import('@/components/FrontEnd/InitFrontEnd'),
        meta: { title: 'InitFrontEnd', icon: 'nested' }
      },
      {
        path: 'Main',
        name: 'Main',
        component: () => import('@/components/FrontEnd/Main'),
        meta: { title: '前端生成器', icon: 'nested' }
      }
    ]
  },
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
