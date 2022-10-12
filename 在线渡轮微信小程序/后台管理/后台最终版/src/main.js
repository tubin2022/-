import Vue from 'vue'
import App from './App.vue'
import router from './router/index.js'
import Axios from "axios"
import VueAxios from "vue-axios"
import VueRouter from "vue-router"
import ElementUI from 'element-ui'
import moment from 'moment'
import axios from 'axios'
import * as echarts from "echarts"
import qs from 'qs';
import 'element-ui/lib/theme-chalk/index.css';
import VueBus from 'vue-bus'

import Top from './components/Top.vue'
import Ticketing from './components/Ticketing.vue'
import Basic from './components/Basic.vue'
import Stock from './components/Stock.vue'
import AddUser from './components/AddUser.vue'
import ChangeUser from './components/ChangeUser.vue'
import QueryUser from './components/QueryUser.vue'
import Order from './components/Order.vue'
import Feedback from './components/Feedback.vue'
import Boat from './components/Boat.vue'
import Statistics from './components/Statistics.vue'
import Login from './components/Login.vue'

// 开启axios
Vue.use(VueAxios,Axios);
Vue.prototype.$echarts = echarts;
Vue.use(VueBus)
Vue.config.productionTip = false
Vue.filter('dateFormat', function (daraStr, pattern = 'YYYY-MM-DD HH:mm:ss') {
  return moment(daraStr).format(pattern)
})
Vue.prototype.$axios = axios
axios.default

Vue.use(ElementUI);

// 开启路由
Vue.use(VueRouter);
// 定义路由
let myRouter = [
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
];

let vr = new VueRouter({
	routes:myRouter
	});


// 设置axios访问的基准路径
Axios.default.baseURL = "http://localhost:8080";
// 挂载axios到vue原型对象中
Vue.prototype.myAxios = Axios;

new Vue({
  render: h => h(App),
  // 挂载路由
  router: vr
}).$mount('#app')