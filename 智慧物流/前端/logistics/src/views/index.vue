<template>
	<div id="index">
		<router-view></router-view>
		<!-- 底部标签栏 -->
		<div style="height: 60px;"></div>
		<div id="tabbar">
			<van-tabbar v-model="active" active-color="#29A1F7" inactive-color="#000" route>
			  <van-tabbar-item to="/index/send" name="home" icon="guide-o">寄快递</van-tabbar-item>
			  <van-tabbar-item to="/index/search" name="search" icon="search">查快递</van-tabbar-item>
			  <van-tabbar-item to="/index/me" name="me" icon="friends-o">我的</van-tabbar-item>
			</van-tabbar>
		</div>
	</div>
</template>

<script>
	export default {
	  data() {
	    return {
	      active:'home'
	    };
	  },
	};
</script>

<style>
</style>
