<template>
	<div class="logisticsMap">
		<div class="navBar">
			<van-nav-bar
			  title="物流详情"
			  left-text="返回"
			  left-arrow
			  @click-left="onClickLeft"
			/>
		</div>
		<div id="container" />
		<div class="logisticsInfo" @touchmove="move" @touchstart="start" :style="'top:calc(60vh + '+movePx+'px);'">
			<div style="height: 10px;"></div>
			<van-row>
				<van-col span="22" offset="2">
					<span style="font-size: 13px;color: #969799;">{{logistics.dispatchCompany}} {{logistics.logisticsNumber}}</span>
				</van-col>
			</van-row>
			<van-row>
				<van-col span="22" offset="1">
					<van-steps direction="vertical" :active="activeNum">
					  <van-step>
					    <span style="font-size: 15px;font-weight: bolder;">【出发】{{logistics.send}}</span><br />
					    <span>{{logistics.sendTime}}</span>
						<br/><span style="font-size: 10px;" v-if="dispatchUserInfo.length>0">
						由{{dispatchUserInfo[0].userName}}
						<span style="color:orange">[{{dispatchUserInfo[0].userPhone}}]</span>
						派送至下一站
						</span>
					  </van-step>
					  <van-step v-for="(item,index) in realLogistics.pass" :key="index" v-if="item != logistics.receive & index != 0">
					    <span style="font-size: 15px;font-weight: bolder;">【送至】{{item}}</span><br />
					    <span>{{realLogistics.passDate[index]}}</span>
						<br/><span style="font-size: 10px;" v-if="dispatchUserInfo[index] != undefined">
						由{{dispatchUserInfo[index].userName}}
						<span style="color:orange">[{{dispatchUserInfo[index].userPhone}}]</span>
						派送至下一站
						</span>
					  </van-step>
					  <van-step>
					    <span style="font-size: 15px;font-weight: bolder;">【目的地】{{logistics.receive}}</span><br />
					    <span v-if="logistics.receiveTime != null">{{logistics.receiveTime}}</span>
					  </van-step>
					</van-steps>
				</van-col>
			</van-row>
		</div>
	</div>
</template>

