<template>
	<div class="contain">
		<el-amap-search-box class="search-box" :search-option="searchOption" :on-search-result="onSearchResult"
			autofocus>
		</el-amap-search-box>
		<el-amap ref="map" vid="amapDemo" :center="center" :zoom="zoom" class="amap-demo">
			<el-amap-marker :position="center" :key="1"></el-amap-marker>
		</el-amap>
		<!-- <div v-if="searchResult">
      搜索:{{content}},详细地址为:{{searchResult.locationName}},经度:{{searchResult.longitude}},纬度:{{searchResult.latitude}}
    </div> -->
	</div>
</template>

<script>
	export default {
		name: 'AMapDemo',
		data() {
			return {
				zoom: 12,
				center: [121.5273285, 31.21515044],
				searchOption: {
					// 限制搜索城市的范围
					citylimit: false
				},
				content: '',
				inputValue: '',
				searchResult: {
					address: '',
					latitude: '',
					longitude: '',
					name: '',
					type: '',
					country: '',
					province: '',
					city: '',
					area: '',
					township: '',
					street: '',
					neighborhood: '',
					locationName: ''
				},
			}
		},
		methods: {
			onSearchResult(pois) {
				//搜索
				// console.log(pois)
				let latSum = 0
				let lngSum = 0
				let that = this
				let newObj = {}
				let value
				if (pois && pois.length > 0) {
					//如果长度为1则无需转化
					let poi = pois[0]
					let lng = poi["lng"]
					let lat = poi["lat"]
					that.center = [lng, lat]
					that.zoom = 13
					that.content = poi.name
					// console.log(poi.name)
					that.searchResult.address = poi.address
					that.searchResult.latitude = poi.lat
					that.searchResult.longitude = poi.lng
					let geocoder = new AMap.Geocoder({})
					geocoder.getAddress(that.center, function(status, result) {
						// console.log(result) 
						if (status === 'complete' && result.info === 'OK') {
							let obj = result.regeocode.addressComponent
							that.searchResult.locationName = obj.province + obj.city + obj.district + obj
								.township + obj.street +
								poi.address
							that.$emit("address", {
								locationName: obj.province + obj.city + obj.district + obj
									.township + obj.street +
									poi.address,
							longitude:  poi.lng,latitude:poi.lat,province:obj.province,city:obj.city})
						}

					});
					// console.log(a)
					// console.log(that.name)

				} else {
					that.searchResult = []
				}
			},
			onClickLeft() {
				this.$router.go(-1)
			}
		}
	}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
	.amap-demo {
		width: 100%;
		height: 550px;
		position: relative;
	}

	.search-box {
		height: 35px;
		margin: 10px auto;
		width: calc(100% - 20px);
		// border-radius:16px;
		box-shadow: none;
		background: #ffff;
		border: 1px solid #e6e6e6;

		.search-box-wrapper {
			input {
				background: #fff;
				padding-left: 20px;
			}

			.search-btn {
				color: #2A67FE;
				width: 90px;
				height: 20px;
				box-sizing: border-box;
				border-left: 1px solid #D7D7D7;
			}
		}
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
