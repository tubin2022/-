<template>
	<div class="index">
		<div class="left">
			<div class="leftTop">
				<div class="leftTopInfo">
					<div style="height: 20px;"></div>
					<div class="title">
						<span style="font-size: 15px;color: #0593E8;font-weight: bold;">全国当前到件量</span>
					</div>
					<div style="height: 20px;"></div>
					<span style="font-size: 45px;color: white;font-weight: bold;">{{chinaAllLogistics}}</span>
				</div>
			</div>
			<div class="leftCenter">
				<div class="leftCenterTitle">
					<el-row type="flex">
					  <el-col :span="12">
						  <span style="font-size: 15px;color: white;font-weight: bold;">全国物流各公司占比数据</span>
					  </el-col>
					  <el-col :span="7">
					  </el-col>
					  <el-col :span="5">
						  <el-popover
						    placement="right"
						    width="400"
						    trigger="click">
							 <div>
							    <el-radio-group v-model="chinaLogisticsDate" size="mini">
							      <el-radio-button label="year">年</el-radio-button>
							      <el-radio-button label="month">月</el-radio-button>
							      <el-radio-button label="date">日</el-radio-button>
							    </el-radio-group>
								<el-date-picker
								  v-model="selectChinaLogisticsDate"
								  :type="chinaLogisticsDate"
								  placeholder="选择时间">
								</el-date-picker>
							  </div>
							<el-button slot="reference" type="default" icon="el-icon-date" size="mini">日期</el-button>
						  </el-popover>
					  </el-col>
					</el-row>
				</div>
				<div class="chinaCompany">
					<chinaCompany :chinaCompanys="chinaCompanys"></chinaCompany>
				</div>
			</div>
			<div class="leftBottom">
				<div class="leftBottomTitle">
					<el-row type="flex">
					  <el-col :span="9">
						  <span style="font-size: 15px;color: white;font-weight: bold;">{{provinceName}}{{provinceStatus}}数据</span>
					  </el-col>
					  <el-col :span="10">
						  <el-radio-group v-model="provinceStatus" size="mini" @change="provinceStatusChange">
							<el-radio-button label="寄件"></el-radio-button>
							<el-radio-button label="派件"></el-radio-button>
						  </el-radio-group>
					  </el-col>
					  <el-col :span="5">
						  <el-popover
						    placement="right"
						    width="400"
						    trigger="click">
							 <div>
							    <el-radio-group v-model="provinceDate" size="mini">
							      <el-radio-button label="year">年</el-radio-button>
							      <el-radio-button label="month">月</el-radio-button>
							      <el-radio-button label="date">日</el-radio-button>
							    </el-radio-group>
								<el-date-picker
								  v-model="selectProvinceDate"
								  :type="provinceDate"
								  placeholder="选择时间">
								</el-date-picker>
							  </div>
							<el-button slot="reference" type="default" icon="el-icon-date" size="mini">日期</el-button>
						  </el-popover>
					  </el-col>
					</el-row>
				</div>
				<div class="province">
					<province :province="provicne" :provinceData="provinceSendOrDispatch"></province>					
				</div>
			</div>
		</div>
		<div class="center">
			<dv-border-box-13>
			<div class="centerTop">
				<div style="height: 10px;"></div>
				<div id="title" style="font-size: 30px;color: white; font-weight: bolder;">
					智慧物流服务中心
				</div>
				<dv-decoration-5 style="width:96%;height:40px; margin: 0 auto;" />
				<div style="height: 20px;"></div>
				<div class="info">
					<el-row>
					  <el-col :span="7" :offset="1">
							<div class="time">
								<div style="height: 3vh;"></div>
								<span style="font-size: 25px;color: white;font-weight: bold;">{{time}}</span><br />
								<span style="font-size: 25px;color: white;font-weight: bold;">{{date}}&nbsp;&nbsp;{{week}}</span>
							</div>
							<div style="height: 2vh;"></div>
							<el-row type="flex">
							  <el-col :span="14">
								  <el-radio-group v-model="centerStatus" size="mini" @change="centerStatusChange">
									<el-radio-button label="寄件"></el-radio-button>
									<el-radio-button label="派件"></el-radio-button>
								  </el-radio-group>
							  </el-col>
							  <el-col :span="6" :offset="2">
								  <el-popover
								    placement="right"
								    width="400"
								    trigger="click">
									 <div>
									    <el-radio-group v-model="centerDate" size="mini">
									      <el-radio-button label="year">年</el-radio-button>
									      <el-radio-button label="month">月</el-radio-button>
									      <el-radio-button label="date">日</el-radio-button>
									    </el-radio-group>
										<el-date-picker
										  v-model="selectCenterDate"
										  :type="centerDate"
										  placeholder="选择时间">
										</el-date-picker>
									  </div>
									<el-button slot="reference" type="default" icon="el-icon-date" size="mini">日期</el-button>
								  </el-popover>
							  </el-col>
							</el-row>
						</el-col>
					  <el-col :span="13" :offset="2">
						  <dv-scroll-board :config="config" style="width:100%;height:25vh" />
					  </el-col>
					</el-row>
				</div>
			</div>
			<div class="china">
				<china @province="eventData" :transaction="transaction"></china>
			</div>
			</dv-border-box-13>
		</div>
		
		<div class="right">
			<div class="rightTop">
				<div class="rightTopTitle">
					<el-row type="flex">
					  <el-col :span="10">
						  <span style="font-size: 15px;color: white;font-weight: bold;">{{provinceName}}物流详细数据</span>
					  </el-col>
					  <el-col :span="9">
					  </el-col>
					  <el-col :span="5">
						  <el-popover
						    placement="right"
						    width="400"
						    trigger="click">
							 <div>
							    <el-radio-group v-model="rightProvinceDate" size="mini">
							      <el-radio-button label="year">年</el-radio-button>
							      <el-radio-button label="month">月</el-radio-button>
							      <el-radio-button label="date">日</el-radio-button>
							    </el-radio-group>
								<el-date-picker
								  v-model="selectRightProvinceDate"
								  :type="rightProvinceDate"
								  placeholder="选择时间">
								</el-date-picker>
							  </div>
							<el-button slot="reference" type="default" icon="el-icon-date" size="mini">日期</el-button>
						  </el-popover>
					  </el-col>
					</el-row>
				</div>
				<logisticsStatus :statusLogisticses="statusLogisticses"></logisticsStatus>
			</div>
			<div class="rightCenter">
				<div style="height: 20px;"></div>
				<div class="title">
					<span style="font-size: 15px;color: #0593E8;font-weight: bold;">{{provinceName}}当前到件量</span>
				</div>
				<div style="height: 20px;"></div>
				<span style="font-size: 45px;color: white;font-weight: bold;">{{provinceAllLogistics}}</span>
			</div>
			
			<div class="rightBottom">
				<div class="rightBottomTitle">
					<el-row type="flex">
					  <el-col :span="13">
						  <span style="font-size: 15px;color: white;font-weight: bold;">{{provinceName}}物流各公司占比数据</span>
					  </el-col>
					  <el-col :span="6">
					  </el-col>
					  <el-col :span="5">
						  <el-popover
						    placement="right"
						    width="400"
						    trigger="click">
							 <div>
							    <el-radio-group v-model="rightBottomProvineDate" size="mini">
							      <el-radio-button label="year">年</el-radio-button>
							      <el-radio-button label="month">月</el-radio-button>
							      <el-radio-button label="date">日</el-radio-button>
							    </el-radio-group>
								<el-date-picker
								  v-model="selectRightBottomProvineDate"
								  :type="rightBottomProvineDate"
								  placeholder="选择时间">
								</el-date-picker>
							  </div>
							<el-button slot="reference" type="default" icon="el-icon-date" size="mini">日期</el-button>
						  </el-popover>
					  </el-col>
					</el-row>
				</div>
				<div class="provinceCompany">
					<provinceCompany :provinceCompanys="provinceCompanys"></provinceCompany>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import china from './china.vue'
