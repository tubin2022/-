<template>
	<div class="backLogistics">
		<div class="navBar">
			<van-nav-bar title="退货物流" left-text="返回" left-arrow @click-left="onClickLeft" />
		</div>
		<div class="logisticsInfo">
			<div class="back" v-for="item in realBackLogistics" :key="item.logisticsId">
				<div style="height: 10px;"></div>
				<div class="backOrder">
					<div style="height: 10px;"></div>
					<van-row>
					  <van-col span="20" offset="1">
						  <van-icon name="notes-o" color="#1989fa" />
						  <span style="font-size: 15px;font-weight: bolder;">运单号:{{item.logisticsNumber}}</span>
					  </van-col>
					</van-row>
					<div style="height: 20px;"></div>
					<van-row>
					  <van-col span="6" offset="3">
						  <span style="font-size: 20px;font-weight: bolder;">{{item.sendCity}}</span>
					  </van-col>
					  <van-col span="6" offset="1">
						   <img src="../image/arrowhead.png" style="width: 65px;height: 22px;"/>
					  </van-col>
					  <van-col span="6" offset="1">
							<span style="font-size: 20px;font-weight: bolder;">{{item.receiveCity}}</span>
					  </van-col>
					</van-row>
					<div style="height: 10px;"></div>
					<van-row>
					  <van-col span="6" offset="3">
						  <span style="font-size: 18px;color: #C8C6C6;">{{userName}}</span>
					  </van-col>
					  <van-col span="7" offset="1">
						   <span style="font-size: 15px;font-weight: bolder;">{{item.logisticsStatus}}</span>
					  </van-col>
					  <van-col span="6" offset="1">
							<span style="font-size: 18px;color: #C8C6C6;">{{item.receiveUserName}}</span>
					  </van-col>
					</van-row>
					<div style="height: 12px;"></div>
					<van-row>
					  <van-col span="15" offset="1">
						  <span style="font-size: 13px;color: #C8C6C6;" v-if="item.receiveTime != null">
							  签收时间:{{item.receiveTime}}
						  </span><br v-if="item.receiveTime != null"/>
						  <span style="font-size: 13px;color: #C8C6C6;">
							  寄件地址：{{item.send}}
						  </span><br />
						  <span style="font-size: 13px;color: #C8C6C6;">
							  收件地址：{{item.receive}}
						  </span>
					  </van-col>
					  <van-col span="4" offset="4">
						   <span style="font-size: 20px;text-align: center;color: orange;">￥{{item.cost}}</span>
					  </van-col>
					</van-row>
					<div style="height: 12px;"></div>
					<van-row>
					  <van-col span="12" offset="12">
						  <van-button type="default" round size="small" @click="logisticsMap(item)">查看物流</van-button>
						  &nbsp;&nbsp;
						  <van-button type="default" round size="small" @click="moreOne">再来一单</van-button>
					  </van-col>
					</van-row>
				</div>
			</div>
		</div>
		<!-- 加载提示 -->
		<div class="load" v-if="showLoad">
			<van-loading size="24px" vertical text-color="#0094ff" color="#0094ff">加载中...</van-loading>
		</div>
		<!-- 空状态 -->
		<van-empty
		  class="custom-image"
		  image="https://img01.yzcdn.cn/vant/custom-empty-image.png"
		  description="亲,您还没有物流订单信息~"
		  v-if="showEmpty"
		/>
	</div>
</template>

<script>
import http from 'axios'
import {get} from '../../http/axios'
	export default {
	  data() {
	    return {
			userId:'',
			userName:'',
			userPhone:'',
			backLogistics:{},
			realBackLogistics:{},
			showLoad:true,
			showEmpty:false
		}
	},
	created() {
		this.getBacklogistics()
	},
	methods:{
		onClickLeft() { //返回上一页
			this.$router.go(-1)
		},
		async getBacklogistics(){
			//获取登录用户信息
			let token = localStorage.getItem('token')
			let params = {
				userToken:token
			}
			let userInfo = await get('/user/userInfo',params)
			if(userInfo.data.code === 1000){
				this.userId = userInfo.data.data.userId
				this.userName = userInfo.data.data.userName
				this.userPhone = userInfo.data.data.userPhone
				//查询我的退货记录
				let params = {
					receiveUserName:this.userName,
					receiveUserPhone:this.userPhone,
					logisticsStatus:'退货'
				}
				let result = await get('/logistics/pageQuery',params)
				// console.log(result)
				this.backLogistics = result.data.data.data
				this.getBack(this.backLogistics)
			}
		},
		async getBack(arrObj){
			//将数组对象中的每一个经纬度转换为详细地址
			for(var i = 0;i<arrObj.length;i++){
				//将经纬度转换为详细地址
				await http.get('http://localhost:8080/amap/regeo',{
					params:{
						output:'json',
						location:arrObj[i].sendAddress[0]+","+arrObj[i].sendAddress[1],
						key:'f00282ae8aa693716b42f9971486eba6'
					},
				}).then(async res=>{
					await http.get('http://localhost:8080/amap/regeo',{
						params:{
							output:'json',
							location:arrObj[i].receiveAddress[0]+","+arrObj[i].receiveAddress[1],
							key:'f00282ae8aa693716b42f9971486eba6'
						},
					}).then(res2=>{
						this.backLogistics[i].send = res.data.regeocode.formatted_address
						this.backLogistics[i].sendCity = res.data.regeocode.addressComponent.city
						this.backLogistics[i].receive = res2.data.regeocode.formatted_address
						this.backLogistics[i].receiveCity = res2.data.regeocode.addressComponent.city
						console.log(this.backLogistics)
					})
				})	
			}
			this.showLoad = false
			this.realBackLogistics = this.backLogistics
			if(this.realBackLogistics.length === 0){
				this.showEmpty = true
			}
		},
		moreOne(){
			this.$router.push({
				path:'/sendLogistics'
			})
		},
		logisticsMap(item){
		this.$router.push({
			path:'/logisticsMap',
			query:{
				logistics:JSON.stringify(item)
			}
		})
		// console.log(item)
		},
	}
}
</script>

<style>
	.backLogistics{
		background-color: #F6F6F6;
		/* height: 100vh; */
	}
	.backLogistics .logisticsInfo{
		width: 100%;
		height: 100%;
	}
	.backLogistics .logisticsInfo .backOrder{
		width: 95%;
		border-radius: 15px;
		background-color: white;
		margin: 0 auto;
		text-align: left;
	}
</style>
