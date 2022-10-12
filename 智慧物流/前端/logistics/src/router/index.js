import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)
import index from '../views/index.vue'
import search from '../views/logistics/search.vue'
import me from '../views/user/me.vue'
import send from '../views/logistics/send.vue'
import sendLogistics from '../views/logistics/sendLogistics.vue'
import login from '../views/user/login.vue'
import logisticsMap from '../views/logistics/logisticsMap.vue'
import dispatch from '../views/logistics/dispatchLogistics.vue'
import aliMap from '../views/logistics/aliMap.vue'
import userInfo from '../views/user/userInfo.vue'
import backLogistics from '../views/logistics/backLogistics.vue'

export default new Router({
	mode:'history',
	routes:[
		{
			path:'/',
			redirect:'/index/send'
		},
		{
			path:'/index',
			name:'index',
			component:index,
			children:[
				{
					path:'send',
					component:send
				},
				{
					path:'me',
					component:me
				},
				{
					path:'search',
					component:search
				}
			]
		},
		{
			path:'/sendLogistics',
			component:sendLogistics
		},
		{
			path:'/login',
			component:login
		},
		{
			path:'/logisticsMap',
			component:logisticsMap
		},
		{
			path:'/dispatchLogistics',
			component:dispatch
		},
		{
			path:'/aliMap',
			component:aliMap
		},
		{
			path:'/userInfo',
			component:userInfo
		},
		{
			path:'/backLogistics',
			component:backLogistics
		}
		
	]
})