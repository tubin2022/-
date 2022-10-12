<template>
	<div class="sendLogistics">
		<div class="navBar">
			<van-nav-bar title="寄快递" left-text="返回" left-arrow @click-left="onClickLeft" />
		</div>
		<div style="height: 10px;"></div>
		<div class="info1">
			<div style="height: 10px;"></div>
			<div class="address">
				<van-cell title="寄件人信息">
					<template #icon>
						<van-icon name="http://localhost:8088/icon/send.png" size="25" />
					</template>
					<!-- 使用 right-icon 插槽来自定义右侧图标 -->
					<template #right-icon>
						<van-icon name="edit" class="search-icon" size="25" @click="editSendAddress" />
					</template>
					<template #label>
						{{sendName}}&nbsp;&nbsp;{{sendPhone}} <br />
						{{sendAddress.locationName}}
					</template>
				</van-cell>
				<van-cell title="收件人信息">
					<template #icon>
						<van-icon name="http://localhost:8088/icon/receive.png" size="25" />
					</template>
					<!-- 使用 right-icon 插槽来自定义右侧图标 -->
					<template #right-icon>
						<van-icon name="edit" class="search-icon" size="25" @click="editReceiveAddress" />
					</template>
					<template #label>
						{{receiveName}}&nbsp;&nbsp;{{receivePhone}} <br />
						{{receiveAddress.locationName}}
					</template>
				</van-cell>
			</div>
			<div style="height: 10px;"></div>
		</div>
		<div style="height: 10px;"></div>
		<div class="info2">
			<div style="height: 10px;"></div>
			<div class="goodsinfo">
				<van-cell value="请完善货物信息" is-link @click="editLogisticsInfo">
					<!-- 使用 title 插槽来自定义标题 -->
					<template #title>
						<span class="custom-title">货物信息</span>
						<van-tag type="primary">必填</van-tag>
					</template>
					<template #label>
						<span v-if="type != ''">快件类型:{{type}}</span><br />
						<span v-if="weight != ''">重量:{{weight}}</span><br />
						<span v-if="company != ''">物流公司:{{company}}</span>
					</template>
				</van-cell>
				<van-cell is-link @click="editPrice">
					<template #default>
						<span v-if="price != ''" style="color: orange;">￥{{price}}</span>
					</template>
					<!-- 使用 title 插槽来自定义标题 -->
					<template #title>
						<span class="custom-title">价格</span>
						<van-tag type="danger">推荐支付宝支付</van-tag>
					</template>
				</van-cell>
			</div>
			<div style="height: 10px;"></div>
		</div>
		<div style="height: 10px;"></div>
		<div class="info3">
			<div style="height: 10px;"></div>
			<div class="part1">
				<van-row>
					<van-col span="4">
						<span style="font-size: 15px;color: black;">保价</span>
					</van-col>
					<van-col span="10"></van-col>
					<van-col span="10">
						<span style="font-size: 10px;color: #969799;">未保价物品最高赔付3倍运费</span>
					</van-col>
				</van-row>
			</div>
			<div class="part2">
				<van-checkbox-group v-model="result">
					<van-cell-group>
						<van-cell v-for="(item, index) in list" clickable :key="item" :title="`${item}`"
							@click="toggle(index)">
							<template #right-icon>
								<van-checkbox :name="item" ref="checkboxes" />
							</template>
						</van-cell>
					</van-cell-group>
				</van-checkbox-group>
			</div>
			<div style="height: 10px;"></div>
		</div>
		<div style="height: 10px;"></div>
		<div class="agree">
			<van-checkbox v-model="checked">
				<span style="font-size: smaller;color: #969799;">我已阅读并同意遵守
					<span style="color: black;">《电子运单服务协议》</span></span>
			</van-checkbox>
		</div>
		<div class="submit">
			<van-row>
				<van-col span="24">
					<van-button 
					 type="info" round @click="sendLogistics">
						立即下单
					</van-button>
				</van-col>
			</van-row>
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
		<!-- 信息动作面板 -->
		<van-action-sheet v-model="actionShow1" title="寄件人信息">
			<div class="content">
				<van-cell-group>
					<van-form>
						<van-field v-model="sendName" label="姓名" left-icon="user-o" placeholder="寄件人姓名" required />
						<van-field v-model="sendPhone" clearable label="手机号码" left-icon="phone-o" required
							placeholder="寄件人手机号码"
							:rules="[{ pattern:/^1[0-9]{10}$/, message: '手机号码格式错误',trigger :'onBlur'  }]" />
						<van-field :value="sendAddress.locationName" clearable label="寄件地址" left-icon="location-o"
							placeholder="寄件人地址" @focus="focus" required />
					</van-form>
				</van-cell-group>
			</div>
		</van-action-sheet>
		<van-action-sheet v-model="actionShow2" title="收件人信息">
			<div class="content">
				<van-cell-group>
					<van-form>
						<van-field v-model="receiveName" label="姓名" left-icon="user-o" placeholder="收件人姓名" required />
						<van-field v-model="receivePhone" clearable label="手机号码" left-icon="phone-o" required
							placeholder="收件人手机号码"
							:rules="[{pattern:/^1[0-9]{10}$/, message: '手机号码格式错误',trigger :'onBlur' }]" />
						<van-field :value="receiveAddress.locationName" clearable label="收件地址" left-icon="location-o"
							placeholder="收件人地址" @focus="focus" required />
					</van-form>
				</van-cell-group>
			</div>
		</van-action-sheet>
		<van-action-sheet v-model="actionShow3" title="快件信息">
			<div class="content">
				<van-cell-group>
					<van-field v-model="type" label="类型" left-icon="apps-o" placeholder="快件类型" />
					<van-field v-model="weight" clearable label="快件重量" left-icon="logistics" placeholder="快件重量" />
					<van-field v-model="company" clearable label="物流公司" left-icon="wap-home-o" placeholder="物流公司" />
				</van-cell-group>
			</div>
		</van-action-sheet>
		<van-popup v-model="actionShow4">
			<van-field v-model="price" type="number" label="价格" />
		</van-popup>
	</div>
