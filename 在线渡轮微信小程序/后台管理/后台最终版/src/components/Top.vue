<template>
	<div class="top" style="width: 100%;">
		<span class="iconfont icon-lunchuan1" style="font-size: 30px;"></span>
		在线轮渡购票后台管理系统
			<button @click="out" class="right-btn">退出登录</button>
	</div>
</template>

<script>
	export default {
	    data() {
	      return {
			  type:1,
	      };
	    },
	    methods: {
	      out(){
			  this.$emit("out",this.type);
			  console.log(this.type)
		  }
	    }
	  }
</script>

<style>
	.right-btn {
	  float: right; 
	  border: 0;
	  color: rgb(213,248,255);
	  font-size: 20px;
	  font-weight: bolder;
	  background:rgba(64,69,81,0.3);
	}
</style>
