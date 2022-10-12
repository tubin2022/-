<template>
	<div class="logisticsStatus">
		<div id="status">
			
		</div>
	</div>
</template>

<script>
import echarts from 'echarts'
	export default {
		props:['statusLogisticses'],
		name: "logisticsStatus",
		data(){
			return {
				option:{}
			}
		},
		mounted() {
			this.setOption(this.statusLogisticses)
			this.initMap()
		},
		watch:{
			statusLogisticses(newVal,oldVal){
				this.setOption(newVal);
				this.initMap()
			}
		},
		methods:{
			setOption(datas){
				this.option = {
					tooltip: {
					    trigger: 'item'
					  },
					// color:'#142437',
				  xAxis: {
				    type: 'category',
				    data: datas[0],
					axisLabel:{
						textStyle:{
							color:'white'
						}
					}
				  },
				  yAxis: {
				    type: 'value',
					axisLabel:{
						textStyle:{
							color:'white'
						}
					}
				  },
				  series: [
				    {
				      data: datas[1],
				      type: 'bar',
				      // showBackground: true,
				      // backgroundStyle: {
				      //   color: 'white'
				      // }
					  itemStyle: {
						 normal:{
							 color: function (colors) {
							 var colorList = [
							   '#fc8251',
							   '#5470c6',
							   '#91cd77',
							   '#ef6567',
							   '#f9c956',
							   '#75bedc'
							 ];
							 return colorList[colors.dataIndex];
						   }
						},
				    },
					}
				  ]
				}
			},
			initMap(){
				let chartDom = document.getElementById('status');
				let myChart = echarts.init(chartDom);
				myChart.setOption(this.option)
			}
		}
	}
</script>

<style>
	.logisticsStatus{
		width: 100%;
		height: 100%;
	}
	.logisticsStatus #status{
		width: 100%;
		height: 100%;
	}
</style>
