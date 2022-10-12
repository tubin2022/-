import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import qs from 'qs'

import dataV from '@jiaminghi/data-view';
Vue.use(dataV);

//引入echart
import echarts from 'echarts'
Vue.prototype.$echarts = echarts;
import 'echarts/map/js/china.js'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);
Vue.config.productionTip = false

Vue.prototype.$axios = axios
Vue.prototype.$qs = qs

new Vue({
  render: h => h(App),
  router,
  el: '#app'
}).$mount('#app')
