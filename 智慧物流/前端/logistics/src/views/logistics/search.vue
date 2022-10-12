<template>
	<div class="search">
		<!-- 顶部导航 -->
		<van-nav-bar
		  title="智慧物流"
		  left-arrow
		/>
		<!-- 搜索区 -->
		<div class="searchLogistics">
			<van-search
			  v-model="value"
			  show-action
			  placeholder="请输入搜索关键词"
			  @search="onSearch"
			>
			  <template #action>
				<van-button type="info" @click="onSearch" round size="small">搜索</van-button>
			  </template>
			</van-search>
		</div>
		<!-- tab页 -->
		<div class="tab">
			<van-tabs v-model="active" @change="changeTab">
			  <van-tab title="我寄的">
				  
				  <div class="send" v-for="item in realSendLogistics" :key="item.logisticsId">
				  	<div style="height: 10px;"></div>
					<div class="sendOrder">
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
				  
				  </van-tab>
				  <van-tab title="派件中">
				  <div class="dispatching" v-for="item in realReceiveLogistics" :key="item.logisticsId">
					<div style="height: 10px;"></div>
					<div class="dispatchingOrder">
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
							  <span style="font-size: 18px;color: #C8C6C6;">{{item.sendUser.userName}}</span>
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
							  <van-button type="default" round size="small" @click="moreOne">我要寄件</van-button>
						  </van-col>
						</van-row>
					</div>
				  </div>
				  </van-tab>
				  
			  </van-tab>
			  <van-tab title="待签收">
				  <div class="receiving" v-for="item in realReceiveLogistics" :key="item.logisticsId">
				  	<div style="height: 10px;"></div>
					<div class="receivingOrder">
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
							  <span style="font-size: 18px;color: #C8C6C6;">{{item.sendUser.userName}}</span>
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
							  <span style="font-size: 13px;color: #C8C6C6;">
								  寄件地址：{{item.send}}
							  </span><br />
							  <span style="font-size: 13px;color: #C8C6C6;">
							  	  收件地址：{{item.receive}}
							  </span>
						  </van-col>
						  <van-col span="4" offset="4">
							   <span style="font-size: 20px;text-align: center;color: orange;">￥45</span>
						  </van-col>
						</van-row>
						<div style="height: 12px;"></div>
						<van-row>
						  <van-col span="18" offset="6">
							  <van-button type="default" round size="small" @click="receive(item)">
								  用户签收
							  </van-button>
							  &nbsp;&nbsp;
							  <van-button type="default" round size="small" @click="back(item)">
							  我要退签
							</van-button>
							  &nbsp;&nbsp;
							  <van-button type="default" round size="small" @click="logisticsMap(item)">查看物流</van-button>
						  </van-col>
						</van-row>
					</div>
				  </div>
			  </van-tab>
			  
			  <van-tab title="已签收">
				  <div class="received" v-for="item in realReceiveLogistics" :key="item.logisticsId">
				  	<div style="height: 10px;"></div>
					<div class="receivedOrder">
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
							  <span style="font-size: 18px;color: #C8C6C6;">{{item.sendUser.userName}}</span>
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
							  <span style="font-size: 13px;color: #C8C6C6;">
								  签收时间:{{item.receiveTime}}
							  </span><br />
							  <span style="font-size: 13px;color: #C8C6C6;">
								  寄件地址：{{item.send}}
							  </span><br />
							  <span style="font-size: 13px;color: #C8C6C6;">
							  	  收件地址：{{item.receive}}
							  </span><br />
						  </van-col>
						  <van-col span="4" offset="4">
							   <span style="font-size: 20px;text-align: center;color: orange;">￥45</span>
						  </van-col>
						</van-row>
						<div style="height: 12px;"></div>
						<van-row>
						  <van-col span="18" offset="6">
							  <van-button type="default" round size="small" @click="logisticsMap(item)">查看物流</van-button>
							  &nbsp;&nbsp;
							  <van-button type="warning" plain round size="small" @click="evaluate(item)">物流评价</van-button>
							  &nbsp;&nbsp;
							  <van-button type="default" round size="small" @click="moreOne">我要寄件</van-button>
						  </van-col>
						</van-row>
					</div>
				  </div>
			  </van-tab>
			</van-tabs>
			
			<!-- 空状态 -->
			<van-empty
			  class="custom-image"
			  image="https://img01.yzcdn.cn/vant/custom-empty-image.png"
			  description="亲,您还没有物流订单信息~"
			  v-if="showEmpty"
			/>
		</div>
		<!-- 用户签收验证身份弹出框 -->
		<div class="dialog">
			<van-dialog v-model="show" title="身份验证" show-cancel-button @confirm="confirm">
			  <van-cell-group>
			    <van-field
			      v-model="receiveName"
			      label="收件人姓名"
			      left-icon="contact"
			      placeholder="请输入收件人姓名"
			    />
			    <van-field
			      v-model="receivePhone"
			      clearable
			      label="收件人手机"
			      left-icon="phone-o"
			      placeholder="请输入收件人手机号码"
			    />
			  </van-cell-group>
			</van-dialog>
			
			<van-dialog v-model="show2" title="身份验证" show-cancel-button @confirm="confirm2">
			  <van-cell-group>
			    <van-field
			      v-model="receiveName2"
			      label="收件人姓名"
			      left-icon="contact"
			      placeholder="请输入收件人姓名"
			    />
			    <van-field
			      v-model="receivePhone2"
			      clearable
			      label="收件人手机"
			      left-icon="phone-o"
			      placeholder="请输入收件人手机号码"
			    />
			  </van-cell-group>
			</van-dialog>
		</div>
		<!-- 用户反馈评价 -->
		<div class="feed">
			<van-action-sheet v-model="show3" title="物流反馈评价">
				<van-field
				  v-model="message"
				  rows="2"
				  autosize
				  label="留言"
				  type="textarea"
				  maxlength="50"
				  placeholder="请输入留言"
				  show-word-limit
				/>
				<van-button type="info" size="small"  @click="confirmFeed()">确认</van-button>
			</van-action-sheet>
		</div>
		<!-- 加载提示 -->
		<div class="load" v-if="showLoad">
			<van-loading size="24px" vertical text-color="#0094ff" color="#0094ff">加载中...</van-loading>
		</div>
	</div>
