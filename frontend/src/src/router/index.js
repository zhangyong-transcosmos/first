import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

  {
    path: '/spu',
    component: Layout,
    redirect: '/spu',
    children: [
      {
        path: 'spu',
        component: () => import('@/views/spu/index'),
        name: 'Spu',
        meta: { title: 'Spu', icon: 'dashboard' }
      }
    ]
  },

  {
    path: '/order',
    component: Layout,
    redirect: '/order',
    children: [
      {
        path: 'order',
        component: () => import('@/views/order/index'),
        name: 'Order',
        meta: { title: '订单', icon: 'dashboard' }
      }
    ]
  },

  {
    path: '/template',
    component: Layout,
    redirect: '/template',
    children: [
      {
        path: 'template',
        component: () => import('@/views/template/index'),
        name: 'Template',
        meta: { title: '模版', icon: 'dashboard' }
      },
      {
        path: 'template/create',
        component: () => import('@/views/template/info'),
        name: 'template-create',
        meta: {
          title: '新增模板',
          navActive: '/template',
          noCache: true
        },
        hidden: true
      },
      {
        path: 'template/edit/:id',
        component: () => import('@/views/template/info'),
        name: 'template-edit',
        meta: {
          title: '模板编辑',
          navActive: '/template',
          noCache: true
        },
        hidden: true
      }

    ]
  },

  {
    path: '/quote',
    component: Layout,
    redirect: '/quote',
    children: [
      {
        path: 'quote',
        component: () => import('@/views/quote/index'),
        name: 'Quote',
        meta: { title: '报价', icon: 'dashboard' }
      }
    ]
  },

  {
    path: '/system',
    component: Layout,
    redirect: '/system/user',
    alwaysShow: true,
    name: 'System',
    meta: { title: '系统设置', icon: 'dashboard' },
    children: [
      {
        path: '/system/user',
        component: () => import('@/views/system/user/index'),
        name: 'User',
        meta: { title: '用户管理' }
      },
      {
        path: '/system/dictionary',
        component: () => import('@/views/system/dictionary/index'),
        name: 'Dictionary',
        meta: { title: '数据字典' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
