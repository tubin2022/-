import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)
import index from '../views/index.vue'
export default new Router({
	mode:'history',
	routes:[
		{
			path:'/',
			redirect:'/index'
		},
		{
			path:'/index',
			component:index
		},
		
	]
})