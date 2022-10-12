import Vue from 'vue'
import Router from 'vue-router'
import Top from '../components/Top.vue'
import Ticketing from '../components/Ticketing.vue'
import Basic from '../components/Basic.vue'
import Stock from '../components/Stock.vue'
import Boat from '../components/Boat.vue'
import AddUser from '../components/AddUser.vue'
import ChangeUser from '../components/ChangeUser.vue'
import QueryUser from '../components/QueryUser.vue'
import Order from '../components/Order.vue'
import Feedback from '../components/Feedback.vue'
import Statistics from '../components/Statistics.vue'
import Login from "../components/Login.vue"

Vue.use(Router)

export default new Router({
	model:'history',
	routes:[
		{
			path:'/Top',
			component:Top
		},
		{
			path:'/Ticketing',
			component:Ticketing
		},
		{
			path:'/Basic',
			component:Basic
		},
		{
			path:'/Stock',
			component:Stock
		},
		{
			path:'/AddUser',
			component:AddUser
		},
		{
			path:'/ChangeUser',
			component:ChangeUser
		},
		{
			path:'/QueryUser',
			component:QueryUser
		},
		{
			path:'/Order',
			component:Order
		},
		{
			path:'/Feedback',
			component:Feedback
		},
		{
			path:'/Statistics',
			component:Statistics
		},
		{
			path:'/Login',
			component:Login
		},
		{
			path:'/Boat',
			component:Boat
		},
		{
			path:'/',
			redirect:'/Statistics'
		}
		
	],
	        
	    
})