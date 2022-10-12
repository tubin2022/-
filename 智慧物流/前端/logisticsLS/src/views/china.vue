<template>
    <div id="china_map_box">
        <div class="echarts">
            <div id="map"></div>
        </div>
    </div>
</template>

<script>
import {getChinaJson,getProvinceJSON} from '../api/get-json'
import {cityProvincesMap} from '../config/cityProvincesMap'
import {mapOption} from '../config/mapOption'
import {provinceGeo} from '../config/province.js'
import {provinceCode} from '../config/provinceCode.js'
import echarts from 'echarts'

    export default {
		props:['transaction'],
        name: "china",
        components: {},
        data() {
            return {
                chinaChart: null, // 实例化echarts
                mapData: [], // 当前地图上的地区
                option: {...mapOption.basicOption}, // map的相关配置
                areaName: '中国', // 当前地名
                areaCode: '000000', // 当前行政区划
                areaLevel: 'country', // 当前级别
				series:[],
				provinceGeo:provinceGeo,
				color:'yellow',//飞线颜色（派件黄色，寄件绿色）
				provinceCode:provinceCode,
				status:''
            }
        },
        mounted() {
            this.$nextTick(() => {
                this.initEcharts();
                this.chinaChart.on('click', this.echartsMapClick);
				let data = this.getData(this.transaction)
				this.getSeries(data,this.status)
				// console.log(this.provinceGeo)
            });
        },
		watch:{
			transaction(newVal,oldVal){
				let data = this.getData(newVal)
				if(data[0][0].coord === data[1][0].coord){
					this.status = '寄件'
				}else{
					this.status = '派件'
				}
				// console.log(data)
				this.getSeries(data,this.status)
				this.initEcharts()
				// console.log(this.series)
				console.log(this.series)
			}
		},
        methods: {
			getData(item){
				//获取飞线数据
				let result = []
				for(var i =0;i<item.length;i++){
					let startPro = item[i].startPro
					let endPro = item[i].endPro
					result[i] = [{coord: provinceGeo[startPro]},{coord:provinceGeo[endPro]}]
				}
				return result
			},
            // 初次加载绘制地图
            initEcharts() {
                //地图容器
                this.chinaChart = echarts.init(document.getElementById('map'));
                this.requestGetChinaJson();
            },
            // 地图点击
            echartsMapClick(params) {
                console.log(params);
				// console.log(this.provinceCode)
				let provinceName = params.name
				let provinceCode = this.provinceCode[provinceName]
				// console.log(provinceName)
				// console.log(provinceCode)
				this.$emit('province',{provinceName:provinceName,provinceCode:provinceCode})
    //             let areaName = params.data.areaName;
				// let areaCode = params.data.areaCode
				// let areaLevel = params.data.areaLevel
				// console.log(areaName)
				// console.log(areaCode)
				// console.log(areaLevel)
            },
            //绘制全国地图
            requestGetChinaJson() {
                getChinaJson().then(res => {
                    let arr = [];
                    for (let i = 0; i < res.features.length; i++) {
                        let obj = {
                            name: res.features[i].properties.name,
                            areaName: res.features[i].properties.name,
                            areaCode: res.features[i].id,
                            areaLevel: 'province',
                            value: Math.round(Math.random()),
                        };
                        arr.push(obj)
                    }
                    this.mapData = arr;
                    //注册地图
                    echarts.registerMap('china', res);
                    //绘制地图
                    this.renderMap('china', arr);
                });
            },
            renderMap(map, data) {
				let option = {
				    title: {
				        left: 'left',
				        textStyle: {
				            color: '#fff'
				        }
				    },
				    tooltip: {
				        trigger: 'item'
				    },
				    geo: {
				        map: 'china',
				        zoom: 1.2,
				        label: {
							 normal: {
							  show: true, //关闭省份名展示
							  fontSize: "10",
							  color: "white"
							},
				            emphasis: {
				                show: true,
								fontSize: "13",
								color: "red"
				            }
				        },
				        roam: false,//是否可以拖拽
				        itemStyle: {
				            normal: {
				                areaColor: 'rgba(128, 128, 128, 0)',
				                borderColor: '#0E94EB',
				                borderWidth: 1.0,
				                shadowBlur: 0,
				                shadowOffsetX: .5,
				                shadowOffsetY: 1,
				            },
				            emphasis: {
				                label: {
				                    show: true
				                },
				                areaColor: 'blue',
				            }
				        },
				    },
				    series: this.series
				};
                //渲染地图
				console.log(option)
                this.chinaChart.setOption(option);
            },
			getSeries(datas,status){
				this.series = [
					{
				        name: status,
				        type: 'lines',
				        zlevel: 1,
				        effect: {
				            show: true,
				            period: 2,//箭头指向速度，值越小速度越快
				            trailLength: 0.1,//特效尾迹长度[0,1]值越大，尾迹越长重
				            color: '#fff',
				            symbolSize: 1, //图标大小
				            borderColor: this.color,
				            borderWidth: 2,
				            shadowBlur: 2,
				            shadowColor: this.status === '寄件'?'yellow':'#6EFE7B',
				            shadowOffsetX: 0,
				            shadowOffsetY: 0,
							symbol: 'arrow', //箭头图标
				            delay: function(){
				                return Math.random()*5000
				            }
				        },
				        lineStyle: {
				            normal: {
				                color: this.status === '寄件'?'yellow':'#6EFE7B',
				                width: 1,
				                curveness: -0.2,
				            },
				        },
				        data: datas
				    }, {
				        name: status,
				        type: 'effectScatter',
				        coordinateSystem: 'geo',
				        zlevel: 2,
				        rippleEffect: {
				            brushType: 'fill'
				        },
				        label: {
				            normal: {
				                show: true,
				                position: 'right',
				                formatter: '{b}',
				                color: "#fff",
				                fontSize: 10
				            }
				        },
				        symbolSize: function(val) {
				            return val[2] / 8;
				        },
				        itemStyle: {
				            normal: {
				                color: this.status === '寄件'?'yellow':'#6EFE7B'
				            }
				        }
				},
			]
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
            width: 0;
            flex: 1;
            background-size: 100% 100%;
            #map {
                height: 100%;
            }
        }
        .back {
            position: absolute;
            top: .8rem;
            right: .5rem;
            z-index: 999;
            padding-left: .12rem;
            padding-right: .12rem;

        }
    }

</style>