import province from './province.vue'
import chinaCompany from './chinaCompany.vue'
import provinceCompany from './provinceCompany.vue'
import logisticsStatus from './logisticsStatus.vue'
import http from 'axios'
	export default {
	    components: {china,province,chinaCompany,logisticsStatus,provinceCompany},
		data(){
			return {
				provicne:{},
				provinceName:'江西省',
				provinceStatus:'寄件',//左下角选择省份是寄件还是派件
				provinceDate:'year',//左下角选择时间的维度
				selectProvinceDate:'2022',//左下角选择的时间
				selectProvinceDate2:'2022',//左下角选择的时间
				centerStatus:'寄件',//中间选择是寄件还是派件
				centerDate:'year',//中间选择的时间维度
				selectCenterDate:'2022',//中间选择的时间
				selectCenterDate2:'2022',//中间选择的时间
				rightProvinceDate:'year',//右上角最上层的时间维度
				selectRightProvinceDate:'2022',//右上角最上层选择的时间
				selectRightProvinceDate2:'2022',//右上角最上层选择的时间
				rightBottomProvineDate:'year',//右下角时间维度
				selectRightBottomProvineDate:'2022',//右下角选择的时间
				selectRightBottomProvineDate2:'2022',//右下角选择的时间
				chinaLogisticsDate:'year',//全国物流各公司占比时间
				selectChinaLogisticsDate:'2022',//全国物流各公司占比选择的时间
				selectChinaLogisticsDate2:'2022',//全国物流各公司占比选择的时间
				config:{
					header: ['出发省份', '到达省份', '总量'],
					data: []
				},
				chinaAllLogistics:'',//当前全国物流总量
				provinceAllLogistics:'',//当前省物流总量
				chinaCompanys:[],//全国物流公司占比
				provinceCompanys:[],//省物流公司占比
				statusLogisticses:[],//省各状态物流量
				provinceSendOrDispatch:[],//省寄件或者派件数据
				transaction:[],
				time:'',
				date:'',
				week:''
			}
		},
		methods:{
			eventData(data){
				//接收子组件（中国地图传来的点击省份和省份code）
				this.provicne = data
				if(data.provinceName ==='新疆维吾尔自治区'){
					this.provinceName = '新疆'
				}else if(data.provinceName === '西藏自治区'){
					this.provinceName = '西藏'
				}else if(data.provinceName === '内蒙古自治区'){
					this.provinceName = '内蒙古'
				}else if(data.provinceName === '宁夏回族自治区'){
					this.provinceName = '宁夏'
				}else if(data.provinceName === '广西壮族自治区'){
					this.provinceName = '广西省'
				}else{
					this.provinceName = data.provinceName
				}
				this.getProvinceLogistics()
				this.getProvinceCompany()
				this.getStatusLogistics()
				this.getProvinceSendOrDispatch(this.provinceStatus)
				this.getTransaction(this.centerStatus)
				// console.log(this.provicne)
			},
			async getChinaAllLogistics(){
				//获取全国物流量
				let param = {
					time:this.selectCenterDate2
				}
				// console.log(param)
				let res = await http.get('http://localhost:8089/chinaLogistics/query',{params:param})
				// console.log(res.data.data)
				this.chinaAllLogistics = res.data.data
			},
			async getProvinceLogistics(){
				//获取省物流量
				let params = {
					pro:this.provinceName,
					time:this.selectCenterDate2
				}
				let res = await http.get('http://localhost:8089/provinceLogistics/query',{params:params})
				// console.log(res.data.data)
				this.provinceAllLogistics = res.data.data
			},
			async getChinaCompany(){
				//获取全国物流公司
				let params = {
					time:this.selectChinaLogisticsDate2
				}
				let res = await http.get('http://localhost:8089/chinaCompany/query',{params:params})
				// console.log(res.data.data)
				this.chinaCompanys = res.data.data
			},
			async getProvinceCompany(){
				//获取各省物流公司
				let params = {
					time:this.selectRightBottomProvineDate2,
					pro:this.provinceName
				}
				let res = await http.get('http://localhost:8089/provinceCompany/query',{params:params})
				this.provinceCompanys = res.data.data
			},
			async getStatusLogistics(){
				//获取各省各状态物流
				let params = {
					time:this.selectRightProvinceDate2,
					pro:this.provinceName
				}
				let res = await http.get('http://localhost:8089/statusLogistics/query',{params:params})
				this.statusLogisticses = res.data.data
			},
			async getProvinceSendOrDispatch(status){
				//获取省寄件或者派件数据
				let params = {
					time:this.selectProvinceDate2,
					pro:this.provinceName
				}
				if(status === '寄件'){
					//获取该省寄件数据
					let res = await http.get('http://localhost:8089/sendLogistics/query',{params:params})
					this.provinceSendOrDispatch = res.data.data
				}else if(status === '派件'){
					//获取该省派件数据
					let res = await http.get('http://localhost:8089/dispatchLogistics/query',{params:params})
					this.provinceSendOrDispatch = res.data.data
				}
				// console.log(this.provinceSendOrDispatch)
			},
			async getTransaction(status){
				//获取中间运输数据
				let params = {
					time:this.selectCenterDate2,
					pro:this.provinceName
				}
				if(status === '寄件'){
					//获取寄件运输物流
					let res = await http.get('http://localhost:8089/transaction/send',{params:params})
					this.transaction = res.data.data
					let data = this.objToArr(res.data.data)
					this.config={data:data,header: ['出发省份', '到达省份', '总量']}
				}else if(status === '派件'){
					//获取派件运输物流
					let res = await http.get('http://localhost:8089/transaction/dispatch',{params:params})
					this.transaction = res.data.data
					let data = this.objToArr(res.data.data)
					this.config={data:data,header: ['出发省份', '到达省份', '总量']}
				}
				// console.log(this.config)
			},
			objToArr(item){
				let result = []
				for(var i =0;i<item.length;i++){
					result[i]=[item[i].startPro,item[i].endPro,item[i].sums]
				}
				return result;
			},
			provinceStatusChange(data){
				//左下角省寄件派件状态改变触发事件
				// console.log(data)
				this.getProvinceSendOrDispatch(data)
			},
			centerStatusChange(data){
				//中间寄件派件状态改变事件
				this.getTransaction(data)
			},
			getTime(){
				var now = new Date();
				var year = now.getFullYear(); //得到年份
				var month = now.getMonth()+1;//得到月份
				var date = now.getDate();//得到日期
				var day = now.getDay();//得到周几
				var hour= now.getHours();//得到小时数
				var minute= now.getMinutes();//得到分钟数
				var second= now.getSeconds();//得到秒数
				this.time = hour+':'+minute+':'+second
				this.date = year+"-"+month+'-'+date
				this.week = '周'+day
			},
			filterDate(time) {
			  const date = new Date(time)
			  const Y = date.getFullYear()
			  const M = date.getMonth() + 1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1 
			  const D = date.getDate()
			  return `${Y}-${M}-${D}`
			},
			filterMonth(time){
				const date = new Date(time)
				const Y = date.getFullYear()
				const M = date.getMonth() + 1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1 
				return `${Y}-${M}`
			},
			filterYear(time){
				const date = new Date(time)
				const Y = date.getFullYear()
				return `${Y}`
			}
		},
		created() {
			this.getChinaAllLogistics();
			this.getProvinceLogistics();
			this.getChinaCompany()
			this.getProvinceCompany()
			this.getStatusLogistics()
			this.getProvinceSendOrDispatch(this.provinceStatus)
			this.getTransaction(this.centerStatus)
			setInterval(this.getTime,1000)
		},
		watch:{
			selectCenterDate(newVal,oldVal){
				if(this.centerDate === 'year'){
					let res = this.filterYear(newVal)
					// console.log(res)
					this.selectCenterDate2 = res;
				}else if(this.centerDate === 'month'){
					let res = this.filterMonth(newVal)
					// console.log(res)
					this.selectCenterDate2 = res;
				}else if(this.centerDate === 'date'){
					let res = this.filterDate(newVal)
					// console.log(res)
					this.selectCenterDate2 = res;
				}
				this.getChinaAllLogistics();
				this.getProvinceLogistics();
				this.getTransaction(this.centerStatus)
			},
			selectChinaLogisticsDate(newVal,oldVal){
				if(this.chinaLogisticsDate === 'year'){
					let res = this.filterYear(newVal)
					// console.log(res)
					this.selectChinaLogisticsDate2 = res;
				}else if(this.chinaLogisticsDate === 'month'){
					let res = this.filterMonth(newVal)
					// console.log(res)
					this.selectChinaLogisticsDate2 = res;
				}else if(this.chinaLogisticsDate === 'date'){
					let res = this.filterDate(newVal)
					// console.log(res)
					this.selectChinaLogisticsDate2 = res;
				}
				this.getChinaCompany()
			},
			selectRightBottomProvineDate(newVal,oldVal){
				if(this.rightBottomProvineDate === 'year'){
					let res = this.filterYear(newVal)
					// console.log(res)
					this.selectRightBottomProvineDate2 = res;
				}else if(this.rightBottomProvineDate === 'month'){
					let res = this.filterMonth(newVal)
					// console.log(res)
					this.selectRightBottomProvineDate2 = res;
				}else if(this.rightBottomProvineDate === 'date'){
					let res = this.filterDate(newVal)
					// console.log(res)
					this.selectRightBottomProvineDate2 = res;
				}
				this.getProvinceCompany()
			},
			selectRightProvinceDate(newVal,oldVal){
				if(this.rightProvinceDate === 'year'){
					let res = this.filterYear(newVal)
					// console.log(res)
					this.selectRightProvinceDate2 = res;
				}else if(this.rightProvinceDate === 'month'){
					let res = this.filterMonth(newVal)
					// console.log(res)
					this.selectRightProvinceDate2 = res;
				}else if(this.rightProvinceDate === 'date'){
					let res = this.filterDate(newVal)
					// console.log(res)
					this.selectRightProvinceDate2 = res;
				}
				this.getStatusLogistics()
			},
			selectProvinceDate(newVal,oldVal){
				if(this.provinceDate === 'year'){
					let res = this.filterYear(newVal)
					// console.log(res)
					this.selectProvinceDate2 = res;
				}else if(this.provinceDate === 'month'){
					let res = this.filterMonth(newVal)
					// console.log(res)
					this.selectProvinceDate2 = res;
				}else if(this.provinceDate === 'date'){
					let res = this.filterDate(newVal)
					// console.log(res)
					this.selectProvinceDate2 = res;
				}
				this.getProvinceSendOrDispatch(this.provinceStatus);
			}
		}
	}
