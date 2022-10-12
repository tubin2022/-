<template>
	<div class="provinceCompany">
		<div id="company">
			
		</div>
	</div>
</template>

<script>
import echarts from 'echarts'
	export default {
		props:['provinceCompanys'],
		name: "provinceCompany",
		data(){
			return {
				option:{}
			}
		},
		mounted() {
			this.$nextTick(() => {
			    this.setOption()
			    this.initMap()
			});
		},
		watch:{
			provinceCompanys(newVal,oldVal){
				this.setOption(newVal)
				this.initMap()
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
				     top:'25%',
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
				           fontSize: '28',
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
				let chartDom = document.getElementById('company');
				let myChart = echarts.init(chartDom);
				myChart.setOption(this.option);
			}
		}
	}
</script>

<style>
	.provinceCompany{
		width: 100%;
		height: 100%;
	}
	.provinceCompany #company{
		width: 80%;
		height: 100%;
	}
</style>
