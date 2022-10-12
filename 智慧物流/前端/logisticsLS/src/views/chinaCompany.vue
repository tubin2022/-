<template>
	<div class="chinaCompany">
		<div id="company2">
			
		</div>
	</div>
</template>

<script>
import echarts from 'echarts'
	export default {
		props:['chinaCompanys'],
		name: "chinaCompany",
		data(){
			return {
				option:{},
			}
		},
		mounted() {
			this.$nextTick(() => {
			    this.setOption(this.chinaCompanys)
			    this.initMap()
			});
		},
		watch:{
			chinaCompanys(newVal,oldVal){
				// console.log(newVal)
				this.setOption(newVal);
				// console.log(this.option)
				this.initMap();
			}
		},
		methods:{
			setOption(companys){
				this.option = {
				 tooltip: {
				     trigger: 'item'
				   },
				   legend: {
				     origin:'vertical',
				     top:'30%',
				     left:'80%',
					 textStyle:{
						 color:'white'
					 }
				   },
				   series: [
				     {
				       name: 'Access From',
				       type: 'pie',
				       radius: ['40%', '70%'],
				       avoidLabelOverlap: false,
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
				         borderRadius: 10,
				         borderColor: '#142031',
				         borderWidth: 2
				       },
				       label: {
						   color:'white'
				       },
				       emphasis: {
				         label: {
				           show: true,
				           fontSize: '38',
				           fontWeight: 'bold',
						   color:'white'
				         }
				       },
				       labelLine: {
				         show: true
				       },
				       data: companys
				     }
				   ]
				}
			},
			initMap(){
				let chartDom = document.getElementById('company2');
				let myChart = echarts.init(chartDom);
				myChart.setOption(this.option);
			}
		}
	}
</script>

<style>
	.chinaCompany{
		width: 100%;
		height: 100%;
	}
	.chinaCompany #company2{
		width: 90%;
		height: 100%;
	}
</style>
