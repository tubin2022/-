<template>
	<div class="me">
	    <!-- 顶部区域 -->
	    <div class="navBar">
	      <div class="show">
	        <van-row type="flex" justify="center" gutter="15">
	          <!-- 左部用户头像 -->
	          <van-col span="6">
	            <div class="userface">
	              <van-image
	                show-error
	                show-loading
	                radius="5"
	                width="100"
	                height="100"
	                :src="userface"
					@click="previewImg"
	              />
	            </div>
	          </van-col>
	          <!-- 右部展示个人信息 -->
	          <van-col span="14">
	            <div class="userInfo" @click="userInformation">
	              <van-cell-group :border="false">
	                <van-cell :title="userInfo.userName" :label="'登录号:'+ userInfo.userAccount">
	                  <template #right-icon>
	                    <van-icon name="qr-invalid" size="22px" />
	                  </template>
	                </van-cell>
	              </van-cell-group>
	            </div>
	          </van-col>
	        </van-row>
	      </div>
	    </div>
	    <div style="height: 20px"></div>
	    <!-- 下部功能区域 -->
	    <div class="service">
	      <div class="serve">
	        <van-cell title="我的退货" is-link @click="backLogistics">
	          <template #icon>
	            <van-icon name="share" size="20" />
	          </template>
	        </van-cell>
	        <van-cell title="我的会员" is-link>
	          <template #icon>
	            <van-icon name="vip-card" size="20" />
	          </template>
	        </van-cell>
	      </div>
	      <div style="height: 20px"></div>
	      <div class="other">
	        <van-cell title="我的订单" is-link>
	          <template #icon>
	            <van-icon name="balance-list" size="20" />
	          </template>
	        </van-cell>
	        <van-cell title="签收码" is-link>
	          <template #icon>
	            <van-icon name="enlarge" size="20" />
	          </template>
	        </van-cell>
	        <van-cell title="客户中心" is-link>
	          <template #icon>
	            <van-icon name="manager" size="20" />
	          </template>
	        </van-cell>
	        <van-cell title="地址蒲" is-link>
	          <template #icon>
	            <van-icon name="map-marked" size="20" />
	          </template>
	        </van-cell>
	        <van-cell title="客服" is-link>
	          <template #icon>
	            <van-icon name="service" size="20" />
	          </template>
	        </van-cell>
	      </div>
	      <div style="height: 20px"></div>
	      <div class="setting">
	        <van-cell title="设置" is-link>
	          <template #icon>
	            <van-icon name="setting" size="20" />
	          </template>
	        </van-cell>
	      </div>
	    </div>
	  </div>
</template>

<script>
import {get} from '../../http/axios'
import {Toast} from 'vant'
import { ImagePreview } from 'vant';

	export default {
	  data() {
	    return {
	      //用户名
	      username: "",
	      //密码
	      password: "",
		  userInfo:{},
		  userface:'https://img01.yzcdn.cn/vant/cat.jpeg'
	    };
	  },
	  created() {
	  	this.getUserInfo()
	  },
	  methods:{
		async getUserInfo(){
			let token = localStorage.getItem('token')
			let params = {
				userToken:token
			}
			let result = await get('/user/userInfo',params)
			if(result.data.code === 1000){
				this.userInfo = result.data.data
				this.userface = result.data.data.nowFace
			}else{
				Toast('网络质量差，请检查网络服务后再试')
			}
		},
	  // 点击头像,预览图片
		  previewImg() {
			ImagePreview({images:[this.userface],closeable: true,});
		  },
		  userInformation(){
			  //跳转到用户信息详情页面
			  this.$router.push({
				  path:'/userInfo',
				  query:{
					  userInfo:this.userInfo
				  }
			  })
		  },
		  backLogistics(){
			  this.$router.push({
				  path:'/backLogistics'
			  })
		  }
	  }
	}
		
</script>

<style>
	.me {
	  background-color: #e5e5e5;
	  width: 100%;
	  height: 100%;
	  position: fixed;
	}
	.me .navBar {
	  background-color: #ffff;
	  height: 180px;
	  width: 100%;
	  margin: 0 auto;
	  display: flex;
	  align-items: center;
	  justify-content: center;
	}
	.me .navBar .show {
	  width: 100%;
	}
	.me .service .serve {
	  background-color: #ffff;
	  width: 100%;
	  text-align: left;
	}
	.me .service .other {
	  background-color: #ffff;
	  width: 100%;
	  text-align: left;
	}
	.me .service .setting {
	  background-color: #ffff;
	  width: 100%;
	  text-align: left;
	}
</style>
