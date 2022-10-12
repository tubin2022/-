import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

const routes = [
  {
    path:'/',
    redirect: '/manage/home',
  },
  {
    path: '/manage',
    name: 'manage',
    component: () => import('../views/Manage.vue'),
    children:[
      {
        path:'home',
        component: () => import('../views/Home/home.vue')
      },
      {
        path:'order',
        component: () => import('../views/Order/order.vue')
      },
      {
        path:'me',
        component: () => import('../views/Me/me.vue')
      },
      {
        path:'userInfoPage',
        component: () => import('../views/Me/userInfoPage.vue')
      },
      {
        path:'vip',
        component: () => import('../views/Me/vip.vue')
      },
      {
        path:'cash',
        component: () => import('../views/Me/cash.vue')
      },
      {
        path:'searchProduct',
        component: () =>import('../views/Product/searchProduct.vue')
      },
      {
        path:'productList',
        component: () =>import('../views/Product/productList.vue')
      },
      {
        path:'message',
        component: () =>import('../views/Message/message.vue')
      },
      {
        path:'shoppingCart',
        component: () =>import('../views/Order/shoppingCart.vue')
      },
    ]
  },
  {
    path:'/login',
    name:'login',
    component: () => import('../views/Login/login.vue')
  }
]

const router = new VueRouter({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