</script>

<style>
	.index{
		width: 100%;
		height: 100vh;
		background-image:  url(../assets/index_bg.png);
	}
	.index .left{
		width: 25%;
		height: 100vh;
		float: left;
	}
	.index .center{
		width: 50%;
		height: 100vh;
		float: left;
	}
	.index .right{
		width: 25%;
		height: 100vh;
		float: left;
	}
	.index .left .leftTop{
		width: 100%;
		height: 20vh;
		margin-left: 5%;
	}
	.index .left .leftTop .leftTopInfo{
		width: 88%;
		height: 100%;
		background-image: url(../assets/border_bg01.png);
		background-size: 100% 100%;
		background-repeat: no-repeat;
	}
	.index .left .leftTop .leftTopInfo .title{
		background-image: url(../assets/title_bg01.png);
		background-repeat: no-repeat;
		background-size: 100% 100%;
		width: 50%;
		height: 15%;
		margin-left: 25%;
	}
	.index .left .leftCenter{
		width: 100%;
		height: 40vh;
	}
	.index .left .leftCenter .leftCenterTitle{
		width: 100%;
		/* height: 10%; */
		background-image: url(../assets/box_title.png);
		line-height: 28px;
	}
	.index .left .leftCenter .chinaCompany{
		width: 100%;
		height: 90%;
	}
	.index .left .leftBottom{
		width: 100%;
		height: 40vh;
	}
	.index .left .leftBottom .leftBottomTitle{
		width: 100%;
		/* height: 10%; */
		background-image: url(../assets/box_title.png);
		line-height: 28px;
	}
	.index .left .leftBottom .province{
		width: 100%;
		height: 90%;
	}
	.index .center .centerTop{
		width: 100%;
		height: 40vh;
	}
	.index .center .china{
		width: 100%;
		height: 60vh;
	}
	.index .right .rightTop{
		width: 100%;
		height: 40vh;
	}
	.index .right .rightTop .rightTopTitle{
		margin-top: 5%;
		width: 100%;
		/* height: 10%; */
		background-image: url(../assets/box_title.png);
		line-height: 28px;
	}
	.index .right .rightCenter{
		width: 100%;
		height: 15vh;
		background-image: url(../assets/border_bg01.png);
		background-size: 100% 100%;
		background-repeat: no-repeat;
	}
	.index .right .rightCenter .title{
		background-image: url(../assets/title_bg01.png);
		background-repeat: no-repeat;
		background-size: 100% 100%;
		width: 50%;
		height: 15%;
		margin-left: 25%;
	}
	.index .right .rightBottom{
		width: 100%;
		height: 40vh;
		margin-top: 5%;
	}
	.index .right .rightBottom .rightBottomTitle{
		width: 100%;
		/* height: 10%; */
		background-image: url(../assets/box_title.png);
		line-height: 28px;
	}
	.index .right .rightBottom .provinceCompany{
		width: 100%;
		height: 88%;
	}
	.time{
		width: 100%;
		height: 14vh;
		border: solid #0595EB;
	}
</style>
