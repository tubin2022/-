<template>
    <div id="china_map_box">
        <div class="echarts">
            <div id="map2"></div>
        </div>
    </div>
</template>

<script>
import {getChinaJson,getProvinceJSON} from '../api/get-json'
import {cityProvincesMap} from '../config/cityProvincesMap'
import {mapOption} from '../config/mapOption'
import echarts from 'echarts'


    export default {
		props:['province','provinceData'],
        name: "province",
        components: {},
        data() {
            return {
                provinceChart: null, // 实例化echarts
                mapData: [], // 当前地图上的地区
                option: {...mapOption.basicOption}, // map的相关配置
                areaName: '江西省', // 当前地名
                areaCode: '360000', // 当前行政区划
                areaLevel: 'province', // 当前级别
				logistics:[]
            }
        },
        mounted() {
            this.$nextTick(() => {
                this.initEcharts(this.provinceData);
				// console.log(this.logistics)
            });
        },
		watch:{
			province(newVal,oldVal){
				// console.log(newVal)
				// console.log(oldVal)
				this.areaName = newVal.provinceName
				this.areaCode = newVal.provinceCode
				this.requestGetProvinceJSON(this.areaCode,this.areaName,this.provinceData)
			},
			provinceData(newVal,oldVal){
				console.log(newVal)
				// this.logistics = newVal
				this.initEcharts(newVal)
			}
		},
        methods: {
            // 初次加载绘制地图
            initEcharts(pdatas) {
                //地图容器
                this.provinceChart = echarts.init(document.getElementById('map2'));
                this.requestGetProvinceJSON(this.areaCode,this.areaName,pdatas);
            },
            // 加载省级地图
            requestGetProvinceJSON(areaCode,areaName,pdatas) {
                getProvinceJSON(areaCode).then(res => {
                    echarts.registerMap(areaName, res);
                    let arr = [];
                    for (let i = 0; i < res.features.length; i++) {
                        let obj = {
                            name: res.features[i].properties.name,
                            areaName: res.features[i].properties.name,
                            areaCode: res.features[i].id,
                            areaLevel: 'city',
                            value: Math.round(Math.random()),
                        };
                        arr.push(obj)
                    }
                    this.mapData = arr;
                    this.renderMap(areaName, arr,pdatas);
                });
            },
            renderMap(map,data,pdatas) {
                // this.option.series = [
                //     {
                //         name: map,
                //         mapType: map,
                //         ...mapOption.seriesOption,
                //         data: data
                //     }
                // ];
				this.option = {
					tooltip: {
					    trigger: 'item',
					    // formatter: '{b}:20'
						formatter:function (params,ticket,callback){
							// console.log(params.name)
							for(var i =0;i<pdatas.length;i++){
								if(pdatas[i].city === params.name){
									return params.name+pdatas[i].sums
								}
							}
						}
					},
					geo: {
					    top:100
					    // layoutCenter: ['30%', '30%'],
					    // // 如果宽高比大于 1 则宽度为 100，如果小于 1 则高度为 100，保证了不超过 100x100 的区域
					    // layoutSize: 100
					    // 设置这两个值后 left/right/top/bottom/width/height 无效。
					},
					series:[
						{
						name:map,
						mapType:map,
						data: data,
						type: 'map',
						roam: false,
						nameMap:{
						    'china':'中国'
						},
						label: {
						    normal:{
						        show:false,
						        textStyle:{
						            color:'#fff',
						        }
						    },
						    emphasis: {
						        show: true,
						        textStyle:{
						            color:'#fff',
						        }
						    }
						},
						itemStyle: {
						    normal: {
						        color: '#fff',
						        areaColor: '#6ED8FE',
						        borderColor: '#0E94EB',
						        borderWidth: 0.5,
						        label: {
						            show: false,
						            textStyle: {
						                color: "rgb(249, 249, 249)",
						            }
						        },
						    },
						    emphasis: {
						        areaColor: 'green',
						    }
						},
					  }
					]
				}
				// console.log(this.option)
                //渲染地图
                this.provinceChart.setOption(this.option);
            }
        }
    }

</script>

<style lang="scss" scoped>
    #china_map_box {
        display: flex;
        width: 100%;
        height: 100%;
        position: relative;
        .echarts {
            width: 1;
            flex: 1;
            background-size: 100% 100%;
			
            #map2 {
				width: 100%;
                height: 100%;
            }
        }
        .back {
            position: absolute;
            top: .10rem;
            right: .5rem;
            z-index: 999;
            padding-left: .12rem;
            padding-right: .12rem;

        }
    }
</style>

