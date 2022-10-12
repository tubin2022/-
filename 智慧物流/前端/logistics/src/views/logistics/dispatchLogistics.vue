<template>
	<div class="dispatch">
		<div class="navBar">
			<van-nav-bar
			  title="物流派送"
			  left-text="返回"
			  left-arrow
			  @click-left="onClickLeft"
			/>
		</div>
		<!-- 地图容器 -->
		<div id="container" />
		<div class="dispatchInfo">
			<div style="height: 15px;"></div>
			<div class="inputInfo">
				<van-field
				  v-model="logisticsNumber"
				  required
				  center
				  clearable
				  label="物流编号"
				  placeholder="请输入物流编号"
				>
			  <template #button>
				<van-button size="small" type="primary" v-if="showButton" @click="confirmLogistics">确认</van-button>
				<van-button size="small" type="primary" v-if="!showButton" @click="successSend">
					确认送达
				</van-button>
			  </template>
				</van-field>
				 <div style="height: 15px;"></div>
				 <van-field
				   :value="startAddress"
				   center
				   clearable
				   readonly
				   label="出发地"
				   placeholder="请输入出发地"
				 >
				 </van-field>
				 <van-field
				   :value="arriveAddress.locationName"
				   center
				   clearable
				   label="下一站"
				   placeholder="请输入下一站"
				   @focus="editArrive"
				   ref="arrive"
				 >
				 </van-field>
			</div>
			<div style="height: 15px;"></div>
			<div class="steps">
				<van-steps direction="vertical" :active="0">
				  <van-step v-if="startAddress != ''">
				   <span style="font-size: 15px;font-weight: bolder;">【当前位置】{{startAddress}}</span><br />
				   <span>{{startDate}}</span>
				  </van-step>
				  <van-step v-if="showStep">
				    <span style="font-size: 15px;font-weight: bolder;">【下一站】{{arriveAddress.locationName}}</span><br />
				  </van-step>
				  <van-step v-if="endAddress != ''">
				    <span style="font-size: 15px;font-weight: bolder;">【终点】{{endAddress}}</span><br />
				  </van-step>
				</van-steps>
			</div>
		</div>
		<!-- 遮罩层 -->
		<van-overlay :show="show">
			<div class="wrapper" @click="show = false">
				<div class="block" @click.stop>
					<van-nav-bar title="地址查询" left-arrow right-text="确认" @click-left="shutdownOverlay"
						@click-right="shutdownOverlay" />
					<aliMap @address="eventData"></aliMap>
				</div>
			</div>
		</van-overlay>
	</div>
</template>

