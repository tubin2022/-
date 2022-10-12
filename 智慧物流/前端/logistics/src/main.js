import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import qs from 'qs'
import Vant from 'vant';
import 'vant/lib/index.css';


import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);

import AMap from 'vue-amap'
Vue.use(AMap)
AMap.initAMapApiLoader({
  key: '579a74d37de489291eb454b1beff0548',
  plugin: ['AMap.AutoComplete','AMap.Geolocation','AMap.ElasticMarker','AMap.Geocoder', 'AMap.CitySearch','AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor'],
  // 默认高德 sdk 版本为 1.4.4
  v: '2.0',
  uiVersion: "1.1"
})
//高德的安全密钥
window._AMapSecurityConfig = {
	securityJsCode:'7300fc2e36852dc5d42ebabbf1a0e994',
}

Vue.use(Vant);
Vue.prototype.$axios = axios
Vue.prototype.$qs = qs
// axios.defaults.baseURL = "http://localhost:8088"
Vue.config.productionTip = false
new Vue({
  render: h => h(App),
  router,
  el: '#app',
  render: h => h(App)
}).$mount('#app')
