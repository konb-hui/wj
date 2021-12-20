import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login'), hidden: true },

  {
    path: '/',
    redirect: '/login',
    name: 'login',
    hidden: true,
  },
  {
    path: '/index',
    name: 'home',
    component: () => import('@/views/Home'),
    children: [
      {
        path: '/library',
        name: '图书馆',
        component: () => import('@/views/library/library'),
        meta: { title: '图书馆', icon: 'table' }
      },
      {
        path: '/jotter',
        name: '笔记本',
        redirect: '/jotter/editor',
        component: () => import('@/views/jotter/jotter'),
        children: [
          {
            path: '/jotter/directory',
            name: '文件夹',
            component: () => import('@/views/jotter/directory'),
            meta: { title: '文件夹', icon: 'table' }
          },
          {
            path: '/jotter/editor',
            name: '新建笔记',
            component: () => import('@/views/jotter/editor'),
            meta: { title: '新建笔记', icon: 'table' }
          },
          {
            path: '/jotter/list',
            name: '最新笔记',
            component: () => import('@/views/jotter/list'),
            meta: { title: '最新笔记', icon: 'table' }
          },
          {
            path: '/jotter/recycle',
            name: '回收站',
            component: () => import('@/views/jotter/recycle'),
            meta: { title: '回收站', icon: 'table' }
          },
        ],
        meta: { title: '笔记本', icon: 'table' }
      },
      {
        path: '/main',
        name: '首页',
        component: () => import('@/views/index'),
        meta: { title: '首页', icon: 'table' }
      },
      {
        path: '/user',
        name: '个人中心',
        component: () => import('@/views/user/user'),
        meta: { title: '个人中心', icon: 'table' }
      },
      {
        path: '/more',
        name: '更多',
        component: () => import('@/views/more/more'),
        meta: { title: '更多', icon: 'table' },
        children: [
          {
            path: '/notice',
            name: '通知管理',
            component: () => import('@/views/notice/notice'),
            meta: { title: '通知管理', icon: 'table' }
          },
          {
            path: '/google/version',
            name: '谷歌版本',
            component: () => import('@/views/google/version/version'),
            meta: { title: '谷歌版本', icon: 'table' }
          }
        ]
      },
    ]
  }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