<script>
import http from 'axios'
import {get,post} from '../../http/axios'
import { Dialog } from 'vant';
import { Toast } from 'vant';
import aliMap from './aliMap.vue'
	export default {
		components: {
			aliMap
		},
		data () {
		    return {
		      firstArr:[108.983569, 34.285675], //地图中心点
			  route:[],//路线
			  startAddress:'',
			  arriveAddress:{},
			  logisticsNumber:'',
			  showButton:true,
			  show:false,//是否展示遮罩层
			  endAddress:'',
			  showStep:false,
			  startDate:'',
			  logisticsId:'',
			  logistics:{},
			  receiveProvince:'',
			  receiveCity:'',
			  startProvince:'',
			  startCity:''
		    }
		  },
	  mounted () {
		  setTimeout(() => {
			this.initMap() // 异步加载（否则报错initMap is not defined）
			// this.initroad()
		  }, 1000)
		},
		created() {
			this.getDispatchUserInfo()
		},
	  methods: {
	    onClickLeft() {
	      this.$router.go(-1)
	    },
		// 初始化地图
		    initMap () {
		      var that = this
		      this.map = new AMap.Map('container', {
		        resizeEnable: true, // 窗口大小调整
		        center: this.firstArr, // 中心 firstArr: [116.478935, 39.997761],
		        zoom: 15,
				zoomEnable:true,
				scrollWheel:true
		      })
		      // 添加maker
		      this.marker = new AMap.Marker({
		        map: this.map,
		        position: this.firstArr,
		        icon: 'https://webapi.amap.com/images/car.png',
		        offset: new AMap.Pixel(-26, -13), // 调整图片偏移
		        autoRotation: true, // 自动旋转
		        angle: -90 // 图片旋转角度
		      })
		      // that.initroad()
		    },
		    // 初始化轨迹
		    initroad () {
		      // 绘制派送轨迹
		      var passedPolyline = new AMap.Polyline({
		        map: this.map,
		        strokeColor: '#F60A0A', // 线颜色-深蓝色
		        path: this.route,//[[116.478935, 39.997761], [108.983569, 34.285675]],
		        // strokeOpacity: 1,     //线透明度
		        strokeWeight: 6 // 线宽
		        // strokeStyle: "solid"  //线样式
		      })
		      this.map.setFitView() // 合适的视口
		    },
			async getLogisticsInfo(){
				//获取物流信息
				let params = {
					logisticsNumber:this.logisticsNumber
				}
				let info = await get('/logistics/pageQuery',params)
				if(info.data.code === 1000){
					this.logistics = info.data.data.data[0]
					// console.log(info)
					let passTime = info.data.data.data[0].passTime
					let startTime = passTime[passTime.length-1]
					// console.log(startTime)
					let startDate = this.filterTime(startTime)
					// console.log(startDate)
					this.startDate = startDate
					let pass = info.data.data.data[0].passAddresses
					let start = pass[pass.length-1]
					this.logisticsId = info.data.data.data[0].logisticsId
					this.firstArr = start
					let startRoute = [start]
					this.route = startRoute
					// console.log(this.route)
					this.initMap()
					let locationName = this.getaddre(start)
					// console.log(locationName)
					//获取终点信息
					let end = info.data.data.data[0].receiveAddress
					this.getaddre2(end)
					
				}else{
					Toast('服务器错误')
				}
				
			},
			 getaddre(geneAddress){
				 //将经纬度转换为详细地址
			    http.get('http://localhost:8080/amap/regeo',{
					params:{
						output:'json',
						location:geneAddress[0]+","+geneAddress[1],
						key:'f00282ae8aa693716b42f9971486eba6'
					},
				}).then(res=>{
					// console.log(res)
					this.startAddress = res.data.regeocode.formatted_address
					this.startProvince = res.data.regeocode.addressComponent.province
					this.startCity = res.data.regeocode.addressComponent.city
				})
			},
			getaddre2(end){
				 //将经纬度转换为详细地址
			    http.get('http://localhost:8080/amap/regeo',{
					params:{
						output:'json',
						location:end[0]+","+end[1],
						key:'f00282ae8aa693716b42f9971486eba6'
					},
				}).then(res=>{
					// console.log(res)
					this.receiveProvince = res.data.regeocode.addressComponent.province
					this.receiveCity = res.data.regeocode.addressComponent.city
					this.endAddress = res.data.regeocode.formatted_address
				})
			},
			confirmLogistics(){
				//确定物流
				Dialog.confirm({
				  message: '是否确认物流编号',
				})
				  .then(() => {
					// on confirm
					this.showButton = false//替换按钮
					//发送请求，获取物流信息
					this.getLogisticsInfo()
					// console.log("info")
				  })
				  .catch(() => {
					// on cancel
				  });
			},
			editArrive(){
				//编辑下一个到达地址
				let length = this.route.length
				if(length === 0){
					Toast('请先确认物流号')
					this.$refs.arrive.blur();
				}else{
					//编辑地址
					this.show = true
				}
			},
			shutdownOverlay() {
				//关闭遮罩层
				this.show = false;
			},
			eventData(data){
				this.arriveAddress = data
				this.showStep = true
				//将下一站信息加入到route中，并绘制地图
				let next = [data.longitude,data.latitude]
				this.route[1]=next
				this.initMap()
				this.initroad()
				// console.log(this.route)
				// console.log(data)
			},
			filterTime(time) {
			  const date = new Date(time)
			  const Y = date.getFullYear()
			  const M = date.getMonth() + 1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1 
			  const D = date.getDate()
			  const H = date.getHours() // 小时
			  const m = date.getMinutes() // 分钟
			  return `${Y}-${M}-${D} ${H}:${m}`
			},
			async getDispatchUserInfo(){
				//获取派件人信息
				let token = localStorage.getItem('token')
				let params={
					userToken:token
				}
				let userInfo = await get('/user/userInfo',params)
				this.dispatchUserId = userInfo.data.data.userId
			},
			successSend(){
				//确认送达后，将数据保存到数据库中
				Dialog.confirm({
				  message: '是否确认送达',
				})
				  .then(() => {
				    // on confirm
					this.saveData()
				  })
				  .catch(() => {
				    // on cancel
				  });
				
			},
			async saveData(){
				//保存数据
				let params = {
					dispatchUserId:this.dispatchUserId,
					logisticsId:this.logisticsId,
					arriveAddress:this.arriveAddress.longitude+","+this.arriveAddress.latitude
				}
				// console.log(params)
				let result = await post('/logistics/dispatch',params)
				// console.log(result)
				if(result.data.code === 1000){
					Toast('确认成功')
					
					//埋点数据
					let track = {
						logisticsId:this.logisticsId,
						receiveProvince:this.receiveProvince,
						receiveCity:this.receiveCity,
						startProvince:this.startProvince,
						startCity:this.startCity,
						dispatchedProvince:this.arriveAddress.province,
						dispatchedCity:this.arriveAddress.city,
						dispatchedTime:new Date().getTime(),
						dispatchedLost:0,
						company:this.logistics.dispatchCompany,
						logisticsStatus:0
					}
					console.log(track)
					post('/track/saveTrack',track)
					
					//埋点数据
					let track2 = {
						logisticsId:this.logisticsId,
						receiveProvince:this.receiveProvince,
						receiveCity:this.receiveCity,
						startProvince:this.startProvince,
						startCity:this.startCity,
						dispatchedProvince:this.arriveAddress.province,
						dispatchedCity:this.arriveAddress.city,
						dispatchedTime:new Date().getTime(),
						dispatchedLost:0,
						company:this.logistics.dispatchCompany,
						logisticsStatus:1
					}
					console.log(track)
					post('/track/saveTrack',track2)
					
					this.$router.push({
						path:'/index/send'
					})
				}else{
					Toast('请求失败，请检查网络后再试')
				}
			}
	  },
	};
</script>

<style>
	.dispatch #container{
		width:100%;
		height:50vh;
	}
	.dispatch .dispatchInfo{
		width: 98%;
		height: 50vh;
		position: absolute;
		top: 55vh;
		border-radius: 15px;
		background-color: whitesmoke;
		left: 1%;
		text-align: left;
	}
	.dispatch .dispatchInfo .steps{
		width: 98%;
		margin: 0 auto;
		border-radius: 15px;
	}
	.dispatch .dispatchInfo .inputInfo{
		width: 98%;
		margin: 0 auto;
		border-radius: 15px;
	}
</style>