<script>
import http from 'axios'	
import { Toast } from 'vant';
import {get} from '../../http/axios'
import qs from 'qs'
// var lineArr = [[116.478935, 39.997761], [108.983569, 34.285675], [103.85094, 35.987496], [106.205794, 38.458831], [111.761777, 40.875595]]
	export default {
	  data () {
	      return {
	        firstArr: [],//[108.983569, 34.285675], // 中心点/初始坐标
			startPx:0,//开始点击的坐标距离
			movePx:0, //移动的坐标距离,
			up:0,
			down:1,
			logistics:{},
			realLogistics:{},
			line:[],//路线（已经途径的，以及最近一战到终点）
			passedLine:[],//已经完成的路线
			sendPX:[],//寄件坐标
			receivePX:[],//收件坐标
			activeNum:0,
			dispatchUsers:[],
			dispatchUserInfo:[]
	      }
	    },
		created() {
			// console.log(JSON.parse(this.$route.query.logistics))
			this.logistics = JSON.parse(this.$route.query.logistics)
			this.firstArr = this.logistics.sendAddress
			this.passedLine = this.logistics.passAddresses
			let lines = this.passedLine.concat()
			lines[lines.length] = this.logistics.receiveAddress
			this.line = lines
			this.dispatchUsers = this.logistics.dispatchUserId
			// console.log(this.line)
			// console.log(this.passedLine)
			// console.log(this.logistics)
			this.getAddress()
			this.getDispatchUsers()
		},
	  mounted () {
	      setTimeout(() => {
	        this.initMap() // 异步加载（否则报错initMap is not defined）
	        // this.initroad()
	      }, 1000)
	    },	
	  methods: {
		 getDispatchUsers(){
			  http.get('http://localhost:8088/user/queryUsers',{
			      params:{
			          userIds:this.dispatchUsers
			      },
			      paramsSerializer: function (params) {  
			        return qs.stringify(params, { arrayFormat: "repeat" });  
			      },
			  }).then(res =>{
				  // console.log(res)
				  this.dispatchUserInfo = res.data.data
				  console.log(this.dispatchUserInfo)
			  })
		  },
		async getAddress(){
			//坐标转换
			this.sendPX = this.firstArr.concat()
			this.receivePX = this.logistics.receiveAddress
			// console.log(this.sendPX)
			// console.log(this.receivePX)
			// let send = await this.pxToAddress(this.sendPX[0]+","+this.sendPX[1])
			// let receive = await this.pxToAddress(this.receivePX[0]+","+this.receivePX[1])
			let pass = []
			for(var i =0;i<this.passedLine.length;i++){
				let res = await this.pxToAddress(this.passedLine[i][0]+","+this.passedLine[i][1])
				pass[i] = res.data.regeocode.formatted_address
			}
			let passDate = []
			for(var i =0;i<this.logistics.passTime.length;i++){
				let res = this.filterTime(this.logistics.passTime[i])
				passDate[i] = res
			}
			this.logistics.passDate = passDate
			this.logistics.pass = pass
			this.realLogistics = this.logistics
			// console.log(send)
			// console.log(receive)
			let activeNum = this.logistics.pass.length-1
			this.activeNum =activeNum
			console.log(this.logistics)
		},
		filterTime(time) {
		  const date = new Date(time)
		  const Y = date.getFullYear()
		  const M = date.getMonth() + 1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1 
		  const D = date.getDate()
		  const H = date.getHours() ===0 ? '00' : date.getHours() // 小时
		  const m = date.getMinutes() // 分钟
		  const S = date.getSeconds()
		  return `${Y}-${M}-${D} ${H}:${m}:${S}`
		},
		pxToAddress(location){
			return http({
				  method: 'get',
				  url: 'http://localhost:8080/amap/regeo',
				  params:{
				  	output:'json',
				  	location:location,
				  	key:'f00282ae8aa693716b42f9971486eba6'
				  }
			})
		},   
	    onClickLeft() {
	      this.$router.go(-1)
	    },
		// 初始化地图
		    initMap () {
		      var that = this
		      this.map = new AMap.Map('container', {
		        resizeEnable: true, // 窗口大小调整
		        center: this.firstArr, // 中心 firstArr: [116.478935, 39.997761],
		        zoom: 8,
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
		      that.initroad()
		    },
		    // 初始化轨迹
		    initroad () {
		      // 绘制还未经过的路线
		      this.polyline = new AMap.Polyline({
		        map: this.map,
		        path: this.line,
		        showDir: true,
		        strokeColor: '#14E4ED', // 线颜色--浅蓝色
		        // strokeOpacity: 1,     //线透明度
		        strokeWeight: 6, // 线宽
		        // strokeStyle: "solid"  //线样式
		        lineJoin: 'round' // 折线拐点的绘制样式
		      })
		      // 绘制路过了的轨迹
		      var passedPolyline = new AMap.Polyline({
		        map: this.map,
		        strokeColor: '#AD0909', // 线颜色-深蓝色
		        path: this.passedLine,
		        // strokeOpacity: 1,     //线透明度
		        strokeWeight: 8 // 线宽
		        // strokeStyle: "solid"  //线样式
		      })
		      this.map.setFitView() // 合适的视口
		    },
			move(e){
				// console.log('移动')
				// console.log(e.targetTouches[0].clientY)
				let moveHeight = e.targetTouches[0].clientY -this.startPx
				let movingHeight = window.screen.height * 0.2
				// console.log(moveHeight)
				if(moveHeight < 0){
					if(this.up == 0){
						this.movePx = -movingHeight
						this.up = 1;
						this.down = 0;
						// console.log(this.movePx)
					}else{
						return
					}
				}else{
					if(this.down == 0){
						this.movePx = movingHeight
						this.up = 0
						this.down = 1
						// console.log(this.movePx)
					}else{
						return
					}
				}
			},
			start(e){
				// console.log('按下')
				// console.log(e.targetTouches[0].clientX)
				this.startPx = e.targetTouches[0].clientY;
				// console.log(this.startPx)
			},
	  },
   }
</script>

<style>
	.logisticsMap #container{
		position: relative;
		width:100%;
		height:90vh;
		text-align: center;
	}
	.logisticsMap .logisticsInfo{
		position: absolute;
		width: 98%;
		height: 60vh;
		background-color: white;
		overflow-y: hidden;
		border-radius: 15px;
		left: 1%;
		text-align: left;
		overflow: auto;
	}
	 .amap-logo {
		 right: 0 !important;
		 left: auto !important;
		 display: none !important;
	 }
	 
	 .amap-copyright {
		 right: 70px !important;
		 left: auto !important;
		 opacity:0 !important;
	 }
</style>
