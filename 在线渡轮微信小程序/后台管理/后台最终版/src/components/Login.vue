<template>
	<div class="login">
		<div class="login-wrap">
			<h1 style="text-align: center;margin: auto;font-size: 45px;font-weight: bold;">在线轮渡购票后台管理系统</h1><br>	
	    <el-form class="login-container">
			
	      <h1 class="title" style="margin: auto;">管理员登录</h1>
	      <el-form-item label="账号">
	        <el-input prefix-icon="el-icon-s-custom" type="text" v-model="user.username" placeholder="登录账号" autocomplete="off"></el-input>
	      </el-form-item>
	      <el-form-item label="登录密码">
	        <el-input prefix-icon="el-icon-lock" type="password" v-model="user.password" placeholder="登录密码" autocomplete="off"></el-input>
	      </el-form-item>
	      <el-form-item>
	        <el-button type="success" style="width: 100%;" @click="dosubmit()">登录</el-button>
	      </el-form-item>
	    </el-form>
	  </div>
	</div>
	
</template>

<script>
	export default {
		data(){
			return{
				tableData:[],
				user:{
				username:'',
				password:'',
				}
				
			}
		},
		methods:{
				async dosubmit(){
					
					// console.log(this.tableData.code)
					// console.log(this.tableData.msg)
					// console.log(this.user.username)
					// console.log(this.user.password)
					let _this = this; //把vue对象 先保存到第三方变量中
						// 为给定 ID 的 user 创建请求
						await this.myAxios.post("http://localhost:8888/login?password="+_this.user.password+"&username="+_this.user.username)
						.then(function(response) {
							_this.tableData= response.data;
							// console.log(_this.tableData.code);
						})
						.catch(function(error) {
							console.log(error);
						});
						// console.log(this.tableData.code)
						this.$emit("dosubmit", this.tableData.code);
						this.user.username="";
						this.user.password="";
				}
		},
	}
</script>

<style >
	/* template{
		width: 100%;
		height: 100%;
		display: flex;
		
	} */
	.login-wrap {
	    box-sizing: border-box;
	    width: 100%;
	    height: 100%;
	    padding-top: 10%;
	    background: url(../../img/boat.jpeg);
	    /* background-repeat: no-repeat; */
	    /* background-position: center right; */
	    /* background-size: 100%; */
		background-size:100%;
		background-repeat: no-repeat;
	  }
	 
	  .login-container {
	    border-radius: 10px;
	    margin: 0px auto;
		/* margin-left: 220.5px; */
	    width: 350px;
	    padding: 30px 35px 15px 35px;
	    background: rgba(255, 255, 255,0.3);
	    
	    text-align: left;
	    box-shadow: 0 0 20px 2px rgba(135, 135, 135, 0.1);
	  }
	  .title {
	    margin: 0px auto 40px auto;
	    text-align: center;
	    color: #505458;
	  }
</style>