</template>

<script>
import {Toast} from 'vant';
import aliMap from './aliMap.vue'
import {get,post_json,post} from '../../http/axios'
	export default {
		components: {
			aliMap
		},
		data() {
			return {
				checked: false,
				list: ['是否需要电话联系', '是否购买运费险', '是否需要保价'],
				result: [],
				show: false, //是否展示遮罩层1（寄件）
				sendOrReceive: true, //当前编辑的是寄件人还是收件人信息（0代表寄件人，1代表收件人）
				actionShow1: false, //是否展示动作面板1（寄件）
				actionShow2: false, //是否展示动作面板2 (收件)
				actionShow3: false, //是否展示动作面板3 （物流信息）
				actionShow4: false, //是否展示动作面板4 （快件价格）
				sendAddress: {}, //寄件地址信息
				receiveAddress: {}, //收件地址信息
				sendName: '', //寄件人姓名
				sendPhone: '', //寄件人手机
				receiveName: '', //收件人姓名
				receivePhone: '', //收件人手机
				type: '',
				weight: '',
				company: '',
				price: '',
				userId: '',
				triggerType:''
			};
		},
		created() {
			//生命周期钩子函数
			this.getUserInfo()
		},
		methods: {
			onClickLeft() { //返回上一页
				this.$router.go(-1)
			},
			toggle(index) {
				this.$refs.checkboxes[index].toggle();
			},
			editSendAddress() { //编辑寄件人信息
				// this.show = true
				this.sendOrReceive = true
				this.actionShow1 = true;
				this.actionShow2 = false;
			},
			eventData(data) { //地图定位子组件传值
				// console.log(data)
				if (this.sendOrReceive) {
					// console.log(1) 
					this.sendAddress = data;
					// console.log(this.sendAddress)
				} else {
					// console.log(2)
					this.receiveAddress = data
					// console.log(this.receiveAddress)
				}
				// console.log(this.sendAddress)
			},
			focus() { //编辑寄件地址，打开地图进行定位选择
				this.actionShow1 = false;
				this.actionShow2 = false
				this.show = true
			},
			editReceiveAddress() { //编辑收货人信息
				// this.show = true
				this.sendOrReceive = false;
				this.actionShow2 = true;
				this.actionShow1 = false;
			},
			async getUserInfo() {
				//获取用户信息
				let token = localStorage.getItem('token')
				let params = {
					userToken: token
				}
				let userInfo = await get('/user/userInfo', params);
				this.sendName = userInfo.data.data.userName
				this.sendPhone = userInfo.data.data.userPhone
				this.userId = userInfo.data.data.userId
			},
			shutdownOverlay() {
				//关闭遮罩层
				this.show = false;
			},
			editLogisticsInfo() {
				//编辑物流信息
				this.actionShow3 = true
			},
			editPrice() {
				//输入价格
				this.actionShow4 = true
			},
			async sendLogistics() {
				let sendLocation = [this.sendAddress.longitude, this.sendAddress.latitude]
				let receiveLocation = [this.receiveAddress.longitude, this.receiveAddress.latitude]
				//寄件
				let params = {
					cost: this.price,
					dispatchCompany: this.company,
					logisticsType: this.type,
					receiveUserName: this.receiveName,
					receiveAddress: receiveLocation,
					receiveUserPhone: this.receivePhone,
					sendAddress: sendLocation,
					sendUserId: this.userId,
					weight: this.weight
				}
				let success = true
				for (let key in params) {
					if (!params[key]) {
						success = false
					}
				}
				if (this.checked && success) {
					let result = await post_json('/logistics/send', params)
					if (result.data.code === 1000) {
						Toast('寄件成功')
						this.triggerType = 'click'
						//埋点数据
						let track = {
							sendProvince:this.sendAddress.province,
							sendCity:this.sendAddress.city,
							receiveProvince:this.receiveAddress.province,
							receiveCity:this.receiveAddress.city,
							sendTime:new Date().getTime(),
							company:this.company,
							logisticsStatus:1
						}
						console.log(track)
						post('/track/saveTrack',track)
						
						this.$router.push({
							path: '/index/send'
						})
					} else {
						Toast('服务器错误')
					}
					// console.log(result)
				}
				if (!this.checked) {
					Toast('请阅读并同意相关协议')
				}
				if (!success) {
					Toast('请完善相关物流信息')
				}
				console.log(JSON.stringify(params))
				// console.log(success)
			}
		},

	};
</script>

<style>
	.sendLogistics {
		background-color: #e5e5e5;
		width: 100%;
		height: 100vh;
	}

	.sendLogistics .info1 {
		width: 100%;
		background-color: #FFFFFF;
		border-radius: 15px;
	}

	.sendLogistics .info1 .address {
		text-align: left;
		width: 98%;
		margin: 0 auto;
	}

	.sendLogistics .info2 {
		width: 100%;
		background-color: #FFFFFF;
		border-radius: 15px;
	}

	.sendLogistics .info2 .goodsinfo {
		text-align: left;
		width: 98%;
		margin: 0 auto;
	}

	.sendLogistics .info3 {
		width: 100%;
		background-color: #FFFFFF;
		border-radius: 15px;
	}

	.sendLogistics .info3 .part1 {
		text-align: center;
	}

	.sendLogistics .info3 .part2 {
		text-align: left;
	}

	.sendLogistics .submit {
		width: 100%;
		text-align: right;
	}

	.wrapper {
		display: flex;
		align-items: center;
		justify-content: center;
		height: 100%;
	}

	.block {
		width: 100vh;
		height: 90vh;
	}
</style>
