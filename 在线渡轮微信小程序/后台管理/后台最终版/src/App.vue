<template>
      <el-container>
		  <el-header height="90px" v-show="visible" key="1">
			  <Top style="width: 14100px; height: 90px;font-size: 30px;
			  color: #d5f8ff;background-color: rgb(64, 69, 81);
			  dispaly:table;display:table-cell;vertical-align:middle;text-align: left;"
			  @out="outLogin">
			  </Top>
		  </el-header>
		<el-container >
			<el-aside width="200px" v-show="visible" key="2">
          <!-- 侧边栏 -->
		  <el-scrollbar style="width: 100%;">
			  <Ticketing >
					
			</Ticketing>
		  </el-scrollbar>
				
			</el-aside>
			<el-main>
			<!-- Body -->
			<Login v-show="!visible" style="height: 100vh;" @dosubmit="getCode" key="3"></Login>
				<router-view style="background-color: #e5ffff;" v-show="visible" key="4">
				</router-view>
			</el-main>
		</el-container>
        
      </el-container>
</template>

<script>
import Top from './components/Top.vue'
import Ticketing from './components/Ticketing.vue'
import Basic from './components/Basic.vue'
import Stock from './components/Stock.vue'
import Boat from './components/Boat.vue'
import AddUser from './components/AddUser.vue'
import ChangeUser from './components/ChangeUser.vue'
import QueryUser from './components/QueryUser.vue'
import Order from './components/Order.vue'
import Feedback from './components/Feedback.vue'
import Statistics from './components/Statistics.vue'
import Login from './components/Login.vue'
import "../icon/iconfont.css"

export default {
  name: 'app',
  data(){
	  return{
		  visible: false,
	  }
  },
  components: {
	Top,
    Ticketing,
	Basic,
	Stock,
	AddUser,
	ChangeUser,
	QueryUser,
	Order,
	Feedback,
	Statistics,
	Login,
	Boat
  },
  methods: {
      //第六步：定义获取子组件值的方法，该方法的参数就为子组件传递过来的值
      getCode(temp) {
        // 控制台输出看看能不能获取
        console.log(temp);
		if(temp == "1000"){
			this.$alert("登录成功", {
				confirmButtonText: '确定',
				type: 'success',
			});
			this.visible = !this.visible;
		}
		else{
			this.$alert("用户名或密码错误", {
				confirmButtonText: '确定',
				type: 'warning',
			});
		}
      },
	  outLogin(temp){
		  if(temp==1)
		  {
			  this.$alert("退出登录", {
			  	confirmButtonText: '确定',
			  	type: 'warning',
			  });
			  this.visible = !this.visible;
		  }
		  console.log("八嘎"+temp);
	  }
	},
	created() {
		document.title = "在线轮渡购票后台管理系统"	
	},
}
</script>

<style scoped>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  padding: 0px;
}
.el-card__body, .el-main {
   padding: 0px;
}
.el-header{
	padding: 0px;
}


</style>
