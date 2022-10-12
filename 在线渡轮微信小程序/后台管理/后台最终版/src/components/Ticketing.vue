<template>
	<div>
		<!-- <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
		  <el-radio-button :label="false">展开</el-radio-button>
		  <el-radio-button :label="true">收起</el-radio-button>
		</el-radio-group> -->
		<el-menu :default-active="this.$router.path" 
			router
			class="el-menu-vertical-demo" 
			style="height: 952px;width: 199px;">
		  <el-submenu index="1">
		    <template slot="title">
		      <i class="iconfont icon-fapiao" style="font-size: 26px;"></i>&nbsp;
		      <span slot="title" style="color:rgb(163, 210, 207);font-size: 18px;">票务管理</span>
		    </template>
		      <el-menu-item index="/Basic" >班次基础信息</el-menu-item>
		      <el-menu-item index="/Stock" >票务库存信息</el-menu-item>
		  </el-submenu>
		  <el-submenu index="2">
			  <template slot="title">
				<i class="iconfont icon-yonghu" style="font-size: 26px;"></i>&nbsp;
				<span slot="title" style="color:rgb(163, 210, 207);font-size: 18px;">用户管理</span>  
			  </template>
				<el-menu-item index="/AddUser">添加用户</el-menu-item>
				<el-menu-item index="/ChangeUser">更改用户</el-menu-item>
				<el-menu-item index="/QueryUser">查询用户</el-menu-item>
		  </el-submenu>
		  <el-submenu index="3">
			<template slot="title">
				<i class="iconfont icon-fapiaoguanli" style="font-size: 21px;"></i>&nbsp;
				<span slot="title" style="color:rgb(163, 210, 207);font-size: 18px;">购票管理</span>
			</template>
				<el-menu-item index="/Order">订单管理</el-menu-item>
		  </el-submenu>
		  <el-menu-item index="/Boat">
		    <i class="iconfont icon-lunchuan" style="font-size: 26px;"></i>&nbsp;
		    <span slot="title" style="color:rgb(163, 210, 207);font-size: 18px;">渡轮管理</span>
		  </el-menu-item>
		  <el-menu-item index="/Feedback">
		    <i class="iconfont icon-fankui" style="font-size: 26px;"></i>&nbsp;
		    <span slot="title" style="color:rgb(163, 210, 207);font-size: 18px;">用户反馈</span>
		  </el-menu-item>
		  <el-menu-item index="/Statistics">
		    <i class="iconfont icon-shuzhuangtu" style="font-size: 26px;"></i>&nbsp;
		    <span slot="title" style="color:rgb(163, 210, 207);font-size: 18px;">统计分析</span>
		  </el-menu-item>
		</el-menu>
	</div>
</template>

<script>
	// export default {
	//     data() {
	//       return {
	//         isCollapse: true
	//       };
	//     },
	//     methods: {
	//       handleOpen(key, keyPath) {
	//         console.log(key, keyPath);
	//       },
	//       handleClose(key, keyPath) {
	//         console.log(key, keyPath);
	//       }
	//     }
	//   }
</script>

<style>
	.el-menu-vertical-demo:not(.el-menu--collapse) {
	    width: 199px;
	    min-height: 800px;
		background-color: rgb(64,69,81);
		color:#4ad5ff;
		border-top: 1px solid rgb(106, 114, 134);
		border-right: 0px;
		margin-right: 0px;
	  }
	  
</style>
