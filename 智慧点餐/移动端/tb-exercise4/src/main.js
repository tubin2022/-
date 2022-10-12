import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/vant'
import Router from 'vue-router'
// 引⼊moment
import moment from 'moment'
// 全局注册
Vue.filter("fmtDate_global", function (date) {
 return moment(date).format("YYYY年MM月DD日HH时mm分ss秒");
 // 或者return⾃⼰编写的时间处理函数
})

const routerPush = Router.prototype.push
Router.prototype.push = function push(location) {
return routerPush.call(this, location).catch(error=> error)
}
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
