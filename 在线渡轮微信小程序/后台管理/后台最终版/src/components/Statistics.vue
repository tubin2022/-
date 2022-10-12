<template>
	<div class="statistics" style="width: 100%;height: 966px;">
		
	
		<el-container >
			<el-header class="top">
			统计分析
			</el-header>
			<el-main class="statisticsAll">
			<div class="center" align="center" style="height: 100%;margin: auto;">
		<!-- 日期选择 -->
		<div id="selectDate">
			<span style="color: #9bc9c9;">
				请选择查看日期
			</span><br>
			<!-- <input type="date" v-model="date"/> -->
			<el-date-picker
				v-model="date"
			      type="date"
				  value-format="yyyy-MM-dd">
			</el-date-picker>&nbsp;
			<el-button type="primary" @click="cinfirm()">确认</el-button>
		</div><br>
		<!-- 每天售票统计图容器 -->
		<!-- <el-tag type="info">每日票务售卖情况柱状图</el-tag> -->
		<el-row>
 			<el-col :span="12">
			 	<div class="grid-content bg-purple">
					<span style="font-weight:bold">每日票务售卖情况柱状图</span>
					<div id="statisticsTicket" style="width: 800px;height: 500px;"></div>
				</div>
			</el-col>
 			<el-col :span="9" :offset="3">
				<div class="grid-content bg-purple-light">
					<!-- 每天城市发出班次统计图容器 -->
					<div id="statisticsShift" style="width: 500px;height: 500px;"></div>
				</div>
			</el-col>
		</el-row>
		</div>	
			</el-main>
		</el-container>
	</div>
</template>

<script>
	export default {
		data() {
			return{
				//日期参数
				date:'',
				//每天售票统计数据，分别为路线，已售票数，未售票数（即剩余票数）
				line:[],
				saled:[],
				surplus:[],
				//每天城市发出班次统计数据
				shifts:[]
			}
		},
		
		methods:{
			//绘制每天售票情况统计图
			drawCharts(line,saled,surplus){
				let that = this;
				 // 基于准备好的dom，初始化echarts实例
				let myChart = this.$echarts.init(document.getElementById('statisticsTicket'));
				
				let  option = {
				    backgroundColor:'#f5fffe',
				    tooltip:{
				      trigger:'axis'  
				    },
				    legend: {
				        data: ['已售票数', '未售票数'],
				        textStyle:{
				            color:'#02c4ef'
				        }
				    },
				    grid: {
				        left: '3%',
				        right: '4%',
				        bottom: '3%',
				        containLabel: true
				    },
				    xAxis:  {
				        type: 'category',
				        data: line,
				        axisTick: { show: false },
				        axisLine: {
				            show: true,
				            lineStyle:{
				                color:'#58f1f3'
				            },
				        },
				        axisLabel: { 
				            show:true,
				            textStyle:{
				                color:'#215ae0',
				                fontSize:12,
				            },
				            //rotate:30
				        }
				    },
				    yAxis: {
				        type: 'value',
				        splitLine: {show: true,
				            lineStyle:{
				                color:'#a5ffff'
				            }
				        },
				        axisTick: { show: false },
				        axisLine: { 
				            show: true,
				            lineStyle:{
				                color:'#3b73f5'
				            }
				        },
				    },
				    series: [
				        {
				            name: '已售票数',
				            type: 'bar',
				            stack: '总量',
				            barWidth: 30,
				            itemStyle:{
				                normal: {
				                    color: '#89afff',
				                }
				            },
				            label: {
				                normal: {
				                    show: true,
				                    position:'inside'
				                }
				            },
				            z:  10,
				            data: saled
				        },
				        {
				            name: '未售票数',
				            type: 'bar',
				            stack: '总量',
				            itemStyle:{
				                normal: {
				                    color: '#8ed6f4'
				                }
				            },
				            label: {
				                normal: {
				                    show: true,
				                    position: 'inside',
				                    textStyle:{
				                        color:'#7375ff'
				                    }
				                }
				            },
				            z: 5,
				            data: surplus
				        },
				    ]
				};
				  
				  // if (myChart == null) {
				  //           myChart = this.$echarts.init(this.$refs[this.chartObj.type]);
				  //         }
				  //         myChart.setOption(option);
				if(option){
				myChart.setOption(option);
				}
			},
			//绘制每天城市发出班次统计图
			drawCharts2(shifts){
				// 基于准备好的dom，初始化echarts实例
				let myChart = this.$echarts.init(document.getElementById('statisticsShift'))
				let option = {
				  title: {
				    text: '每日城市发出班次分布图',
				    left: 'center'
				  },
				  tooltip: {
				    trigger: 'item'
				  },
				  legend: {
				    orient: 'vertical',
				    left: 'left'
				  },
				  series: [
				    {
				      name: 'Access From',
				      type: 'pie',
				      radius: '60%',
				      data:shifts,
				      emphasis: {
				        itemStyle: {
				          shadowBlur: 10,
				          shadowOffsetX: 0,
				          shadowColor: 'rgba(0, 0, 0, 0.5)'
				        }
				      }
				    }
				  ]
				};
					myChart.setOption(option);
			},
			//获取每天售票统计数据
			getData(){
				const that = this;
				this.$axios.get("http://localhost:8888/statistics/daySale?date="+that.date).then(res =>{
					that.line = res.data.data.line;
					that.saled = res.data.data.saled;
					that.surplus = res.data.data.surplus;
					that.drawCharts(that.line,that.saled,that.surplus)
				})
			},
			//获取每天城市发出班次统计数据
			getData2(){
				const that = this;
				this.$axios.get("http://localhost:8888/statistics/dayShift?date="+that.date).then(res =>{
					that.shifts = res.data.data;
					that.drawCharts2(that.shifts);
				})
			},
			//点击确认按钮，执行获取数据的方法，并绘制两个统计图
			cinfirm(){
				console.log(this.date)
				this.getData();
				this.getData2();
			},
			//获取当前时间并格式化
			dateFormat(){
				let date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
				if (month < 10) {
				    month = "0" + month;
				}
				if (day < 10) {
				    day = "0" + day;
				}
				let nowDate = year + "-" + month + "-" + day; 
				this.date = nowDate;
			}
		},
		//生命周期函数，默认刚进入页面加载今天的统计数据对应的统计图
		mounted(){
			this.dateFormat();
			this.getData();
			this.getData2();
			this.drawCharts(this.line,this.saled,this.surplus);
			this.drawCharts2(this.shifts);
		}
	}
</script>

<style>
	.top {
		/* margin-top: 10px; */
		margin: auto;
		font-size: 39px;
		color: rgb(151, 148, 253);
		font-weight: bold;
		text-align: center;
		height: 50px;
		/* background-color: rgb(253, 255, 254); */
	}
</style>
