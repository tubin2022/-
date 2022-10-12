<template>
	<div class="send">
		<!-- 轮播 -->
		<div class="swipe">
			<van-swipe class="my-swipe" :autoplay="3000" indicator-color="white">
			  <van-swipe-item>
				  <img src="../image/swipe1.png" />
			  </van-swipe-item>
			  <van-swipe-item>
				  <img src="../image/swipe2.png" />
			  </van-swipe-item>
			  <van-swipe-item>
				  <img src="../image/swipe3.png" />
			  </van-swipe-item>
			  <van-swipe-item>
				  <img src="../image/swipe4.png" />
			  </van-swipe-item>
			</van-swipe>
		</div>
		<!-- 操作区 -->
		<div class="operation">
			<div style="height: 18px;"></div>
			<div >
				<van-grid clickable :column-num="2" :border="false" direction="horizontal">
				  <van-grid-item icon="http://localhost:8088/icon/delivery.png" text="寄快递" @click="sendLogistics"/>
				  <van-grid-item icon="http://localhost:8088/icon/scancode.png" text="派送快递" @click="dispatch" />
				</van-grid>
			</div>
			<div style="height: 15px;"></div>
			<div>
				<van-grid :column-num="3">
				  <van-grid-item icon="http://localhost:8088/icon/sendlogistics.png" text="发物流" @click="sendLogistics" />
				  <van-grid-item icon="http://localhost:8088/icon/logisticscart.png" text="整车直达" @click="sendLogistics"/>
				  <van-grid-item icon="http://localhost:8088/icon/sendsome.png" text="批量寄件" @click="sendLogistics" />
				  <van-grid-item icon="http://localhost:8088/icon/icon_aviation.png" text="航空大件" @click="sendLogistics"/>
				  <van-grid-item icon="http://localhost:8088/icon/removehome.png" text="搬家寄件"  @click="sendLogistics"/>
				  <van-grid-item icon="http://localhost:8088/icon/receivemoney.png" text="代收贷款" />
				  <van-grid-item icon="http://localhost:8088/icon/graduation.png" text="毕业搬"  @click="sendLogistics"/>
				  <van-grid-item icon="http://localhost:8088/icon/love.png" text="公益寄件" />
				  <van-grid-item icon="http://localhost:8088/icon/more.png" text="其它"  />
				</van-grid>
			</div>
			<div style="height: 10px;"></div>
			<van-row>
			  <van-col span="6"><van-icon name="http://localhost:8088/icon/price.png"/><van-tag color="#F2EFEF" text-color="#ad0000">价格时效</van-tag></van-col>
			  <van-col span="6"><van-icon name="http://localhost:8088/icon/netdot.png" /><van-tag color="#F2EFEF" text-color="#ad0000">网点查询</van-tag></van-col>
			  <van-col span="6"><van-icon name="http://localhost:8088/icon/location.png" /><van-tag color="#F2EFEF" text-color="#ad0000">派送范围</van-tag></van-col>
			  <van-col span="6"><van-icon name="http://localhost:8088/icon/standard.png" /><van-tag color="#F2EFEF" text-color="#ad0000">收寄标准</van-tag></van-col>
			</van-row>
		</div>
		<router-view></router-view>
	</div>
</template>

<script>
import { Toast } from 'vant';
import {get} from '../../http/axios'
	export default {
	  data() {
	    return {
	      active:'home'
	    };
	  },
	  methods:{
		  async sendLogistics(){
			  let token = localStorage.getItem('token');
			  if(token == null){
				  Toast('请您先登录哦')
				  this.$router.push({
					  path:'/login'
				  })
			  }else{
				  let params = {
					  userToken: token
				  }
				  let userInfo = await get('/user/userInfo',params)
				  // console.log(userInfo)
				  if(userInfo.data.code === 1000){
					  this.$router.push({
						  path:'/sendLogistics'
					  })
				  }
			  }
		  },
		  async dispatch(){
			  let token = localStorage.getItem('token');
			  if(token == null){
				  Toast('请您先登录哦')
				  this.$router.push({
					  path:'/login'
				  })
			  }else{
				  let params = {
					  userToken: token
				  }
				  let userInfo = await get('/user/userInfo',params)
				  // console.log(userInfo)
				  if(userInfo.data.code === 1000){
					  let userId = userInfo.data.data.userId
					  // console.log(userId)
					  let params = {
						  userId:userId
					  }
					  let roles = await get('/role/queryRole',params)
					  let userRole = roles.data.data
					  let access = false
					  for(var item of userRole){
						  if(item.roleName === 'dispatchUser'){
							  access = true
						  }
					  }
					  // console.log(access)
					  if(access){
						  this.$router.push({
						  	path:'/dispatchLogistics'
						  })
					  }else{
						  Toast('您还不是派送员，请申请成功后再试')
					  }
					  
				  }
			  }
			  
		  }
	  }
	};
</script>

<style>
	 .my-swipe .van-swipe-item {
	    color: #fff;
	    font-size: 20px;
	    line-height: 150px;
	    text-align: center;
		height: 150px;
	  }
	  img{
		  width: 100%;
		  height: 150px;
	  }
	  .operation{
		  border-top-left-radius: 30px;
		  border-top-right-radius: 30px;
		  background-color: #F2EFEF;
		  opacity: 0.8;
		  width: 100%;
		  height: 100%;
		  position: absolute;
		  top: 138px;
	  }
</style>