</template>

<script>
import http from 'axios'
import { Toast } from 'vant';
import {get,post} from '../../http/axios'
	export default {
	  data() {
	    return {
	      value: '',
		  active:0,
		  show:false,
		  receiveName:'',
		  receivePhone:'',
		  userId:'',
		  userPhone:'',
		  sendLogistics:[],
		  realSendLogistics:[],
		  receiveLogistics:[],
		  realReceiveLogistics:[],
		  showLoad:true,
		  receiveLogisticsId:'',//当前要签收的物流id
		  show2:false,
		  receiveName2:'',
		  receivePhone2:'',
		  backLogisticsId:'',
		  message:'',
		  show3:false,
		  feedLogisticsId:'',
		  showEmpty:false,
		  tempItem:{}
	    };
	  },
	  created() {
	  	this.getSendlogistics()	
	  },
	  methods: {
	    onSearch() {
	      Toast(this.value);
	    },
		logisticsMap(item){
			this.$router.push({
				path:'/logisticsMap',
				query:{
					logistics:JSON.stringify(item)
				}
			})
			console.log(item)
		},
		receive(item){
			this.receiveLogisticsId = item.logisticsId
			this.tempItem = item
			this.show = true
			console.log(this.tempItem)
		},
		back(item){
			this.backLogisticsId = item.logisticsId
			this.tempItem =item
			this.show2 = true
		},
		evaluate(item){
			//物流评价
			this.feedLogisticsId = item.logisticsId
			this.show3 = true
		},
		async confirmFeed(){
			//确认提交反馈评价
			let params = {
				feedUserId:this.userId,
				logisticsId:this.feedLogisticsId,
				feedContext:this.message
			}
			let result = await post('/feedback/feed',params)
			this.show3 = false
			if(result.data.code === 1000){
				Toast('评价反馈成功')
			}else{
				Toast('网络错误，请检查网络再试')
			}
			console.log(params)
		},
		async confirm(){
			console.log('确认')
			// console.log(this.receiveName)
			// console.log(this.receivePhone)
			let params = {
				receiveUserName:this.receiveName,
				receiveUserPhone:this.receivePhone,
				logisticsId:this.receiveLogisticsId
			}
			console.log(params)
			let result = await post('/logistics/receive',params)
			//重置确认信息
			this.receiveName = ''
			this.receivePhone = ''
			if(result.data.code === 1000){
				
				//签收成功埋点数据
				let track = {
					logisticsId:this.receiveLogisticsId,
					sendProvince:this.tempItem.sendProvince,
					sendCity:this.tempItem.sendCity,
					sendTime:this.tempItem.sendTime,
					dispatchedProvince:this.tempItem.receiveProvince,
					dispatchedCity:this.tempItem.receiveCity,
					dispatchedLost:0,
					receiveProvince:this.tempItem.receiveProvince,
					receiveCity:this.tempItem.receiveCity,
					signTime:new Date().getTime(),
					company:this.tempItem.dispatchCompany,
					logisticsStatus:0
				}
				console.log(track)
				post('/track/saveTrack',track)
				
				Toast('签收成功')
				this.realReceiveLogistics = []
				this.showLoad = true
				let title = '待签收'
				this.getReceiveLogistcs(title)
			}else if(result.data.code === 3000){
				Toast(result.data.msg)
			}else{
				Toast('网络质量差，请检查网络后再试')
			}
		},
		async confirm2(){
			console.log('确认q')
			// console.log(this.receiveName)
			// console.log(this.receivePhone)
			let params = {
				receiveUserName:this.receiveName2,
				receiveUserPhone:this.receivePhone2,
				logisticsId:this.backLogisticsId
			}
			console.log(params)
			let result = await post('/logistics/back',params)
			//重置确认信息
			this.receiveName2 = ''
			this.receivePhone2 = ''
			if(result.data.code === 1000){
				
				//签收成功埋点数据
				let track = {
					logisticsId:this.receiveLogisticsId,
					sendProvince:this.tempItem.sendProvince,
					sendCity:this.tempItem.sendCity,
					sendTime:this.tempItem.sendTime,
					dispatchedProvince:this.tempItem.receiveProvince,
					dispatchedCity:this.tempItem.receiveCity,
					dispatchedLost:0,
					receiveProvince:this.tempItem.receiveProvince,
					receiveCity:this.tempItem.receiveCity,
					backTime:new Date().getTime(),
					company:this.tempItem.dispatchCompany,
					logisticsStatus:0
				}
				console.log(track)
				post('/track/saveTrack',track)
				
				Toast('退货成功')
				this.realReceiveLogistics = []
				this.showLoad = true
				let title = '待签收'
				this.getReceiveLogistcs(title)
			}else if(result.data.code === 3000){
				Toast(result.data.msg)
			}else{
				Toast('网络质量差，请检查网络后再试')
			}
		},
		async getSendlogistics(){
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
				//查询我寄的物流
				let params = {
					sendUserId:this.userId
				}
				let result = await get('/logistics/pageQuery',params)
				// console.log(result)
				this.sendLogistics = result.data.data.data
				this.getAddress(this.sendLogistics)
			}
		},
		async getAddress(arrObj){
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
						this.sendLogistics[i].send = res.data.regeocode.formatted_address
						this.sendLogistics[i].sendProvince = res.data.regeocode.addressComponent.province
						this.sendLogistics[i].sendCity = res.data.regeocode.addressComponent.city
						this.sendLogistics[i].receive = res2.data.regeocode.formatted_address
						this.sendLogistics[i].receiveProvince = res2.data.regeocode.addressComponent.province
						this.sendLogistics[i].receiveCity = res2.data.regeocode.addressComponent.city
						console.log(this.sendLogistics)
					})
				})	
			}
			this.showLoad = false
			this.realSendLogistics = this.sendLogistics
			if(this.realSendLogistics.length === 0){
				this.showEmpty = true
			}
		},
		async getReceive(arrObj){
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
						this.receiveLogistics[i].send = res.data.regeocode.formatted_address
						this.receiveLogistics[i].sendProvince = res.data.regeocode.addressComponent.province
						this.receiveLogistics[i].sendCity = res.data.regeocode.addressComponent.city
						this.receiveLogistics[i].receive = res2.data.regeocode.formatted_address
						this.receiveLogistics[i].receiveProvince = res2.data.regeocode.addressComponent.province
						this.receiveLogistics[i].receiveCity = res2.data.regeocode.addressComponent.city
						console.log(this.receiveLogistics)
					})
				})	
			}
			this.showLoad = false
			this.realReceiveLogistics = this.receiveLogistics
			if(this.realReceiveLogistics.length === 0){
				this.showEmpty = true
			}
		},
		changeTab(name,title){
			//改变tab触发事件
			// console.log(name)
			// console.log(title)
			if(name != 0){
				this.showEmpty = false
				this.realReceiveLogistics = []
				this.showLoad = true
				this.getReceiveLogistcs(title)
				
			}else{
				this.showEmpty = false
				this.realSendLogistics = []
				this.showLoad = true
				this.getSendlogistics();
				
			}
		},
		async getReceiveLogistcs(status){
			let params = {
				receiveUserName:this.userName,
				receiveUserPhone:this.userPhone,
				logisticsStatus:status
			}
			let result = await get('/logistics/pageQuery',params)
			this.receiveLogistics = result.data.data.data
			this.getReceive(this.receiveLogistics)
		},
		moreOne(){
			this.$router.push({
				path:'/sendLogistics'
			})
		}
	},
	}
</script>

<style>
	.search{
		background-color: #F6F6F6;
		/* height: 100vh; */
	}
	.search .load{
		width: 100%;
		height: 100vh;
		text-align: center;
	}
	.search .tab .send{
		width: 100%;
		height: 100%;
	}
	.search .tab .send .sendOrder{
		width: 95%;
		border-radius: 15px;
		background-color: white;
		margin: 0 auto;
		text-align: left;
	}
	.search .tab .receiving{
		width: 100%;
		height: 100%;
	}
	.search .tab .receiving .receivingOrder{
		width: 95%;
		border-radius: 15px;
		background-color: white;
		margin: 0 auto;
		text-align: left;
	}
	.search .tab .dispatching{
		width: 100%;
		height: 100%;
	}
	.search .tab .dispatching .dispatchingOrder{
		width: 95%;
		border-radius: 15px;
		background-color: white;
		margin: 0 auto;
		text-align: left;
	}
	.search .tab .received{
		width: 100%;
		height: 100%;
	}
	.search .tab .received .receivedOrder{
		width: 95%;
		border-radius: 15px;
		background-color: white;
		margin: 0 auto;
		text-align: left;
	}
	 .custom-image .van-empty__image {
	    width: 90px;
	    height: 90px;
	  }
</style>
